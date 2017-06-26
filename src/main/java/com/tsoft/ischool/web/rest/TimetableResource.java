package com.tsoft.ischool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.domain.Timetable;

import com.tsoft.ischool.repository.TimetableRepository;
import com.tsoft.ischool.service.TimetableService;
import com.tsoft.ischool.service.TimetableService;
import com.tsoft.ischool.service.dto.TimetableDTO;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing Timetable.
 */
@RestController
@RequestMapping("/api")
public class TimetableResource {

    private final Logger log = LoggerFactory.getLogger(TimetableResource.class);

    private static final String ENTITY_NAME = "timetable";

    private final TimetableRepository timetableRepository;
    private final TimetableService timetableService;



    public TimetableResource(TimetableRepository timetableRepository,TimetableService timetableService) {
        this.timetableRepository = timetableRepository;
        this.timetableService= timetableService;
    }

    /**
     * POST /timetables : Create a new timetable.
     *
     * @param timetable the timetable to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new timetable, or with status 400 (Bad Request) if the timetable has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/timetables")
    @Timed
    public ResponseEntity<Timetable> createTimetable(@Valid @RequestBody Timetable timetable) throws Exception {
        log.debug("REST request to save Timetable : {}", timetable);
        if (timetable.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new timetable cannot already have an ID")).body(null);
        }
        Timetable result = timetableService.save(timetable);
        return ResponseEntity.created(new URI("/api/timetables/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /timetables : Updates an existing timetable.
     *
     * @param timetable the timetable to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * timetable, or with status 400 (Bad Request) if the timetable is not
     * valid, or with status 500 (Internal Server Error) if the timetable
     * couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/timetables")
    @Timed
    public ResponseEntity<Timetable> updateTimetable(@Valid @RequestBody Timetable timetable) throws Exception {
        log.debug("REST request to update Timetable : {}", timetable);
        if (timetable.getId() == null) {
            return createTimetable(timetable);
        }
        Timetable result = timetableService.save(timetable);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timetable.getId().toString()))
                .body(result);
    }

    /**
     * GET /timetables : get all the timetables.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     * timetables in body
     */
    @GetMapping("/timetables")
    @Timed
    public ResponseEntity<List<Timetable>> getAllTimetables(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Timetables");
        Page<Timetable> page = timetableRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/timetables");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /timetables/:id : get the "id" timetable.
     *
     * @param id the id of the timetable to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * timetable, or with status 404 (Not Found)
     */
    @GetMapping("/timetables/{id}")
    @Timed
    public ResponseEntity<Timetable> getTimetable(@PathVariable Long id) {
        log.debug("REST request to get Timetable : {}", id);
        Timetable timetable = timetableRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(timetable));
    }

    /**
     * DELETE /timetables/:id : delete the "id" timetable.
     *
     * @param id the id of the timetable to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/timetables/{id}")
    @Timed
    public ResponseEntity<Void> deleteTimetable(@PathVariable Long id) {
        log.debug("REST request to delete Timetable : {}", id);
        timetableRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/timetablesRun")
    @Timed
    public void timetableGenerate() throws Exception {
        log.debug("REST request to generate Timetable");
        timetableService.generateEDT();
    }
    @GetMapping(value = "/getTimetableC/{classe}")
    @Timed
    public List<TimetableDTO>  getTimetablesC(@PathVariable String classe) throws Exception {
        log.debug("REST request to  Timetables to class {}",classe);
        return timetableService.getTimetablesC(classe);
    }
    @GetMapping(value = "/getTimetableP/{prof}")
    @Timed
    public List<TimetableDTO> getTimetablesP(@PathVariable Long prof) throws Exception {
        log.debug("REST request to  Timetables to professeur {}", prof);
        return timetableService.getTimetablesP(prof);
    }

}
