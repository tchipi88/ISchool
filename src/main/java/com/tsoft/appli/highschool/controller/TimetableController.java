/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.controller;

import com.tsoft.appli.highschool.model.Timetable;
import com.tsoft.appli.highschool.repository.TimetableRepository;

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
 * REST controller for managing Timetable.
 */

@RestController
@RequestMapping(value = "/app")
public class TimetableController  {

    private final Logger log = LoggerFactory.getLogger(TimetableController.class);


    @Autowired
    TimetableRepository Timetablerepository;

    @Autowired
    EntityUtils entityUtils;
    @Autowired
    ObjectUtils objectUtils;
    /**
     * GET  /Timetable : get all the Timetable.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of Timetable in body
     */
    @RequestMapping(value = "/Timetable", method = RequestMethod.GET)
    public ResponseEntity<List<Timetable>> listTimetable() throws Exception{
        log.debug("REST request to get all Timetable");
        return new ResponseEntity<>(Timetablerepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET  Timetable/{id}.
     *
     * @param id the id of the Timetable to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the Timetable, or with status 404 (Not Found)
     */

    @RequestMapping(value = "/Timetable/{id}", method = RequestMethod.GET)
    public ResponseEntity<Timetable> findTimetable(@PathVariable Integer id) throws Exception{
         log.debug("REST request to get Timetable : {}", id);
         Timetable v = Timetablerepository.findOne(id);
         return Optional.ofNullable(v)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/Timetable", method = RequestMethod.POST)
    public ResponseEntity<Timetable> saveTimetable(@RequestBody Timetable e) throws Exception{
        objectUtils.setSpelValues(e);
        return new ResponseEntity<>(Timetablerepository.save(e), HttpStatus.OK);
    }

    /**
     * DELETE  /Timetable/{id}.
     *
     * @param id the id of the (${entity})] to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @RequestMapping(value = "/Timetable/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delTimetable(@PathVariable Integer id) throws Exception {
        log.debug("REST request to delete Timetable : {}", id);
        Timetablerepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param categorie
     * @return a list model correspondant a la categorie .Cette liste model
     * permet l'affichage données dans la datatables
     * @throws NoSuchCategorieException
     */
    @RequestMapping(value = "/Timetable/listmodel",
            method = RequestMethod.GET)
    public ListModel getlistmodel() throws Exception {
       return  entityUtils.getlistmodel(Timetable.class);
    }

     /**
     *
     * @param categorie
     * @return a form model permettant la creation du formulaire pour la
     * categorie
     * @throws Exception
     */
    @RequestMapping(value = "/Timetable/formmodel",
            method = RequestMethod.GET)
    public FormModel getformmodel() throws Exception {
        return entityUtils.getformmodel(Timetable.class);
    }
}


