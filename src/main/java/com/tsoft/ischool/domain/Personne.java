/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.domain;

import com.tsoft.ischool.domain.enumeration.Civilite;
import com.tsoft.ischool.service.template.Label;
import com.tsoft.ischool.service.template.Libelle;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author tchipi
 */
@MappedSuperclass
public class Personne extends AbstractAuditingEntity {

    @NotNull
    @Libelle
    @Column
    private String nom;
    
    private String prenom;
    @Column
    @Enumerated(EnumType.STRING)
    private Civilite civilite;

    @Column
    @Label("Né(e) le")
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
    @Label("Téléphone")
    private String telephone;

    @Column
    private String adresse;

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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
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

}
