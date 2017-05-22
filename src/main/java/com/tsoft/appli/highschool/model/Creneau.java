/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.model;

import com.tsoft.domain.SimpleAbstractAuditingEntity;
import com.tsoft.utils.annotations.Label;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Formula;

/**
 *
 * @author tchipi
 */
@Entity
public class Creneau extends SimpleAbstractAuditingEntity {
    
    @Formula("concat(date_format(heure_debut,'%H:%i'),concat('-',date_format(heure_fin,'%H:%i')))")
    private String libelle;

    @NotNull
    @Column(name = "heure_debut")
    private LocalTime heureDebut;
    @NotNull
    @Column(name = "heure_fin")
    private LocalTime heureFin;
    
    @Column
    @Label("Pause")
    private boolean disponible;

    

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

//    @Formula
//    private double duree;
    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    
    

}
