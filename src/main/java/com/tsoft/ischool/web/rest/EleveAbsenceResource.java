package com.tsoft.ischool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.domain.EleveAbsence;

import com.tsoft.ischool.repository.EleveAbsenceRepository;
import com.tsoft.ischool.service.EleveAbsenceService;
import com.tsoft.ischool.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing EleveAbsence.
 */
@RestController
@RequestMapping("/api")
public class EleveAbsenceResource {

    private final Logger log = LoggerFactory.getLogger(EleveAbsenceResource.class);

    private static final String ENTITY_NAME = "eleveAbsence";

    private final EleveAbsenceRepository eleveAbsenceRepository;
    private final EleveAbsenceService eleveAbsenceService;

    public EleveAbsenceResource(EleveAbsenceRepository eleveAbsenceRepository, EleveAbsenceService eleveAbsenceService) {
        this.eleveAbsenceRepository = eleveAbsenceRepository;
        this.eleveAbsenceService = eleveAbsenceService;
    }

    /**
     * POST /eleve-absences : Create a new eleveAbsence.
     *
     * @param eleveAbsences
     * @param eleveAbsence the eleveAbsence to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new eleveAbsence, or with status 400 (Bad Request) if the eleveAbsence
     * has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/eleve-absences")
    @Timed
    public ResponseEntity<List<EleveAbsence>> createEleveAbsence(@RequestBody List<EleveAbsence> eleveAbsences) throws Exception {
        log.debug("REST request to save EleveAbsence : {}");
        List<EleveAbsence> result = eleveAbsenceService.save(eleveAbsences);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

   

    /**
     * GET /eleve-absences : get all the eleveAbsences.
     *
     * @param classe
     * @param numseq
     * @return the ResponseEntity with status 200 (OK) and the list of
     * eleveAbsences in body
     */
    @GetMapping("/eleve-absences")
    @Timed
    public ResponseEntity<List<EleveAbsence>> getAllEleveAbsences(@ApiParam String classe,@ApiParam Integer numseq ) throws Exception {
        log.debug("REST request to get all EleveAbsences");
        List<EleveAbsence> page = eleveAbsenceService.retrieveDatas( numseq, classe);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * GET /eleve-absences/:id : get the "id" eleveAbsence.
     *
     * @param id the id of the eleveAbsence to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * eleveAbsence, or with status 404 (Not Found)
     */
    @GetMapping("/eleve-absences/{id}")
    @Timed
    public ResponseEntity<EleveAbsence> getEleveAbsence(@PathVariable Long id) {
        log.debug("REST request to get EleveAbsence : {}", id);
        EleveAbsence eleveAbsence = eleveAbsenceRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eleveAbsence));
    }

    /**
     * DELETE /eleve-absences/:id : delete the "id" eleveAbsence.
     *
     * @param id the id of the eleveAbsence to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/eleve-absences/{id}")
    @Timed
    public ResponseEntity<Void> deleteEleveAbsence(@PathVariable Long id) {
        log.debug("REST request to delete EleveAbsence : {}", id);
        eleveAbsenceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
