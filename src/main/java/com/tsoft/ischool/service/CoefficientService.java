/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service;

import com.tsoft.ischool.domain.Coefficient;
import com.tsoft.ischool.domain.Matiere;
import com.tsoft.ischool.domain.Serie;
import com.tsoft.ischool.repository.CoefficientRepository;
import com.tsoft.ischool.repository.MatiereRepository;
import com.tsoft.ischool.repository.SerieRepository;
import java.util.Comparator;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class CoefficientService {

    @Autowired
    MatiereRepository matiereRepository;
    @Autowired
    SerieRepository serieRepository;
    @Autowired
    CoefficientRepository coefficientRepository;

    public List<Coefficient> retrieveDatas(Long Idmatiere) throws Exception {
        List<Serie> serieAll = serieRepository.findAll();
        Matiere matiere = matiereRepository.findOne(Idmatiere);
        List<Coefficient> coefs = coefficientRepository.findByMatiereId(Idmatiere);

        //determine serie sans coefficient 
        List<String> series = coefs.stream().map(c -> c.getSerie().getId()).collect(toList());
        serieAll.stream().filter((s) -> (!series.contains(s.getId()))).map((s) -> {
            Coefficient c = new Coefficient();
            c.setValeur(0D);
            c.setSerie(s);
            return c;
        }).map((c) -> {
            c.setMatiere(matiere);
            return c;
        }).forEachOrdered((c) -> {
            coefs.add(c);
        });
        Comparator<Coefficient> bySerie = (Coefficient c1, Coefficient c2) -> c1.getSerie().getNiveau().compareTo(c2.getSerie().getNiveau());
        List<Coefficient> coefsbySerie = coefs.stream().sorted(bySerie).collect(toList());
        return coefsbySerie;
    }

    public List<Coefficient> save(List<Coefficient> coefficients) throws Exception {
        List<Coefficient> result = coefficients.stream().filter(c -> c.getValeur() != 0).collect(toList());
        return coefficientRepository.save(result);
    }

}
