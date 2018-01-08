package com.tsoft.ischool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.domain.ClasseEleve;

import com.tsoft.ischool.repository.ClasseEleveRepository;
import com.tsoft.ischool.service.AnneeService;
import com.tsoft.ischool.service.ClasseEleveService;
import com.tsoft.ischool.web.rest.util.HeaderUtil;
import com.tsoft.ischool.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing ClasseEleve.
 */
@RestController
@RequestMapping("/api")
public class ClasseEleveResource {

    private final Logger log = LoggerFactory.getLogger(ClasseEleveResource.class);

    private static final String ENTITY_NAME = "classeEleve";

    private final ClasseEleveRepository classeEleveRepository;

    private final AnneeService annneService;

    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    ClasseEleveService classeEleveService;

    public ClasseEleveResource(ClasseEleveRepository classeEleveRepository, AnneeService anneeService) {
        this.classeEleveRepository = classeEleveRepository;
        this.annneService = anneeService;
    }

    /**
     * POST /classe-eleves : Create a new classeEleve.
     *
     * @param classeEleve the classeEleve to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new classeEleve, or with status 400 (Bad Request) if the classeEleve has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/classe-eleves")
    @Timed
    public ResponseEntity<ClasseEleve> createClasseEleve(@RequestBody ClasseEleve classeEleve) throws Exception {
        log.debug("REST request to save ClasseEleve : {}", classeEleve);
        if (classeEleve.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new classeEleve cannot already have an ID")).body(null);
        }
        ClasseEleve result = classeEleveService.create(classeEleve);
        return ResponseEntity.created(new URI("/api/classe-eleves/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /classe-eleves : Updates an existing classeEleve.
     *
     * @param classeEleve the classeEleve to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * classeEleve, or with status 400 (Bad Request) if the classeEleve is not
     * valid, or with status 500 (Internal Server Error) if the classeEleve
     * couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/classe-eleves")
    @Timed
    public ResponseEntity<ClasseEleve> updateClasseEleve(@RequestBody ClasseEleve classeEleve) throws Exception {
        log.debug("REST request to update ClasseEleve : {}", classeEleve);
        if (classeEleve.getId() == null) {
            return createClasseEleve(classeEleve);
        }
        ClasseEleve result = classeEleveService.update(classeEleve);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classeEleve.getId().toString()))
                .body(result);
    }

    /**
     * GET /classe-eleves : get all the classeEleves.
     *
     * @param classe
     * @param pageable
     * @return the ResponseEntity with status 200 (OK) and the list of
     * classeEleves in body
     */
    @GetMapping(path = "/classe-eleves", params = {"classe"})
    @Timed
    public ResponseEntity<List<ClasseEleve>> getAllClasseEleves(
            @ApiParam String classe, @ApiParam Pageable pageable) throws Exception {
        log.debug("REST request to get all ClasseEleves");
        Page<ClasseEleve> page = classeEleveRepository.findByClasseIdAndAnnee(classe, annneService.getAnneeCourante(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/classe-eleves");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /classe-eleves/:id : get the "id" classeEleve.
     *
     * @param id the id of the classeEleve to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * classeEleve, or with status 404 (Not Found)
     */
    @GetMapping("/classe-eleves/{id}")
    @Timed
    public ResponseEntity<ClasseEleve> getClasseEleve(@PathVariable Long id) {
        log.debug("REST request to get ClasseEleve : {}", id);
        ClasseEleve classeEleve = classeEleveRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(classeEleve));
    }

    /**
     * DELETE /classe-eleves/:id : delete the "id" classeEleve.
     *
     * @param id the id of the classeEleve to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/classe-eleves/{id}")
    @Timed
    public ResponseEntity<Void> deleteClasseEleve(@PathVariable Long id) {
        log.debug("REST request to delete ClasseEleve : {}", id);
        classeEleveRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


}
