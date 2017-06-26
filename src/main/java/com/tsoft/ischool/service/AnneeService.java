/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service;

import com.tsoft.ischool.domain.Annee;
import com.tsoft.ischool.domain.FraisScolarite;
import com.tsoft.ischool.domain.enumeration.EtatAnnee;
import com.tsoft.ischool.repository.AnneeRepository;
import com.tsoft.ischool.repository.FraisScolariteRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class AnneeService {

    @Autowired
    AnneeRepository anneeScolaireRepository;
    @Autowired
    FraisScolariteRepository fraisScolariteRepository;

    public Annee getAnneeCourante() throws Exception {
        // Annee as = anneeScolaireRepository.findByDateDebutAfterAndDateFinBefore(LocalDate.now(), LocalDate.now());
        Annee as = anneeScolaireRepository.findByEtat(EtatAnnee.EN_COURS);
        if (as == null) {
            throw new Exception("Annee courante non definie");
        }
        return as;
    }

    public void ClotureAnnee(Annee annee) throws Exception {
        annee.setEtat(EtatAnnee.TERMINE);
        anneeScolaireRepository.save(annee);
    }

    public Annee create(Annee annee) throws Exception {
        annee.setId(annee.getDateDebut().getYear() + "-" + annee.getDateFin().getYear());

        Annee oldAnnee = anneeScolaireRepository.findByEtat(EtatAnnee.EN_COURS);
        if (oldAnnee != null) {

            List<FraisScolarite> frais = fraisScolariteRepository.findByAnnee(oldAnnee);
            for (FraisScolarite fraisScolarite : frais) {
//copie des nouveaux frais
            }

            ClotureAnnee(oldAnnee);
        }
        return anneeScolaireRepository.save(annee);
    }

}
