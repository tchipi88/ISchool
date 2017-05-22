/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.repository;

import com.tsoft.appli.highschool.model.Encaissement;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Encaissement entity.
 */
public interface EncaissementRepository extends JpaRepository<Encaissement, Integer> {

    

}

