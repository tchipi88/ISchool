/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.repository;

import com.tsoft.appli.highschool.model.Caisse;
import com.tsoft.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Caisse entity.
 */
public interface CaisseRepository extends JpaRepository<Caisse, Integer> {

    public List<Caisse> findByGerantAndCode(User u, Integer code);

    

}

