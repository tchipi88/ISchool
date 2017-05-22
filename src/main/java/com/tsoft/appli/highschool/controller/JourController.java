/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.controller;

import com.tsoft.appli.highschool.model.Jour;
import com.tsoft.appli.highschool.repository.JourRepository;

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
 * REST controller for managing Jour.
 */

@RestController
@RequestMapping(value = "/app")
public class JourController  {

    private final Logger log = LoggerFactory.getLogger(JourController.class);


    @Autowired
    JourRepository Jourrepository;

    @Autowired
    EntityUtils entityUtils;
    @Autowired
    ObjectUtils objectUtils;
    /**
     * GET  /Jour : get all the Jour.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of Jour in body
     */
    @RequestMapping(value = "/Jour", method = RequestMethod.GET)
    public ResponseEntity<List<Jour>> listJour() throws Exception{
        log.debug("REST request to get all Jour");
        return new ResponseEntity<>(Jourrepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET  Jour/{id}.
     *
     * @param id the id of the Jour to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the Jour, or with status 404 (Not Found)
     */

    @RequestMapping(value = "/Jour/{id}", method = RequestMethod.GET)
    public ResponseEntity<Jour> findJour(@PathVariable Integer id) throws Exception{
         log.debug("REST request to get Jour : {}", id);
         Jour v = Jourrepository.findOne(id);
         return Optional.ofNullable(v)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/Jour", method = RequestMethod.POST)
    public ResponseEntity<Jour> saveJour(@RequestBody Jour e) throws Exception{
        objectUtils.setSpelValues(e);
        return new ResponseEntity<>(Jourrepository.save(e), HttpStatus.OK);
    }

    /**
     * DELETE  /Jour/{id}.
     *
     * @param id the id of the (${entity})] to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @RequestMapping(value = "/Jour/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delJour(@PathVariable Integer id) throws Exception {
        log.debug("REST request to delete Jour : {}", id);
        Jourrepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param categorie
     * @return a list model correspondant a la categorie .Cette liste model
     * permet l'affichage données dans la datatables
     * @throws NoSuchCategorieException
     */
    @RequestMapping(value = "/Jour/listmodel",
            method = RequestMethod.GET)
    public ListModel getlistmodel() throws Exception {
       return  entityUtils.getlistmodel(Jour.class);
    }

     /**
     *
     * @param categorie
     * @return a form model permettant la creation du formulaire pour la
     * categorie
     * @throws Exception
     */
    @RequestMapping(value = "/Jour/formmodel",
            method = RequestMethod.GET)
    public FormModel getformmodel() throws Exception {
        return entityUtils.getformmodel(Jour.class);
    }
}


