/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.controller;

import com.tsoft.appli.highschool.model.Coefficient;
import com.tsoft.appli.highschool.repository.CoefficientRepository;

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
 * REST controller for managing Coefficient.
 */

@RestController
@RequestMapping(value = "/app")
public class CoefficientController  {

    private final Logger log = LoggerFactory.getLogger(CoefficientController.class);


    @Autowired
    CoefficientRepository Coefficientrepository;

    @Autowired
    EntityUtils entityUtils;
    @Autowired
    ObjectUtils objectUtils;
    /**
     * GET  /Coefficient : get all the Coefficient.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of Coefficient in body
     */
    @RequestMapping(value = "/Coefficient", method = RequestMethod.GET)
    public ResponseEntity<List<Coefficient>> listCoefficient() throws Exception{
        log.debug("REST request to get all Coefficient");
        return new ResponseEntity<>(Coefficientrepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET  Coefficient/{id}.
     *
     * @param id the id of the Coefficient to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the Coefficient, or with status 404 (Not Found)
     */

    @RequestMapping(value = "/Coefficient/{id}", method = RequestMethod.GET)
    public ResponseEntity<Coefficient> findCoefficient(@PathVariable Integer id) throws Exception{
         log.debug("REST request to get Coefficient : {}", id);
         Coefficient v = Coefficientrepository.findOne(id);
         return Optional.ofNullable(v)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/Coefficient", method = RequestMethod.POST)
    public ResponseEntity<Coefficient> saveCoefficient(@RequestBody Coefficient e) throws Exception{
        objectUtils.setSpelValues(e);
        return new ResponseEntity<>(Coefficientrepository.save(e), HttpStatus.OK);
    }

    /**
     * DELETE  /Coefficient/{id}.
     *
     * @param id the id of the (${entity})] to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @RequestMapping(value = "/Coefficient/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delCoefficient(@PathVariable Integer id) throws Exception {
        log.debug("REST request to delete Coefficient : {}", id);
        Coefficientrepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param categorie
     * @return a list model correspondant a la categorie .Cette liste model
     * permet l'affichage données dans la datatables
     * @throws NoSuchCategorieException
     */
    @RequestMapping(value = "/Coefficient/listmodel",
            method = RequestMethod.GET)
    public ListModel getlistmodel() throws Exception {
       return  entityUtils.getlistmodel(Coefficient.class);
    }

     /**
     *
     * @param categorie
     * @return a form model permettant la creation du formulaire pour la
     * categorie
     * @throws Exception
     */
    @RequestMapping(value = "/Coefficient/formmodel",
            method = RequestMethod.GET)
    public FormModel getformmodel() throws Exception {
        return entityUtils.getformmodel(Coefficient.class);
    }
}


