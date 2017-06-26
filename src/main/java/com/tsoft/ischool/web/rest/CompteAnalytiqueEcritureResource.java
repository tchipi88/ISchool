package com.tsoft.ischool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.domain.CompteAnalytiqueEcriture;

import com.tsoft.ischool.repository.CompteAnalytiqueEcritureRepository;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing CompteAnalytiqueEcriture.
 */
@RestController
@RequestMapping("/api")
public class CompteAnalytiqueEcritureResource {

    private final Logger log = LoggerFactory.getLogger(CompteAnalytiqueEcritureResource.class);

    private static final String ENTITY_NAME = "compteAnalytiqueEcriture";

    private final CompteAnalytiqueEcritureRepository compteAnalytiqueEcritureRepository;

    public CompteAnalytiqueEcritureResource(CompteAnalytiqueEcritureRepository compteAnalytiqueEcritureRepository) {
        this.compteAnalytiqueEcritureRepository = compteAnalytiqueEcritureRepository;
    }

    /**
     * POST /compte-analytique-ecritures : Create a new
     * compteAnalytiqueEcriture.
     *
     * @param compteAnalytiqueEcriture the compteAnalytiqueEcriture to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new compteAnalytiqueEcriture, or with status 400 (Bad Request) if the
     * compteAnalytiqueEcriture has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/compte-analytique-ecritures")
    @Timed
    public ResponseEntity<CompteAnalytiqueEcriture> createCompteAnalytiqueEcriture(@Valid @RequestBody CompteAnalytiqueEcriture compteAnalytiqueEcriture) throws Exception {
        log.debug("REST request to save CompteAnalytiqueEcriture : {}", compteAnalytiqueEcriture);
        if (compteAnalytiqueEcriture.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new compteAnalytiqueEcriture cannot already have an ID")).body(null);
        }
        CompteAnalytiqueEcriture result = compteAnalytiqueEcritureRepository.save(compteAnalytiqueEcriture);
        return ResponseEntity.created(new URI("/api/compte-analytique-ecritures/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /compte-analytique-ecritures : Updates an existing
     * compteAnalytiqueEcriture.
     *
     * @param compteAnalytiqueEcriture the compteAnalytiqueEcriture to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * compteAnalytiqueEcriture, or with status 400 (Bad Request) if the
     * compteAnalytiqueEcriture is not valid, or with status 500 (Internal
     * Server Error) if the compteAnalytiqueEcriture couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/compte-analytique-ecritures")
    @Timed
    public ResponseEntity<CompteAnalytiqueEcriture> updateCompteAnalytiqueEcriture(@Valid @RequestBody CompteAnalytiqueEcriture compteAnalytiqueEcriture) throws Exception {
        log.debug("REST request to update CompteAnalytiqueEcriture : {}", compteAnalytiqueEcriture);
        if (compteAnalytiqueEcriture.getId() == null) {
            return createCompteAnalytiqueEcriture(compteAnalytiqueEcriture);
        }
        CompteAnalytiqueEcriture result = compteAnalytiqueEcritureRepository.save(compteAnalytiqueEcriture);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compteAnalytiqueEcriture.getId().toString()))
                .body(result);
    }

    /**
     * GET /compte-analytique-ecritures : get all the compteAnalytiqueEcritures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     * compteAnalytiqueEcritures in body
     */
    @GetMapping("/compte-analytique-ecritures")
    @Timed
    public ResponseEntity<List<CompteAnalytiqueEcriture>> getAllCompteAnalytiqueEcritures(@ApiParam Pageable pageable) {
        log.debug("REST request to get all CompteAnalytiqueEcritures");
        Page<CompteAnalytiqueEcriture> page = compteAnalytiqueEcritureRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/compte-analytique-ecritures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /compte-analytique-ecritures/:id : get the "id"
     * compteAnalytiqueEcriture.
     *
     * @param id the id of the compteAnalytiqueEcriture to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * compteAnalytiqueEcriture, or with status 404 (Not Found)
     */
    @GetMapping("/compte-analytique-ecritures/{id}")
    @Timed
    public ResponseEntity<CompteAnalytiqueEcriture> getCompteAnalytiqueEcriture(@PathVariable Long id) {
        log.debug("REST request to get CompteAnalytiqueEcriture : {}", id);
        CompteAnalytiqueEcriture compteAnalytiqueEcriture = compteAnalytiqueEcritureRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(compteAnalytiqueEcriture));
    }

    /**
     * DELETE /compte-analytique-ecritures/:id : delete the "id"
     * compteAnalytiqueEcriture.
     *
     * @param id the id of the compteAnalytiqueEcriture to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/compte-analytique-ecritures/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompteAnalytiqueEcriture(@PathVariable Long id) {
        log.debug("REST request to delete CompteAnalytiqueEcriture : {}", id);
        compteAnalytiqueEcritureRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET /ecriture-compte-analytiques : get a page of
     * EcritureCompteAnalytiques between the fromDate and toDate.
     *
     * @param fromDate the start of the time period of
     * ecriture-compte-analytiques to get
     * @param toDate the end of the time period of ecriture-compte-analytiques
     * to get
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of
     * EcritureCompteAnalytiques in body
     */
    @GetMapping(path = "/ecriture-compte-analytiques", params = {"fromDate", "toDate"})
    public ResponseEntity<List<CompteAnalytiqueEcriture>> getByDates(
            @RequestParam(value = "fromDate") LocalDate fromDate,
            @RequestParam(value = "toDate") LocalDate toDate,
            @ApiParam Pageable pageable) {

        Page<CompteAnalytiqueEcriture> page = compteAnalytiqueEcritureRepository.findAllByDateEcritureBetween(fromDate.atTime(0, 0), toDate.atTime(23, 59), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/compte-analytique-ecritures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
