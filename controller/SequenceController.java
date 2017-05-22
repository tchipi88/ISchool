/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.controller;

import com.tsoft.appli.highschool.model.Sequence;
import com.tsoft.appli.highschool.repository.SequenceRepository;

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
 * REST controller for managing Sequence.
 */

@RestController
@RequestMapping(value = "/app")
public class SequenceController  {

    private final Logger log = LoggerFactory.getLogger(SequenceController.class);


    @Autowired
    SequenceRepository Sequencerepository;

    @Autowired
    EntityUtils entityUtils;
    @Autowired
    ObjectUtils objectUtils;
    /**
     * GET  /Sequence : get all the Sequence.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of Sequence in body
     */
    @RequestMapping(value = "/Sequence", method = RequestMethod.GET)
    public ResponseEntity<List<Sequence>> listSequence() throws Exception{
        log.debug("REST request to get all Sequence");
        return new ResponseEntity<>(Sequencerepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET  Sequence/{id}.
     *
     * @param id the id of the Sequence to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the Sequence, or with status 404 (Not Found)
     */

    @RequestMapping(value = "/Sequence/{id}", method = RequestMethod.GET)
    public ResponseEntity<Sequence> findSequence(@PathVariable Integer id) throws Exception{
         log.debug("REST request to get Sequence : {}", id);
         Sequence v = Sequencerepository.findOne(id);
         return Optional.ofNullable(v)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/Sequence", method = RequestMethod.POST)
    public ResponseEntity<Sequence> saveSequence(@RequestBody Sequence e) throws Exception{
        objectUtils.setSpelValues(e);
        return new ResponseEntity<>(Sequencerepository.save(e), HttpStatus.OK);
    }

    /**
     * DELETE  /Sequence/{id}.
     *
     * @param id the id of the (${entity})] to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @RequestMapping(value = "/Sequence/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delSequence(@PathVariable Integer id) throws Exception {
        log.debug("REST request to delete Sequence : {}", id);
        Sequencerepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param categorie
     * @return a list model correspondant a la categorie .Cette liste model
     * permet l'affichage données dans la datatables
     * @throws NoSuchCategorieException
     */
    @RequestMapping(value = "/Sequence/listmodel",
            method = RequestMethod.GET)
    public ListModel getlistmodel() throws Exception {
       return  entityUtils.getlistmodel(Sequence.class);
    }

     /**
     *
     * @param categorie
     * @return a form model permettant la creation du formulaire pour la
     * categorie
     * @throws Exception
     */
    @RequestMapping(value = "/Sequence/formmodel",
            method = RequestMethod.GET)
    public FormModel getformmodel() throws Exception {
        return entityUtils.getformmodel(Sequence.class);
    }
}


