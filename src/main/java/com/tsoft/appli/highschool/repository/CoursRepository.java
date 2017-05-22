/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.repository;

import com.tsoft.appli.highschool.model.AnneeScolaire;
import com.tsoft.appli.highschool.model.Classe;
import com.tsoft.appli.highschool.model.Cours;
import com.tsoft.appli.highschool.model.Matiere;
import com.tsoft.appli.highschool.model.Professeur;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Cours entity.
 */
public interface CoursRepository extends JpaRepository<Cours, Integer> {

    public List<Cours> findByAnnee(AnneeScolaire anneeCourante);

    public List<Cours> findByAnneeAndClasse(AnneeScolaire anneeCourante, Classe classe);

    public List<Cours> findByAnneeAndClasseCode(AnneeScolaire anneeCourante, Integer classe);

    public List<Cours> findByAnneeAndProfesseurCode(AnneeScolaire anneeCourante, Integer prof);

    public Cours findByAnneeAndClasseAndMatiere(AnneeScolaire annee, Classe classe, Matiere matiere);

}
