/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service.reports;

import com.tsoft.ischool.domain.ClasseEleve;
import com.tsoft.ischool.domain.Matiere;
import com.tsoft.ischool.domain.Note;
import com.tsoft.ischool.domain.enumeration.BulletinType;
import com.tsoft.ischool.domain.enumeration.Sexe;
import com.tsoft.ischool.repository.ClasseRepository;
import com.tsoft.ischool.repository.CoefficientRepository;
import com.tsoft.ischool.repository.NoteRepository;
import com.tsoft.ischool.service.AnneeService;
import com.tsoft.ischool.service.NoteService;
import com.tsoft.ischool.service.dto.PVExam;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tchipi
 */
@RestController
@RequestMapping("/api")
public class ReportExam {

    @Autowired
    AnneeService as;

    @Autowired
    JdbcTemplate jt;
    @Autowired
    CoefficientRepository coefficientRepo;
    @Autowired
    ClasseRepository classeRepo;
    @Autowired
    NoteRepository noteRepo;
    @Autowired
    NoteService noteService;

    @GetMapping("/pvTri/{idclasse}/{numTri}")
    public PVExam procesVerbalTrimestre(@PathVariable String idclasse, @PathVariable Integer numTri) throws Exception {

        //Classe classe = classeRepo.findOne(idclasse);
        // List<Integer> numSeqs = Arrays.asList(numTri * 2 - 1, numTri * 2);
        //retrieve all notes of classe for the trimestre
        // List<Note> notes = noteRepo.findByClasseEleveClasseAndClasseEleveAnneeAndNumeroSequenceIn(classe, as.getAnneeCourante(), numSeqs);
        List<Object[]> datas = noteRepo.retrieveNoteTriWithCoef(idclasse, as.getAnneeCourante(), numTri * 2 - 1, numTri * 2);
        return summarizeNote(noteService.processNote(datas, BulletinType.TRIMESTRE));
    }

    @GetMapping("/pvSeq/{idclasse}/{numSeq}")
    public PVExam procesVerbalSequence(@PathVariable String idclasse, @PathVariable Integer numSeq) throws Exception {

        // Classe classe = classeRepo.findOne(idclasse);
        //retrieve all notes of classe for the sequence
        // List<Note> notes = noteRepo.findByClasseEleveClasseAndClasseEleveAnneeAndNumeroSequence(classe, as.getAnneeCourante(), numSeq);
        List<Object[]> datas = noteRepo.retrieveNoteSeqWithCoef(idclasse, as.getAnneeCourante(), numSeq);
        return summarizeNote(noteService.processNote(datas, BulletinType.SEQUENCE));
    }

    public PVExam summarizeNote(Map<ClasseEleve, Double> eleveMoy) throws Exception {
        PVExam result = new PVExam();

        result.setEffectif(eleveMoy.keySet().size());

        Long nbremoyG = eleveMoy.entrySet().stream().filter(map -> Sexe.G.equals(map.getKey().getEleve().getSexe())).map(map -> map.getValue()).filter(moy -> moy >= 10).count();
        Long nbremoyF = eleveMoy.entrySet().stream().filter(map -> Sexe.F.equals(map.getKey().getEleve().getSexe())).map(map -> map.getValue()).filter(moy -> moy >= 10).count();

        result.setNbremoyG(nbremoyG);
        result.setNbremoyF(nbremoyF);

        Long nbresousmoyG = eleveMoy.entrySet().stream().filter(map -> Sexe.G.equals(map.getKey().getEleve().getSexe())).map(map -> map.getValue()).filter(moy -> moy < 10).count();
        Long nbresousmoyF = eleveMoy.entrySet().stream().filter(map -> Sexe.F.equals(map.getKey().getEleve().getSexe())).map(map -> map.getValue()).filter(moy -> moy < 10).count();

        result.setNbresousmoyG(nbresousmoyG);
        result.setNbresousmoyF(nbresousmoyF);

        DoubleSummaryStatistics moyGenerale = eleveMoy.entrySet().stream().map(map -> map.getValue()).collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::accept,
                DoubleSummaryStatistics::combine);

        result.setAvgmoy(moyGenerale.getAverage());
        result.setMinmoy(moyGenerale.getMin());
        result.setMaxmoy(moyGenerale.getMax());

        DoubleSummaryStatistics moyGarcon = eleveMoy.entrySet().stream().filter(map -> Sexe.G.equals(map.getKey().getEleve().getSexe())).map(map -> map.getValue()).collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::accept,
                DoubleSummaryStatistics::combine);

        result.setAvgmoyG(moyGarcon.getAverage());
        result.setMinmoyG(moyGarcon.getMin());
        result.setMaxmoyG(moyGarcon.getMax());

        DoubleSummaryStatistics moyFille = eleveMoy.entrySet().stream().filter(map -> Sexe.F.equals(map.getKey().getEleve().getSexe())).map(map -> map.getValue()).collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::accept,
                DoubleSummaryStatistics::combine);

        result.setAvgmoyF(moyFille.getAverage());
        result.setMinmoyF(moyFille.getMin());
        result.setMaxmoyF(moyFille.getMax());

        Long tableauHonneurGarcon = eleveMoy.entrySet().stream().filter(map -> Sexe.G.equals(map.getKey().getEleve().getSexe())).map(map -> map.getValue()).filter(moy -> moy >= 12).count();
        Long tableauHonneurMoyFille = eleveMoy.entrySet().stream().filter(map -> Sexe.F.equals(map.getKey().getEleve().getSexe())).map(map -> map.getValue()).filter(moy -> moy >= 12).count();

        result.setThG(tableauHonneurGarcon);
        result.setThF(tableauHonneurMoyFille);
        return result;
    }

//    @GetMapping("/pv/{idclasse}/{idmatiere}/{numSeq}")
//    public PVExam procesVerbalSequenceMatiere(@PathVariable String idclasse, @PathVariable Long idmatiere, @PathVariable Integer numSeq) throws Exception {
//
//        Classe classe = classeRepo.findOne(idclasse);
//        //retrieve all notes of classe for the sequence
//        List<Note> notes = noteRepo.findByClasseEleveClasseAndClasseEleveAnneeAndNumeroSequenceAndMatiereId(classe, as.getAnneeCourante(), numSeq, idmatiere);
//        return summarizeNote(processNote(classe, notes, BulletinType.SEQUENCE));
//    }
    public void processNotes(List<Note> notes) {
        Map<ClasseEleve, Map<Matiere, Double>> notesGroupByEleveMatiere
                = notes.stream().collect(Collectors.groupingBy(Note::getClasseEleve, Collectors.groupingBy(Note::getMatiere, Collectors.averagingDouble(Note::getValeur))));

    }
}
