/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author tchipi
 */
@Entity
@DiscriminatorValue("C")
public class CaisseEncaissement extends CaisseMouvement {

}
