/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.repository;

import com.tsoft.ischool.domain.Annee;
import com.tsoft.ischool.domain.Classe;
import com.tsoft.ischool.domain.Creneau;
import com.tsoft.ischool.domain.Professeur;
import com.tsoft.ischool.domain.Timetable;
import com.tsoft.ischool.domain.enumeration.Jour;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Timetable entity.
 */
public interface TimetableRepository extends JpaRepository<Timetable, Long> {

    public List<Timetable> findByCoursAnnee(Annee anneeCourante);

    public List<Timetable> findByCoursClasseIdAndCoursAnnee(String classe, Annee anneeCourante);

    public List<Timetable> findByCoursProfesseurIdAndCoursAnnee(Long prof, Annee anneeCourante);

    public Optional<Timetable> findByJourAndCreneauAndCoursClasseAndCoursAnnee(Jour jour, Creneau creneau, Classe classe, Annee anneeCourante);

    public Optional<Timetable> findByJourAndCreneauAndCoursProfesseurAndCoursAnnee(Jour jour, Creneau creneau, Professeur professeur, Annee anneeCourante);

    public Integer countByCoursProfesseurAndCoursAnnee(Professeur professeur, Annee anneeCourante);

    

}

