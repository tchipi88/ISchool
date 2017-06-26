/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.repository;

import com.tsoft.ischool.domain.Coefficient;
import com.tsoft.ischool.domain.Matiere;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Coefficient entity.
 */
public interface CoefficientRepository extends JpaRepository<Coefficient, Long> {

    public List<Coefficient> findByMatiereId(Long matiere);

    public List<Coefficient> findBySerieId(String serie);

    public List<Coefficient> findBySerieIdAndMatiereIdIn(String id, List<Long> idMatieres);

    

}

