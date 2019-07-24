/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.domain;

import com.tsoft.ischool.domain.enumeration.Civilite;
import com.tsoft.ischool.domain.enumeration.Sexe;
import com.tsoft.ischool.domain.enumeration.TypePersonne;
import com.tsoft.ischool.service.template.Label;
import com.tsoft.ischool.service.template.Libelle;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 *
 * @author tchipi
 */
@Entity
@Table(name="person")
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "person")
public class Person extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Libelle
    @Column
    private String nomPrenom;

//    @Size(max = 15)
    @Column(name="type_personne")
    @Enumerated(EnumType.STRING)
    private TypePersonne typePersonne;

//    @Size(max = 15)
    @Column(name="sexe")
    @Enumerated(EnumType.STRING)
    private Sexe sexe;

    @Column
    @Enumerated(EnumType.STRING)
    private Civilite civilite;

    @Column
    @Label("Birth Date")
    private LocalDate dateNaissance;

    @Size(max = 100)
    @Column(length = 100)
    private String lieuNaissance;

    @Column
    private Integer numeroCNI;
    @Column
    private LocalDate dateDelivranceCNI;
    @Column
    private String lieuDelivranceCNI;

    @Size(max = 100)
    @Column(length = 100)
    @Label("Email")
    private String email;

    @Size(max = 100)
    @Column(length = 100)
    @Label("Phone")
    private String telephone;

    @Column
    private String adresse;

    public Person(){}

    public Person(String nomPrenom, TypePersonne typePersonne, Sexe sexe){
        this.nomPrenom=nomPrenom;
        this.typePersonne = typePersonne;
        this.sexe = sexe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomPrenom() {
        return nomPrenom;
    }

    public void setNomPrenom(String nomPrenom) {
        this.nomPrenom = nomPrenom;
    }

    public TypePersonne getTypePersonne() {
        return typePersonne;
    }

    public void setTypePersonne(TypePersonne typePersonne) {
        this.typePersonne = typePersonne;
    }

    public Civilite getCivilite() {
        return civilite;
    }

    public void setCivilite(Civilite civilite) {
        this.civilite = civilite;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public Integer getNumeroCNI() {
        return numeroCNI;
    }

    public void setNumeroCNI(Integer numeroCNI) {
        this.numeroCNI = numeroCNI;
    }

    public LocalDate getDateDelivranceCNI() {
        return dateDelivranceCNI;
    }

    public void setDateDelivranceCNI(LocalDate dateDelivranceCNI) {
        this.dateDelivranceCNI = dateDelivranceCNI;
    }

    public String getLieuDelivranceCNI() {
        return lieuDelivranceCNI;
    }

    public void setLieuDelivranceCNI(String lieuDelivranceCNI) {
        this.lieuDelivranceCNI = lieuDelivranceCNI;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }
}
