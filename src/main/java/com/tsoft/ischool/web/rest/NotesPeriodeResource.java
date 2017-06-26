package com.tsoft.ischool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.domain.NotePeriode;

import com.tsoft.ischool.repository.NotesPeriodeRepository;
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
 * REST controller for managing NotePeriode.
 */
@RestController
@RequestMapping("/api")
public class NotesPeriodeResource {

    private final Logger log = LoggerFactory.getLogger(NotesPeriodeResource.class);

    private static final String ENTITY_NAME = "notesPeriode";
        
    private final NotesPeriodeRepository notesPeriodeRepository;


    public NotesPeriodeResource(NotesPeriodeRepository notesPeriodeRepository) {
        this.notesPeriodeRepository = notesPeriodeRepository;
    }

    /**
     * POST  /notes-periodes : Create a new notesPeriode.
     *
     * @param notesPeriode the notesPeriode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new notesPeriode, or with status 400 (Bad Request) if the notesPeriode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/notes-periodes")
    @Timed
    public ResponseEntity<NotePeriode> createNotesPeriode(@Valid @RequestBody NotePeriode notesPeriode) throws Exception {
        log.debug("REST request to save NotesPeriode : {}", notesPeriode);
        if (notesPeriode.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new notesPeriode cannot already have an ID")).body(null);
        }
        NotePeriode result = notesPeriodeRepository.save(notesPeriode);
        return ResponseEntity.created(new URI("/api/notes-periodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /notes-periodes : Updates an existing notesPeriode.
     *
     * @param notesPeriode the notesPeriode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated notesPeriode,
     * or with status 400 (Bad Request) if the notesPeriode is not valid,
     * or with status 500 (Internal Server Error) if the notesPeriode couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/notes-periodes")
    @Timed
    public ResponseEntity<NotePeriode> updateNotesPeriode(@Valid @RequestBody NotePeriode notesPeriode) throws Exception {
        log.debug("REST request to update NotesPeriode : {}", notesPeriode);
        if (notesPeriode.getId() == null) {
            return createNotesPeriode(notesPeriode);
        }
        NotePeriode result = notesPeriodeRepository.save(notesPeriode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, notesPeriode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /notes-periodes : get all the notesPeriodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of notesPeriodes in body
     */
    @GetMapping("/notes-periodes")
    @Timed
    public ResponseEntity<List<NotePeriode>> getAllNotesPeriodes(@ApiParam Pageable pageable) {
        log.debug("REST request to get all NotesPeriodes");
        Page<NotePeriode> page = notesPeriodeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/notes-periodes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /notes-periodes/:id : get the "id" notesPeriode.
     *
     * @param id the id of the notesPeriode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the notesPeriode, or with status 404 (Not Found)
     */
    @GetMapping("/notes-periodes/{id}")
    @Timed
    public ResponseEntity<NotePeriode> getNotesPeriode(@PathVariable Long id) {
        log.debug("REST request to get NotesPeriode : {}", id);
        NotePeriode notesPeriode = notesPeriodeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(notesPeriode));
    }

    /**
     * DELETE  /notes-periodes/:id : delete the "id" notesPeriode.
     *
     * @param id the id of the notesPeriode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/notes-periodes/{id}")
    @Timed
    public ResponseEntity<Void> deleteNotesPeriode(@PathVariable Long id) {
        log.debug("REST request to delete NotesPeriode : {}", id);
        notesPeriodeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
