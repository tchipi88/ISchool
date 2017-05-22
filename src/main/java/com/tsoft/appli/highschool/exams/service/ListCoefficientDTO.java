/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.exams.service;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author tchipi
 */
public class ListCoefficientDTO implements Serializable {

    private List<CoefficientDTO> coefs;

    public List<CoefficientDTO> getCoefs() {
        return coefs;
    }

    public void setCoefs(List<CoefficientDTO> coefs) {
        this.coefs = coefs;
    }

    public ListCoefficientDTO() {
    }
}
