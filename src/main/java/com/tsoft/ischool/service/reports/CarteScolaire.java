package com.tsoft.ischool.service.reports;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.config.ApplicationProperties;
import com.tsoft.ischool.domain.ClasseEleve;
import com.tsoft.ischool.repository.ClasseEleveRepository;
import com.tsoft.ischool.service.AnneeService;
import com.tsoft.ischool.service.util.FileUtils;
import com.tsoft.ischool.web.rest.util.HeaderUtil;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tchipi
 */
@RestController
@RequestMapping("/api")
public class CarteScolaire {

    private final Logger log = LoggerFactory.getLogger(CarteScolaire.class);

    @Autowired
    AnneeService as;
    @Autowired
    ClasseEleveRepository eleveInscritRepository;

    @Autowired
    ApplicationProperties app;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    DataSource dataSource;

    @GetMapping(value = "/printCarteByEleve/{eleve}",
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    @Timed
    public ResponseEntity<byte[]> print(@PathVariable Long eleve) throws Exception {
        String reportfile = "";
        //remplissage des parametres du report
        Map params = new HashMap();
        reportfile = "file:reports/CarteScolaire.jasper";
        //recuperation de la classe
        params.put("code_eleve", eleve);
        params.put("code_annee", as.getAnneeCourante().getId());

        //information about school
        ApplicationProperties.Ecole ecole = app.getEcole();
        params.put("nom_ecole", ecole.getNom());
        params.put("slogan_ecole", ecole.getSlogan());
        params.put("adress_ecole", ecole.getBoitePostale() + " Tel:" + ecole.getTelephonePortable());
        params.put("logo_ecole", resourceLoader.getResource("file:reports/logo-ecole.png").getFile().getAbsolutePath());

        //determination du chemin des subreports
        params.put("SUBREPORT_DIR", resourceLoader.getResource(reportfile).getFile().getParent() + File.separator);
        params.put("cmr", resourceLoader.getResource("file:reports/Cameroun.jpg").getFile().getAbsolutePath());

        //  params.put("upload_dir", FileUtils.getUploadedDir());
        String destfile = FileUtils.getUploadedfile().getAbsolutePath() + File.separator + "CarteEleve"
                + eleve + ".pdf";
        //fill report
        JasperPrint jp = JasperFillManager.fillReport(
                resourceLoader.getResource(reportfile).getInputStream() //file jasper
                ,
                 params, //params report
                dataSource.getConnection());  //datasource

        JasperExportManager.exportReportToPdfFile(jp, destfile);

        Resource resource = resourceLoader.getResource("file:" + destfile);
        InputStream in = resource.getInputStream();
        try {
            return new ResponseEntity<>(IOUtils.toByteArray(in), HeaderUtil.downloadAlert(resource), HttpStatus.OK);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    @GetMapping(value = "/printCarte/{classe}",
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    @Timed
    public ResponseEntity<byte[]> print(@PathVariable String classe) throws Exception {
        log.debug("REST request to print Bulletin SEq Classe : {}", classe);
        List<ClasseEleve> eleves = eleveInscritRepository.findByClasseIdAndAnneeOrderByEleveNom(classe, as.getAnneeCourante());
        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        String destfile = uploadedfile.getAbsolutePath() + File.separator + "Carte"
                + classe + ".pdf";
        List<JasperPrint> jasperPrintList = new ArrayList<>();
        String reportfile = resourceLoader.getResource("file:reports/CarteScolaire.jasper").getFile().getAbsolutePath();
//remplissage des parametres du report
        Map params = new HashMap();
        params.put("code_annee", as.getAnneeCourante().getId());
        params.put("cmr", resourceLoader.getResource("file:reports/Cameroun.jpg").getFile().getAbsolutePath());
//information about school
        ApplicationProperties.Ecole ecole = app.getEcole();
        params.put("nom_ecole", ecole.getNom());
        params.put("slogan_ecole", ecole.getSlogan());
        params.put("adress_ecole", ecole.getBoitePostale() + " Tel:" + ecole.getTelephonePortable());
        params.put("logo_ecole", resourceLoader.getResource("file:reports/logo-ecole.png").getFile().getAbsolutePath());
        Connection connection = dataSource.getConnection();

        for (ClasseEleve ce : eleves) {
            log.debug("REST request to print Carte Eleve : {}", ce.getEleve());
            //recuperation de la classe
            params.put("code_eleve", ce.getEleve().getId());
            //fill report
            JasperPrint jp = JasperFillManager.fillReport(
                    reportfile,//file jasper
                    params, //params report
                    connection);  //datasource
            jasperPrintList.add(jp);
        }
// Generating report using List<JasperPrint>
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(destfile));
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        configuration.setCreatingBatchModeBookmarks(true);
        exporter.setConfiguration(configuration);
        exporter.exportReport();

        Resource resource = resourceLoader.getResource("file:" + destfile);
        InputStream in = resource.getInputStream();
        try {
            return new ResponseEntity<>(IOUtils.toByteArray(in), HeaderUtil.downloadAlert(resource), HttpStatus.OK);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
}
