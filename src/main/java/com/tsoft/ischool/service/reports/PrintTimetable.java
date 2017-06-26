/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service.reports;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import com.tsoft.ischool.config.ApplicationProperties;
import com.tsoft.ischool.domain.Professeur;
import com.tsoft.ischool.repository.ClasseRepository;
import com.tsoft.ischool.repository.ProfesseurRepository;
import com.tsoft.ischool.service.AnneeService;
import com.tsoft.ischool.config.ApplicationProperties.Ecole;
import com.tsoft.ischool.service.TimetableService;
import com.tsoft.ischool.service.dto.TimetableDTO;
import com.tsoft.ischool.web.rest.util.HeaderUtil;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.io.IOUtils;
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
public class PrintTimetable {
    
    @Autowired
    AnneeService as;
    @Autowired
    ApplicationProperties app;
    
    @Autowired
    ClasseRepository classeRepository;
    @Autowired
    ProfesseurRepository professeurRepository;
    @Autowired
    TimetableService timetableService;
    @Autowired
    ResourceLoader resourceLoader;
    
    @Autowired
    DataSource dataSource;
    
    @GetMapping(value = "/printTimetablesALLC",
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<byte[]> printTimetablesALLC() throws Exception {
        String reportfile = "classpath:ischool/reports/EDTClasseAll.jasper";
        //remplissage des parametres du report
        Map params = new HashMap();
        params.put("codeannee", as.getAnneeCourante().getId());

        //information about school
        ApplicationProperties.Ecole ecole = app.getEcole();
        params.put("nom_ecole", ecole.getNom());
        params.put("slogan_ecole", ecole.getSlogan());
        params.put("adress_ecole", ecole.getBoitePostale() + " Tel:" + ecole.getTelephonePortable());
//        params.put("logo_ecole", FileUtils.getUploadedFile(ecole.getLogo()).getAbsolutePath());

        //determination du chemin des subreports
        params.put("SUBREPORT_DIR", resourceLoader.getResource(reportfile).getFile().getParent() + File.separator);
        
        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        String destfile = uploadedfile.getAbsolutePath() + File.separator + "EDTClasseALL"
                + ".pdf";
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
    
    @GetMapping(value = "/printTimetablesALLP",
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<byte[]> printTimetablesALLP() throws Exception {
        String reportfile = "classpath:ischool/reports/EDTProfAll.jasper";
        //remplissage des parametres du report
        Map params = new HashMap();
        params.put("codeannee", as.getAnneeCourante().getId());

        //information about school
        Ecole ecole = app.getEcole();
        params.put("nom_ecole", ecole.getNom());
        params.put("slogan_ecole", ecole.getSlogan());
        params.put("adress_ecole", ecole.getBoitePostale() + " Tel:" + ecole.getTelephonePortable());
//        params.put("logo_ecole", FileUtils.getUploadedFile(ecole.getLogo()).getAbsolutePath());

        //determination du chemin des subreports
        params.put("SUBREPORT_DIR", resourceLoader.getResource(reportfile).getFile().getParent() + File.separator);
        
        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        String destfile = uploadedfile.getAbsolutePath() + File.separator + "EDTProfALL"
                + ".pdf";
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

//    @GetMapping(value = "/printTimetableC/{codeclasse}",
//            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
//    public ResponseEntity<byte[]> printTimetablesC(@PathVariable String codeclasse) throws Exception {
//        // Classe classe = classeRepository.findOne(codeclasse);
//        String reportfile = "classpath:ischool/reports/EDTClasse.jasper";
//        //remplissage des parametres du report
//        Map params = new HashMap();
//        params.put("codeclasse", codeclasse);
//
//        params.put("codeannee", as.getAnneeCourante().getId());
//
//        //information about school
//        ApplicationProperties.Ecole ecole = app.getEcole();
//        params.put("nom_ecole", ecole.getNom());
//        params.put("slogan_ecole", ecole.getSlogan());
//        params.put("adress_ecole", ecole.getBoitePostale() + " Tel:" + ecole.getTelephonePortable());
////        params.put("logo_ecole", FileUtils.getUploadedFile(ecole.getLogo()).getAbsolutePath());
//
//        File uploadedfile = new File("." + File.separator + "reports");
//        if (!uploadedfile.exists()) {
//            uploadedfile.mkdirs();
//        }
//        String destfile = uploadedfile.getAbsolutePath() + File.separator + "EDTClasse"
//                + ".pdf";
//        List<TimetableDTO> timetables = timetableService.getTimetablesC(codeclasse);
//        JRDataSource source = new JRBeanCollectionDataSource(timetables);
//        //fill report
//        JasperPrint jp = JasperFillManager.fillReport(
//                resourceLoader.getResource(reportfile).getInputStream() //file jasper
//                ,
//                 params, //params report
//                source);  //datasource
//        Resource resource = resourceLoader.getResource("file:" + destfile);
//        InputStream in = resource.getInputStream();
//        try {
//            return new ResponseEntity<>(IOUtils.toByteArray(in), HeaderUtil.downloadAlert(resource), HttpStatus.OK);
//        } finally {
//            IOUtils.closeQuietly(in);
//        }
//    }
//    @GetMapping(value = "/printTimetablePP/{prof}",
//            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
//    public ResponseEntity<byte[]> printTimetablesPP(@PathVariable Long prof) throws Exception {
//        Professeur professeur = professeurRepository.findOne(prof);
//        String reportfile = "classpath:ischool/reports/EDTProf.jasper";
//        //remplissage des parametres du report
//        Map params = new HashMap();
//        params.put("codeprof", professeur.getId());
//        params.put("nomprof", professeur.getNom());
//        //params.put("matricule", professeur.getMatricule());
//        params.put("civilite", professeur.getCivilite());
//
//        params.put("codeannee", as.getAnneeCourante().getId());
//
//        //information about school
//        Ecole ecole = app.getEcole();
//        params.put("nom_ecole", ecole.getNom());
//        params.put("slogan_ecole", ecole.getSlogan());
//        params.put("adress_ecole", ecole.getBoitePostale() + " Tel:" + ecole.getTelephonePortable());
////        params.put("logo_ecole", FileUtils.getUploadedFile(ecole.getLogo()).getAbsolutePath());
//
//        File uploadedfile = new File("." + File.separator + "reports");
//        if (!uploadedfile.exists()) {
//            uploadedfile.mkdirs();
//        }
//        String destfile = uploadedfile.getAbsolutePath() + File.separator + "EDTProf"
//                + ".pdf";
//
//        List<TimetableDTO> timetables = timetableService.getTimetablesP(prof);
//        JRDataSource source = new JRBeanCollectionDataSource(timetables);
//        //fill report
//        JasperPrint jp = JasperFillManager.fillReport(
//                resourceLoader.getResource(reportfile).getInputStream() //file jasper
//                ,
//                 params, //params report
//                source);  //datasource
//
//        JasperExportManager.exportReportToPdfFile(jp, destfile);
//
//        Resource resource = resourceLoader.getResource("file:" + destfile);
//        InputStream in = resource.getInputStream();
//        try {
//            return new ResponseEntity<>(IOUtils.toByteArray(in), HeaderUtil.downloadAlert(resource), HttpStatus.OK);
//        } finally {
//            IOUtils.closeQuietly(in);
//        }
//    }
    @GetMapping(value = "/printTimetableP/{prof}",
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<byte[]> printTimetablesP(@PathVariable Long prof) throws Exception {
        Professeur professeur = professeurRepository.findOne(prof);
        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        String destfile = uploadedfile.getAbsolutePath() + File.separator + "EDTProf"
                + professeur.getId() + ".pdf";
        
        FastReportBuilder drb = new FastReportBuilder();
        drb.addFirstPageImageBanner(resourceLoader.getResource("classpath:ischool/reports/logo-ecole.png").getFile().getAbsolutePath(), new Integer(300), new Integer(60), ImageBanner.Alignment.Center);
        drb.addColumn("Creneau", "creneau.heureDebut", LocalTime.class.getName(), 30)
                .addColumn("Lundi", "lundi.cours.classe.id", String.class.getName(), 30)
                .addColumn("Mardi", "mardi.cours.classe.id", String.class.getName(), 30)
                .addColumn("Mercredi", "mercredi.cours.classe.id", String.class.getName(), 30)
                .addColumn("Jeudi", "jeudi.cours.classe.id", String.class.getName(), 30)
                .addColumn("Vendredi", "vendredi.cours.classe.id", String.class.getName(), 30)
                .setTitle("Emploi de Temps " + professeur.getCivilite() + " " + professeur.getNom() + "    Annee :" + as.getAnneeCourante().getId())
                //.setSubtitle("This report was generated at " + LocalDate.now())
                .setPrintBackgroundOnOddRows(true)
                .setUseFullPageWidth(true);
        
        DynamicReport dr = drb.build();
        List<TimetableDTO> timetables = timetableService.getTimetablesP(prof);
        JRDataSource source = new JRBeanCollectionDataSource(timetables);
        
        JasperReport jr = DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager(), new HashMap());
        JasperPrint jp = JasperFillManager.fillReport(jr, new HashMap(), source);
        JasperExportManager.exportReportToPdfFile(jp, destfile);
        
        Resource resource = resourceLoader.getResource("file:" + destfile);
        InputStream in = resource.getInputStream();
        try {
            return new ResponseEntity<>(IOUtils.toByteArray(in), HeaderUtil.downloadAlert(resource), HttpStatus.OK);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
    
    @GetMapping(value = "/printTimetableC/{codeclasse}",
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<byte[]> printTimetablesC(@PathVariable String codeclasse) throws Exception {
        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        String destfile = uploadedfile.getAbsolutePath() + File.separator + "EDTClasse"
                + codeclasse + ".pdf";
        
        FastReportBuilder drb = new FastReportBuilder();
        drb.addFirstPageImageBanner(resourceLoader.getResource("classpath:ischool/reports/logo-ecole.png").getFile().getAbsolutePath(), new Integer(300), new Integer(60), ImageBanner.Alignment.Center);
        drb.addColumn("Creneau", "creneau.heureDebut", LocalTime.class.getName(), 30)
                .addColumn("Lundi", "lundi.cours.professeur.nom", String.class.getName(), 30)
                .addColumn("Mardi", "mardi.cours.professeur.nom", String.class.getName(), 30)
                .addColumn("Mercredi", "mercredi.cours.professeur.nom", String.class.getName(), 30)
                .addColumn("Jeudi", "jeudi.cours.professeur.nom", String.class.getName(), 30)
                .addColumn("Vendredi", "vendredi.cours.professeur.nom", String.class.getName(), 30)
                .setTitle("Emploi de Temps " + codeclasse + "    Annee :" + as.getAnneeCourante().getId())
                //.setSubtitle("This report was generated at " + LocalDate.now())
                .setPrintBackgroundOnOddRows(true)
                .setUseFullPageWidth(true);
        
        DynamicReport dr = drb.build();
        List<TimetableDTO> timetables = timetableService.getTimetablesC(codeclasse);
        JRDataSource source = new JRBeanCollectionDataSource(timetables);
        
        JasperReport jr = DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager(), new HashMap());
        JasperPrint jp = JasperFillManager.fillReport(jr, new HashMap(), source);
        JasperExportManager.exportReportToPdfFile(jp, destfile);
        
        Resource resource = resourceLoader.getResource("file:" + destfile);
        InputStream in = resource.getInputStream();
        try {
            return new ResponseEntity<>(IOUtils.toByteArray(in), HeaderUtil.downloadAlert(resource), HttpStatus.OK);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
    
}
