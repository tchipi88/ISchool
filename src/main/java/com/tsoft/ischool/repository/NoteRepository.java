/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.repository;

import com.tsoft.ischool.domain.Annee;
import com.tsoft.ischool.domain.Classe;
import com.tsoft.ischool.domain.Note;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Note entity.
 */
public interface NoteRepository extends JpaRepository<Note, Long> {


    public List<Note> findByMatiereIdAndClasseEleveClasseIdAndClasseEleveAnneeAndNumeroSequence(Long idMatiere, String idClasse, Annee anneeCourante, Integer numSeq);

    @Query("select n,coef from Note n  join Coefficient coef on coef.serie=n.classeEleve.classe.serie  and coef.matiere=n.matiere where n.numeroSequence=:numSeq and n.classeEleve.classe.id=:idClasse  and n.classeEleve.annee=:annee")
    public List<Object[]> retrieveNoteSeqWithCoef(@Param("idClasse")String idclasse,@Param("annee") Annee annee,@Param("numSeq") Integer numSeq);
    
    @Query("select n,coef from Note n  join Coefficient coef on coef.serie=n.classeEleve.classe.serie  and coef.matiere=n.matiere where  n.classeEleve.classe.id=:idClasse  and n.classeEleve.annee=:annee")
    public List<Object[]> retrieveNoteAnnuelWithCoef(@Param("idClasse")String idclasse,@Param("annee") Annee annee);
    
    @Query("select n,coef from Note n  join Coefficient coef on coef.serie=n.classeEleve.classe.serie  and coef.matiere=n.matiere where (n.numeroSequence=:numSeq1 or n.numeroSequence=:numSeq2) and n.classeEleve.classe.id=:idClasse  and n.classeEleve.annee=:annee")
    public List<Object[]> retrieveNoteTriWithCoef(@Param("idClasse") String idclasse, @Param("annee") Annee annee, @Param("numSeq1") Integer numSeq1,@Param("numSeq2") Integer numSeq2);

    public List<Note> findByClasseEleveClasseAndClasseEleveAnneeAndNumeroSequence(Classe classe, Annee anneeCourante, Integer numSeq);

    public List<Note> findByClasseEleveClasseAndClasseEleveAnneeAndNumeroSequenceAndMatiereId(Classe classe, Annee anneeCourante, Integer numSeq, Long idmatiere);

    public List<Note> findByClasseEleveClasseAndClasseEleveAnneeAndNumeroSequenceIn(Classe classe, Annee anneeCourante, List<Integer> numSeqs);


    

}




