/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.model;

import com.tsoft.utils.annotations.ReadOnly;
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
public class Cours extends SimpleAbstractAuditingEntity {

    @SpelValue("professeur.nomprenom +'-'+ classe.libelle")
    @Column
    private String libelle;

    @JoinColumn(name = "code_matiere", referencedColumnName = "code")
    @ReadOnly
    @ManyToOne
    @SpelValue("professeur.matiere")
    private Matiere matiere;

    @NotNull
    @JoinColumn(name = "code_professeur", referencedColumnName = "code")
    @Select
    @ManyToOne
    private Professeur professeur;

    @NotNull
    @JoinColumn(name = "code_classe", referencedColumnName = "code")
    @Select
    @ManyToOne
    private Classe classe;

    @NotNull
    @JoinColumn(name = "code_annee", referencedColumnName = "code")
    @ManyToOne
    @ReadOnly
    @Select
    private AnneeScolaire annee;

    @Column
    @NotNull
    private Short duree_heures;

    @Formula("duree_heures*60")
    private Short duree_minutes;

    public Professeur getProfesseur() {
        return professeur;
    }

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Short getDuree_heures() {
        return duree_heures;
    }

    public void setDuree_heures(Short duree_heures) {
        this.duree_heures = duree_heures;
    }

    public Short getDuree_minutes() {
        return duree_minutes;
    }

    public void setDuree_minutes(Short duree_minutes) {
        this.duree_minutes = duree_minutes;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public AnneeScolaire getAnnee() {
        return annee;
    }

    public void setAnnee(AnneeScolaire annee) {
        this.annee = annee;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

   

}
