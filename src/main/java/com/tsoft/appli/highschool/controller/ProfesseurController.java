/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.controller;

import com.tsoft.appli.highschool.model.Professeur;
import com.tsoft.appli.highschool.repository.ProfesseurRepository;

import com.tsoft.utils.EntityUtils;
import com.tsoft.utils.ObjectUtils;
import com.tsoft.web.model.FormModel;
import com.tsoft.web.model.ListModel;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tchipi
 */
/**
 * REST controller for managing Professeur.
 */

@RestController
@RequestMapping(value = "/app")
public class ProfesseurController  {

    private final Logger log = LoggerFactory.getLogger(ProfesseurController.class);


    @Autowired
    ProfesseurRepository Professeurrepository;

    @Autowired
    EntityUtils entityUtils;
    @Autowired
    ObjectUtils objectUtils;
    /**
     * GET  /Professeur : get all the Professeur.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of Professeur in body
     */
    @RequestMapping(value = "/Professeur", method = RequestMethod.GET)
    public ResponseEntity<List<Professeur>> listProfesseur() throws Exception{
        log.debug("REST request to get all Professeur");
        return new ResponseEntity<>(Professeurrepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET  Professeur/{id}.
     *
     * @param id the id of the Professeur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the Professeur, or with status 404 (Not Found)
     */

    @RequestMapping(value = "/Professeur/{id}", method = RequestMethod.GET)
    public ResponseEntity<Professeur> findProfesseur(@PathVariable Integer id) throws Exception{
         log.debug("REST request to get Professeur : {}", id);
         Professeur v = Professeurrepository.findOne(id);
         return Optional.ofNullable(v)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/Professeur", method = RequestMethod.POST)
    public ResponseEntity<Professeur> saveProfesseur(@RequestBody Professeur e) throws Exception{
        objectUtils.setSpelValues(e);
        return new ResponseEntity<>(Professeurrepository.save(e), HttpStatus.OK);
    }

    /**
     * DELETE  /Professeur/{id}.
     *
     * @param id the id of the (${entity})] to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @RequestMapping(value = "/Professeur/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delProfesseur(@PathVariable Integer id) throws Exception {
        log.debug("REST request to delete Professeur : {}", id);
        Professeurrepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param categorie
     * @return a list model correspondant a la categorie .Cette liste model
     * permet l'affichage données dans la datatables
     * @throws NoSuchCategorieException
     */
    @RequestMapping(value = "/Professeur/listmodel",
            method = RequestMethod.GET)
    public ListModel getlistmodel() throws Exception {
       return  entityUtils.getlistmodel(Professeur.class);
    }

     /**
     *
     * @param categorie
     * @return a form model permettant la creation du formulaire pour la
     * categorie
     * @throws Exception
     */
    @RequestMapping(value = "/Professeur/formmodel",
            method = RequestMethod.GET)
    public FormModel getformmodel() throws Exception {
        return entityUtils.getformmodel(Professeur.class);
    }
}


