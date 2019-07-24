package com.tsoft.ischool.web.rest;


import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.domain.Employe;
import com.tsoft.ischool.domain.Person;
import com.tsoft.ischool.domain.enumeration.Civilite;
import com.tsoft.ischool.domain.enumeration.Sexe;
import com.tsoft.ischool.domain.enumeration.TypePersonne;
import com.tsoft.ischool.repository.EmployeRepository;
import com.tsoft.ischool.repository.PersonRepository;
import com.tsoft.ischool.repository.search.EmployeSearchRepository;
import com.tsoft.ischool.repository.search.PersonSearchRepository;
import com.tsoft.ischool.web.rest.util.HeaderUtil;
import com.tsoft.ischool.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing Employe.
 */
@RestController
@RequestMapping("/api")
public class EmployeResource {

    private final Logger log = LoggerFactory.getLogger(EmployeResource.class);

    private static final String ENTITY_NAME = "employe";
        
    private final EmployeRepository employeRepository;

    private final EmployeSearchRepository employeSearchRepository;
    private final PersonRepository personRepository;
    private final PersonSearchRepository personSearchRepository;

    public EmployeResource(EmployeRepository employeRepository, EmployeSearchRepository employeSearchRepository,
                           PersonRepository personRepository, PersonSearchRepository personSearchRepository) {
        this.employeRepository = employeRepository;
        this.employeSearchRepository = employeSearchRepository;
        this.personRepository = personRepository;
        this.personSearchRepository = personSearchRepository;
    }

    /**
     * POST  /employes : Create a new employe.
     *
     * @param employe the employe to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employe, or with status 400 (Bad Request) if the employe has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employes")
    @Timed
    public ResponseEntity<Employe> createEmploye(@Valid @RequestBody Employe employe) throws URISyntaxException {
        log.debug("REST request to save Employe : {}", employe);
        if (employe.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new employe cannot already have an ID")).body(null);
        }

        Civilite civil = employe.getCivilite();
        Sexe sexe = civil==null? null : civil.equals(Civilite.MR) ? Sexe.G : Sexe.F;
        String nom = employe.getNom()+ (employe.getPrenom()!=null? " "+employe.getPrenom() : "");
        Person person = new Person(nom, TypePersonne.STAFF, sexe);
        person.setCivilite(civil);
        person = personRepository.save(person);
        personSearchRepository.save(person);

        employe.setPerson(person);
        Employe result = employeRepository.save(employe);
        employeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/employes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employes : Updates an existing employe.
     *
     * @param employe the employe to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employe,
     * or with status 400 (Bad Request) if the employe is not valid,
     * or with status 500 (Internal Server Error) if the employe couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employes")
    @Timed
    public ResponseEntity<Employe> updateEmploye(@Valid @RequestBody Employe employe) throws URISyntaxException {
        log.debug("REST request to update Employe : {}", employe);
        if (employe.getId() == null) {
            return createEmploye(employe);
        }
        Employe result = employeRepository.save(employe);
        employeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employe.getId().toString()))
            .body(result);
    }

   /**
     * GET  /employes : get all the employes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of employes in body
     */
    @GetMapping("/employes")
    @Timed
    public ResponseEntity<List<Employe>> getAllEmployes(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Employes");
        Page<Employe> page = employeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/employes");
        return new ResponseEntity<List<Employe>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /employes/:id : get the "id" employe.
     *
     * @param id the id of the employe to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employe, or with status 404 (Not Found)
     */
    @GetMapping("/employes/{id}")
    @Timed
    public ResponseEntity<Employe> getEmploye(@PathVariable Long id) {
        log.debug("REST request to get Employe : {}", id);
        Employe employe = employeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employe));
    }

    /**
     * DELETE  /employes/:id : delete the "id" employe.
     *
     * @param id the id of the employe to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employes/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmploye(@PathVariable Long id) {
        log.debug("REST request to delete Employe : {}", id);
        employeRepository.delete(id);
        employeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/employes?query=:query : search for the employe corresponding
     * to the query.
     *
     * @param query the query of the employe search 
     * @return the result of the search
     */
    @GetMapping("/_search/employes")
    @Timed
    public ResponseEntity<List<Employe>> searchEmployes(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search Employes for query {}", query);
      Page<Employe> page = employeSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/employes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }




}
