/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service;

import com.tsoft.ischool.config.ApplicationProperties;
import com.tsoft.ischool.domain.*;
import com.tsoft.ischool.domain.enumeration.CaisseMouvementMotif;
import static com.tsoft.ischool.domain.enumeration.ModePaiement.CHEQUE;
import static com.tsoft.ischool.domain.enumeration.ModePaiement.ESPECES;
import static com.tsoft.ischool.domain.enumeration.ModePaiement.VIREMENT;
import com.tsoft.ischool.domain.enumeration.SensEcritureComptable;
import com.tsoft.ischool.domain.enumeration.TypePersonne;
import com.tsoft.ischool.repository.EleveRepository;
import com.tsoft.ischool.repository.PersonRepository;
import com.tsoft.ischool.repository.ReglementRepository;
import com.tsoft.ischool.service.dto.ReglementDto;
import com.tsoft.ischool.web.rest.util.HeaderUtil;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tchipi
 */
@Service
@Transactional
public class ReglementService {

    @Autowired
    ReglementRepository reglementRepository;
    @Autowired
    EncaissementService encaissementService;
    @Autowired
    EcritureCompteAnalytiqueService ecritureCompteAnalytiqueService;
    @Autowired
    CompteAnalytiqueService compteAnalytiqueService;
    @Autowired
    CompteService compteService;
    @Autowired
    AnneeService as;
    @Autowired
    ApplicationProperties app;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    DataSource dataSource;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EleveRepository eleveRepository;

    public Reglement save(Reglement reglement) throws Exception {
        if (reglement.getId() != null) {
            throw new Exception("Mise à jour des reglement interdite");
        }
        ecritureCompteAnalytiqueService.create(reglement.getEleve(), reglement.getMontant(), SensEcritureComptable.C, "Ecolage ");

//        //Compte comptePersonnel = compteService.getComptePersonnel();
//        comptePersonnel.setDebit(reglement.getMontant().add(comptePersonnel.getDebit()));
//        compteService.save(comptePersonnel);
        switch (reglement.getModePaiement()) {
            case ESPECES: {

                Compte compteCaisse = compteService.getCompteCaisse();
                compteCaisse.setCredit(reglement.getMontant().add(compteCaisse.getCredit()));
                compteService.save(compteCaisse);

                CaisseEncaissement encaissement = new CaisseEncaissement();
                encaissement.setMontant(reglement.getMontant());
                encaissement.setDateVersement(reglement.getDateVersement());
                encaissement.setModePaiement(reglement.getModePaiement());
                encaissement.setMotif(CaisseMouvementMotif.ECOLAGE);
                Eleve eleve = reglement.getEleve();
                PersonEntity person = eleve.getPerson();
                if(person==null){
                    person = new PersonEntity(eleve.getNom() + eleve.getPrenom() != null ? " " + eleve.getPrenom() : "", TypePersonne.STUDENT,
                            eleve.getSexe());
                    person = personRepository.save(person);
                    eleve.setPerson(person);
                    eleveRepository.save(eleve);
                }
                encaissement.setPerson(person);
                encaissement.setCommentaires("Ecolage : " + eleve.getNom());

                encaissementService.save(encaissement);

                break;
            }
            case CHEQUE: {
                Compte compteCheque = compteService.getCompteCheque();
                compteCheque.setCredit(reglement.getMontant().add(compteCheque.getCredit()));
                compteService.save(compteCheque);
                break;
            }
            case VIREMENT: {
                Compte compteBanque = compteService.getCompteBanque();
                compteBanque.setCredit(reglement.getMontant().add(compteBanque.getCredit()));
                compteService.save(compteBanque);
                break;
            }
        }

        return reglementRepository.save(reglement);
    }

    public ResponseEntity<byte[]> print(Reglement reglement) throws Exception {
        String reportfile = "";
        //remplissage des parametres du report
        Map params = new HashMap();
        reportfile = "classpath:ischool/reports/RecuVersement.jasper";
        //recuperation de la classe
        params.put("code_annee", as.getAnneeCourante().getId());
        params.put("code_versement", reglement.getId());
        CompteAnalytique compte = compteAnalytiqueService.getCompteEleve(reglement.getEleve());
        params.put("solde", compte.getSolde());

        //information about school
        ApplicationProperties.Ecole ecole = app.getEcole();
        params.put("nom_ecole", ecole.getNom());
        params.put("slogan_ecole", ecole.getSlogan());
        params.put("adress_ecole", ecole.getBoitePostale() + " Tel:" + ecole.getTelephonePortable());
        params.put("logo_ecole", resourceLoader.getResource("classpath:ischool/reports/logo-ecole.png").getInputStream());

        int printNumber = reglement.getPrintNumber()+1;
        String messagePrint= printNumber==1? "ORIGINAL": "COPY" + (printNumber-1);
        params.put("print_message", messagePrint);


        //  params.put("upload_dir", FileUtils.getUploadedDir());
        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        String destfile = uploadedfile.getAbsolutePath() + File.separator + "Reglement.pdf";
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
            reglement.setPrintNumber(printNumber);
            reglementRepository.save(reglement);
            return new ResponseEntity<>(IOUtils.toByteArray(in), HeaderUtil.downloadAlert(resource), HttpStatus.OK);
        } finally {
            IOUtils.closeQuietly(in);
        }

    }

    public Reglement create(ReglementDto reglementDto)  throws Exception {
        Reglement reglement = new Reglement();
//        reglement.setId(reglementDto.getId());
        reglement.setEleve(reglementDto.getEleve());
        reglement.setCaisse(reglementDto.getCaisse());
        reglement.setDateVersement(reglementDto.getDateVersement());
        reglement.setMontant(reglementDto.getMontant());
//        reglement.setFileReference(reglementDto.getFileReference().getOriginalFilename());
        reglement.setModePaiement(reglementDto.getModePaiement());
        reglement.setMotif(reglementDto.getMotif());

        return save(reglement);
    }

}
