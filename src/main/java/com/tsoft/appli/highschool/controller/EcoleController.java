/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.controller;

import com.tsoft.appli.highschool.model.Ecole;
import com.tsoft.appli.highschool.repository.EcoleRepository;

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
 * REST controller for managing Ecole.
 */

@RestController
@RequestMapping(value = "/app")
public class EcoleController  {

    private final Logger log = LoggerFactory.getLogger(EcoleController.class);


    @Autowired
    EcoleRepository Ecolerepository;

    @Autowired
    EntityUtils entityUtils;
    @Autowired
    ObjectUtils objectUtils;
    /**
     * GET  /Ecole : get all the Ecole.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of Ecole in body
     */
    @RequestMapping(value = "/Ecole", method = RequestMethod.GET)
    public ResponseEntity<List<Ecole>> listEcole() throws Exception{
        log.debug("REST request to get all Ecole");
        return new ResponseEntity<>(Ecolerepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET  Ecole/{id}.
     *
     * @param id the id of the Ecole to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the Ecole, or with status 404 (Not Found)
     */

    @RequestMapping(value = "/Ecole/{id}", method = RequestMethod.GET)
    public ResponseEntity<Ecole> findEcole(@PathVariable Integer id) throws Exception{
         log.debug("REST request to get Ecole : {}", id);
         Ecole v = Ecolerepository.findOne(id);
         return Optional.ofNullable(v)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/Ecole", method = RequestMethod.POST)
    public ResponseEntity<Ecole> saveEcole(@RequestBody Ecole e) throws Exception{
        objectUtils.setSpelValues(e);
        return new ResponseEntity<>(Ecolerepository.save(e), HttpStatus.OK);
    }

    /**
     * DELETE  /Ecole/{id}.
     *
     * @param id the id of the (${entity})] to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @RequestMapping(value = "/Ecole/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delEcole(@PathVariable Integer id) throws Exception {
        log.debug("REST request to delete Ecole : {}", id);
        Ecolerepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param categorie
     * @return a list model correspondant a la categorie .Cette liste model
     * permet l'affichage données dans la datatables
     * @throws NoSuchCategorieException
     */
    @RequestMapping(value = "/Ecole/listmodel",
            method = RequestMethod.GET)
    public ListModel getlistmodel() throws Exception {
       return  entityUtils.getlistmodel(Ecole.class);
    }

     /**
     *
     * @param categorie
     * @return a form model permettant la creation du formulaire pour la
     * categorie
     * @throws Exception
     */
    @RequestMapping(value = "/Ecole/formmodel",
            method = RequestMethod.GET)
    public FormModel getformmodel() throws Exception {
        return entityUtils.getformmodel(Ecole.class);
    }
}


