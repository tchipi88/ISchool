/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tsoft.appli.highschool.model;

import java.io.Serializable;

/**
 *
 * @author tchipi
 */
public class CoursOccurence implements Serializable{
    
    public CoursOccurence(){
        
    }
    
    private Cours cours;
    private Short num_occurence;

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public Short getNum_occurence() {
        return num_occurence;
    }

    public void setNum_occurence(Short num_occurence) {
        this.num_occurence = num_occurence;
    }

   
    
    
}
