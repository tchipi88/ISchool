/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.controller;

import com.tsoft.appli.highschool.model.Eleve;
import com.tsoft.appli.highschool.repository.EleveRepository;

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
 * REST controller for managing Eleve.
 */

@RestController
@RequestMapping(value = "/app")
public class EleveController  {

    private final Logger log = LoggerFactory.getLogger(EleveController.class);


    @Autowired
    EleveRepository Eleverepository;

    @Autowired
    EntityUtils entityUtils;
    @Autowired
    ObjectUtils objectUtils;
    /**
     * GET  /Eleve : get all the Eleve.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of Eleve in body
     */
    @RequestMapping(value = "/Eleve", method = RequestMethod.GET)
    public ResponseEntity<List<Eleve>> listEleve() throws Exception{
        log.debug("REST request to get all Eleve");
        return new ResponseEntity<>(Eleverepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET  Eleve/{id}.
     *
     * @param id the id of the Eleve to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the Eleve, or with status 404 (Not Found)
     */

    @RequestMapping(value = "/Eleve/{id}", method = RequestMethod.GET)
    public ResponseEntity<Eleve> findEleve(@PathVariable Integer id) throws Exception{
         log.debug("REST request to get Eleve : {}", id);
         Eleve v = Eleverepository.findOne(id);
         return Optional.ofNullable(v)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/Eleve", method = RequestMethod.POST)
    public ResponseEntity<Eleve> saveEleve(@RequestBody Eleve e) throws Exception{
        objectUtils.setSpelValues(e);
        return new ResponseEntity<>(Eleverepository.save(e), HttpStatus.OK);
    }

    /**
     * DELETE  /Eleve/{id}.
     *
     * @param id the id of the (${entity})] to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @RequestMapping(value = "/Eleve/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delEleve(@PathVariable Integer id) throws Exception {
        log.debug("REST request to delete Eleve : {}", id);
        Eleverepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param categorie
     * @return a list model correspondant a la categorie .Cette liste model
     * permet l'affichage données dans la datatables
     * @throws NoSuchCategorieException
     */
    @RequestMapping(value = "/Eleve/listmodel",
            method = RequestMethod.GET)
    public ListModel getlistmodel() throws Exception {
       return  entityUtils.getlistmodel(Eleve.class);
    }

     /**
     *
     * @param categorie
     * @return a form model permettant la creation du formulaire pour la
     * categorie
     * @throws Exception
     */
    @RequestMapping(value = "/Eleve/formmodel",
            method = RequestMethod.GET)
    public FormModel getformmodel() throws Exception {
        return entityUtils.getformmodel(Eleve.class);
    }
}


