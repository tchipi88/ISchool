/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.controller;

import com.tsoft.appli.highschool.model.MouvementCaisse;
import com.tsoft.appli.highschool.repository.MouvementCaisseRepository;

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
 * REST controller for managing MouvementCaisse.
 */

@RestController
@RequestMapping(value = "/app")
public class MouvementCaisseController  {

    private final Logger log = LoggerFactory.getLogger(MouvementCaisseController.class);


    @Autowired
    MouvementCaisseRepository MouvementCaisserepository;

    @Autowired
    EntityUtils entityUtils;
    @Autowired
    ObjectUtils objectUtils;
    /**
     * GET  /MouvementCaisse : get all the MouvementCaisse.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of MouvementCaisse in body
     */
    @RequestMapping(value = "/MouvementCaisse", method = RequestMethod.GET)
    public ResponseEntity<List<MouvementCaisse>> listMouvementCaisse() throws Exception{
        log.debug("REST request to get all MouvementCaisse");
        return new ResponseEntity<>(MouvementCaisserepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET  MouvementCaisse/{id}.
     *
     * @param id the id of the MouvementCaisse to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the MouvementCaisse, or with status 404 (Not Found)
     */

    @RequestMapping(value = "/MouvementCaisse/{id}", method = RequestMethod.GET)
    public ResponseEntity<MouvementCaisse> findMouvementCaisse(@PathVariable Integer id) throws Exception{
         log.debug("REST request to get MouvementCaisse : {}", id);
         MouvementCaisse v = MouvementCaisserepository.findOne(id);
         return Optional.ofNullable(v)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/MouvementCaisse", method = RequestMethod.POST)
    public ResponseEntity<MouvementCaisse> saveMouvementCaisse(@RequestBody MouvementCaisse e) throws Exception{
        objectUtils.setSpelValues(e);
        return new ResponseEntity<>(MouvementCaisserepository.save(e), HttpStatus.OK);
    }

    /**
     * DELETE  /MouvementCaisse/{id}.
     *
     * @param id the id of the (${entity})] to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @RequestMapping(value = "/MouvementCaisse/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delMouvementCaisse(@PathVariable Integer id) throws Exception {
        log.debug("REST request to delete MouvementCaisse : {}", id);
        MouvementCaisserepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param categorie
     * @return a list model correspondant a la categorie .Cette liste model
     * permet l'affichage données dans la datatables
     * @throws NoSuchCategorieException
     */
    @RequestMapping(value = "/MouvementCaisse/listmodel",
            method = RequestMethod.GET)
    public ListModel getlistmodel() throws Exception {
       return  entityUtils.getlistmodel(MouvementCaisse.class);
    }

     /**
     *
     * @param categorie
     * @return a form model permettant la creation du formulaire pour la
     * categorie
     * @throws Exception
     */
    @RequestMapping(value = "/MouvementCaisse/formmodel",
            method = RequestMethod.GET)
    public FormModel getformmodel() throws Exception {
        return entityUtils.getformmodel(MouvementCaisse.class);
    }
}

