/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.domain;

import com.tsoft.ischool.domain.enumeration.TypeMatiere;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Matiere extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(length = 100)
    @NotNull
    private String libelle;
    @NotNull
    @Enumerated
    private TypeMatiere type;
    @Formula("(select count(p.id) from matiere m left join professeur p on m.id=p.matiere_id  where m.id=id)")
    private Integer nombreProfesseurs;

    @JsonIgnore
    @OneToMany(mappedBy = "matiere")
    private List<Coefficient> coefficients = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "matiere")
    private List<Professeur> professeurs = new ArrayList<>();

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNombreProfesseurs() {
        return nombreProfesseurs;
    }

    public void setNombreProfesseurs(Integer nombreProfesseurs) {
        this.nombreProfesseurs = nombreProfesseurs;
    }

    public List<Coefficient> getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(List<Coefficient> coefficients) {
        this.coefficients = coefficients;
    }

    public List<Professeur> getProfesseurs() {
        return professeurs;
    }

    public void setProfesseurs(List<Professeur> professeurs) {
        this.professeurs = professeurs;
    }

}
