/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.model;

import com.tsoft.domain.SimpleLifeCycleEntity;
import com.tsoft.utils.annotations.Label;
import com.tsoft.utils.annotations.ReadOnly;
import com.tsoft.utils.annotations.Select;
import com.tsoft.utils.annotations.SpelValue;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Formula;

/**
 *
 * @author User
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public  class MouvementCaisse extends SimpleLifeCycleEntity {

    @Formula("(concat(code,concat('/P/',date_format(date_paiement,'%m/%Y'))))")
    @Column
    private String reference;

    @Column
    @NotNull
    private BigDecimal montant;

    @Column
    @Enumerated(EnumType.STRING)
    private MoyenPaiement moyen_paiement;

    @Column
    @NotNull
    private LocalDate date_paiement;

    @JoinColumn(name = "code_caisse", referencedColumnName = "code")
    @ManyToOne
    @Select
    private Caisse caisse;
    @Column
    @ReadOnly
    private BigDecimal solde;
    @Column
    @SpelValue("caisse.solde_theorique")
    @Label("Solde Caisse Initial")
    private BigDecimal solde_initial;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public MoyenPaiement getMoyen_paiement() {
        return moyen_paiement;
    }

    public void setMoyen_paiement(MoyenPaiement moyen_paiement) {
        this.moyen_paiement = moyen_paiement;
    }

    public LocalDate getDate_paiement() {
        return date_paiement;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    public BigDecimal getSolde_initial() {
        return solde_initial;
    }

    public void setSolde_initial(BigDecimal solde_initial) {
        this.solde_initial = solde_initial;
    }
    
    

    public void setDate_paiement(LocalDate date_paiement) {
        this.date_paiement = date_paiement;
    }

    public Caisse getCaisse() {
        return caisse;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }

}
