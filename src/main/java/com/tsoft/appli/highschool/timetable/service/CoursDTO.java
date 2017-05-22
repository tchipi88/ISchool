/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tsoft.appli.highschool.timetable.service;

import java.io.Serializable;

/**
 *
 * @author tchipi
 */
public class CoursDTO  implements Serializable{
    
    
    private Integer classe;
    private Short duree_heures;
    private Integer code;
    private String libelle;
    
    public CoursDTO(){
        
    }

    public Integer getClasse() {
        return classe;
    }

    public void setClasse(Integer classe) {
        this.classe = classe;
    }

    public Short getDuree_heures() {
        return duree_heures;
    }

    public void setDuree_heures(Short duree_heures) {
        this.duree_heures = duree_heures;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
    
    
}
