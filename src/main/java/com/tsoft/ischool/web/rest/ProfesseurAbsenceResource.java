package com.tsoft.ischool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.domain.ProfesseurAbsence;

import com.tsoft.ischool.repository.ProfesseurAbsenceRepository;
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
 * REST controller for managing ProfesseurAbsence.
 */
@RestController
@RequestMapping("/api")
public class ProfesseurAbsenceResource {

    private final Logger log = LoggerFactory.getLogger(ProfesseurAbsenceResource.class);

    private static final String ENTITY_NAME = "professeurAbsence";
        
    private final ProfesseurAbsenceRepository professeurAbsenceRepository;


    public ProfesseurAbsenceResource(ProfesseurAbsenceRepository professeurAbsenceRepository) {
        this.professeurAbsenceRepository = professeurAbsenceRepository;
    }

    /**
     * POST  /professeur-absences : Create a new professeurAbsence.
     *
     * @param professeurAbsence the professeurAbsence to create
     * @return the ResponseEntity with status 201 (Created) and with body the new professeurAbsence, or with status 400 (Bad Request) if the professeurAbsence has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/professeur-absences")
    @Timed
    public ResponseEntity<ProfesseurAbsence> createProfesseurAbsence(@Valid @RequestBody ProfesseurAbsence professeurAbsence) throws Exception {
        log.debug("REST request to save ProfesseurAbsence : {}", professeurAbsence);
        if (professeurAbsence.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new professeurAbsence cannot already have an ID")).body(null);
        }
        ProfesseurAbsence result = professeurAbsenceRepository.save(professeurAbsence);
        return ResponseEntity.created(new URI("/api/professeur-absences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /professeur-absences : Updates an existing professeurAbsence.
     *
     * @param professeurAbsence the professeurAbsence to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated professeurAbsence,
     * or with status 400 (Bad Request) if the professeurAbsence is not valid,
     * or with status 500 (Internal Server Error) if the professeurAbsence couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/professeur-absences")
    @Timed
    public ResponseEntity<ProfesseurAbsence> updateProfesseurAbsence(@Valid @RequestBody ProfesseurAbsence professeurAbsence) throws Exception {
        log.debug("REST request to update ProfesseurAbsence : {}", professeurAbsence);
        if (professeurAbsence.getId() == null) {
            return createProfesseurAbsence(professeurAbsence);
        }
        ProfesseurAbsence result = professeurAbsenceRepository.save(professeurAbsence);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, professeurAbsence.getId().toString()))
            .body(result);
    }

    /**
     * GET  /professeur-absences : get all the professeurAbsences.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of professeurAbsences in body
     */
    @GetMapping("/professeur-absences")
    @Timed
    public ResponseEntity<List<ProfesseurAbsence>> getAllProfesseurAbsences(@ApiParam Pageable pageable) {
        log.debug("REST request to get all ProfesseurAbsences");
        Page<ProfesseurAbsence> page = professeurAbsenceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/professeur-absences");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /professeur-absences/:id : get the "id" professeurAbsence.
     *
     * @param id the id of the professeurAbsence to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the professeurAbsence, or with status 404 (Not Found)
     */
    @GetMapping("/professeur-absences/{id}")
    @Timed
    public ResponseEntity<ProfesseurAbsence> getProfesseurAbsence(@PathVariable Long id) {
        log.debug("REST request to get ProfesseurAbsence : {}", id);
        ProfesseurAbsence professeurAbsence = professeurAbsenceRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(professeurAbsence));
    }

    /**
     * DELETE  /professeur-absences/:id : delete the "id" professeurAbsence.
     *
     * @param id the id of the professeurAbsence to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/professeur-absences/{id}")
    @Timed
    public ResponseEntity<Void> deleteProfesseurAbsence(@PathVariable Long id) {
        log.debug("REST request to delete ProfesseurAbsence : {}", id);
        professeurAbsenceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
