/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service;

import com.tsoft.ischool.domain.CompteAnalytique;
import com.tsoft.ischool.domain.CompteAnalytiqueEcriture;
import com.tsoft.ischool.domain.Eleve;
import com.tsoft.ischool.domain.enumeration.SensEcritureComptable;
import com.tsoft.ischool.repository.EcritureCompteAnalytiqueRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tchipi
 */
@Service
@Transactional
public class EcritureCompteAnalytiqueService {

    @Autowired
    EcritureCompteAnalytiqueRepository ecritureCompteAnalytiqueRepository;
    @Autowired
    CompteAnalytiqueService compteAnalytiqueService;

    public CompteAnalytiqueEcriture create(Eleve eleve, BigDecimal montant, SensEcritureComptable sens, String operation) throws Exception {
        CompteAnalytique compte = compteAnalytiqueService.getCompteEleve(eleve);

        CompteAnalytiqueEcriture ecriture = new CompteAnalytiqueEcriture();
        ecriture.setCompte(compte);
        ecriture.setMontant(montant);
        ecriture.setDateEcriture(LocalDateTime.now());
        ecriture.setSensEcriture(sens);
        ecriture.setLibelleOperation(operation);

        if (sens.equals(SensEcritureComptable.C)) {
            compte.setCredit(compte.getCredit().add(montant));
        } else {
            compte.setDebit(compte.getDebit().add(montant));
        }
        compteAnalytiqueService.save(compte);
        return ecritureCompteAnalytiqueRepository.save(ecriture);
    }

}
