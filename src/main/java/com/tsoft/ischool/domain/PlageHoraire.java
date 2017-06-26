/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tsoft.ischool.domain;

import com.tsoft.ischool.domain.enumeration.Jour;
import java.io.Serializable;

/**
 *
 * @author tchipi
 */
public class PlageHoraire  implements Serializable{
    
    Creneau  creneau;
    Jour jour;

    public Creneau getCreneau() {
        return creneau;
    }

    public void setCreneau(Creneau creneau) {
        this.creneau = creneau;
    }

    public Jour getJour() {
        return jour;
    }

    public void setJour(Jour jour) {
        this.jour = jour;
    }

   

    
    
    
    
    
}
