/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service.reports;

import com.tsoft.ischool.domain.Reglement;
import com.tsoft.ischool.service.AnneeService;
import com.tsoft.ischool.config.ApplicationProperties;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import com.tsoft.ischool.repository.ClasseEleveRepository;

/**
 *
 * @author tchipi
 */
@Service
public class RecuVersement {

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

    public Resource print(Reglement reglement) throws Exception {

        //impression Recu
        String reportfile = "";
        //remplissage des parametres du report
        Map params = new HashMap();
        reportfile = "classpath:com/tsoft/appli/highschool/finances/service/RecuVersement.jasper";

//        params.put("code_versement", r.getCode());
//        params.put("code_eleve", getEleve().getCode());
//        params.put("annee", as.getAnneeCourante().getLibelle());
//        params.put("code_annee", as.getAnneeCourante().getCode());
//        params.put("code_classe", ei.getClasse().getCode());

        //information about school
        ApplicationProperties.Ecole ecole = app.getEcole();
        params.put("nom_ecole", ecole.getNom());
        params.put("slogan_ecole", ecole.getSlogan());
        params.put("adress_ecole", ecole.getBoitePostale() + " Tel:" + ecole.getTelephonePortable());
//        params.put("logo_ecole", FileUtils.getUploadedFile(ecole.getLogo()).getAbsolutePath());

        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        String destfile = uploadedfile.getAbsolutePath() + File.separator + "RecuVersement"
                + reglement.getId() + ".pdf";
        //fill report
        JasperPrint jp = JasperFillManager.fillReport(
                resourceLoader.getResource(reportfile).getInputStream() //file jasper
                ,
                 params, //params report
                dataSource.getConnection());  //datasource

        JasperExportManager.exportReportToPdfFile(jp, destfile);

        return resourceLoader.getResource("file:" + destfile);

    }

   

}
