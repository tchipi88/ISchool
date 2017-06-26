package com.tsoft.ischool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.domain.Creneau;

import com.tsoft.ischool.repository.CreneauRepository;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing Creneau.
 */
@RestController
@RequestMapping("/api")
public class CreneauResource {

    private final Logger log = LoggerFactory.getLogger(CreneauResource.class);

    private static final String ENTITY_NAME = "creneau";

    private final CreneauRepository creneauRepository;

    public CreneauResource(CreneauRepository creneauRepository) {
        this.creneauRepository = creneauRepository;
    }

    /**
     * POST /creneaus : Create a new creneau.
     *
     * @param creneau the creneau to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new creneau, or with status 400 (Bad Request) if the creneau has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/creneaus")
    @Timed
    public ResponseEntity<Creneau> createCreneau(@Valid @RequestBody Creneau creneau) throws Exception {
        log.debug("REST request to save Creneau : {}", creneau);
        if (creneau.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new creneau cannot already have an ID")).body(null);
        }
        Creneau result = creneauRepository.save(creneau);
        return ResponseEntity.created(new URI("/api/creneaus/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /creneaus : Updates an existing creneau.
     *
     * @param creneau the creneau to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * creneau, or with status 400 (Bad Request) if the creneau is not valid, or
     * with status 500 (Internal Server Error) if the creneau couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/creneaus")
    @Timed
    public ResponseEntity<Creneau> updateCreneau(@Valid @RequestBody Creneau creneau) throws Exception {
        log.debug("REST request to update Creneau : {}", creneau);
        if (creneau.getId() == null) {
            return createCreneau(creneau);
        }
        Creneau result = creneauRepository.save(creneau);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, creneau.getId().toString()))
                .body(result);
    }

    /**
     * GET /creneaus : get all the creneaus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of creneaus
     * in body
     */
    @GetMapping("/creneaus")
    @Timed
    public ResponseEntity<List<Creneau>> getAllCreneaus(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Creneaus");
        Page<Creneau> page = creneauRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/creneaus");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /creneaus/:id : get the "id" creneau.
     *
     * @param id the id of the creneau to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * creneau, or with status 404 (Not Found)
     */
    @GetMapping("/creneaus/{id}")
    @Timed
    public ResponseEntity<Creneau> getCreneau(@PathVariable Long id) {
        log.debug("REST request to get Creneau : {}", id);
        Creneau creneau = creneauRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(creneau));
    }

    /**
     * DELETE /creneaus/:id : delete the "id" creneau.
     *
     * @param id the id of the creneau to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/creneaus/{id}")
    @Timed
    public ResponseEntity<Void> deleteCreneau(@PathVariable Long id) {
        log.debug("REST request to delete Creneau : {}", id);
        creneauRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/creneaus-demo")
    @Timed
    public ResponseEntity<Void> createBulkCreneau() {
        LocalTime timee = LocalTime.of(07, 30);
        List<Creneau> creneaux = new ArrayList();
        for (int i = 1; i <= 10; i++) {
            Creneau cr = new Creneau();
            cr.setHeureDebut(timee);
            if (i == 3) {
                timee = timee.plusMinutes(15);
                 cr.setPause(true);
            } else if (i == 7) {
                timee = timee.plusMinutes(45);
                cr.setPause(true);
            } 
            else {
                timee = timee.plusHours(1);
            }
            cr.setHeureFin(timee);
            creneaux.add(cr);
        }
        creneauRepository.save(creneaux);
        return ResponseEntity.ok().build();
    }

}
