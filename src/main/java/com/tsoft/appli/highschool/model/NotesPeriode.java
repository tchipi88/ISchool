/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tsoft.appli.highschool.model;

import com.tsoft.domain.SimpleAbstractAuditingEntity;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tchipi
 */
@Entity
public class NotesPeriode  extends SimpleAbstractAuditingEntity{
    
    @Column(name = "date_debut")
    @NotNull
    private LocalDate dateDebut;
    @Column(name = "date_fin")
    @NotNull
    private LocalDate dateFin;

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
