/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service;

import com.tsoft.ischool.domain.ClasseEleve;
import com.tsoft.ischool.domain.Coefficient;
import com.tsoft.ischool.domain.Matiere;
import com.tsoft.ischool.domain.Note;
import com.tsoft.ischool.domain.enumeration.BulletinType;
import com.tsoft.ischool.repository.ClasseEleveRepository;
import com.tsoft.ischool.repository.MatiereRepository;
import com.tsoft.ischool.repository.NoteRepository;
import com.tsoft.ischool.repository.NotesPeriodeRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class NoteService {

    @Autowired
    NotesPeriodeRepository notesPeriodeRepository;
    @Autowired
    ClasseEleveRepository classeEleveRepository;
    @Autowired
    AnneeService anneeService;
    @Autowired
    MatiereRepository matiereRepository;
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    UserService userService;

    public List<Note> retrieveDatas(Long idMatiere, Integer numSeq, String idClasse) throws Exception {
        Matiere matiere = matiereRepository.findOne(idMatiere);
        //retrieve tous les eleves de la classe
        List<ClasseEleve> classeEleves = classeEleveRepository.findByClasseIdAndAnnee(idClasse, anneeService.getAnneeCourante());
        //retrieve les notes deja saisies
        List<Note> notes = noteRepository.findByMatiereIdAndClasseEleveClasseIdAndClasseEleveAnneeAndNumeroSequence(idMatiere, idClasse, anneeService.getAnneeCourante(), numSeq);

        List<Long> elevesAvecNotes = notes.stream().map(n -> n.getClasseEleve().getId()).collect(toList());
        classeEleves.stream().filter((ce) -> (!elevesAvecNotes.contains(ce.getId()))).map((ce) -> {
            Note n = new Note();
            n.setNumeroSequence(numSeq);
            n.setClasseEleve(ce);
            n.setValeur(0D);
            return n;
        }).map((n) -> {
            n.setMatiere(matiere);
            return n;
        }).forEachOrdered((n) -> {
            notes.add(n);
        });
        Comparator<Note> byEleve = (Note n1, Note n2) -> n1.getClasseEleve().getEleve().getNom().compareTo(n2.getClasseEleve().getEleve().getNom());
        List<Note> notebyEleve = notes.stream().sorted(byEleve).collect(toList());
        return notebyEleve;
    }

    public List<Note> save(List<Note> notes) throws Exception {
        checkPeriodeSaisieNote();
        // List<Note> result = notes.stream().filter(c -> c.getValeur() != 0).collect(toList());
        return noteRepository.save(notes);
    }

    public void checkPeriodeSaisieNote() throws Exception {

        if (notesPeriodeRepository.findByDateDebutBeforeAndDateFinAfter(LocalDate.now(), LocalDate.now()) == null) {
            throw new Exception("Periode de saisie des notes depassée ou non specifiée");
        }

    }

    public Map processNote(List<Object[]> datas, BulletinType typeBulletin) throws Exception {

        List<Note> notes = new ArrayList();
        Set<Coefficient> coefs = new HashSet();

        datas.stream().map((o) -> {
            Coefficient c = (Coefficient) o[1];
            coefs.add(c);
            Note n = (Note) o[0];
            n.setCoefficient(c.getValeur());
            return n;
        }).map((n) -> {
            n.setValeurCoefficie(n.getValeur() * n.getCoefficient());
            return n;
        }).forEachOrdered((n) -> {
            notes.add(n);
        });

        Map<Matiere, Map<ClasseEleve, Double>> notesByMatiere = notes.stream().collect(Collectors.groupingBy(Note::getMatiere, (Collectors.groupingBy(Note::getClasseEleve, Collectors.summingDouble(Note::getValeur)))));
        Map<Matiere, DoubleSummaryStatistics> statsByMatiere = new HashMap();

        notesByMatiere.entrySet().stream().forEach(entry -> {
            statsByMatiere.put(entry.getKey(), entry.getValue().entrySet().stream().map(map -> map.getValue()).collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::accept,
                    DoubleSummaryStatistics::combine));
        });

        Double sumCoefs = coefs.stream().mapToDouble(Coefficient::getValeur).sum();

        Map<ClasseEleve, Double> eleveMoy = notes.stream().collect(
                Collectors.groupingBy(Note::getClasseEleve, Collectors.summingDouble(Note::getValeurCoefficie)));

        eleveMoy.keySet().stream().forEach(e -> eleveMoy.put(e, eleveMoy.get(e) / (sumCoefs * (BulletinType.ANNUEL.equals(typeBulletin) ? 6 : (BulletinType.TRIMESTRE.equals(typeBulletin) ? 2 : 1)))));

        //ranking
        // sort by keys, a,b,c..., and return a new LinkedHashMap
        // toMap() will returns HashMap by default, we need LinkedHashMap to keep the order.
        Map<ClasseEleve, Double> result = eleveMoy.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        return result;
    }

}
