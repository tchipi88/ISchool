/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.repository;

import com.tsoft.ischool.domain.CaisseEncaissement;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the CaisseEncaissement entity.
 */
public interface CaisseEncaissementRepository extends JpaRepository<CaisseEncaissement, Long> {

    public Page<CaisseEncaissement> findAllByDateVersementBetween(LocalDate fromDate, LocalDate toDate, Pageable pageable);

    

}




