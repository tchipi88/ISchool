/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import org.hibernate.validator.constraints.URL;

/**
 *
 * @author tchipi
 */
@MappedSuperclass
public class PersonMoral extends PersonSuperClass {

   //pour les societes  Registre Commerce,Num contribuable

    @Column
    @URL
    private String siteweb;
    @Column(name = "boite_postale")
    private String boitePostale;
    

    
    public String getSiteweb() {
        return siteweb;
    }

    public void setSiteweb(String siteweb) {
        this.siteweb = siteweb;
    }

    public String getBoitePostale() {
        return boitePostale;
    }

    public void setBoitePostale(String boitePostale) {
        this.boitePostale = boitePostale;
    }


    
    
    
    
    
    
    

}
