/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.model;

import com.tsoft.domain.SimpleLifeCycleEntity;
import com.tsoft.utils.annotations.Select;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Formula;

/**
 *
 * @author eisti
 */
@Entity
public class ClotureCaisse extends SimpleLifeCycleEntity {

    @Formula("concat(date_format(date_debut,'%d/%m/%Y'),concat('-',date_format(date_fin,'%d/%m/%Y')))")
    private String libelle;

    @NotNull
    @Column(name = "date_debut")
    private LocalDate dateDebut;
    @NotNull
    @Column(name = "date_fin")
    private LocalDate dateFin;
    
    @JoinColumn(name = "code_caisse", referencedColumnName = "code")
    @ManyToOne
    @Select
    private Caisse caisse;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    

    

    public Caisse getCaisse() {
        return caisse;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }
    
    
    

}
