/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tsoft.appli.highschool.model;

import com.tsoft.domain.SimpleAbstractAuditingEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

/**
 *
 * @author tchipi
 */
@Entity
public class TypeMatiere extends SimpleAbstractAuditingEntity{
    
   
    @Size(max = 100)
    @Column(length = 100)
    private String libelle;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    
    
}
