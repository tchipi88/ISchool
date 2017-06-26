/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.repository;

import com.tsoft.ischool.domain.Annee;
import com.tsoft.ischool.domain.EleveAbsence;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the EleveAbsence entity.
 */
public interface EleveAbsenceRepository extends JpaRepository<EleveAbsence, Long> {

    public List<EleveAbsence> findByClasseEleveClasseIdAndClasseEleveAnneeAndNumeroSequence(String idClasse, Annee anneeCourante, Integer numSeq);

    

}




