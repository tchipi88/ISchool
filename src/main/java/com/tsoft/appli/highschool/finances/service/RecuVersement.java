/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.finances.service;

import com.tsoft.appli.highschool.model.Caisse;
import com.tsoft.appli.highschool.model.Ecole;
import com.tsoft.appli.highschool.model.Eleve;
import com.tsoft.appli.highschool.model.EleveInscrit;
import com.tsoft.appli.highschool.model.Encaissement;
import com.tsoft.appli.highschool.model.ObjectEntreeCaisse;
import com.tsoft.appli.highschool.model.Reglement;
import com.tsoft.appli.highschool.repository.EcoleRepository;
import com.tsoft.appli.highschool.repository.EleveInscritRepository;
import com.tsoft.appli.highschool.service.AnneeService;
import com.tsoft.exception.BusinessException;
import com.tsoft.service.process.EmptyReportProcess;
import com.tsoft.utils.FileUtils;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
@Scope("session")
public class RecuVersement extends EmptyReportProcess {

    @JoinColumn
    @NotNull
    private Eleve eleve;
    @JoinColumn
    @NotNull
    private Caisse caisse;
    @Enumerated(EnumType.STRING)
    private ObjectEntreeCaisse objet;
    @NotNull
    private BigDecimal montant;
    @NotNull
    private LocalDate date_reglement;

    @Autowired
    AnneeService as;

    @Override
    public void afterPropertiesSet() throws Exception {
        date_reglement = LocalDate.now();
    }

    public Caisse getCaisse() {
        return caisse;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }
    
    

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public ObjectEntreeCaisse getObjet() {
        return objet;
    }

    public void setObjet(ObjectEntreeCaisse objet) {
        this.objet = objet;
    }

    @Override
    public String libelle() {
        return "Effectuer Versement";
    }

    public LocalDate getDate_reglement() {
        return date_reglement;
    }

    public void setDate_reglement(LocalDate date_reglement) {
        this.date_reglement = date_reglement;
    }

    
    @Autowired
    EleveInscritRepository  eleveInscritRepository;
 
    @Autowired
    EcoleRepository  ecoleRepository;
    @Autowired
    CaisseService cs;
    

    @Override
    public Object run(HttpSession session, HttpServletRequest request) throws Exception {
        EleveInscrit ei = eleveInscritRepository.findByAnneeAndEleve(as.getAnneeCourante(), getEleve());

        Reglement r = new Reglement();
        r.setEleveinscrit(ei);
        r.setMontant(getMontant());
        r.setMotif(getObjet());
        r.setDateReglement(getDate_reglement());
        r.setCaisse(getCaisse());
        cs.create(r);

        //impression Recu
        String reportfile = "";
        //remplissage des parametres du report
        Map params = new HashMap();
        reportfile = "classpath:com/tsoft/appli/highschool/finances/service/RecuVersement.jasper";

        params.put("code_versement", r.getCode());
        params.put("code_eleve", getEleve().getCode());
        params.put("annee", as.getAnneeCourante().getLibelle());
        params.put("code_annee", as.getAnneeCourante().getCode());
        params.put("code_classe", ei.getClasse().getCode());
        //information about school
       if (ecoleRepository.findAll().isEmpty()) {
            throw new BusinessException("Paramètres de l'ecole non definis");
        }
        Ecole ecole = ecoleRepository.findAll().get(0);
        params.put("nom_ecole", ecole.getNom());
        params.put("slogan_ecole", ecole.getSlogan());
        params.put("adress_ecole", ecole.getBoitePostale() + " Tel:" + ecole.getTelephone());
       // params.put("logo_ecole", FileUtils.getUploadedFile(ecole.getLogo()).getAbsolutePath());

        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        String destfile = uploadedfile.getAbsolutePath() + File.separator + "RecuVersement"
                + r.getCode() + ".pdf";
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
        return Encaissement.class;
    }

}
