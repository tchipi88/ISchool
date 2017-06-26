package com.tsoft.ischool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.domain.FraisScolarite;

import com.tsoft.ischool.repository.FraisScolariteRepository;
import com.tsoft.ischool.service.AnneeService;
import com.tsoft.ischool.service.FraisScolariteService;
import com.tsoft.ischool.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing FraisScolarite.
 */
@RestController
@RequestMapping("/api")
public class FraisScolariteResource {

    private final Logger log = LoggerFactory.getLogger(FraisScolariteResource.class);

    private static final String ENTITY_NAME = "fraisScolarite";

    private final FraisScolariteRepository fraisScolariteRepository;
    private final FraisScolariteService fraisScolariteService;
    private final AnneeService anneeService;

    public FraisScolariteResource(FraisScolariteRepository fraisScolariteRepository,
            FraisScolariteService fraisScolariteService,
            AnneeService anneeService) {
        this.fraisScolariteRepository = fraisScolariteRepository;
        this.fraisScolariteService = fraisScolariteService;
        this.anneeService = anneeService;
    }

    /**
     * POST /frais-scolarites : Create a new fraisScolarite.
     *
     * @param fraisScolarite the fraisScolarite to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new fraisScolarite, or with status 400 (Bad Request) if the
     * fraisScolarite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/frais-scolarites")
    @Timed
    public ResponseEntity<List<FraisScolarite>> createFraisScolarite(@Valid @RequestBody List<FraisScolarite> fraisScolarite) throws Exception {
        log.debug("REST request to save FraisScolarite : {}");

        List<FraisScolarite> result = fraisScolariteService.save(fraisScolarite);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * GET /frais-scolarites : get all the coefficients.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     * coefficients in body
     */
    @GetMapping("/frais-scolarites")
    @Timed
    public ResponseEntity<List<FraisScolarite>> retrieveDatas() throws Exception {
        log.debug("REST request to get all Coefficients");
        List<FraisScolarite> page = fraisScolariteService.retrieveDatas();
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * GET /frais-scolarites/:id : get the "id" fraisScolarite.
     *
     * @param id the id of the fraisScolarite to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * fraisScolarite, or with status 404 (Not Found)
     */
    @GetMapping("/frais-scolarites/{id}")
    @Timed
    public ResponseEntity<FraisScolarite> getFraisScolarite(@PathVariable Long id) {
        log.debug("REST request to get FraisScolarite : {}", id);
        FraisScolarite fraisScolarite = fraisScolariteRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fraisScolarite));
    }

    /**
     * DELETE /frais-scolarites/:id : delete the "id" fraisScolarite.
     *
     * @param id the id of the fraisScolarite to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/frais-scolarites/{id}")
    @Timed
    public ResponseEntity<Void> deleteFraisScolarite(@PathVariable Long id) {
        log.debug("REST request to delete FraisScolarite : {}", id);
        fraisScolariteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
