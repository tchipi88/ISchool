/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.repository;

import com.tsoft.ischool.domain.Creneau;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Creneau entity.
 */
public interface CreneauRepository extends JpaRepository<Creneau, Long> {

    public List<Creneau> findByIdNotIn(List<Long> ids);

    

}

