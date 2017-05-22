/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.service;

import com.tsoft.appli.highschool.model.Ecole;
import com.tsoft.service.process.EmptyActionProcess;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class ClotureAnneeScolaire extends EmptyActionProcess {

    @Override
    public String description() {
        return "Permet de cloturer une annee scolaire.Cette action est irreversible";
    }

    @Override
    public Object run(HttpSession session, HttpServletRequest request) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    //cloture les elevesincrits
        //cloture les reglements
    }

    @Override
    public String libelle() {
        return "Ctorure Annee Scolaire";
    }

    @Override
    public Class rubrique() throws Exception {
        return Ecole.class;
    }
}
