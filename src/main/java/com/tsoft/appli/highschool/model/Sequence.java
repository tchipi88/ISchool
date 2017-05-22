/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.model;

import com.tsoft.domain.SimpleAbstractAuditingEntity;
import javax.persistence.Entity;
import org.hibernate.annotations.Formula;

/**
 *
 * @author tchipi
 */
@Entity
public class Sequence extends SimpleAbstractAuditingEntity {

    @Formula("concat('Sequence',code)")
    private String libelle;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

}
