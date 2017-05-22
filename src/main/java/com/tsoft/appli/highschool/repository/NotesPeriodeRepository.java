/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.repository;

import com.tsoft.appli.highschool.model.NotesPeriode;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the NotesPeriode entity.
 */
public interface NotesPeriodeRepository extends JpaRepository<NotesPeriode, Integer> {

    @Query("select v from NotesPeriode v where v.dateFin>=:date   and v.dateDebut<=:date")
    public List<NotesPeriode> checkPeriodeSaisieNote(@Param("date") LocalDate now);

    

}

