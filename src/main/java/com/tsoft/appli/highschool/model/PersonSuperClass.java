/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.model;

import com.tsoft.domain.SimpleLifeCycleEntity;
import com.tsoft.utils.annotations.Label;
import com.tsoft.utils.annotations.Phone;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

/**
 *
 * @author tchipi
 */
@MappedSuperclass
public class PersonSuperClass extends SimpleLifeCycleEntity {

    @Size(max = 100)
    @Column( length = 100)
    @Label("Email")
    private String email;

    @Size(max = 100)
    @Column(length = 100)
    @Label("Téléphone")
    @Phone
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

  
}
