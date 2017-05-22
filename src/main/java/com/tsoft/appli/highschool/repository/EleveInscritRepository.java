/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.repository;

import com.tsoft.appli.highschool.model.AnneeScolaire;
import com.tsoft.appli.highschool.model.Classe;
import com.tsoft.appli.highschool.model.Eleve;
import com.tsoft.appli.highschool.model.EleveInscrit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the EleveInscrit entity.
 */
public interface EleveInscritRepository extends JpaRepository<EleveInscrit, Integer> {

    public EleveInscrit findByAnneeAndEleve(AnneeScolaire anneeCourante, Eleve eleve);

    public List<EleveInscrit> findByAnneeAndClasseOrderByEleveNomprenomAsc(AnneeScolaire anneeCourante, Classe eleve);

    public List<EleveInscrit> findByAnneeAndEleveNomprenomContainingOrderByEleveNomprenomAsc(AnneeScolaire anneeCourante, String nomprenom);

}
