/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.repository;

import com.tsoft.ischool.domain.Annee;
import com.tsoft.ischool.domain.FraisScolarite;
import com.tsoft.ischool.domain.Serie;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Compte entity.
 */
public interface FraisScolariteRepository extends JpaRepository<FraisScolarite, Long> {


    public List<FraisScolarite> findBySerieIdAndAnnee(String serie, Annee anneeCourante);

    public FraisScolarite findByAnneeAndSerie(Annee anneeCourante, Serie serie);

    public List<FraisScolarite> findByAnnee(Annee anneeCourante);

    

}




