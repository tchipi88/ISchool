/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.controller;

import com.tsoft.appli.highschool.model.Caisse;
import com.tsoft.appli.highschool.repository.CaisseRepository;

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
 * REST controller for managing Caisse.
 */

@RestController
@RequestMapping(value = "/app")
public class CaisseController  {

    private final Logger log = LoggerFactory.getLogger(CaisseController.class);


    @Autowired
    CaisseRepository Caisserepository;

    @Autowired
    EntityUtils entityUtils;
    @Autowired
    ObjectUtils objectUtils;
    /**
     * GET  /Caisse : get all the Caisse.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of Caisse in body
     */
    @RequestMapping(value = "/Caisse", method = RequestMethod.GET)
    public ResponseEntity<List<Caisse>> listCaisse() throws Exception{
        log.debug("REST request to get all Caisse");
        return new ResponseEntity<>(Caisserepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET  Caisse/{id}.
     *
     * @param id the id of the Caisse to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the Caisse, or with status 404 (Not Found)
     */

    @RequestMapping(value = "/Caisse/{id}", method = RequestMethod.GET)
    public ResponseEntity<Caisse> findCaisse(@PathVariable Integer id) throws Exception{
         log.debug("REST request to get Caisse : {}", id);
         Caisse v = Caisserepository.findOne(id);
         return Optional.ofNullable(v)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/Caisse", method = RequestMethod.POST)
    public ResponseEntity<Caisse> saveCaisse(@RequestBody Caisse e) throws Exception{
        objectUtils.setSpelValues(e);
        return new ResponseEntity<>(Caisserepository.save(e), HttpStatus.OK);
    }

    /**
     * DELETE  /Caisse/{id}.
     *
     * @param id the id of the (${entity})] to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @RequestMapping(value = "/Caisse/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delCaisse(@PathVariable Integer id) throws Exception {
        log.debug("REST request to delete Caisse : {}", id);
        Caisserepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param categorie
     * @return a list model correspondant a la categorie .Cette liste model
     * permet l'affichage données dans la datatables
     * @throws NoSuchCategorieException
     */
    @RequestMapping(value = "/Caisse/listmodel",
            method = RequestMethod.GET)
    public ListModel getlistmodel() throws Exception {
       return  entityUtils.getlistmodel(Caisse.class);
    }

     /**
     *
     * @param categorie
     * @return a form model permettant la creation du formulaire pour la
     * categorie
     * @throws Exception
     */
    @RequestMapping(value = "/Caisse/formmodel",
            method = RequestMethod.GET)
    public FormModel getformmodel() throws Exception {
        return entityUtils.getformmodel(Caisse.class);
    }
}


