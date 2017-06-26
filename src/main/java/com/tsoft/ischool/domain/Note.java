/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tsoft.ischool.domain.enumeration.EtatNote;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tchipi
 */
@Entity
public class Note extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private ClasseEleve classeEleve;
    @ManyToOne
    @NotNull
    private Matiere matiere;

    @Column
    @NotNull
    @Min(0)
    @Max(20)
    private double valeur;

    @Transient
    @JsonProperty
    private Integer coefficient;

    @Transient
    @JsonProperty
    private double valeurCoefficie;

    @Min(1)
    @Max(6)
    @NotNull
    private Integer numeroSequence;

    @Enumerated
    private EtatNote statut = EtatNote.DRAFT;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClasseEleve getClasseEleve() {
        return classeEleve;
    }

    public void setClasseEleve(ClasseEleve classeEleve) {
        this.classeEleve = classeEleve;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public Integer getNumeroSequence() {
        return numeroSequence;
    }

    public void setNumeroSequence(Integer numeroSequence) {
        this.numeroSequence = numeroSequence;
    }

    public EtatNote getStatut() {
        return statut;
    }

    public void setStatut(EtatNote statut) {
        this.statut = statut;
    }

    public double getValeurCoefficie() {
        return valeurCoefficie;
    }

    public void setValeurCoefficie(double valeurCoefficie) {
        this.valeurCoefficie = valeurCoefficie;
    }

    public Integer getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Integer coefficient) {
        this.coefficient = coefficient;
    }

    
}
