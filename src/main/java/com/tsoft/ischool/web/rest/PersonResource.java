package com.tsoft.ischool.web.rest;


import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.domain.Eleve;
import com.tsoft.ischool.domain.Employe;
import com.tsoft.ischool.domain.Person;
import com.tsoft.ischool.domain.Professeur;
import com.tsoft.ischool.domain.enumeration.Civilite;
import com.tsoft.ischool.domain.enumeration.Sexe;
import com.tsoft.ischool.domain.enumeration.TypePersonne;
import com.tsoft.ischool.repository.*;
import com.tsoft.ischool.repository.search.*;
import com.tsoft.ischool.service.EleveService;
import com.tsoft.ischool.web.rest.util.HeaderUtil;
import com.tsoft.ischool.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing Employe.
 */
@RestController
@RequestMapping("/api")
public class PersonResource {

    private final Logger log = LoggerFactory.getLogger(PersonResource.class);

    private static final String ENTITY_NAME = "person";

    private final EmployeRepository employeRepository;

    private final EmployeSearchRepository employeSearchRepository;
    private final PersonRepository personRepository;
    private final PersonSearchRepository personSearchRepository;
    private final ProfesseurRepository professeurRepository;
    private final ProfesseurSearchRepository professeurSearchRepository;

    private final EleveService eleveService;

    public PersonResource(EmployeRepository employeRepository, EmployeSearchRepository employeSearchRepository,
                          PersonRepository personRepository, ProfesseurRepository professeurRepository, ProfesseurSearchRepository professeurSearchRepository,
                          EleveService eleveService, PersonSearchRepository personSearchRepository) {
        this.employeRepository = employeRepository;
        this.employeSearchRepository = employeSearchRepository;
        this.personRepository = personRepository;
        this.personSearchRepository = personSearchRepository;

        this.professeurRepository = professeurRepository;
        this.professeurSearchRepository = professeurSearchRepository;

        this.eleveService = eleveService;
    }

    /**
     * POST  /employes : Create a new employe.
     *
     * @param personR the employe to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employe, or with status 400 (Bad Request) if the employe has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/persons")
    @Timed
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person personR) throws URISyntaxException, Exception {
        log.debug("REST request to save Person : {}", personR);
        if (personR.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new person cannot already have an ID")).body(null);
        }

        Person person = personRepository.save(personR);
        if(person.getTypePersonne()!=null) {
            if (person.getTypePersonne().equals(TypePersonne.STAFF)) {
                Employe employe = new Employe();
                employe.setNom(person.getNomPrenom());
                employe.setCivilite(person.getSexe().equals(Sexe.M) ? Civilite.MR : Civilite.MRS);
                employe.setPerson(person);
                employeRepository.save(employe);
                employeSearchRepository.save(employe);
            } else if (person.getTypePersonne().equals(TypePersonne.TEACHER)) {
                Professeur prof = new Professeur();
                prof.setNom(person.getNomPrenom());
                prof.setCivilite(person.getSexe().equals(Sexe.M) ? Civilite.MR : Civilite.MRS);
                prof.setPerson(person);
                professeurRepository.save(prof);
                professeurSearchRepository.save(prof);
            } else if (person.getTypePersonne().equals(TypePersonne.STUDENT)) {
                Eleve eleve = new Eleve();
                eleve.setNom(person.getNomPrenom());
                eleve.setSexe(person.getSexe());
                eleve.setPerson(person);
                eleveService.create(eleve);
            }
        }

        personSearchRepository.save(person);
        return ResponseEntity.created(new URI("/api/persons/" + person.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, person.getId().toString()))
            .body(person);
    }

    /**
     * PUT  /employes : Updates an existing employe.
     *
     * @param person the employe to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employe,
     * or with status 400 (Bad Request) if the employe is not valid,
     * or with status 500 (Internal Server Error) if the employe couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/persons")
    @Timed
    public ResponseEntity<Person> updateEmploye(@Valid @RequestBody Person person) throws URISyntaxException, Exception {
        log.debug("REST request to update Person : {}", person);
        if (person.getId() == null) {
            return createPerson(person);
        }
        Person result = personRepository.save(person);
        personSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, person.getId().toString()))
            .body(result);
    }

   /**
     * GET  /employes : get all the employes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of employes in body
     */
    @GetMapping("/persons")
    @Timed
    public ResponseEntity<List<Person>> getAllPersons(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Person");
        Page<Person> page = personRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/persons");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /employes/:id : get the "id" employe.
     *
     * @param id the id of the employe to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employe, or with status 404 (Not Found)
     */
    @GetMapping("/persons/{id}")
    @Timed
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {
        log.debug("REST request to get Person : {}", id);
        Person person = personRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(person));
    }

    /**
     * DELETE  /employes/:id : delete the "id" employe.
     *
     * @param id the id of the employe to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/persons/{id}")
    @Timed
    public ResponseEntity<Void> deletePerron(@PathVariable Long id) {
        log.debug("REST request to delete Person : {}", id);
        personRepository.delete(id);
        personSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/employes?query=:query : search for the employe corresponding
     * to the query.
     *
     * @param query the query of the employe search 
     * @return the result of the search
     */
    @GetMapping("/_search/persons")
    @Timed
    public ResponseEntity<List<Person>> searchPersons(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search Persons for query {}", query);
      Page<Person> page = personSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/persons");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
