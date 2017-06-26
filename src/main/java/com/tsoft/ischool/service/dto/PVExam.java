/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service.dto;

import java.io.Serializable;

/**
 *
 * @author tchipnangngansopa
 */
public class PVExam implements Serializable {

    public PVExam() {
    }
    Integer effectif;
    
    Long nbremoyG;
    Long nbremoyF;
    
    Long nbresousmoyG;
    Long nbresousmoyF;
    
    Double avgmoyG;
    Double avgmoyF;
    Double avgmoy;
    
    Double maxmoyG;
    Double maxmoyF;
    Double maxmoy;
    
    Double minmoyG;
    Double minmoyF;
    Double minmoy;
    
    Long thG;
    Long thF;

    public Long getNbremoyG() {
        return nbremoyG;
    }

    public void setNbremoyG(Long nbremoyG) {
        this.nbremoyG = nbremoyG;
    }

    public Long getNbremoyF() {
        return nbremoyF;
    }

    public void setNbremoyF(Long nbremoyF) {
        this.nbremoyF = nbremoyF;
    }

    public Long getNbresousmoyG() {
        return nbresousmoyG;
    }

    public void setNbresousmoyG(Long nbresousmoyG) {
        this.nbresousmoyG = nbresousmoyG;
    }

    public Long getNbresousmoyF() {
        return nbresousmoyF;
    }

    public void setNbresousmoyF(Long nbresousmoyF) {
        this.nbresousmoyF = nbresousmoyF;
    }

    public Double getAvgmoyG() {
        return avgmoyG;
    }

    public void setAvgmoyG(Double avgmoyG) {
        this.avgmoyG = avgmoyG;
    }

    public Double getAvgmoyF() {
        return avgmoyF;
    }

    public void setAvgmoyF(Double avgmoyF) {
        this.avgmoyF = avgmoyF;
    }

    public Double getAvgmoy() {
        return avgmoy;
    }

    public void setAvgmoy(Double avgmoy) {
        this.avgmoy = avgmoy;
    }

    public Double getMaxmoyG() {
        return maxmoyG;
    }

    public void setMaxmoyG(Double maxmoyG) {
        this.maxmoyG = maxmoyG;
    }

    public Double getMaxmoyF() {
        return maxmoyF;
    }

    public void setMaxmoyF(Double maxmoyF) {
        this.maxmoyF = maxmoyF;
    }

    public Double getMinmoyG() {
        return minmoyG;
    }

    public void setMinmoyG(Double minmoyG) {
        this.minmoyG = minmoyG;
    }

    public Double getMinmoyF() {
        return minmoyF;
    }

    public void setMinmoyF(Double minmoyF) {
        this.minmoyF = minmoyF;
    }

    public Long getThG() {
        return thG;
    }

    public void setThG(Long thG) {
        this.thG = thG;
    }

    public Long getThF() {
        return thF;
    }

    public void setThF(Long thF) {
        this.thF = thF;
    }

    public Integer getEffectif() {
        return effectif;
    }

    public void setEffectif(Integer effectif) {
        this.effectif = effectif;
    }

    public Double getMaxmoy() {
        return maxmoy;
    }

    public void setMaxmoy(Double maxmoy) {
        this.maxmoy = maxmoy;
    }

    public Double getMinmoy() {
        return minmoy;
    }

    public void setMinmoy(Double minmoy) {
        this.minmoy = minmoy;
    }
    
    

}
