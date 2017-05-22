/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.service;

import com.tsoft.appli.highschool.model.Classe;
import com.tsoft.appli.highschool.model.Ecole;
import com.tsoft.appli.highschool.model.Eleve;
import com.tsoft.appli.highschool.model.EleveInscrit;
import com.tsoft.appli.highschool.repository.ClasseRepository;
import com.tsoft.appli.highschool.repository.EcoleRepository;
import com.tsoft.appli.highschool.repository.EleveInscritRepository;
import com.tsoft.appli.highschool.repository.EleveRepository;
import com.tsoft.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tchipi
 */
@RestController
@RequestMapping("/app")
public class EleveResource {

    @Autowired
    EleveInscritRepository eleveInscritRepository;
    @Autowired
    EleveRepository eleveRepository;
    @Autowired
    ClasseRepository ClasseRepository;
    @Autowired
    AnneeService as;
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    DataSource dataSource;
    @Autowired
    EcoleRepository ecoleRepository;

    @RequestMapping(value = "/findEleveByClasse/{classe}", method = RequestMethod.GET)
    public List<EleveInscrit> findEleveByClasse(@PathVariable int classe) throws Exception {
        Classe c = ClasseRepository.findOne(classe);
        return eleveInscritRepository.findByAnneeAndClasseOrderByEleveNomprenomAsc(as.getAnneeCourante(), c);
    }

    @RequestMapping(value = "/findEleveByNom", method = RequestMethod.POST)
    public List<EleveInscrit> findEleveByName(@RequestBody String nomprenom) throws Exception {
        return eleveInscritRepository.findByAnneeAndEleveNomprenomContainingOrderByEleveNomprenomAsc(as.getAnneeCourante(), nomprenom);
    }

    @RequestMapping(value = "/printCarteByClasse/{classe}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> printCarteByClasse(@PathVariable int classe) throws Exception {
        Classe c = ClasseRepository.findOne(classe);
        String reportfile = "";
        //remplissage des parametres du report
        Map params = new HashMap();
        reportfile = "classpath:ischool/reports/CarteScolaireAll.jasper";
        //recuperation de la classe
        params.put("code_classe", c.getCode());
        params.put("annee", as.getAnneeCourante().getLibelle());
        params.put("code_annee", as.getAnneeCourante().getCode());
        //information about school
        if (ecoleRepository.findAll().isEmpty()) {
            throw new com.tsoft.exception.BusinessException("Paramètres de l'ecole non definis");
        }
        Ecole ecole = ecoleRepository.findAll().get(0);
        params.put("nom_ecole", ecole.getNom());
        params.put("slogan_ecole", ecole.getSlogan());
        params.put("adress_ecole", ecole.getBoitePostale() + " Tel:" + ecole.getTelephone());
        params.put("logo_ecole", ecole.getLogo());
//        
        //determination du chemin des subreports
        params.put("SUBREPORT_DIR", resourceLoader.getResource(reportfile).getFile().getParent() + File.separator);
        params.put("cmr", resourceLoader.getResource("classpath:ischool/reports/Cameroun.jpg").getFile().getAbsolutePath());

        params.put("upload_dir", FileUtils.getUploadedDir());
        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        String destfile = uploadedfile.getAbsolutePath() + File.separator + "Carte"
                + c.getLibelle() + ".pdf";
        //fill report
        JasperPrint jp = JasperFillManager.fillReport(
                resourceLoader.getResource(reportfile).getInputStream() //file jasper
                , params, //params report
                dataSource.getConnection()
        );  //datasource

        JasperExportManager.exportReportToPdfFile(jp, destfile);

        return download(destfile);
    }

    @RequestMapping(value = "/printCarteByEleve/{eleve}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> printCarteByEleve(@PathVariable int eleve) throws Exception {
        Eleve e = eleveRepository.findOne(eleve);
        String reportfile = "";
        //remplissage des parametres du report
        Map params = new HashMap();
        reportfile = "classpath:ischool/reports/CarteScolaire.jasper";
        //recuperation de la classe
        params.put("code_eleve", e.getCode());
        params.put("annee", as.getAnneeCourante().getLibelle());
        params.put("code_annee", as.getAnneeCourante().getCode());
        //information about school
        if (ecoleRepository.findAll().isEmpty()) {
            throw new com.tsoft.exception.BusinessException("Paramètres de l'ecole non definis");
        }
        Ecole ecole = ecoleRepository.findAll().get(0);
        params.put("nom_ecole", ecole.getNom());
        params.put("slogan_ecole", ecole.getSlogan());
        params.put("adress_ecole", ecole.getBoitePostale() + " Tel:" + ecole.getTelephone());
        params.put("logo_ecole", ecole.getLogo());
        //determination du chemin des subreports
        params.put("SUBREPORT_DIR", resourceLoader.getResource(reportfile).getFile().getParent() + File.separator);
        params.put("cmr", resourceLoader.getResource("classpath:ischool/reports/Cameroun.jpg").getFile().getAbsolutePath());

        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        String destfile = uploadedfile.getAbsolutePath() + File.separator + "Carte"
                + e.getMatricule() + ".pdf";
        //fill report
        JasperPrint jp = JasperFillManager.fillReport(
                resourceLoader.getResource(reportfile).getInputStream() //file jasper
                , params, //params report
                dataSource.getConnection());  //datasource

        JasperExportManager.exportReportToPdfFile(jp, destfile);

        return download(destfile);

    }

    public ResponseEntity<byte[]> download(String pathfile) throws IOException {
        Resource resource = resourceLoader.getResource("file:" + pathfile);
        InputStream in = resource.getInputStream();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(resource.contentLength());
            headers.add("Content-Disposition", "attachment; filename=\"" + resource.getFilename() + "\"");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers,
                    HttpStatus.OK);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
}
