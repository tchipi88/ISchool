package com.tsoft.ischool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.domain.CaisseEncaissement;

import com.tsoft.ischool.repository.CaisseEncaissementRepository;
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
 * REST controller for managing CaisseEncaissement.
 */
@RestController
@RequestMapping("/api")
public class CaisseEncaissementResource {

    private final Logger log = LoggerFactory.getLogger(CaisseEncaissementResource.class);

    private static final String ENTITY_NAME = "caisseEncaissement";
        
    private final CaisseEncaissementRepository caisseEncaissementRepository;


    public CaisseEncaissementResource(CaisseEncaissementRepository caisseEncaissementRepository) {
        this.caisseEncaissementRepository = caisseEncaissementRepository;
    }

    /**
     * POST  /caisse-encaissements : Create a new caisseEncaissement.
     *
     * @param caisseEncaissement the caisseEncaissement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new caisseEncaissement, or with status 400 (Bad Request) if the caisseEncaissement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/caisse-encaissements")
    @Timed
    public ResponseEntity<CaisseEncaissement> createCaisseEncaissement(@Valid @RequestBody CaisseEncaissement caisseEncaissement) throws Exception {
        log.debug("REST request to save CaisseEncaissement : {}", caisseEncaissement);
        if (caisseEncaissement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new caisseEncaissement cannot already have an ID")).body(null);
        }
        CaisseEncaissement result = caisseEncaissementRepository.save(caisseEncaissement);
        return ResponseEntity.created(new URI("/api/caisse-encaissements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /caisse-encaissements : Updates an existing caisseEncaissement.
     *
     * @param caisseEncaissement the caisseEncaissement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated caisseEncaissement,
     * or with status 400 (Bad Request) if the caisseEncaissement is not valid,
     * or with status 500 (Internal Server Error) if the caisseEncaissement couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/caisse-encaissements")
    @Timed
    public ResponseEntity<CaisseEncaissement> updateCaisseEncaissement(@Valid @RequestBody CaisseEncaissement caisseEncaissement) throws Exception {
        log.debug("REST request to update CaisseEncaissement : {}", caisseEncaissement);
        if (caisseEncaissement.getId() == null) {
            return createCaisseEncaissement(caisseEncaissement);
        }
        CaisseEncaissement result = caisseEncaissementRepository.save(caisseEncaissement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, caisseEncaissement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /caisse-encaissements : get all the caisseEncaissements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of caisseEncaissements in body
     */
    @GetMapping("/caisse-encaissements")
    @Timed
    public ResponseEntity<List<CaisseEncaissement>> getAllCaisseEncaissements(@ApiParam Pageable pageable) {
        log.debug("REST request to get all CaisseEncaissements");
        Page<CaisseEncaissement> page = caisseEncaissementRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/caisse-encaissements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /caisse-encaissements/:id : get the "id" caisseEncaissement.
     *
     * @param id the id of the caisseEncaissement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the caisseEncaissement, or with status 404 (Not Found)
     */
    @GetMapping("/caisse-encaissements/{id}")
    @Timed
    public ResponseEntity<CaisseEncaissement> getCaisseEncaissement(@PathVariable Long id) {
        log.debug("REST request to get CaisseEncaissement : {}", id);
        CaisseEncaissement caisseEncaissement = caisseEncaissementRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(caisseEncaissement));
    }

    /**
     * DELETE  /caisse-encaissements/:id : delete the "id" caisseEncaissement.
     *
     * @param id the id of the caisseEncaissement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/caisse-encaissements/{id}")
    @Timed
    public ResponseEntity<Void> deleteCaisseEncaissement(@PathVariable Long id) {
        log.debug("REST request to delete CaisseEncaissement : {}", id);
        caisseEncaissementRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   

/**
     * GET /caisse-encaissements : get a page of encaissements between the fromDate and toDate.
     *
     * @param fromDate the start of the time period of encaissements to get
     * @param toDate the end of the time period of encaissements to get
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of
     * encaissements in body
     */
    @GetMapping(path = "/caisse-encaissements", params = {"fromDate", "toDate"})
    public ResponseEntity<List<CaisseEncaissement>> getByDates(
            @RequestParam(value = "fromDate") LocalDate fromDate,
            @RequestParam(value = "toDate") LocalDate toDate,
            @ApiParam Pageable pageable) {

        Page<CaisseEncaissement> page = caisseEncaissementRepository.findAllByDateVersementBetween(fromDate, toDate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/caisse-encaissements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
