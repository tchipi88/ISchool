/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.controller;

import com.tsoft.appli.highschool.model.Matiere;
import com.tsoft.appli.highschool.repository.MatiereRepository;

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
 * REST controller for managing Matiere.
 */

@RestController
@RequestMapping(value = "/app")
public class MatiereController  {

    private final Logger log = LoggerFactory.getLogger(MatiereController.class);


    @Autowired
    MatiereRepository Matiererepository;

    @Autowired
    EntityUtils entityUtils;
    @Autowired
    ObjectUtils objectUtils;
    /**
     * GET  /Matiere : get all the Matiere.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of Matiere in body
     */
    @RequestMapping(value = "/Matiere", method = RequestMethod.GET)
    public ResponseEntity<List<Matiere>> listMatiere() throws Exception{
        log.debug("REST request to get all Matiere");
        return new ResponseEntity<>(Matiererepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET  Matiere/{id}.
     *
     * @param id the id of the Matiere to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the Matiere, or with status 404 (Not Found)
     */

    @RequestMapping(value = "/Matiere/{id}", method = RequestMethod.GET)
    public ResponseEntity<Matiere> findMatiere(@PathVariable Integer id) throws Exception{
         log.debug("REST request to get Matiere : {}", id);
         Matiere v = Matiererepository.findOne(id);
         return Optional.ofNullable(v)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/Matiere", method = RequestMethod.POST)
    public ResponseEntity<Matiere> saveMatiere(@RequestBody Matiere e) throws Exception{
        objectUtils.setSpelValues(e);
        return new ResponseEntity<>(Matiererepository.save(e), HttpStatus.OK);
    }

    /**
     * DELETE  /Matiere/{id}.
     *
     * @param id the id of the (${entity})] to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @RequestMapping(value = "/Matiere/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delMatiere(@PathVariable Integer id) throws Exception {
        log.debug("REST request to delete Matiere : {}", id);
        Matiererepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param categorie
     * @return a list model correspondant a la categorie .Cette liste model
     * permet l'affichage données dans la datatables
     * @throws NoSuchCategorieException
     */
    @RequestMapping(value = "/Matiere/listmodel",
            method = RequestMethod.GET)
    public ListModel getlistmodel() throws Exception {
       return  entityUtils.getlistmodel(Matiere.class);
    }

     /**
     *
     * @param categorie
     * @return a form model permettant la creation du formulaire pour la
     * categorie
     * @throws Exception
     */
    @RequestMapping(value = "/Matiere/formmodel",
            method = RequestMethod.GET)
    public FormModel getformmodel() throws Exception {
        return entityUtils.getformmodel(Matiere.class);
    }
}


