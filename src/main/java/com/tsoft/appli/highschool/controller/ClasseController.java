/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.controller;

import com.tsoft.appli.highschool.model.Classe;
import com.tsoft.appli.highschool.model.Serie;
import com.tsoft.appli.highschool.repository.ClasseRepository;
import com.tsoft.exception.NoSuchCategorieException;

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
import org.springframework.util.CollectionUtils;

/**
 *
 * @author tchipi
 */
/**
 * REST controller for managing Classe.
 */
@RestController
@RequestMapping(value = "/app")
public class ClasseController {

    private final Logger log = LoggerFactory.getLogger(ClasseController.class);

    @Autowired
    ClasseRepository Classerepository;

    @Autowired
    EntityUtils entityUtils;
    @Autowired
    ObjectUtils objectUtils;

    /**
     * GET /Classe : get all the Classe.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of Classe in
     * body
     */
    @RequestMapping(value = "/Classe", method = RequestMethod.GET)
    public ResponseEntity<List<Classe>> listClasse() throws Exception {
        log.debug("REST request to get all Classe");
        return new ResponseEntity<>(Classerepository.findAll(), HttpStatus.OK);
    }

    /**
     * GET Classe/{id}.
     *
     * @param id the id of the Classe to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the Classe,
     * or with status 404 (Not Found)
     */
    @RequestMapping(value = "/Classe/{id}", method = RequestMethod.GET)
    public ResponseEntity<Classe> findClasse(@PathVariable Integer id) throws Exception {
        log.debug("REST request to get Classe : {}", id);
        Classe v = Classerepository.findOne(id);
        return Optional.ofNullable(v)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/Classe", method = RequestMethod.POST)
    public ResponseEntity<Classe> saveClasse(@RequestBody Classe e) throws Exception {
        objectUtils.setSpelValues(e);
        return new ResponseEntity<>(Classerepository.save(e), HttpStatus.OK);
    }

    /**
     * DELETE /Classe/{id}.
     *
     * @param id the id of the (${entity})] to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/Classe/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delClasse(@PathVariable Integer id) throws Exception {
        log.debug("REST request to delete Classe : {}", id);
        Classerepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param categorie
     * @return a list model correspondant a la categorie .Cette liste model
     * permet l'affichage données dans la datatables
     * @throws NoSuchCategorieException
     */
    @RequestMapping(value = "/Classe/listmodel",
            method = RequestMethod.GET)
    public ListModel getlistmodel() throws Exception {
        return entityUtils.getlistmodel(Classe.class);
    }

    /**
     *
     * @param categorie
     * @return a form model permettant la creation du formulaire pour la
     * categorie
     * @throws Exception
     */
    @RequestMapping(value = "/Classe/formmodel",
            method = RequestMethod.GET)
    public FormModel getformmodel() throws Exception {
        return entityUtils.getformmodel(Classe.class);
    }

    @RequestMapping(value = "/ClasseBulk/{classetocreate}", method = RequestMethod.POST)
    public ResponseEntity<List<Classe>> CreateBulkClasse(@RequestBody Serie s, @PathVariable Integer classetocreate) throws Exception {
        log.debug("Create " + classetocreate + " Classe");
        List<Classe> classes = Classerepository.findBySerieCode(s.getCode());
        int index = (CollectionUtils.isEmpty(classes) ? 1 : classes.size() + 1);
        for (int i = 0; i < classetocreate; i++) {
            Classe c = new Classe();
            c.setLibelle(s.getAbreviation() + (index + i));
            c.setSerie(s);
            Classerepository.save(c);
        }
        return new ResponseEntity<>(Classerepository.findAll(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/ClasseBulk/{serie}", method = RequestMethod.GET)
    public ResponseEntity<List<Classe>> CreateBulkClasse(@PathVariable Integer serie) throws Exception {
        return new ResponseEntity<>(Classerepository.findBySerieCode(serie), HttpStatus.OK);
    }
}
