package com.tsoft.ischool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.domain.Classe;
import com.tsoft.ischool.domain.Serie;

import com.tsoft.ischool.repository.ClasseRepository;
import com.tsoft.ischool.service.ClasseService;
import com.tsoft.ischool.web.rest.util.HeaderUtil;
import com.tsoft.ischool.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing Classe.
 */
@RestController
@RequestMapping("/api")
public class ClasseResource {

    private final Logger log = LoggerFactory.getLogger(ClasseResource.class);

    private static final String ENTITY_NAME = "classe";
        
    private final ClasseRepository classeRepository;
    private final ClasseService classeService;


    public ClasseResource(ClasseRepository classeRepository,ClasseService classeService) {
        this.classeRepository = classeRepository;
        this.classeService= classeService;
    }

    /**
     * POST  /classes : Create a new classe.
     *
     * @param series
     * @return the ResponseEntity with status 201 (Created) and with body the new classe, or with status 400 (Bad Request) if the classe has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/classes")
    @Timed
    public ResponseEntity<List<Classe>> createClasse(@Valid @RequestBody List<Serie> series) throws Exception {
        log.debug("REST request to save Classe : {}");
         List<Classe> result = classeService.save(series);
        return new ResponseEntity<>(result,HeaderUtil.createSuccesMessage(), HttpStatus.OK);
    }

    

    /**
     * GET  /classes : get all the classes.
     *
     * @param pageable
     * @return the ResponseEntity with status 200 (OK) and the list of classes in body
     */
    @GetMapping("/classes")
    @Timed
    public ResponseEntity<List<Classe>> getAllClasses(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Classes");
        Page<Classe> page = classeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/classes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /classes/:id : get the "id" classe.
     *
     * @param id the id of the classe to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classe, or with status 404 (Not Found)
     */
    @GetMapping("/classes/{id}")
    @Timed
    public ResponseEntity<Classe> getClasse(@PathVariable String id) {
        log.debug("REST request to get Classe : {}", id);
        Classe classe = classeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(classe));
    }

    /**
     * DELETE  /classes/:id : delete the "id" classe.
     *
     * @param id the id of the classe to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/classes/{id}")
    @Timed
    public ResponseEntity<Void> deleteClasse(@PathVariable String id) {
        log.debug("REST request to delete Classe : {}", id);
        classeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
