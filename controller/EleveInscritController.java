/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.controller;

import com.tsoft.appli.highschool.model.EleveInscrit;
import com.tsoft.appli.highschool.repository.EleveInscritRepository;

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
 * REST controller for managing EleveInscrit.
 */

@RestController
@RequestMapping(value = "/app")
public class EleveInscritController  {

    private final Logger log = LoggerFactory.getLogger(EleveInscritController.class);


    @Autowired
    EleveInscritRepository EleveInscritrepository;

    @Autowired
    EntityUtils entityUtils;
    @Autowired
    ObjectUtils objectUtils;
    /**
     * GET  /EleveInscrit : get all the EleveInscrit.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of EleveInscrit in body
     */
    @RequestMapping(value = "/EleveInscrit", method = RequestMethod.GET)
    public ResponseEntity<List<EleveInscrit>> listEleveInscrit() throws Exception{
        log.debug("REST request to get all EleveInscrit");
        return new ResponseEntity<>(EleveInscritrepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET  EleveInscrit/{id}.
     *
     * @param id the id of the EleveInscrit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the EleveInscrit, or with status 404 (Not Found)
     */

    @RequestMapping(value = "/EleveInscrit/{id}", method = RequestMethod.GET)
    public ResponseEntity<EleveInscrit> findEleveInscrit(@PathVariable Integer id) throws Exception{
         log.debug("REST request to get EleveInscrit : {}", id);
         EleveInscrit v = EleveInscritrepository.findOne(id);
         return Optional.ofNullable(v)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/EleveInscrit", method = RequestMethod.POST)
    public ResponseEntity<EleveInscrit> saveEleveInscrit(@RequestBody EleveInscrit e) throws Exception{
        objectUtils.setSpelValues(e);
        return new ResponseEntity<>(EleveInscritrepository.save(e), HttpStatus.OK);
    }

    /**
     * DELETE  /EleveInscrit/{id}.
     *
     * @param id the id of the (${entity})] to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @RequestMapping(value = "/EleveInscrit/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delEleveInscrit(@PathVariable Integer id) throws Exception {
        log.debug("REST request to delete EleveInscrit : {}", id);
        EleveInscritrepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param categorie
     * @return a list model correspondant a la categorie .Cette liste model
     * permet l'affichage données dans la datatables
     * @throws NoSuchCategorieException
     */
    @RequestMapping(value = "/EleveInscrit/listmodel",
            method = RequestMethod.GET)
    public ListModel getlistmodel() throws Exception {
       return  entityUtils.getlistmodel(EleveInscrit.class);
    }

     /**
     *
     * @param categorie
     * @return a form model permettant la creation du formulaire pour la
     * categorie
     * @throws Exception
     */
    @RequestMapping(value = "/EleveInscrit/formmodel",
            method = RequestMethod.GET)
    public FormModel getformmodel() throws Exception {
        return entityUtils.getformmodel(EleveInscrit.class);
    }
}


