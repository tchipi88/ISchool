/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.controller;

import com.tsoft.appli.highschool.model.NotesPeriode;
import com.tsoft.appli.highschool.repository.NotesPeriodeRepository;

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
 * REST controller for managing NotesPeriode.
 */

@RestController
@RequestMapping(value = "/app")
public class NotesPeriodeController  {

    private final Logger log = LoggerFactory.getLogger(NotesPeriodeController.class);


    @Autowired
    NotesPeriodeRepository NotesPerioderepository;

    @Autowired
    EntityUtils entityUtils;
    @Autowired
    ObjectUtils objectUtils;
    /**
     * GET  /NotesPeriode : get all the NotesPeriode.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of NotesPeriode in body
     */
    @RequestMapping(value = "/NotesPeriode", method = RequestMethod.GET)
    public ResponseEntity<List<NotesPeriode>> listNotesPeriode() throws Exception{
        log.debug("REST request to get all NotesPeriode");
        return new ResponseEntity<>(NotesPerioderepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET  NotesPeriode/{id}.
     *
     * @param id the id of the NotesPeriode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the NotesPeriode, or with status 404 (Not Found)
     */

    @RequestMapping(value = "/NotesPeriode/{id}", method = RequestMethod.GET)
    public ResponseEntity<NotesPeriode> findNotesPeriode(@PathVariable Integer id) throws Exception{
         log.debug("REST request to get NotesPeriode : {}", id);
         NotesPeriode v = NotesPerioderepository.findOne(id);
         return Optional.ofNullable(v)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/NotesPeriode", method = RequestMethod.POST)
    public ResponseEntity<NotesPeriode> saveNotesPeriode(@RequestBody NotesPeriode e) throws Exception{
        objectUtils.setSpelValues(e);
        return new ResponseEntity<>(NotesPerioderepository.save(e), HttpStatus.OK);
    }

    /**
     * DELETE  /NotesPeriode/{id}.
     *
     * @param id the id of the (${entity})] to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @RequestMapping(value = "/NotesPeriode/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delNotesPeriode(@PathVariable Integer id) throws Exception {
        log.debug("REST request to delete NotesPeriode : {}", id);
        NotesPerioderepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param categorie
     * @return a list model correspondant a la categorie .Cette liste model
     * permet l'affichage données dans la datatables
     * @throws NoSuchCategorieException
     */
    @RequestMapping(value = "/NotesPeriode/listmodel",
            method = RequestMethod.GET)
    public ListModel getlistmodel() throws Exception {
       return  entityUtils.getlistmodel(NotesPeriode.class);
    }

     /**
     *
     * @param categorie
     * @return a form model permettant la creation du formulaire pour la
     * categorie
     * @throws Exception
     */
    @RequestMapping(value = "/NotesPeriode/formmodel",
            method = RequestMethod.GET)
    public FormModel getformmodel() throws Exception {
        return entityUtils.getformmodel(NotesPeriode.class);
    }
}


