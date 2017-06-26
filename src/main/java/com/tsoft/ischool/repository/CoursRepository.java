/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.repository;

import com.tsoft.ischool.domain.Annee;
import com.tsoft.ischool.domain.Classe;
import com.tsoft.ischool.domain.Cours;
import com.tsoft.ischool.domain.Matiere;
import com.tsoft.ischool.domain.Professeur;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Cours entity.
 */
public interface CoursRepository extends JpaRepository<Cours, Long> {

    public List<Cours> findByAnnee(Annee anneeCourante);

    public List<Cours> findByAnneeAndClasse(Annee anneeCourante, Classe classe);


    public List<Cours> findByAnneeAndProfesseurId(Annee anneeCourante, Long prof);

    public List<Cours> findByAnneeAndClasseId(Annee anneeCourante, String classe);



}
