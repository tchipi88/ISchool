/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.reports.bulletin;

import com.tsoft.appli.highschool.model.Ecole;
import com.tsoft.appli.highschool.model.Eleve;
import com.tsoft.appli.highschool.model.EleveInscrit;
import com.tsoft.appli.highschool.model.Notes;
import com.tsoft.appli.highschool.repository.EcoleRepository;
import com.tsoft.appli.highschool.repository.EleveInscritRepository;
import com.tsoft.appli.highschool.service.AnneeService;
import com.tsoft.service.process.EmptyReportProcess;
import com.tsoft.utils.FileUtils;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.JoinColumn;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
public class BulletinTri extends EmptyReportProcess {

    @JoinColumn
    @NotNull
    private Eleve eleve;
    @Max(3)
    @Min(1)
    @NotNull
    private int numero_trimestre;
    @Autowired
    EleveInscritRepository eleveInscritRepository;

    @Autowired
    EcoleRepository ecoleRepository;

    @Autowired
    AnneeService as;

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public int getNumero_trimestre() {
        return numero_trimestre;
    }

    public void setNumero_trimestre(int numero_trimestre) {
        this.numero_trimestre = numero_trimestre;
    }

    @Override
    public String libelle() {
        return "Bulletin Trimestre Eleve";
    }

    @Override
    public Object run(HttpSession session, HttpServletRequest request) throws Exception {
        EleveInscrit ei = eleveInscritRepository.findByAnneeAndEleve(as.getAnneeCourante(), getEleve());
        String reportfile = "";
        //remplissage des parametres du report
        Map params = new HashMap();
        reportfile = "classpath:ischool/reports/BulletinTri.jasper";
        params.put("numtri", getNumero_trimestre());
        //recuperation de la classe
        params.put("code_eleve", getEleve().getCode());
        params.put("annee", as.getAnneeCourante().getLibelle());
        params.put("code_annee", as.getAnneeCourante().getCode());
        params.put("code_classe", ei.getClasse().getCode());
        params.put("effectif", ei.getClasse().getNbre_Eleves());
        //information about school
        if (ecoleRepository.findAll().isEmpty()) {
            throw new com.tsoft.exception.BusinessException("Paramètres de l'ecole non definis");
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
                + ("TRI") + getNumero_trimestre() + getEleve().getNomprenom() + ".pdf";
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
