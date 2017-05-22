/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.service;

import com.tsoft.appli.highschool.model.AnneeScolaire;
import com.tsoft.appli.highschool.repository.AnneeScolaireRepository;
import com.tsoft.exception.BusinessException;
import com.tsoft.utils.enumerations.DataLifeCycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class AnneeService {
    
    @Autowired
    AnneeScolaireRepository  anneeScolaireRepository;

    public AnneeScolaire getAnneeCourante() throws Exception {
        AnneeScolaire as =  anneeScolaireRepository.findByCycleVie(DataLifeCycle.ACTIF);
        if (as == null) {
            throw new BusinessException("Annee courante non definie");
        }
        return as;
    }
    
    

}
