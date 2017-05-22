/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.model;

import com.tsoft.domain.SimpleLifeCycleEntity;
import com.tsoft.utils.annotations.Select;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 *
 * @author tchipi
 */
@Entity
public class Reglement extends SimpleLifeCycleEntity {

    @Column(name = "date_reglement")
    @NotNull
    private LocalDate dateReglement;
    @ManyToOne
    @JoinColumn(name = "code_eleveinscrit", referencedColumnName = "code")
    @NotNull
    private EleveInscrit eleveinscrit;
    @Enumerated
    @Null
    private ObjectEntreeCaisse motif;
    @NotNull
    @Column
    BigDecimal montant;
    @JoinColumn(name = "code_caisse", referencedColumnName = "code")
    @ManyToOne
    @Select
    private Caisse caisse;

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public Caisse getCaisse() {
        return caisse;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }
    
    

    public LocalDate getDateReglement() {
        return dateReglement;
    }

    public void setDateReglement(LocalDate dateReglement) {
        this.dateReglement = dateReglement;
    }

   

    public EleveInscrit getEleveinscrit() {
        return eleveinscrit;
    }

    public void setEleveinscrit(EleveInscrit eleveinscrit) {
        this.eleveinscrit = eleveinscrit;
    }

    public ObjectEntreeCaisse getMotif() {
        return motif;
    }

    public void setMotif(ObjectEntreeCaisse motif) {
        this.motif = motif;
    }

   
    
    
    
}
