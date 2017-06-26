package com.tsoft.ischool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.domain.Coefficient;
import com.tsoft.ischool.domain.Matiere;

import com.tsoft.ischool.repository.CoefficientRepository;
import com.tsoft.ischool.service.CoefficientService;
import com.tsoft.ischool.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing Coefficient.
 */
@RestController
@RequestMapping("/api")
public class CoefficientResource {

    private final Logger log = LoggerFactory.getLogger(CoefficientResource.class);

    private static final String ENTITY_NAME = "coefficient";

    private final CoefficientRepository coefficientRepository;
    private final CoefficientService coefficientService;

    public CoefficientResource(CoefficientRepository coefficientRepository, CoefficientService coefficientService) {
        this.coefficientRepository = coefficientRepository;
        this.coefficientService = coefficientService;
    }

    /**
     * POST /coefficients : Create a new coefficient.
     *
     * @param coefficients
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new coefficient, or with status 400 (Bad Request) if the coefficient has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/coefficients")
    @Timed
    public ResponseEntity<List<Coefficient>> createCoefficient(@Valid @RequestBody List<Coefficient> coefficients) throws Exception {
        log.debug("REST request to save Coefficient : {}");
        
        List<Coefficient> result = coefficientService.save(coefficients);
        return new ResponseEntity<>(result,HeaderUtil.createSuccesMessage(), HttpStatus.OK);
    }

    

    /**
     * GET /coefficients : get all the coefficients.
     *
     * @param matiere
     * @return the ResponseEntity with status 200 (OK) and the list of
     * coefficients in body
     */
    @GetMapping("/coefficients")
    @Timed
    public ResponseEntity<List<Coefficient>> getAllCoefficients(@ApiParam Long matiere) throws Exception {
        log.debug("REST request to get all Coefficients");
        List<Coefficient> page = coefficientService.retrieveDatas(matiere);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * GET /coefficients/:id : get the "id" coefficient.
     *
     * @param id the id of the coefficient to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * coefficient, or with status 404 (Not Found)
     */
    @GetMapping("/coefficients/{id}")
    @Timed
    public ResponseEntity<Coefficient> getCoefficient(@PathVariable Long id) {
        log.debug("REST request to get Coefficient : {}", id);
        Coefficient coefficient = coefficientRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(coefficient));
    }

    /**
     * DELETE /coefficients/:id : delete the "id" coefficient.
     *
     * @param id the id of the coefficient to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/coefficients/{id}")
    @Timed
    public ResponseEntity<Void> deleteCoefficient(@PathVariable Long id) {
        log.debug("REST request to delete Coefficient : {}", id);
        coefficientRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    
    @GetMapping("/matiereclasse/{serie}")
    @Timed
    public ResponseEntity<List<Matiere>> getCoefficient(@PathVariable String serie) {
        log.debug("REST request to get Coefficient : {}", serie);
        List<Coefficient> coefs = coefficientRepository.findBySerieId(serie);
        List<Matiere> result=new ArrayList();
        coefs.forEach((c) -> {
            result.add(c.getMatiere());
        });
        return new ResponseEntity(result, HttpStatus.OK);
    }

   
  

}
