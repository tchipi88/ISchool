/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.controller;

import com.tsoft.appli.highschool.model.Notes;
import com.tsoft.appli.highschool.repository.NotesRepository;

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
 * REST controller for managing Notes.
 */

@RestController
@RequestMapping(value = "/app")
public class NotesController  {

    private final Logger log = LoggerFactory.getLogger(NotesController.class);


    @Autowired
    NotesRepository Notesrepository;

    @Autowired
    EntityUtils entityUtils;
    @Autowired
    ObjectUtils objectUtils;
    /**
     * GET  /Notes : get all the Notes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of Notes in body
     */
    @RequestMapping(value = "/Notes", method = RequestMethod.GET)
    public ResponseEntity<List<Notes>> listNotes() throws Exception{
        log.debug("REST request to get all Notes");
        return new ResponseEntity<>(Notesrepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET  Notes/{id}.
     *
     * @param id the id of the Notes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the Notes, or with status 404 (Not Found)
     */

    @RequestMapping(value = "/Notes/{id}", method = RequestMethod.GET)
    public ResponseEntity<Notes> findNotes(@PathVariable Integer id) throws Exception{
         log.debug("REST request to get Notes : {}", id);
         Notes v = Notesrepository.findOne(id);
         return Optional.ofNullable(v)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/Notes", method = RequestMethod.POST)
    public ResponseEntity<Notes> saveNotes(@RequestBody Notes e) throws Exception{
        objectUtils.setSpelValues(e);
        return new ResponseEntity<>(Notesrepository.save(e), HttpStatus.OK);
    }

    /**
     * DELETE  /Notes/{id}.
     *
     * @param id the id of the (${entity})] to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @RequestMapping(value = "/Notes/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delNotes(@PathVariable Integer id) throws Exception {
        log.debug("REST request to delete Notes : {}", id);
        Notesrepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param categorie
     * @return a list model correspondant a la categorie .Cette liste model
     * permet l'affichage données dans la datatables
     * @throws NoSuchCategorieException
     */
    @RequestMapping(value = "/Notes/listmodel",
            method = RequestMethod.GET)
    public ListModel getlistmodel() throws Exception {
       return  entityUtils.getlistmodel(Notes.class);
    }

     /**
     *
     * @param categorie
     * @return a form model permettant la creation du formulaire pour la
     * categorie
     * @throws Exception
     */
    @RequestMapping(value = "/Notes/formmodel",
            method = RequestMethod.GET)
    public FormModel getformmodel() throws Exception {
        return entityUtils.getformmodel(Notes.class);
    }
}


