/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tsoft.appli.highschool.model;

import com.tsoft.utils.annotations.Label;
import com.tsoft.utils.annotations.Libelle;
import com.tsoft.utils.enumerations.Civilite;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author tchipi
 */

@MappedSuperclass
public class PersonPhysique  extends  PersonSuperClass {
    
    
    @NotNull
    @Libelle
    @Label("Nom Et Prénom")
    @Column(name="nom_prenom")
    private String nomprenom;
    @Column
    @Enumerated(EnumType.STRING)
    private Civilite  civilite;
   
    @Column( length = 100)
    @Label("Né(e) le")
    @Temporal(TemporalType.DATE)
    private Date date_naissance;
       
    @Size(max = 100)
    @Column( length = 100)
    private String lieu_naissance;
    
    @Column
    private Integer numero_cni;
    @Column
    @Temporal(TemporalType.DATE)
    private Date date_delivrance_cni;
    @Column
    private String lieu_delivrance_cni;

    public String getNomprenom() {
        return nomprenom;
    }

    public void setNomprenom(String nomPrenom) {
        this.nomprenom = nomPrenom;
    }
    
    
    
    

    

    

    public Civilite getCivilite() {
        return civilite;
    }

    public void setCivilite(Civilite civilite) {
        this.civilite = civilite;
    }

    public Integer getNumero_cni() {
        return numero_cni;
    }

    public Date getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getLieu_naissance() {
        return lieu_naissance;
    }

    public void setLieu_naissance(String lieu_naissance) {
        this.lieu_naissance = lieu_naissance;
    }

    public void setNumero_cni(Integer numero_cni) {
        this.numero_cni = numero_cni;
    }

    public Date getDate_delivrance_cni() {
        return date_delivrance_cni;
    }

    public void setDate_delivrance_cni(Date date_delivrance_cni) {
        this.date_delivrance_cni = date_delivrance_cni;
    }

    public String getLieu_delivrance_cni() {
        return lieu_delivrance_cni;
    }

    public void setLieu_delivrance_cni(String lieu_delivrance_cni) {
        this.lieu_delivrance_cni = lieu_delivrance_cni;
    }
    
    
   
}
