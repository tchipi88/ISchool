/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.exams.service;

import java.io.Serializable;

/**
 *
 * @author tchipi
 */
public class CoefficientDTO implements Serializable {

    private Integer code;

    private Integer codeMatiere;

    private Integer codeSerie;

    private Byte valeur;

    private String libelle;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public CoefficientDTO() {
    }

    public Integer getCodeMatiere() {
        return codeMatiere;
    }

    public void setCodeMatiere(Integer codeMatiere) {
        this.codeMatiere = codeMatiere;
    }

    public Integer getCodeSerie() {
        return codeSerie;
    }

    public void setCodeSerie(Integer codeSerie) {
        this.codeSerie = codeSerie;
    }

    public Byte getValeur() {
        return valeur;
    }

    public void setValeur(Byte valeur) {
        this.valeur = valeur;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
