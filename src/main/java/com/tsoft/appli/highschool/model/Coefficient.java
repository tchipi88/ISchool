/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.model;

import com.tsoft.utils.annotations.Select;
import com.tsoft.domain.SimpleAbstractAuditingEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Formula;

/**
 *
 * @author tchipi
 */
@Entity
public class Coefficient extends SimpleAbstractAuditingEntity {

    @Formula("(select concat(s.libelle,concat('--',m.libelle)) from Coefficient cf "
            + "join Matiere m on m.code=cf.code_matiere join Serie s on s.code=cf.code_serie where m.code=code_matiere and s.code=code_serie limit 1)")
    private String libelle;

    @JoinColumn(name = "code_matiere", referencedColumnName = "code")
    @ManyToOne
    @Select
    private Matiere codeMatiere;

    @JoinColumn(name = "code_serie", referencedColumnName = "code")
    @ManyToOne
    @Select
    private Serie codeSerie;

    @Column
    @NotNull
    @Min(1)
    @Max(7)
    private Byte valeur;

    public Coefficient() {
    }

    

    public Matiere getCodeMatiere() {
        return codeMatiere;
    }

    public void setCodeMatiere(Matiere codeMatiere) {
        this.codeMatiere = codeMatiere;
    }

    public Serie getCodeSerie() {
        return codeSerie;
    }

    public void setCodeSerie(Serie codeSerie) {
        this.codeSerie = codeSerie;
    }

    public Byte getValeur() {
        return valeur;
    }

    public void setValeur(Byte valeur) {
        this.valeur = valeur;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

}
