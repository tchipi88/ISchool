package com.tsoft.ischool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.domain.Matiere;

import com.tsoft.ischool.repository.MatiereRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing Matiere.
 */
@RestController
@RequestMapping("/api")
public class MatiereResource {

    private final Logger log = LoggerFactory.getLogger(MatiereResource.class);

    private static final String ENTITY_NAME = "matiere";

    private final MatiereRepository matiereRepository;

    public MatiereResource(MatiereRepository matiereRepository) {
        this.matiereRepository = matiereRepository;
    }

    /**
     * POST /matieres : Create a new matiere.
     *
     * @param matiere the matiere to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new matiere, or with status 400 (Bad Request) if the matiere has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/matieres")
    @Timed
    public ResponseEntity<Matiere> createMatiere(@Valid @RequestBody Matiere matiere) throws Exception {
        log.debug("REST request to save Matiere : {}", matiere);
        if (matiere.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new matiere cannot already have an ID")).body(null);
        }
        Matiere result = matiereRepository.save(matiere);
        return ResponseEntity.created(new URI("/api/matieres/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /matieres : Updates an existing matiere.
     *
     * @param matiere the matiere to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * matiere, or with status 400 (Bad Request) if the matiere is not valid, or
     * with status 500 (Internal Server Error) if the matiere couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/matieres")
    @Timed
    public ResponseEntity<Matiere> updateMatiere(@Valid @RequestBody Matiere matiere) throws Exception {
        log.debug("REST request to update Matiere : {}", matiere);
        if (matiere.getId() == null) {
            return createMatiere(matiere);
        }
        Matiere result = matiereRepository.save(matiere);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, matiere.getId().toString()))
                .body(result);
    }

    /**
     * GET /matieres : get all the matieres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of matieres
     * in body
     */
    @GetMapping("/matieres")
    @Timed
    public ResponseEntity<List<Matiere>> getAllMatieres(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Matieres");
        Page<Matiere> page = matiereRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/matieres");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/matieress")
    @Timed
    public ResponseEntity<List<Matiere>> getAllMatieres() {
        log.debug("REST request to get all Matieres");
        List<Matiere> page = matiereRepository.findAll();
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * GET /matieres/:id : get the "id" matiere.
     *
     * @param id the id of the matiere to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * matiere, or with status 404 (Not Found)
     */
    @GetMapping("/matieres/{id}")
    @Timed
    public ResponseEntity<Matiere> getMatiere(@PathVariable Long id) {
        log.debug("REST request to get Matiere : {}", id);
        Matiere matiere = matiereRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(matiere));
    }

    /**
     * DELETE /matieres/:id : delete the "id" matiere.
     *
     * @param id the id of the matiere to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/matieres/{id}")
    @Timed
    public ResponseEntity<Void> deleteMatiere(@PathVariable Long id) {
        log.debug("REST request to delete Matiere : {}", id);
        matiereRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
