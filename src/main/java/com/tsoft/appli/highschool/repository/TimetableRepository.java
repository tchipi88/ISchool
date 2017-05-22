/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.repository;

import com.tsoft.appli.highschool.model.AnneeScolaire;
import com.tsoft.appli.highschool.model.Timetable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Timetable entity.
 */
public interface TimetableRepository extends JpaRepository<Timetable, Integer> {

    public List<Timetable> findByAnnee(AnneeScolaire anneeCourante);

    

}

