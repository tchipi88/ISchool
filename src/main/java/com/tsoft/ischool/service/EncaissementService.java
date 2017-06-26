/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service;

import com.tsoft.ischool.domain.Caisse;
import com.tsoft.ischool.domain.CaisseEncaissement;
import com.tsoft.ischool.repository.EncaissementRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
@Transactional
public class EncaissementService {

    @Autowired
    EncaissementRepository encaissementRepository;
    @Autowired
    CaisseService caisseService;

    public CaisseEncaissement save(CaisseEncaissement encaissement) throws Exception {
        Caisse caisse = caisseService.getCaisse();
        if (encaissement.getCaisse() == null) {
            encaissement.setCaisse(caisse);
        }

        caisse.setEntree(caisse.getEntree().add(encaissement.getMontant()));
        caisseService.save(caisse);

        return encaissementRepository.save(encaissement);
    }

}
