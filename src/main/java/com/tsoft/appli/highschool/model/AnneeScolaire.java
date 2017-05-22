/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.model;

import com.tsoft.domain.SimpleLifeCycleEntity;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Formula;

/**
 *
 * @author tchipi
 */
@Entity
public class AnneeScolaire extends SimpleLifeCycleEntity {

    @Formula("concat(date_format(date_debut,'%Y'),concat('/',date_format(date_fin,'%Y')))")
    private String libelle;
    @Column(name="date_debut")
    @NotNull
    private LocalDate dateDebut;
    @Column(name = "date_fin")
    @NotNull
    private LocalDate dateFin;

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

   
}
