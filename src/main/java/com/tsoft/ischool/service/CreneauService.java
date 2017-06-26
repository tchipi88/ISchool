/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service;

import com.tsoft.ischool.domain.Creneau;
import com.tsoft.ischool.domain.PlageHoraire;
import com.tsoft.ischool.domain.enumeration.Jour;
import com.tsoft.ischool.repository.CreneauRepository;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipnangngansopa
 */
@Service
public class CreneauService {

    @Autowired
    CreneauRepository creneauRepo;

    public List<PlageHoraire> getPlageHoraires() throws Exception {
        //todo penser a enlever les creneux de mercredi apres midi et les heurs de pause
        List<Creneau> creneaux = creneauRepo.findAll();
        List<PlageHoraire> phs = new ArrayList();

        for (Jour j : Jour.values()) {
            if (Jour.DIMANCHE.equals(j) || Jour.SAMEDI.equals(j)) {
                continue;
            }
            creneaux.stream().filter((c) -> !c.isPause()).filter((c) -> !(j.equals(Jour.MERCREDI) && c.getHeureDebut().isAfter((LocalTime.NOON)))).map((c) -> {
                PlageHoraire ph = new PlageHoraire();
                ph.setJour(j);
                ph.setCreneau(c);
                return ph;
            }).forEachOrdered((ph) -> {
                phs.add(ph);
            });
        }
        return phs;
    }
}
