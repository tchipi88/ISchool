/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.repository;

import com.tsoft.ischool.domain.Annee;
import com.tsoft.ischool.domain.enumeration.EtatAnnee;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
public interface AnneeRepository extends JpaRepository<Annee, String> {

    public Annee findByDateDebutAfterAndDateFinBefore(LocalDate debut, LocalDate fin);


    public Annee findByEtat(EtatAnnee etatAnnee);
    
}
