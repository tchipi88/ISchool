/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.model;

import com.tsoft.domain.SimpleLifeCycleEntity;
import com.tsoft.utils.annotations.Select;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tchipi
 */
@Entity
public class Timetable extends SimpleLifeCycleEntity {

   
            
    @NotNull
    @JoinColumn(name = "code_creneau", referencedColumnName = "code")
    @ManyToOne
    @Select
    private Creneau creneau;
    @NotNull
    @JoinColumn(name = "code_cours", referencedColumnName = "code")
    @ManyToOne
    @Select
    private Cours cours;
    
    @NotNull
    @JoinColumn(name = "code_jour", referencedColumnName = "code")
    @ManyToOne
    @Select
    private Jour jour;

    @NotNull
    @JoinColumn(name = "code_annee", referencedColumnName = "code")
    @ManyToOne
    @Select
    private AnneeScolaire annee;

    
    public Creneau getCreneau() {
        return creneau;
    }

    public void setCreneau(Creneau creneau) {
        this.creneau = creneau;
    }

    public AnneeScolaire getAnnee() {
        return annee;
    }

    public void setAnnee(AnneeScolaire annee) {
        this.annee = annee;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public Jour getJour() {
        return jour;
    }

    public void setJour(Jour jour) {
        this.jour = jour;
    }

    
    


}
