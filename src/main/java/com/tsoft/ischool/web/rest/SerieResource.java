package com.tsoft.ischool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.domain.Serie;

import com.tsoft.ischool.repository.SerieRepository;
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
 * REST controller for managing Serie.
 */
@RestController
@RequestMapping("/api")
public class SerieResource {

    private final Logger log = LoggerFactory.getLogger(SerieResource.class);

    private static final String ENTITY_NAME = "serie";
        
    private final SerieRepository serieRepository;


    public SerieResource(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    /**
     * POST  /series : Create a new serie.
     *
     * @param serie the serie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serie, or with status 400 (Bad Request) if the serie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/series")
    @Timed
    public ResponseEntity<Serie> createSerie(@Valid @RequestBody Serie serie) throws Exception {
        log.debug("REST request to save Serie : {}", serie);
        if (serie.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new serie cannot already have an ID")).body(null);
        }
        Serie result = serieRepository.save(serie);
        return ResponseEntity.created(new URI("/api/series/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /series : Updates an existing serie.
     *
     * @param serie the serie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serie,
     * or with status 400 (Bad Request) if the serie is not valid,
     * or with status 500 (Internal Server Error) if the serie couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/series")
    @Timed
    public ResponseEntity<Serie> updateSerie(@Valid @RequestBody Serie serie) throws Exception {
        log.debug("REST request to update Serie : {}", serie);
        if (serie.getId() == null) {
            return createSerie(serie);
        }
        Serie result = serieRepository.save(serie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /series : get all the series.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of series in body
     */
    @GetMapping("/series")
    @Timed
    public ResponseEntity<List<Serie>> getAllSeries(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Series");
        Page<Serie> page = serieRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/series");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /series/:id : get the "id" serie.
     *
     * @param id the id of the serie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serie, or with status 404 (Not Found)
     */
    @GetMapping("/series/{id}")
    @Timed
    public ResponseEntity<Serie> getSerie(@PathVariable String id) {
        log.debug("REST request to get Serie : {}", id);
        Serie serie = serieRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(serie));
    }

    /**
     * DELETE  /series/:id : delete the "id" serie.
     *
     * @param id the id of the serie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/series/{id}")
    @Timed
    public ResponseEntity<Void> deleteSerie(@PathVariable String id) {
        log.debug("REST request to delete Serie : {}", id);
        serieRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
