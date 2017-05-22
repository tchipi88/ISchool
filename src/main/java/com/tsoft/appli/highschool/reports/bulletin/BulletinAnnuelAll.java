/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.reports.bulletin;

import com.tsoft.utils.annotations.Select;
import com.tsoft.appli.highschool.model.Classe;
import com.tsoft.appli.highschool.model.Ecole;
import com.tsoft.appli.highschool.model.Notes;
import com.tsoft.appli.highschool.repository.EcoleRepository;
import com.tsoft.appli.highschool.service.AnneeService;
import com.tsoft.exception.BusinessException;
import com.tsoft.service.process.EmptyReportProcess;
import com.tsoft.utils.FileUtils;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.JoinColumn;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tchipi
 */
@RestController
@RequestMapping("/app")
public class BulletinAnnuelAll extends EmptyReportProcess {

    @JoinColumn
    @NotNull
    @Select
    private Classe classe;

    @Autowired
    AnneeService as;
    @Autowired
    EcoleRepository ecoleRepository;

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    @Override
    public String libelle() {
        return "Bulletin Annuel Classe";
    }

    @Override
    public Object run(HttpSession session, HttpServletRequest request) throws Exception {
        String reportfile = "";
        //remplissage des parametres du report
        Map params = new HashMap();
        reportfile = "classpath:ischool/reports/BulletinAnnuelAll.jasper";

        //recuperation de la classe
        params.put("code_classe", getClasse().getCode());
        params.put("effectif", getClasse().getNbre_Eleves());
        params.put("annee", as.getAnneeCourante().getLibelle());
        params.put("code_annee", as.getAnneeCourante().getCode());
        //information about school
        if (ecoleRepository.findAll().isEmpty()) {
            throw new BusinessException("Paramètres de l'ecole non definis");
        }
        Ecole ecole = ecoleRepository.findAll().get(0);
        params.put("nom_ecole", ecole.getNom());
        params.put("slogan_ecole", ecole.getSlogan());
        params.put("adress_ecole", ecole.getBoitePostale() + " Tel:" + ecole.getTelephone());
//        params.put("logo_ecole", FileUtils.getUploadedFile(ecole.getLogo()).getAbsolutePath());

        //determination du chemin des subreports
        params.put("SUBREPORT_DIR", resourceLoader.getResource(reportfile).getFile().getParent() + File.separator);
        params.put("upload_dir", FileUtils.getUploadedDir());

        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        String destfile = uploadedfile.getAbsolutePath() + File.separator + "Bulletin"
                + ("Annuel") + getClasse().getLibelle() + ".pdf";
        //fill report
        JasperPrint jp = JasperFillManager.fillReport(
                resourceLoader.getResource(reportfile).getInputStream() //file jasper
                , params, //params report
                dataSource.getConnection());  //datasource

        JasperExportManager.exportReportToPdfFile(jp, destfile);

        return download(destfile);
    }

    @Override
    public Class rubrique() throws Exception {
        return Notes.class;
    }
}
