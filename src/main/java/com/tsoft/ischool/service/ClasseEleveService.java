/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service;

import com.tsoft.ischool.domain.ClasseEleve;
import com.tsoft.ischool.domain.FraisScolarite;
import com.tsoft.ischool.domain.enumeration.SensEcritureComptable;
import com.tsoft.ischool.repository.ClasseEleveRepository;
import com.tsoft.ischool.repository.FraisScolariteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tchipi
 */
@Service
@Transactional
public class ClasseEleveService {

    @Autowired
    ClasseEleveRepository eleveInscritRepository;
    @Autowired
    AnneeService anneeService;
    @Autowired
    CompteService compteService;
    @Autowired
    EcritureCompteAnalytiqueService ecritureCompteAnalytiqueService;
    @Autowired
    FraisScolariteRepository fraisScolariteRepository;

    public ClasseEleve create(ClasseEleve eleveInscrit) throws Exception {
       ClasseEleve eleveDejaInscrit= eleveInscritRepository.findByAnneeAndEleve(anneeService.getAnneeCourante(), eleveInscrit.getEleve());
        if(eleveDejaInscrit!=null) throw new Exception("Eleve deja inscrit(e) en "+eleveDejaInscrit.getClasse().getId());
        eleveInscrit.setAnnee(anneeService.getAnneeCourante());
        // if(! anneeService.getAnneeCourante().equals(eleveInscrit.getAnnee())) throw  new Exception("Inscription ")
        //determine les frais d'ecolage
        FraisScolarite fraisScolarite = fraisScolariteRepository.findByAnneeAndSerie(anneeService.getAnneeCourante(), eleveInscrit.getClasse().getSerie());
        if (fraisScolarite == null) {
            throw new Exception("Aucun Frais de Scolarite défini pour l'annee courante et la serie " + eleveInscrit.getClasse().getSerie().getId());
        }

        ecritureCompteAnalytiqueService.create(eleveInscrit.getEleve(), fraisScolarite.getTotalScolarite(), SensEcritureComptable.D, "Inscription  en " + eleveInscrit.getClasse().getId() + " pour Année: " + eleveInscrit.getAnnee().getId());

        return eleveInscritRepository.save(eleveInscrit);
    }

    public ClasseEleve update(ClasseEleve eleveInscrit) throws Exception {
        ClasseEleve oldValue = eleveInscritRepository.findOne(eleveInscrit.getId());
        if (!oldValue.getClasse().getSerie().equals(eleveInscrit.getClasse().getSerie())) {
            throw new Exception("Impossible de changer de classe de series différentes ");
        }
        return eleveInscritRepository.save(eleveInscrit);
    }


}
