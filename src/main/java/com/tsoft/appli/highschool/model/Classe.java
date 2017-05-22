/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.model;

import com.tsoft.utils.annotations.Select;
import com.tsoft.utils.annotations.SpelValue;
import com.tsoft.domain.SimpleAbstractAuditingEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Formula;

/**
 *
 * @author tchipi
 */
@Entity
public class Classe extends SimpleAbstractAuditingEntity {

    @Column
//    @SpelValue("serie.abreviation +(codeSerie.nbre_Classes + 1)")
    private String libelle;
    @JoinColumn(name = "code_serie", referencedColumnName = "code")
    @ManyToOne
    @Select
    @NotNull
    private Serie serie;
    @Formula("(select count(ei.code) from Classe c left join EleveInscrit ei on c.code=ei.code_classe  where c.code=code"
            + "  and ei.cycle_vie='ACTIF')")
    private Integer nbre_Eleves;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Integer getNbre_Eleves() {
        return nbre_Eleves;
    }

    public void setNbre_Eleves(Integer nbre_Eleves) {
        this.nbre_Eleves = nbre_Eleves;
    }

}
