/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.controller;

import com.tsoft.appli.highschool.model.AnneeScolaire;
import com.tsoft.appli.highschool.repository.AnneeScolaireRepository;

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
 * REST controller for managing AnneeScolaire.
 */

@RestController
@RequestMapping(value = "/app")
public class AnneeScolaireController  {

    private final Logger log = LoggerFactory.getLogger(AnneeScolaireController.class);


    @Autowired
    AnneeScolaireRepository AnneeScolairerepository;

    @Autowired
    EntityUtils entityUtils;
    @Autowired
    ObjectUtils objectUtils;
    /**
     * GET  /AnneeScolaire : get all the AnneeScolaire.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of AnneeScolaire in body
     */
    @RequestMapping(value = "/AnneeScolaire", method = RequestMethod.GET)
    public ResponseEntity<List<AnneeScolaire>> listAnneeScolaire() throws Exception{
        log.debug("REST request to get all AnneeScolaire");
        return new ResponseEntity<>(AnneeScolairerepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET  AnneeScolaire/{id}.
     *
     * @param id the id of the AnneeScolaire to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the AnneeScolaire, or with status 404 (Not Found)
     */

    @RequestMapping(value = "/AnneeScolaire/{id}", method = RequestMethod.GET)
    public ResponseEntity<AnneeScolaire> findAnneeScolaire(@PathVariable Integer id) throws Exception{
         log.debug("REST request to get AnneeScolaire : {}", id);
         AnneeScolaire v = AnneeScolairerepository.findOne(id);
         return Optional.ofNullable(v)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/AnneeScolaire", method = RequestMethod.POST)
    public ResponseEntity<AnneeScolaire> saveAnneeScolaire(@RequestBody AnneeScolaire e) throws Exception{
        objectUtils.setSpelValues(e);
        return new ResponseEntity<>(AnneeScolairerepository.save(e), HttpStatus.OK);
    }

    /**
     * DELETE  /AnneeScolaire/{id}.
     *
     * @param id the id of the (${entity})] to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @RequestMapping(value = "/AnneeScolaire/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delAnneeScolaire(@PathVariable Integer id) throws Exception {
        log.debug("REST request to delete AnneeScolaire : {}", id);
        AnneeScolairerepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param categorie
     * @return a list model correspondant a la categorie .Cette liste model
     * permet l'affichage données dans la datatables
     * @throws NoSuchCategorieException
     */
    @RequestMapping(value = "/AnneeScolaire/listmodel",
            method = RequestMethod.GET)
    public ListModel getlistmodel() throws Exception {
       return  entityUtils.getlistmodel(AnneeScolaire.class);
    }

     /**
     *
     * @param categorie
     * @return a form model permettant la creation du formulaire pour la
     * categorie
     * @throws Exception
     */
    @RequestMapping(value = "/AnneeScolaire/formmodel",
            method = RequestMethod.GET)
    public FormModel getformmodel() throws Exception {
        return entityUtils.getformmodel(AnneeScolaire.class);
    }
}


