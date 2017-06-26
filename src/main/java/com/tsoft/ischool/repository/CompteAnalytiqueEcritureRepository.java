/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.repository;

import com.tsoft.ischool.domain.CompteAnalytiqueEcriture;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the CompteAnalytiqueEcriture entity.
 */
public interface CompteAnalytiqueEcritureRepository extends JpaRepository<CompteAnalytiqueEcriture, Long> {

    public Page<CompteAnalytiqueEcriture> findAllByDateEcritureBetween(LocalDateTime atTime, LocalDateTime atTime0, Pageable pageable);

    

}




