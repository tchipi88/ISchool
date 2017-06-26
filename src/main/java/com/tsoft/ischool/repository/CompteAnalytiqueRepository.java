/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.repository;

import com.tsoft.ischool.domain.Annee;
import com.tsoft.ischool.domain.CompteAnalytique;
import com.tsoft.ischool.domain.Eleve;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the CompteAnalytique entity.
 */
public interface CompteAnalytiqueRepository extends JpaRepository<CompteAnalytique, Long> {

    public CompteAnalytique findByEleve(Eleve eleve);

    @Query("select c from CompteAnalytique c join ClasseEleve ce on c.eleve=ce.eleve and ce.classe.id=:classe and ce.annee=:annee ")
    public Page<CompteAnalytique> findByClasse(Pageable pageable, @Param("classe") String classe, @Param("annee") Annee annee);

    @Query("select c from CompteAnalytique c join ClasseEleve ce on c.eleve=ce.eleve and ce.classe.id=:classe and ce.annee=:annee ")
    public List<CompteAnalytique> findByClasse(@Param("classe") String classe, @Param("annee") Annee anneeCourante);

}
