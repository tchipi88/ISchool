/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.repository;

import com.tsoft.ischool.domain.Reglement;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Reglement entity.
 */
public interface ReglementRepository extends JpaRepository<Reglement, Long> {

    public Page<Reglement> findAllByDateVersementBetween(LocalDate fromDate, LocalDate toDate, Pageable pageable);



}

