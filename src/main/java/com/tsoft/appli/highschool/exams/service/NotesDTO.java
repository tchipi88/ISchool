/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tsoft.appli.highschool.exams.service;

import com.tsoft.utils.enumerations.DataLifeCycle;
import java.io.Serializable;

/**
 *
 * @author tchipi
 */
public class NotesDTO  implements  Serializable{
    
   
    private Integer code;
    private Integer eleve;
    private Integer coef;
    private double note;
    private String matricule;
    private String nom_prenom;
    private DataLifeCycle cycle_vie;
   

    public NotesDTO() {
    }

    
    public Integer getEleve() {
        return eleve;
    }

    public void setEleve(Integer eleve) {
        this.eleve = eleve;
    }

    public Integer getCoef() {
        return coef;
    }

    public void setCoef(Integer coef) {
        this.coef = coef;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

   

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom_prenom() {
        return nom_prenom;
    }

    public void setNom_prenom(String nom_prenom) {
        this.nom_prenom = nom_prenom;
    }

    public DataLifeCycle getCycle_vie() {
        return cycle_vie;
    }

    public void setCycle_vie(DataLifeCycle cycle_vie) {
        this.cycle_vie = cycle_vie;
    }

    
    
}
