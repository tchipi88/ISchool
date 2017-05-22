/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tsoft.domain.SimpleAbstractAuditingEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Formula;

/**
 *
 * @author tchipi
 */
@Entity
public class Matiere extends SimpleAbstractAuditingEntity {

    @Size(max = 100)
    @Column(length = 100)
    private String libelle;
    @JoinColumn(name = "type", referencedColumnName = "code")
    @ManyToOne
    @NotNull
    private TypeMatiere type;
    @Formula("(select count(p.code) from Matiere m left join Professeur p on m.code=p.code_matiere  where m.code=code)")
    private Integer nbre_Professeurs;

    @JsonIgnore
    @OneToMany(mappedBy = "codeMatiere")
    private List<Coefficient> listcoefficients = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "matiere")
    private List<Professeur> listprofesseurs = new ArrayList<>();

    public List<Coefficient> getListcoefficients() {
        return listcoefficients;
    }

    public void setListcoefficients(List<Coefficient> listcoefficients) {
        this.listcoefficients = listcoefficients;
    }

    public Matiere() {
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public TypeMatiere getType() {
        return type;
    }

    public void setType(TypeMatiere type) {
        this.type = type;
    }

    public Integer getNbre_Professeurs() {
        return nbre_Professeurs;
    }

    public void setNbre_Professeurs(Integer nbre_Professeurs) {
        this.nbre_Professeurs = nbre_Professeurs;
    }

    public List<Professeur> getListprofesseurs() {
        return listprofesseurs;
    }

    public void setListprofesseurs(List<Professeur> listprofesseurs) {
        this.listprofesseurs = listprofesseurs;
    }

}
