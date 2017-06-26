/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.repository;

import com.tsoft.ischool.domain.Classe;
import com.tsoft.ischool.domain.Serie;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Classe entity.
 */
public interface ClasseRepository extends JpaRepository<Classe, String> {

    public List<Classe> findBySerie(Serie s);

   
    
}
