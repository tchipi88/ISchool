/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.controller;

import com.tsoft.appli.highschool.model.Cours;
import com.tsoft.appli.highschool.repository.CoursRepository;

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
 * REST controller for managing Cours.
 */

@RestController
@RequestMapping(value = "/app")
public class CoursController  {

    private final Logger log = LoggerFactory.getLogger(CoursController.class);


    @Autowired
    CoursRepository Coursrepository;

    @Autowired
    EntityUtils entityUtils;
    @Autowired
    ObjectUtils objectUtils;
    /**
     * GET  /Cours : get all the Cours.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of Cours in body
     */
    @RequestMapping(value = "/Cours", method = RequestMethod.GET)
    public ResponseEntity<List<Cours>> listCours() throws Exception{
        log.debug("REST request to get all Cours");
        return new ResponseEntity<>(Coursrepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET  Cours/{id}.
     *
     * @param id the id of the Cours to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the Cours, or with status 404 (Not Found)
     */

    @RequestMapping(value = "/Cours/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cours> findCours(@PathVariable Integer id) throws Exception{
         log.debug("REST request to get Cours : {}", id);
         Cours v = Coursrepository.findOne(id);
         return Optional.ofNullable(v)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/Cours", method = RequestMethod.POST)
    public ResponseEntity<Cours> saveCours(@RequestBody Cours e) throws Exception{
        objectUtils.setSpelValues(e);
        return new ResponseEntity<>(Coursrepository.save(e), HttpStatus.OK);
    }

    /**
     * DELETE  /Cours/{id}.
     *
     * @param id the id of the (${entity})] to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @RequestMapping(value = "/Cours/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delCours(@PathVariable Integer id) throws Exception {
        log.debug("REST request to delete Cours : {}", id);
        Coursrepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param categorie
     * @return a list model correspondant a la categorie .Cette liste model
     * permet l'affichage données dans la datatables
     * @throws NoSuchCategorieException
     */
    @RequestMapping(value = "/Cours/listmodel",
            method = RequestMethod.GET)
    public ListModel getlistmodel() throws Exception {
       return  entityUtils.getlistmodel(Cours.class);
    }

     /**
     *
     * @param categorie
     * @return a form model permettant la creation du formulaire pour la
     * categorie
     * @throws Exception
     */
    @RequestMapping(value = "/Cours/formmodel",
            method = RequestMethod.GET)
    public FormModel getformmodel() throws Exception {
        return entityUtils.getformmodel(Cours.class);
    }
}


