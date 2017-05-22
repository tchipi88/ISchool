/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.controller;

import com.tsoft.appli.highschool.model.Decaissement;
import com.tsoft.appli.highschool.repository.DecaissementRepository;

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
 * REST controller for managing Decaissement.
 */

@RestController
@RequestMapping(value = "/app")
public class DecaissementController  {

    private final Logger log = LoggerFactory.getLogger(DecaissementController.class);


    @Autowired
    DecaissementRepository Decaissementrepository;

    @Autowired
    EntityUtils entityUtils;
    @Autowired
    ObjectUtils objectUtils;
    /**
     * GET  /Decaissement : get all the Decaissement.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of Decaissement in body
     */
    @RequestMapping(value = "/Decaissement", method = RequestMethod.GET)
    public ResponseEntity<List<Decaissement>> listDecaissement() throws Exception{
        log.debug("REST request to get all Decaissement");
        return new ResponseEntity<>(Decaissementrepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET  Decaissement/{id}.
     *
     * @param id the id of the Decaissement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the Decaissement, or with status 404 (Not Found)
     */

    @RequestMapping(value = "/Decaissement/{id}", method = RequestMethod.GET)
    public ResponseEntity<Decaissement> findDecaissement(@PathVariable Integer id) throws Exception{
         log.debug("REST request to get Decaissement : {}", id);
         Decaissement v = Decaissementrepository.findOne(id);
         return Optional.ofNullable(v)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/Decaissement", method = RequestMethod.POST)
    public ResponseEntity<Decaissement> saveDecaissement(@RequestBody Decaissement e) throws Exception{
        objectUtils.setSpelValues(e);
        return new ResponseEntity<>(Decaissementrepository.save(e), HttpStatus.OK);
    }

    /**
     * DELETE  /Decaissement/{id}.
     *
     * @param id the id of the (${entity})] to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @RequestMapping(value = "/Decaissement/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delDecaissement(@PathVariable Integer id) throws Exception {
        log.debug("REST request to delete Decaissement : {}", id);
        Decaissementrepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param categorie
     * @return a list model correspondant a la categorie .Cette liste model
     * permet l'affichage données dans la datatables
     * @throws NoSuchCategorieException
     */
    @RequestMapping(value = "/Decaissement/listmodel",
            method = RequestMethod.GET)
    public ListModel getlistmodel() throws Exception {
       return  entityUtils.getlistmodel(Decaissement.class);
    }

     /**
     *
     * @param categorie
     * @return a form model permettant la creation du formulaire pour la
     * categorie
     * @throws Exception
     */
    @RequestMapping(value = "/Decaissement/formmodel",
            method = RequestMethod.GET)
    public FormModel getformmodel() throws Exception {
        return entityUtils.getformmodel(Decaissement.class);
    }
}


