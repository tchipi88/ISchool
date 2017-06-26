/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service.dto;

import com.tsoft.ischool.domain.Creneau;
import com.tsoft.ischool.domain.Timetable;
import java.io.Serializable;

/**
 *
 * @author tchipnangngansopa
 */
public class TimetableDTO implements Serializable{
    
    private Creneau creneau;
    private Timetable lundi;
    private Timetable mardi;
    private Timetable mercredi;
    private Timetable jeudi;
    private Timetable vendredi;

    public TimetableDTO() {
    }

    public TimetableDTO(Creneau creneau) {
        this.creneau = creneau;
    }
    
    

    public Timetable getLundi() {
        return lundi;
    }

    public void setLundi(Timetable lundi) {
        this.lundi = lundi;
    }

    public Timetable getMardi() {
        return mardi;
    }

    public void setMardi(Timetable mardi) {
        this.mardi = mardi;
    }

    public Timetable getMercredi() {
        return mercredi;
    }

    public void setMercredi(Timetable mercredi) {
        this.mercredi = mercredi;
    }

    public Timetable getJeudi() {
        return jeudi;
    }

    public void setJeudi(Timetable jeudi) {
        this.jeudi = jeudi;
    }

    public Timetable getVendredi() {
        return vendredi;
    }

    public void setVendredi(Timetable vendredi) {
        this.vendredi = vendredi;
    }

    public Creneau getCreneau() {
        return creneau;
    }

    public void setCreneau(Creneau creneau) {
        this.creneau = creneau;
    }
    
    
    
}
