/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.controller;

import com.tsoft.appli.highschool.model.Reglement;
import com.tsoft.appli.highschool.repository.ReglementRepository;

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
 * REST controller for managing Reglement.
 */

@RestController
@RequestMapping(value = "/app")
public class ReglementController  {

    private final Logger log = LoggerFactory.getLogger(ReglementController.class);


    @Autowired
    ReglementRepository Reglementrepository;

    @Autowired
    EntityUtils entityUtils;
    @Autowired
    ObjectUtils objectUtils;
    /**
     * GET  /Reglement : get all the Reglement.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of Reglement in body
     */
    @RequestMapping(value = "/Reglement", method = RequestMethod.GET)
    public ResponseEntity<List<Reglement>> listReglement() throws Exception{
        log.debug("REST request to get all Reglement");
        return new ResponseEntity<>(Reglementrepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET  Reglement/{id}.
     *
     * @param id the id of the Reglement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the Reglement, or with status 404 (Not Found)
     */

    @RequestMapping(value = "/Reglement/{id}", method = RequestMethod.GET)
    public ResponseEntity<Reglement> findReglement(@PathVariable Integer id) throws Exception{
         log.debug("REST request to get Reglement : {}", id);
         Reglement v = Reglementrepository.findOne(id);
         return Optional.ofNullable(v)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/Reglement", method = RequestMethod.POST)
    public ResponseEntity<Reglement> saveReglement(@RequestBody Reglement e) throws Exception{
        objectUtils.setSpelValues(e);
        return new ResponseEntity<>(Reglementrepository.save(e), HttpStatus.OK);
    }

    /**
     * DELETE  /Reglement/{id}.
     *
     * @param id the id of the (${entity})] to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @RequestMapping(value = "/Reglement/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delReglement(@PathVariable Integer id) throws Exception {
        log.debug("REST request to delete Reglement : {}", id);
        Reglementrepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param categorie
     * @return a list model correspondant a la categorie .Cette liste model
     * permet l'affichage données dans la datatables
     * @throws NoSuchCategorieException
     */
    @RequestMapping(value = "/Reglement/listmodel",
            method = RequestMethod.GET)
    public ListModel getlistmodel() throws Exception {
       return  entityUtils.getlistmodel(Reglement.class);
    }

     /**
     *
     * @param categorie
     * @return a form model permettant la creation du formulaire pour la
     * categorie
     * @throws Exception
     */
    @RequestMapping(value = "/Reglement/formmodel",
            method = RequestMethod.GET)
    public FormModel getformmodel() throws Exception {
        return entityUtils.getformmodel(Reglement.class);
    }
}

