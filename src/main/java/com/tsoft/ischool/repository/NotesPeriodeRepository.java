/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.repository;

import com.tsoft.ischool.domain.NotePeriode;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the NotePeriode entity.
 */
public interface NotesPeriodeRepository extends JpaRepository<NotePeriode, Long> {


    public NotePeriode findByDateDebutBeforeAndDateFinAfter(LocalDate now, LocalDate now0);


    

    

}

