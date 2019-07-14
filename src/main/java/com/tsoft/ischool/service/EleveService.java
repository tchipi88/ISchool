/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service;

import com.tsoft.ischool.domain.*;
import com.tsoft.ischool.domain.enumeration.Civilite;
import com.tsoft.ischool.domain.enumeration.EtatAnnee;
import com.tsoft.ischool.domain.enumeration.Sexe;
import com.tsoft.ischool.domain.enumeration.TypePersonne;
import com.tsoft.ischool.repository.*;
import com.tsoft.ischool.repository.search.CompteAnalytiqueSearchRepository;
import com.tsoft.ischool.repository.search.EleveSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author Wilfried
 */
@Service
public class EleveService {

    private final EleveRepository eleveRepository;

    private final EleveSearchRepository eleveSearchRepository;

    private final CompteAnalytiqueRepository compteAnalytiqueRepository;

    private final CompteAnalytiqueSearchRepository compteAnalytiqueSearchRepository;

    private final PersonRepository personRepository;

//    public EleveResource(EleveRepository eleveRepository, EleveSearchRepository eleveSearchRepository,
//                         CompteAnalytiqueRepository compteAnalytiqueRepository, CompteAnalytiqueSearchRepository compteAnalytiqueSearchRepository) {
//        this.eleveRepository = eleveRepository;
//        this.eleveSearchRepository = eleveSearchRepository;
//        this.compteAnalytiqueRepository = compteAnalytiqueRepository;
//        this.compteAnalytiqueSearchRepository = compteAnalytiqueSearchRepository;
//    }

    public EleveService(EleveRepository eleveRepository, EleveSearchRepository eleveSearchRepository,
                         CompteAnalytiqueRepository compteAnalytiqueRepository, CompteAnalytiqueSearchRepository compteAnalytiqueSearchRepository,
                         PersonRepository personRepository) {
        this.eleveRepository = eleveRepository;
        this.eleveSearchRepository = eleveSearchRepository;
        this.compteAnalytiqueRepository = compteAnalytiqueRepository;
        this.compteAnalytiqueSearchRepository = compteAnalytiqueSearchRepository;
        this.personRepository = personRepository;
    }



    public Eleve create(Eleve eleve) throws Exception {

        //if(eleve.getPrenom()==null) {

        PersonEntity person = new PersonEntity(eleve.getNom() + (eleve.getPrenom() != null ? " " + eleve.getPrenom() : ""),
                TypePersonne.STUDENT, eleve.getSexe());
        person = personRepository.save(person);
        eleve.setPerson(person);
        //}

        Eleve result = eleveRepository.save(eleve);
        eleveSearchRepository.save(result);
        CompteAnalytique compte = new CompteAnalytique();
        compte.setEleve(result);
        CompteAnalytique compteSaved = compteAnalytiqueRepository.save(compte);
        compteAnalytiqueSearchRepository.save(compteSaved);

        return result;
    }

}
