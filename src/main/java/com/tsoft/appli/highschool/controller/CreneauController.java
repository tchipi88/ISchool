/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.controller;

import com.tsoft.appli.highschool.model.Creneau;
import com.tsoft.appli.highschool.repository.CreneauRepository;

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
 * REST controller for managing Creneau.
 */

@RestController
@RequestMapping(value = "/app")
public class CreneauController  {

    private final Logger log = LoggerFactory.getLogger(CreneauController.class);


    @Autowired
    CreneauRepository Creneaurepository;

    @Autowired
    EntityUtils entityUtils;
    @Autowired
    ObjectUtils objectUtils;
    /**
     * GET  /Creneau : get all the Creneau.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of Creneau in body
     */
    @RequestMapping(value = "/Creneau", method = RequestMethod.GET)
    public ResponseEntity<List<Creneau>> listCreneau() throws Exception{
        log.debug("REST request to get all Creneau");
        return new ResponseEntity<>(Creneaurepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET  Creneau/{id}.
     *
     * @param id the id of the Creneau to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the Creneau, or with status 404 (Not Found)
     */

    @RequestMapping(value = "/Creneau/{id}", method = RequestMethod.GET)
    public ResponseEntity<Creneau> findCreneau(@PathVariable Integer id) throws Exception{
         log.debug("REST request to get Creneau : {}", id);
         Creneau v = Creneaurepository.findOne(id);
         return Optional.ofNullable(v)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/Creneau", method = RequestMethod.POST)
    public ResponseEntity<Creneau> saveCreneau(@RequestBody Creneau e) throws Exception{
        objectUtils.setSpelValues(e);
        return new ResponseEntity<>(Creneaurepository.save(e), HttpStatus.OK);
    }

    /**
     * DELETE  /Creneau/{id}.
     *
     * @param id the id of the (${entity})] to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @RequestMapping(value = "/Creneau/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delCreneau(@PathVariable Integer id) throws Exception {
        log.debug("REST request to delete Creneau : {}", id);
        Creneaurepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param categorie
     * @return a list model correspondant a la categorie .Cette liste model
     * permet l'affichage données dans la datatables
     * @throws NoSuchCategorieException
     */
    @RequestMapping(value = "/Creneau/listmodel",
            method = RequestMethod.GET)
    public ListModel getlistmodel() throws Exception {
       return  entityUtils.getlistmodel(Creneau.class);
    }

     /**
     *
     * @param categorie
     * @return a form model permettant la creation du formulaire pour la
     * categorie
     * @throws Exception
     */
    @RequestMapping(value = "/Creneau/formmodel",
            method = RequestMethod.GET)
    public FormModel getformmodel() throws Exception {
        return entityUtils.getformmodel(Creneau.class);
    }
}


