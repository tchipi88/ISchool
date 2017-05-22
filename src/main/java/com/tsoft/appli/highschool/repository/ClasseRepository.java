/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.repository;

import com.tsoft.appli.highschool.model.Classe;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Classe entity.
 */
public interface ClasseRepository extends JpaRepository<Classe, Integer> {

    List<Classe> findBySerieLibelle(String serie);

    List<Classe> findBySerieCode(Integer serie);
}
