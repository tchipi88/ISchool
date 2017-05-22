/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.repository;

import com.tsoft.appli.highschool.model.Caisse;
import com.tsoft.appli.highschool.model.ClotureCaisse;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the ClotureCaisse entity.
 */
public interface ClotureCaisseRepository extends JpaRepository<ClotureCaisse, Integer> {

    @Query("select v from ClotureCaisse v where v.dateFin>=:date   and v.dateDebut<=:date  and v.caisse=:caisse")
    public List<ClotureCaisse> findByCaisse(@Param("caisse") Caisse caisse, @Param("date") LocalDate date_paiement);

}
