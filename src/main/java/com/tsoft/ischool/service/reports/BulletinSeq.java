/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service.reports;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.config.ApplicationProperties;
import com.tsoft.ischool.domain.ClasseEleve;
import com.tsoft.ischool.domain.enumeration.BulletinType;
import com.tsoft.ischool.repository.ClasseEleveRepository;
import com.tsoft.ischool.repository.NoteRepository;
import com.tsoft.ischool.service.AnneeService;
import com.tsoft.ischool.service.NoteService;
import com.tsoft.ischool.service.util.FileUtils;
import com.tsoft.ischool.web.rest.util.HeaderUtil;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
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
public class BulletinSeq {

    private final Logger log = LoggerFactory.getLogger(BulletinSeq.class);

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

    @Autowired
    NoteRepository noteRepo;
    @Autowired
    NoteService noteService;

    @GetMapping("/bulletin-seq/{numSeq}/{eleve}")
    @Timed
    public ResponseEntity<byte[]> printBulletinSeq(@PathVariable Long eleve, @PathVariable int numSeq) throws Exception {
        log.debug("REST request to print Bulletin Annuel Eleve : {}", eleve);
        ClasseEleve ei = eleveInscritRepository.findByAnneeAndEleveId(as.getAnneeCourante(), eleve);

        String reportfile = "";
        //remplissage des parametres du report
        Map params = new HashMap();
        params.put("numseq", numSeq);
        reportfile = "file:reports/BulletinSeq.jasper";

        //recuperation de la classe
        params.put("code_eleve", eleve);
        params.put("code_annee", as.getAnneeCourante().getId());
        params.put("code_classe", ei.getClasse().getId());
        params.put("effectif", eleveInscritRepository.countByAnneeAndClasse(as.getAnneeCourante(), ei.getClasse()));
        //information about school
        ApplicationProperties.Ecole ecole = app.getEcole();
        params.put("nom_ecole", ecole.getNom());
        params.put("slogan_ecole", ecole.getSlogan());
        params.put("adress_ecole", ecole.getBoitePostale() + " Tel:" + ecole.getTelephonePortable());
        params.put("logo_ecole", resourceLoader.getResource("file:reports/logo-ecole.png").getFile().getAbsolutePath());

        String destfile = FileUtils.getUploadedfile().getAbsolutePath() + File.separator + "Bulletin"
                + ("SEQ") + numSeq + ei.getEleve().getId() + ".pdf";
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

    @GetMapping("/bulletin-seq-classe/{numSeq}/{classe}")
    @Timed
    public ResponseEntity<byte[]> printBulletinSeqClasse(@PathVariable String classe, @PathVariable Integer numSeq) throws Exception {
        log.debug("REST request to print Bulletin SEq Classe : {}", classe);
        // List<ClasseEleve> eleves = eleveInscritRepository.findByClasseIdAndAnnee(classe, as.getAnneeCourante());
        List<Object[]> datas = noteRepo.retrieveNoteSeqWithCoef(classe, as.getAnneeCourante(), numSeq);
        Map<ClasseEleve, Double> eleveWithMoyenne = noteService.processNote(datas, BulletinType.SEQUENCE);
        
        DoubleSummaryStatistics profil_Classe = eleveWithMoyenne.entrySet().stream().map(map -> map.getValue()).collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::accept,
                DoubleSummaryStatistics::combine);

        String destfile = FileUtils.getUploadedfile().getAbsolutePath() + File.separator + "Bulletin"
                + ("SEQ") + classe + ".pdf";
        List<JasperPrint> jasperPrintList = new ArrayList<>();
        String reportfile = resourceLoader.getResource("file:reports/BulletinSeq.jasper").getFile().getAbsolutePath();

        //remplissage des parametres du report
        Map params = new HashMap();
        params.put("numseq", numSeq);
        params.put("code_annee", as.getAnneeCourante().getId());
        params.put("effectif", eleveWithMoyenne.keySet().size());
        params.put("code_classe", classe);

        //profil de la classe
        params.put("moy_dernier", profil_Classe.getMin());
        params.put("moy_premier", profil_Classe.getMax());
        params.put("moy_gen", profil_Classe.getAverage());
       // params.put("moy_nbre", );
        //information about school
        ApplicationProperties.Ecole ecole = app.getEcole();
        params.put("nom_ecole", ecole.getNom());
        params.put("slogan_ecole", ecole.getSlogan());
        params.put("adress_ecole", ecole.getBoitePostale() + " Tel:" + ecole.getTelephonePortable());
        params.put("logo_ecole", resourceLoader.getResource("file:reports/logo-ecole.png").getFile().getAbsolutePath());
        Connection connection = dataSource.getConnection();

        for (ClasseEleve ce : eleveWithMoyenne.keySet()) {
            log.debug("REST request to print Bulletin Annuel Eleve : {}", ce.getEleve());
            //recuperation de la classe
            params.put("code_eleve", ce.getEleve().getId());
       //     params.put("rang", );
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
