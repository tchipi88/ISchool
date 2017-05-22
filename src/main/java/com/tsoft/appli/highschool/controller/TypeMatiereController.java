/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.controller;

import com.tsoft.appli.highschool.model.TypeMatiere;
import com.tsoft.appli.highschool.repository.TypeMatiereRepository;

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
 * REST controller for managing TypeMatiere.
 */

@RestController
@RequestMapping(value = "/app")
public class TypeMatiereController  {

    private final Logger log = LoggerFactory.getLogger(TypeMatiereController.class);


    @Autowired
    TypeMatiereRepository TypeMatiererepository;

    @Autowired
    EntityUtils entityUtils;
    @Autowired
    ObjectUtils objectUtils;
    /**
     * GET  /TypeMatiere : get all the TypeMatiere.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of TypeMatiere in body
     */
    @RequestMapping(value = "/TypeMatiere", method = RequestMethod.GET)
    public ResponseEntity<List<TypeMatiere>> listTypeMatiere() throws Exception{
        log.debug("REST request to get all TypeMatiere");
        return new ResponseEntity<>(TypeMatiererepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET  TypeMatiere/{id}.
     *
     * @param id the id of the TypeMatiere to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the TypeMatiere, or with status 404 (Not Found)
     */

    @RequestMapping(value = "/TypeMatiere/{id}", method = RequestMethod.GET)
    public ResponseEntity<TypeMatiere> findTypeMatiere(@PathVariable Integer id) throws Exception{
         log.debug("REST request to get TypeMatiere : {}", id);
         TypeMatiere v = TypeMatiererepository.findOne(id);
         return Optional.ofNullable(v)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/TypeMatiere", method = RequestMethod.POST)
    public ResponseEntity<TypeMatiere> saveTypeMatiere(@RequestBody TypeMatiere e) throws Exception{
        objectUtils.setSpelValues(e);
        return new ResponseEntity<>(TypeMatiererepository.save(e), HttpStatus.OK);
    }

    /**
     * DELETE  /TypeMatiere/{id}.
     *
     * @param id the id of the (${entity})] to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @RequestMapping(value = "/TypeMatiere/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delTypeMatiere(@PathVariable Integer id) throws Exception {
        log.debug("REST request to delete TypeMatiere : {}", id);
        TypeMatiererepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param categorie
     * @return a list model correspondant a la categorie .Cette liste model
     * permet l'affichage données dans la datatables
     * @throws NoSuchCategorieException
     */
    @RequestMapping(value = "/TypeMatiere/listmodel",
            method = RequestMethod.GET)
    public ListModel getlistmodel() throws Exception {
       return  entityUtils.getlistmodel(TypeMatiere.class);
    }

     /**
     *
     * @param categorie
     * @return a form model permettant la creation du formulaire pour la
     * categorie
     * @throws Exception
     */
    @RequestMapping(value = "/TypeMatiere/formmodel",
            method = RequestMethod.GET)
    public FormModel getformmodel() throws Exception {
        return entityUtils.getformmodel(TypeMatiere.class);
    }
}


