/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service;

import com.tsoft.ischool.domain.Classe;
import com.tsoft.ischool.domain.Coefficient;
import com.tsoft.ischool.domain.Cours;
import com.tsoft.ischool.repository.ClasseRepository;
import com.tsoft.ischool.repository.CoefficientRepository;
import com.tsoft.ischool.repository.CoursRepository;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipnangngansopa
 */
@Service
public class CoursService {

    @Autowired
    AnneeService anneeService;
    @Autowired
    CoursRepository coursRepository;
    @Autowired
    CoefficientRepository coefficientRepository;
    @Autowired
    ClasseRepository classeRepository;
    @Autowired
    CreneauService creneauService;

    public List<Cours> retrieveDatas(String Idclasse) throws Exception {
        Classe classe = classeRepository.findOne(Idclasse);
        List<Cours> cours = coursRepository.findByAnneeAndClasse(anneeService.getAnneeCourante(), classe);

        //retrieve toutes les matieres de la classe
        List<Coefficient> coefs = coefficientRepository.findBySerieId(classe.getSerie().getId());

        List<Long> matieres = cours.stream().map(c -> c.getMatiere().getId()).collect(toList());
        //creer des cours pour les autres matieres 
        for (Coefficient coef : coefs) {
            if (!matieres.contains(coef.getMatiere().getId())) {
                Cours c = new Cours();
                c.setAnnee(anneeService.getAnneeCourante());
                c.setMatiere(coef.getMatiere());
                c.setClasse(classe);
                cours.add(c);

            }
        }

        return cours;
    }

    /**
     *
     * @param cours
     * @return
     * @throws java.lang.Exception
     */
    public List<Cours> save(List<Cours> cours) throws Exception {
        Integer quotaMaxhebdo = creneauService.getPlageHoraires().size();
        if ((cours.stream().filter(c -> c.getProfesseur() != null).mapToInt(c -> c.getDureeHeures()).sum()) > quotaMaxhebdo) {
            throw new Exception("La durée totale des heures depasse le quota horaire hebdomadaire  max de " + quotaMaxhebdo);
        }
        List<Cours> result = cours.stream().filter(c -> c.getProfesseur() != null).collect(toList());
        return coursRepository.save(result);
    }

}
