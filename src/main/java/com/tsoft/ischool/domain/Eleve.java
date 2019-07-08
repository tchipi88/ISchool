/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tsoft.ischool.domain.enumeration.Sexe;
import com.tsoft.ischool.service.template.Image;
import com.tsoft.ischool.service.template.Label;
import com.tsoft.ischool.service.template.Libelle;
import com.tsoft.ischool.service.template.Phone;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 *
 * @author tchipi
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "eleve")
public class Eleve extends AbstractAuditingEntity  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "photo")
    @Image
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @NotNull
    @Libelle
    @Column
    private String nom;

    private String prenom;
    @Column
    @Enumerated(EnumType.STRING)
    private Sexe sexe;

    @Column
    @Label("Né(e) le")
    private LocalDate dateNaissance;

    @Size(max = 100)
    @Column(length = 100)
    private String lieuNaissance;

    @Size(max = 100)
    @Column(length = 100)
    @Label("Téléphone")
    private String telephone;

    @Column
    private String adresse;

    //adress Parents
    @Column
    private String nomPere;
    @Column
    private String professionPere;
    @Column
    @Phone
    private String telephonePere;
    @Column
    private String nomMere;
    @Column
    @Phone
    private String telephoneMere;
    @Column
    private String professionMere;
    
    @OneToOne(mappedBy = "eleve")
    @JsonIgnore
    private CompteAnalytique  compte;

    @Column
    private Long idPerson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getNomPere() {
        return nomPere;
    }

    public void setNomPere(String nomPere) {
        this.nomPere = nomPere;
    }

    public String getProfessionPere() {
        return professionPere;
    }

    public void setProfessionPere(String professionPere) {
        this.professionPere = professionPere;
    }

    public String getTelephonePere() {
        return telephonePere;
    }

    public void setTelephonePere(String telephonePere) {
        this.telephonePere = telephonePere;
    }

    public String getNomMere() {
        return nomMere;
    }

    public void setNomMere(String nomMere) {
        this.nomMere = nomMere;
    }

    public String getTelephoneMere() {
        return telephoneMere;
    }

    public void setTelephoneMere(String telephoneMere) {
        this.telephoneMere = telephoneMere;
    }

    public String getProfessionMere() {
        return professionMere;
    }

    public void setProfessionMere(String professionMere) {
        this.professionMere = professionMere;
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

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
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

    public CompteAnalytique getCompte() {
        return compte;
    }

    public void setCompte(CompteAnalytique compte) {
        this.compte = compte;
    }

    public Long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Long idPerson) {
        this.idPerson = idPerson;
    }
}
