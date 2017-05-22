/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.model;

import com.tsoft.utils.annotations.Label;
import com.tsoft.domain.SimpleAbstractAuditingEntity;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NaturalId;

/**
 *
 * @author tchipi
 */
@Entity
public class Serie extends SimpleAbstractAuditingEntity {

    @Size(max = 100)
    @Column(length = 100)
    private String libelle;
    @Column
    @NaturalId
    private String abreviation;

    @Enumerated(EnumType.STRING)
    @Column
    private Niveau niveau;
    @Column
    @NotNull
    @Label("Inscription Ancien")
    private BigDecimal inscription;
    @Column
    @NotNull
    @Label("Inscription Nouveau")
    private BigDecimal inscription_new;
    @Column
    @NotNull
    @Label("Tranche 1 Ancien")
    private BigDecimal tranche1;
    @Column
    @NotNull
    @Label("Tranche 1 Nouveau")
    private BigDecimal tranche1_new;
    @Column
    @Label("Tranche 2 Ancien")
    private BigDecimal tranche2;
    @Column
    @Label("Tranche 2 Nouveau")
    private BigDecimal tranche2_new;
    @Column
    @Label("Tranche 3 Ancien")
    private BigDecimal tranche3;
    @Column
    @Label("Tranche 3 Nouveau")
    private BigDecimal tranche3_new;
    @Formula("(select ifnull(count(c.code),0) from Classe c RIGHT join Serie s on c.code_serie=s.code  where s.code=code)")
    private Integer nbre_Classes;

//    @JsonIgnore
//    @OneToMany(mappedBy = "codeSerie")
//    private List<Classe> classeslist = new ArrayList<>();
//
//    @JsonIgnore
//    @OneToMany(mappedBy = "codeSerie")
//    private List<Coefficient> Coefficientlist = new ArrayList<>();

    

    public Serie() {
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public BigDecimal getInscription() {
        return inscription;
    }

    public void setInscription(BigDecimal inscription) {
        this.inscription = inscription;
    }

    public BigDecimal getInscription_new() {
        return inscription_new;
    }

    public void setInscription_new(BigDecimal inscription_new) {
        this.inscription_new = inscription_new;
    }

    public BigDecimal getTranche1_new() {
        return tranche1_new;
    }

    public void setTranche1_new(BigDecimal tranche1_new) {
        this.tranche1_new = tranche1_new;
    }

    public BigDecimal getTranche2_new() {
        return tranche2_new;
    }

    public void setTranche2_new(BigDecimal tranche2_new) {
        this.tranche2_new = tranche2_new;
    }

    public BigDecimal getTranche3_new() {
        return tranche3_new;
    }

    public void setTranche3_new(BigDecimal tranche3_new) {
        this.tranche3_new = tranche3_new;
    }

   

    public BigDecimal getTranche1() {
        return tranche1;
    }

    public void setTranche1(BigDecimal tranche1) {
        this.tranche1 = tranche1;
    }

    public BigDecimal getTranche2() {
        return tranche2;
    }

    public void setTranche2(BigDecimal tranche2) {
        this.tranche2 = tranche2;
    }

    public BigDecimal getTranche3() {
        return tranche3;
    }

    public void setTranche3(BigDecimal tranche3) {
        this.tranche3 = tranche3;
    }

    public Integer getNbre_Classes() {
        return nbre_Classes;
    }

    public void setNbre_Classes(Integer nbre_Classes) {
        this.nbre_Classes = nbre_Classes;
    }

}
