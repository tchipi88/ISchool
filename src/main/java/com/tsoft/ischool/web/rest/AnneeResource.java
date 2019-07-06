package com.tsoft.ischool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.domain.Annee;

import com.tsoft.ischool.repository.AnneeRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing Annee.
 */
@RestController
@RequestMapping("/api")
public class AnneeResource {

    private final Logger log = LoggerFactory.getLogger(AnneeResource.class);

    private static final String ENTITY_NAME = "annee";
        
    private final AnneeRepository anneeRepository;


    public AnneeResource(AnneeRepository anneeRepository) {
        this.anneeRepository = anneeRepository;
    }

    /**
     * POST  /annees : Create a new annee.
     *
     * @param annee the annee to create
     * @return the ResponseEntity with status 201 (Created) and with body the new annee, or with status 400 (Bad Request) if the annee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/annees")
    @Timed
    public ResponseEntity<Annee> createAnnee(@Valid @RequestBody Annee annee) throws Exception {
        log.debug("REST request to save Annee : {}", annee);
        if (annee.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new annee cannot already have an ID")).body(null);
        }
        annee.setId(annee.getDateDebut().getYear()+"-"+annee.getDateFin().getYear());
        Annee result = anneeRepository.save(annee);
        return ResponseEntity.created(new URI("/api/annees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /annees : Updates an existing annee.
     *
     * @param annee the annee to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated annee,
     * or with status 400 (Bad Request) if the annee is not valid,
     * or with status 500 (Internal Server Error) if the annee couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/annees")
    @Timed
    public ResponseEntity<Annee> updateAnnee(@Valid @RequestBody Annee annee) throws Exception {
        log.debug("REST request to update Annee : {}", annee);
        if (annee.getId() == null) {
            return createAnnee(annee);
        }
        //annee.setId(annee.getDateDebut().getYear()+"/"+annee.getDateFin().getYear());
        Annee result = anneeRepository.save(annee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, annee.getId().toString()))
            .body(result);
    }

    /**
     * GET  /annees : get all the annees.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of annees in body
     */
    @GetMapping("/annees")
    @Timed
    public ResponseEntity<List<Annee>> getAllAnnees(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Annees");
        Page<Annee> page = anneeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/annees");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /annees/:id : get the "id" annee.
     *
     * @param id the id of the annee to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the annee, or with status 404 (Not Found)
     */
    @GetMapping("/annees/{id}")
    @Timed
    public ResponseEntity<Annee> getAnnee(@PathVariable String id) {
        log.debug("REST request to get Annee : {}", id);
        Annee annee = anneeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(annee));
    }

    /**
     * DELETE  /annees/:id : delete the "id" annee.
     *
     * @param id the id of the annee to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/annees/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnnee(@PathVariable String id) {
        log.debug("REST request to delete Annee : {}", id);
        anneeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
