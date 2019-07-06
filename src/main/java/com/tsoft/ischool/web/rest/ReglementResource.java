package com.tsoft.ischool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.domain.Reglement;
import com.tsoft.ischool.repository.ReglementRepository;
import com.tsoft.ischool.service.ReglementService;
import com.tsoft.ischool.service.dto.ReglementDto;
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
 * REST controller for managing Reglement.
 */
@RestController
@RequestMapping("/api")
public class ReglementResource {

    private final Logger log = LoggerFactory.getLogger(ReglementResource.class);

    private static final String ENTITY_NAME = "reglement";

    private final ReglementRepository reglementRepository;
    private final ReglementService reglementService;

    public ReglementResource(ReglementRepository reglementRepository, ReglementService reglementService) {
        this.reglementRepository = reglementRepository;
        this.reglementService = reglementService;
    }

    /**
     * POST /reglements : Create a new reglement.
     *
     * @param reglement the reglement to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new reglement, or with status 400 (Bad Request) if the reglement has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping(value = "/reglements")
    @Timed
    public ResponseEntity<Reglement> createReglement(@Valid @RequestBody Reglement reglement) throws Exception {
        log.debug("REST request to save Reglement : {}", reglement);
        if (reglement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new payment cannot already have an ID")).body(null);
        }
        LocalDate dateReglement = reglement.getDateVersement();
        if (dateReglement.isAfter(LocalDate.now())) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "badDate", "A new payment cannot be after now")).body(null);
        }
        Reglement result = reglementService.save(reglement);
        return ResponseEntity.created(new URI("/api/reglements/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId() + ""))
                .body(result);

    }

    
    /**
     * GET /reglements/:id : get the "id" reglement.
     *
     * @param id the id of the reglement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * reglement, or with status 404 (Not Found)
     */
    @GetMapping("/reglements-print/{id}")
    @Timed
    public ResponseEntity<byte[]> printReglement(@PathVariable Long id) throws Exception {
        log.debug("REST request to print Reglement : {}", id);
        Reglement reglement = reglementRepository.findOne(id);
        return reglementService.print(reglement);
    }


    /**
     * GET /reglements/:id : get the "id" reglement.
     *
     * @param id the id of the reglement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * reglement, or with status 404 (Not Found)
     */
    @GetMapping("/reglements/{id}")
    @Timed
    public ResponseEntity<Reglement> getReglement(@PathVariable Long id) {
        log.debug("REST request to get Reglement : {}", id);
        Reglement reglement = reglementRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reglement));
    }

    /**
     * DELETE /reglements/:id : delete the "id" reglement.
     *
     * @param id the id of the reglement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reglements/{id}")
    @Timed
    public ResponseEntity<Void> deleteReglement(@PathVariable Long id) {
        log.debug("REST request to delete Reglement : {}", id);
        reglementRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET /reglements : get a page of reglements between the fromDate and
     * toDate.
     *
     * @param fromDate the start of the time period of decaissements to get
     * @param toDate the end of the time period of decaissements to get
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of
     * decaissements in body
     */
    @GetMapping(path = "/reglements", params = {"fromDate", "toDate"})
    public ResponseEntity<List<Reglement>> getByDates(
            @RequestParam(value = "fromDate") LocalDate fromDate,
            @RequestParam(value = "toDate") LocalDate toDate,
            @ApiParam Pageable pageable) {

        Page<Reglement> page = reglementRepository.findAllByDateVersementBetween(fromDate, toDate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reglements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
