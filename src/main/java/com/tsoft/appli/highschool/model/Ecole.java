/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.model;

import com.tsoft.utils.annotations.Secure;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import com.tsoft.utils.annotations.Image;

/**
 *
 * @author tchipi
 */
@Entity
public class Ecole extends PersonMoral {

    @Column
    @NotNull
    private String nom;
    @Column
    @NotNull
    private String slogan;

    @Column
    @Image
    @Lob
    private byte [] logo;
    @Column
    @Secure
    private String logoContentType;

    private String principal;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

}
