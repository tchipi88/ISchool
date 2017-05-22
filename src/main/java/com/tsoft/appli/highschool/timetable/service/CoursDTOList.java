/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tsoft.appli.highschool.timetable.service;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author tchipi
 */
public class CoursDTOList  implements Serializable{
    
   private List<CoursDTO>  courss;
    
    public CoursDTOList(){
        
    }

    public List<CoursDTO> getCourss() {
        return courss;
    }

    public void setCourss(List<CoursDTO> courss) {
        this.courss = courss;
    }

    
    
}
