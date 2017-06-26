/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service;

import com.tsoft.ischool.domain.ClasseEleve;
import com.tsoft.ischool.domain.EleveAbsence;
import com.tsoft.ischool.repository.ClasseEleveRepository;
import com.tsoft.ischool.repository.EleveAbsenceRepository;
import java.util.Comparator;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipnangngansopa
 */
@Service
public class EleveAbsenceService {

    @Autowired
    EleveAbsenceRepository eleveAbsenceRepo;
    @Autowired
    ClasseEleveRepository classeEleveRepository;
    @Autowired
    AnneeService anneeService;

    public List<EleveAbsence> retrieveDatas(Integer numSeq, String idClasse) throws Exception {
        //retrieve tous les eleves de la classe
        List<ClasseEleve> classeEleves = classeEleveRepository.findByClasseIdAndAnnee(idClasse, anneeService.getAnneeCourante());
        List<EleveAbsence> absences = eleveAbsenceRepo.findByClasseEleveClasseIdAndClasseEleveAnneeAndNumeroSequence(idClasse, anneeService.getAnneeCourante(), numSeq);
        List<Long> elevesAvecAbsences = absences.stream().map(n -> n.getClasseEleve().getId()).collect(toList());
        classeEleves.stream().filter((ce) -> (!elevesAvecAbsences.contains(ce.getId()))).map((ce) -> {
            EleveAbsence n = new EleveAbsence();
            n.setNumeroSequence(numSeq);
            n.setClasseEleve(ce);
            n.setNombreHeures(0);
            return n;
        }).forEachOrdered((n) -> {
            absences.add(n);
        });

        Comparator<EleveAbsence> byEleve = (EleveAbsence n1, EleveAbsence n2) -> n1.getClasseEleve().getEleve().getNom().compareTo(n2.getClasseEleve().getEleve().getNom());
        List<EleveAbsence> absencebyEleve = absences.stream().sorted(byEleve).collect(toList());
        return absencebyEleve;

    }

    public List<EleveAbsence> save(List<EleveAbsence> absences) throws Exception {
        List<EleveAbsence> result = absences.stream().filter(c -> c.getNombreHeures() != 0).collect(toList());
        return eleveAbsenceRepo.save(result);
    }

}
