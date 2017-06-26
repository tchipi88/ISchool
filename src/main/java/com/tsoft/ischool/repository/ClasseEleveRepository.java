/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.repository;

import com.tsoft.ischool.domain.Annee;
import com.tsoft.ischool.domain.Classe;
import com.tsoft.ischool.domain.Eleve;
import com.tsoft.ischool.domain.ClasseEleve;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the ClasseEleve entity.
 */
public interface ClasseEleveRepository extends JpaRepository<ClasseEleve, Long> {

    public ClasseEleve findByAnneeAndEleve(Annee anneeCourante, Eleve eleve);

    public List<ClasseEleve> findByAnneeAndClasseOrderByEleveNomAsc(Annee anneeCourante, Classe classe);

    public Page<ClasseEleve> findByClasseId(String classe, Pageable pageable);

    public List<ClasseEleve> findByClasseIdAndAnnee(String idClasse, Annee anneeCourante);
    
    public Integer countByAnneeAndClasse(Annee annee,Classe classe);

    public Page<ClasseEleve> findByClasseIdAndAnnee(String classe, Annee anneeCourante, Pageable pageable);

    public ClasseEleve findByAnneeAndEleveId(Annee anneeCourante, Long eleve);

    public Object countByAnneeAndClasseId(Annee anneeCourante, String classe);

    public List<ClasseEleve> findByClasseIdAndAnneeOrderByEleveNom(String classe, Annee anneeCourante);


}
