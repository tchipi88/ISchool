/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service;

import com.tsoft.ischool.domain.FraisScolarite;
import com.tsoft.ischool.domain.Serie;
import com.tsoft.ischool.repository.FraisScolariteRepository;
import com.tsoft.ischool.repository.SerieRepository;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class FraisScolariteService {

    @Autowired
    SerieRepository serieRepository;
    @Autowired
    FraisScolariteRepository fraisScolariteRepository;
    @Autowired
    AnneeService anneeService;

    public List<FraisScolarite> retrieveDatas() throws Exception {
        List<Serie> serieAll = serieRepository.findAll();
        List<FraisScolarite> frais = fraisScolariteRepository.findByAnnee(anneeService.getAnneeCourante());

        //determine serie sans coefficient 
        List<String> series = frais.stream().map(c -> c.getSerie().getId()).collect(toList());
        serieAll.stream().filter((s) -> (!series.contains(s.getId()))).map((s) -> {
            FraisScolarite c = new FraisScolarite();
            c.setSerie(s);
            return c;
        }).map((c) -> {
            try {
                c.setAnnee(anneeService.getAnneeCourante());
            } catch (Exception ex) {
                Logger.getLogger(FraisScolariteService.class.getName()).log(Level.SEVERE, null, ex);
            }
            return c;
        }).forEachOrdered((c) -> {
            frais.add(c);
        });
        Comparator<FraisScolarite> bySerie = (FraisScolarite c1, FraisScolarite c2) -> c1.getSerie().getNiveau().compareTo(c2.getSerie().getNiveau());
        List<FraisScolarite> fraisbySerie = frais.stream().sorted(bySerie).collect(toList());
        return fraisbySerie;
    }

    public List<FraisScolarite> save(List<FraisScolarite> frais) throws Exception {
         List<FraisScolarite> result = frais.stream().filter(c -> !c.getFraisExigible().equals(BigDecimal.ZERO)).collect(toList());
        return fraisScolariteRepository.save(result);
    }
    
    

}
