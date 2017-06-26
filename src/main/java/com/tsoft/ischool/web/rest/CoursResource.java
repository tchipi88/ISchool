package com.tsoft.ischool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.domain.Cours;

import com.tsoft.ischool.repository.CoursRepository;
import com.tsoft.ischool.service.AnneeService;
import com.tsoft.ischool.service.CoursService;
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
 * REST controller for managing Cours.
 */
@RestController
@RequestMapping("/api")
public class CoursResource {

    private final Logger log = LoggerFactory.getLogger(CoursResource.class);

    private static final String ENTITY_NAME = "cours";
        
    private final CoursRepository coursRepository;
    
    private final AnneeService anneeService;

    private final CoursService coursService;

    public CoursResource(CoursRepository coursRepository,AnneeService anneeService,CoursService coursService) {
        this.coursRepository = coursRepository;
        this.anneeService=anneeService;
        this.coursService=coursService;
    }

    /**
     * POST  /courss : Create a new cours.
     *
     * @param courss
     * @return the ResponseEntity with status 201 (Created) and with body the new cours, or with status 400 (Bad Request) if the cours has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/courss")
    @Timed
    public ResponseEntity<List<Cours>> createCours( @RequestBody List<Cours> courss) throws Exception {
        log.debug("REST request to save Cours : {}");
        List<Cours> result = coursService.save(courss);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

  

    /**
     * GET  /courss : get all the courss.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of courss in body
     */
    @GetMapping("/coursss/{classe}")
    @Timed
    public ResponseEntity<List<Cours>> getAllCourss(@PathVariable String classe) throws Exception {
        log.debug("REST request to get all Courss");
        List<Cours> page = coursService.retrieveDatas(classe);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
    /**
     * GET  /courss : get all the courss.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of courss in body
     */
    @GetMapping("/courss")
    @Timed
    public ResponseEntity<List<Cours>> getAllCourss() throws Exception {
        log.debug("REST request to get all Courss");
        List<Cours> page = coursRepository.findByAnnee(anneeService.getAnneeCourante());
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

 

    /**
     * GET  /courss/:id : get the "id" cours.
     *
     * @param id the id of the cours to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cours, or with status 404 (Not Found)
     */
    @GetMapping("/courss/{id}")
    @Timed
    public ResponseEntity<Cours> getCours(@PathVariable Long id) {
        log.debug("REST request to get Cours : {}", id);
        Cours cours = coursRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cours));
    }

    /**
     * DELETE  /courss/:id : delete the "id" cours.
     *
     * @param id the id of the cours to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/courss/{id}")
    @Timed
    public ResponseEntity<Void> deleteCours(@PathVariable Long id) {
        log.debug("REST request to delete Cours : {}", id);
        coursRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
