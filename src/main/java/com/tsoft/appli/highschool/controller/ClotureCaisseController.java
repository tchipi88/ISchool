/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.controller;

import com.tsoft.appli.highschool.model.ClotureCaisse;
import com.tsoft.appli.highschool.repository.ClotureCaisseRepository;

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
 * REST controller for managing ClotureCaisse.
 */

@RestController
@RequestMapping(value = "/app")
public class ClotureCaisseController  {

    private final Logger log = LoggerFactory.getLogger(ClotureCaisseController.class);


    @Autowired
    ClotureCaisseRepository ClotureCaisserepository;

    @Autowired
    EntityUtils entityUtils;
    @Autowired
    ObjectUtils objectUtils;
    /**
     * GET  /ClotureCaisse : get all the ClotureCaisse.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ClotureCaisse in body
     */
    @RequestMapping(value = "/ClotureCaisse", method = RequestMethod.GET)
    public ResponseEntity<List<ClotureCaisse>> listClotureCaisse() throws Exception{
        log.debug("REST request to get all ClotureCaisse");
        return new ResponseEntity<>(ClotureCaisserepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET  ClotureCaisse/{id}.
     *
     * @param id the id of the ClotureCaisse to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ClotureCaisse, or with status 404 (Not Found)
     */

    @RequestMapping(value = "/ClotureCaisse/{id}", method = RequestMethod.GET)
    public ResponseEntity<ClotureCaisse> findClotureCaisse(@PathVariable Integer id) throws Exception{
         log.debug("REST request to get ClotureCaisse : {}", id);
         ClotureCaisse v = ClotureCaisserepository.findOne(id);
         return Optional.ofNullable(v)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/ClotureCaisse", method = RequestMethod.POST)
    public ResponseEntity<ClotureCaisse> saveClotureCaisse(@RequestBody ClotureCaisse e) throws Exception{
        objectUtils.setSpelValues(e);
        return new ResponseEntity<>(ClotureCaisserepository.save(e), HttpStatus.OK);
    }

    /**
     * DELETE  /ClotureCaisse/{id}.
     *
     * @param id the id of the (${entity})] to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @RequestMapping(value = "/ClotureCaisse/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delClotureCaisse(@PathVariable Integer id) throws Exception {
        log.debug("REST request to delete ClotureCaisse : {}", id);
        ClotureCaisserepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param categorie
     * @return a list model correspondant a la categorie .Cette liste model
     * permet l'affichage données dans la datatables
     * @throws NoSuchCategorieException
     */
    @RequestMapping(value = "/ClotureCaisse/listmodel",
            method = RequestMethod.GET)
    public ListModel getlistmodel() throws Exception {
       return  entityUtils.getlistmodel(ClotureCaisse.class);
    }

     /**
     *
     * @param categorie
     * @return a form model permettant la creation du formulaire pour la
     * categorie
     * @throws Exception
     */
    @RequestMapping(value = "/ClotureCaisse/formmodel",
            method = RequestMethod.GET)
    public FormModel getformmodel() throws Exception {
        return entityUtils.getformmodel(ClotureCaisse.class);
    }
}


