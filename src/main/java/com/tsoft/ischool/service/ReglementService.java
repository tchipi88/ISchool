/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service;

import com.tsoft.ischool.domain.Compte;
import com.tsoft.ischool.domain.CaisseEncaissement;
import com.tsoft.ischool.domain.Reglement;
import com.tsoft.ischool.domain.enumeration.CaisseMouvementMotif;
import static com.tsoft.ischool.domain.enumeration.ModePaiement.CHEQUE;
import static com.tsoft.ischool.domain.enumeration.ModePaiement.ESPECES;
import static com.tsoft.ischool.domain.enumeration.ModePaiement.VIREMENT;
import com.tsoft.ischool.domain.enumeration.SensEcritureComptable;
import com.tsoft.ischool.repository.ReglementRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    CompteService compteService;

    public Reglement save(Reglement reglement) throws Exception {
        if (reglement.getId() != null) {
            throw new Exception("Mise à jour des reglement interdite");
        }
        ecritureCompteAnalytiqueService.create(reglement.getEleve(), reglement.getMontant(), SensEcritureComptable.C, "Ecologe " );
        
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
                encaissement.setCommentaires("Ecologe :" + reglement.getEleve().getNom() );

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

}
