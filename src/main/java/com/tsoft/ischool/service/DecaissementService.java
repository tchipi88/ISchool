/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service;

import com.tsoft.ischool.domain.Caisse;
import com.tsoft.ischool.domain.CaisseDecaissement;
import com.tsoft.ischool.repository.DecaissementRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
@Transactional
public class DecaissementService {

    @Autowired
    DecaissementRepository decaissementRepository;
    @Autowired
    CaisseService caisseService;

    public CaisseDecaissement save(CaisseDecaissement decaissement) throws Exception {
        Caisse caisse = caisseService.getCaisse();
        if (decaissement.getCaisse() == null) {
            decaissement.setCaisse(caisse);
        }

        caisse.setSortie(caisse.getSortie().add(decaissement.getMontant()));
        caisseService.save(caisse);

        return decaissementRepository.save(decaissement);
    }

}
