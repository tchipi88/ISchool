/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.repository;

import com.tsoft.appli.highschool.model.AnneeScolaire;
import com.tsoft.utils.enumerations.DataLifeCycle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the AnneeScolaire entity.
 */
public interface AnneeScolaireRepository extends JpaRepository<AnneeScolaire, Integer> {

    public AnneeScolaire findByCycleVie(DataLifeCycle dataLifeCycle);

    

}

