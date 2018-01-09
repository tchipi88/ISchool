package com.tsoft.ischool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.domain.CaisseDecaissement;
import com.tsoft.ischool.repository.CaisseDecaissementRepository;
import com.tsoft.ischool.web.rest.util.HeaderUtil;
import com.tsoft.ischool.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing CaisseDecaissement.
 */
@RestController
@RequestMapping("/api")
public class CaisseDecaissementResource {

    private final Logger log = LoggerFactory.getLogger(CaisseDecaissementResource.class);

    private static final String ENTITY_NAME = "caisseCaisseDecaissement";
        
    private final CaisseDecaissementRepository caisseDecaissementRepository;


    public CaisseDecaissementResource(CaisseDecaissementRepository caisseDecaissementRepository) {
        this.caisseDecaissementRepository = caisseDecaissementRepository;
    }

    /**
     * POST  /caisse-decaissements : Create a new caisseCaisseDecaissement.
     *
     * @param caisseCaisseDecaissement the caisseCaisseDecaissement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new caisseCaisseDecaissement, or with status 400 (Bad Request) if the caisseCaisseDecaissement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/caisse-decaissements")
    @Timed
    public ResponseEntity<CaisseDecaissement> createCaisseDecaissement(@Valid @RequestBody CaisseDecaissement caisseCaisseDecaissement) throws Exception {
        log.debug("REST request to save CaisseDecaissement : {}", caisseCaisseDecaissement);
        if (caisseCaisseDecaissement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new caisseCaisseDecaissement cannot already have an ID")).body(null);
        }
        CaisseDecaissement result = caisseDecaissementRepository.save(caisseCaisseDecaissement);
        return ResponseEntity.created(new URI("/api/caisse-decaissements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /caisse-decaissements : Updates an existing caisseCaisseDecaissement.
     *
     * @param caisseCaisseDecaissement the caisseCaisseDecaissement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated caisseCaisseDecaissement,
     * or with status 400 (Bad Request) if the caisseCaisseDecaissement is not valid,
     * or with status 500 (Internal Server Error) if the caisseCaisseDecaissement couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/caisse-decaissements")
    @Timed
    public ResponseEntity<CaisseDecaissement> updateCaisseDecaissement(@Valid @RequestBody CaisseDecaissement caisseCaisseDecaissement) throws Exception {
        log.debug("REST request to update CaisseDecaissement : {}", caisseCaisseDecaissement);
        if (caisseCaisseDecaissement.getId() == null) {
            return createCaisseDecaissement(caisseCaisseDecaissement);
        }
       CaisseDecaissement result = caisseDecaissementRepository.save(caisseCaisseDecaissement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, caisseCaisseDecaissement.getId().toString()))
            .body(result);
    }


 

    /**
     * GET  /caisse-decaissements/:id : get the "id" caisseCaisseDecaissement.
     *
     * @param id the id of the caisseCaisseDecaissement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the caisseCaisseDecaissement, or with status 404 (Not Found)
     */
    @GetMapping("/caisse-decaissements/{id}")
    @Timed
    public ResponseEntity<CaisseDecaissement> getCaisseDecaissement(@PathVariable Long id) {
        log.debug("REST request to get CaisseDecaissement : {}", id);
        CaisseDecaissement caisseCaisseDecaissement = caisseDecaissementRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(caisseCaisseDecaissement));
    }

    /**
     * DELETE  /caisse-decaissements/:id : delete the "id" caisseCaisseDecaissement.
     *
     * @param id the id of the caisseCaisseDecaissement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/caisse-decaissements/{id}")
    @Timed
    public ResponseEntity<Void> deleteCaisseDecaissement(@PathVariable Long id) {
        log.debug("REST request to delete CaisseDecaissement : {}", id);
        caisseDecaissementRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   /**
     * GET /caisse-decaissements : get a page of decaissements between the fromDate and toDate.
     *
     * @param fromDate the start of the time period of decaissements to get
     * @param toDate the end of the time period of decaissements to get
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of
     * decaissements in body
     */
    @GetMapping(path = "/caisse-decaissements", params = {"fromDate", "toDate"})
    public ResponseEntity<List<CaisseDecaissement>> getByDates(
            @RequestParam(value = "fromDate") LocalDate fromDate,
            @RequestParam(value = "toDate") LocalDate toDate,
            @ApiParam Pageable pageable) {

        Page<CaisseDecaissement> page = caisseDecaissementRepository.findAllByDateVersementBetween(fromDate, toDate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/caisse-decaissements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }



}
