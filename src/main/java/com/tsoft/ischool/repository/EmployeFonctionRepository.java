/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.repository;

import com.tsoft.ischool.domain.EmployeFonction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the EmployeFonction entity.
 */
public interface EmployeFonctionRepository extends JpaRepository<EmployeFonction, Long> {

    public EmployeFonction findByLibelle(String FonctionADMIN);

    

}




