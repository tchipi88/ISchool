/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tchipi
 */
@Entity
public class ClasseEleve extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @NotNull
    private Classe classe;
    @ManyToOne
    @NotNull
    protected Eleve eleve;
    @ManyToOne
    @NotNull
    protected Annee annee;

    @Column
    private boolean redoublant;
   
//    @OneToMany(mappedBy = "classeEleve")
//    @JsonIgnore
//    protected List<Note> notes = new ArrayList<>();
//
//    @OneToMany(mappedBy = "classeEleve")
//    @JsonIgnore
//    protected List<Reglement> reglements = new ArrayList<>();

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public Annee getAnnee() {
        return annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public boolean isRedoublant() {
        return redoublant;
    }

    public void setRedoublant(boolean redoublant) {
        this.redoublant = redoublant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public List<Note> getNotes() {
//        return notes;
//    }
//
//    public void setNotes(List<Note> notes) {
//        this.notes = notes;
//    }
//
//    public List<Reglement> getReglements() {
//        return reglements;
//    }
//
//    public void setReglements(List<Reglement> reglements) {
//        this.reglements = reglements;
//    }

   

   

    
}
