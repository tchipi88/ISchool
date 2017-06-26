/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tsoft.ischool.domain.CompteAnalytique;
import com.tsoft.ischool.domain.Eleve;
import com.tsoft.ischool.repository.CompteAnalytiqueRepository;
import com.tsoft.ischool.repository.search.CompteAnalytiqueSearchRepository;

/**
 *
 * @author tchipi
 */
@Service
public class CompteAnalytiqueService {

    @Autowired
    CompteAnalytiqueRepository compteAnalytiqueClientRepository;
    @Autowired
    CompteAnalytiqueSearchRepository compteAnalytiqueClientSearchRepository;

    public CompteAnalytique getCompteEleve(Eleve eleve) throws Exception {
        CompteAnalytique compteClient = compteAnalytiqueClientRepository.findByEleve(eleve);
        if (compteClient == null) {
            compteClient = new CompteAnalytique();
            compteClient.setEleve(eleve);
         
        }

        return compteClient;
    }

    public CompteAnalytique save(CompteAnalytique compteAnalytiqueClient) throws Exception {
        compteAnalytiqueClient = compteAnalytiqueClientRepository.save(compteAnalytiqueClient);
        compteAnalytiqueClientSearchRepository.save(compteAnalytiqueClient);
        return compteAnalytiqueClient;
    }
}
