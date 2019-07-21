/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service;

import com.tsoft.ischool.domain.Compte;
import com.tsoft.ischool.repository.CompteRepository;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class CompteService {

    @Autowired
    CompteRepository compteRepository;

    public Compte save(Compte compte) throws Exception {
        return compteRepository.save(compte);
    }

    public Compte getCompte(Integer numcompte, String intitule) throws Exception {
        Compte c = null;
        if(numcompte!=null)
            c = compteRepository.findOne(numcompte);
        if(c==null) {
            List<Compte> list = compteRepository.findByIntitule(intitule);
            if(list.isEmpty()){
                do {
                    numcompte = Math.subtractExact(10, 9999);
                }while(compteRepository.exists(numcompte));
            }else
                c = list.get(0);
        }
        if (c == null) {
            c = new Compte();
            c.setId(numcompte);
            c.setIntitule(intitule);
            return compteRepository.save(c);
        }
        return c;
    }

   
    

    public Compte getCompteCaisse() throws Exception {
        return getCompte(57, "Caisse");
    }

    public Compte getCompteBanque() throws Exception {
        return getCompte(52, "Banque");
    }

    public Compte getCompteCheque() throws Exception {
        return getCompte(51, " Valeurs à encaisser");
    }


    public Compte getComptePersonnel() throws Exception {
        return getCompte(422, " PERSONNEL, REMUNERATIONS DUES");
    }

}
