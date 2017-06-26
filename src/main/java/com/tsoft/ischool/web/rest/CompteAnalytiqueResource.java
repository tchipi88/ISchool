package com.tsoft.ischool.web.rest;


import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.domain.CompteAnalytique;
import com.tsoft.ischool.domain.enumeration.Sexe;
import com.tsoft.ischool.repository.CompteAnalytiqueRepository;
import com.tsoft.ischool.repository.search.CompteAnalytiqueSearchRepository;
import com.tsoft.ischool.service.AnneeService;
import com.tsoft.ischool.web.rest.util.HeaderUtil;
import com.tsoft.ischool.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.io.IOUtils;

import static org.elasticsearch.index.query.QueryBuilders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * REST controller for managing CompteAnalytique.
 */
@RestController
@RequestMapping("/api")
public class CompteAnalytiqueResource {

    private final Logger log = LoggerFactory.getLogger(CompteAnalytiqueResource.class);

    private static final String ENTITY_NAME = "compteAnalytique";
        
    private final CompteAnalytiqueRepository compteAnalytiqueRepository;

    private final CompteAnalytiqueSearchRepository compteAnalytiqueSearchRepository;
    @Autowired
    ResourceLoader resourceLoader;
    
    @Autowired
    AnneeService anneeService;

    public CompteAnalytiqueResource(CompteAnalytiqueRepository compteAnalytiqueRepository, CompteAnalytiqueSearchRepository compteAnalytiqueSearchRepository) {
        this.compteAnalytiqueRepository = compteAnalytiqueRepository;
        this.compteAnalytiqueSearchRepository = compteAnalytiqueSearchRepository;
    }

   
   /**
     * GET  /compte-analytiques : get all the compteAnalytiques.
     *
     * @param pageable
     * @param idClasse
     * @return the ResponseEntity with status 200 (OK) and the list of compteAnalytiques in body
     */
    @GetMapping("/compte-analytiques")
    @Timed
    public ResponseEntity<List<CompteAnalytique>> getAllCompteAnalytiques(@ApiParam Pageable pageable,String classe) throws Exception {
        log.debug("REST request to get all CompteAnalytiques");
        Page<CompteAnalytique> page = compteAnalytiqueRepository.findByClasse(pageable,classe,anneeService.getAnneeCourante());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/compte-analytiques");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /compte-analytiques/:id : get the "id" compteAnalytique.
     *
     * @param id the id of the compteAnalytique to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compteAnalytique, or with status 404 (Not Found)
     */
    @GetMapping("/compte-analytiques/{id}")
    @Timed
    public ResponseEntity<CompteAnalytique> getCompteAnalytique(@PathVariable Long id) {
        log.debug("REST request to get CompteAnalytique : {}", id);
        CompteAnalytique compteAnalytique = compteAnalytiqueRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(compteAnalytique));
    }

    /**
     * DELETE  /compte-analytiques/:id : delete the "id" compteAnalytique.
     *
     * @param id the id of the compteAnalytique to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/compte-analytiques/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompteAnalytique(@PathVariable Long id) {
        log.debug("REST request to delete CompteAnalytique : {}", id);
        compteAnalytiqueRepository.delete(id);
        compteAnalytiqueSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/compte-analytiques?query=:query : search for the compteAnalytique corresponding
     * to the query.
     *
     * @param query the query of the compteAnalytique search 
     * @return the result of the search
     */
    @GetMapping("/_search/compte-analytiques")
    @Timed
    public ResponseEntity<List<CompteAnalytique>> searchCompteAnalytiques(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search CompteAnalytiques for query {}", query);
      Page<CompteAnalytique> page = compteAnalytiqueSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/compte-analytiques");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 /**
     * GET /classe-eleves-print : get all the classeEleves.
     *
     * @param classe
     * @return the ResponseEntity with status 200 (OK) and the list of
     * classeEleves in body
     */
    @GetMapping(path = "/compte-analytiques-print/{classe}", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    @Timed
    public ResponseEntity<byte[]> printAllClasseEleves(
            @PathVariable String classe) throws Exception {
        log.debug("REST request to print all Compte Eleves de la classe  {}", classe);
        Comparator<CompteAnalytique> byNom = (CompteAnalytique p1, CompteAnalytique p2) -> p1.getEleve().getNom().compareTo(p2.getEleve().getNom());
        List<CompteAnalytique> eleves = compteAnalytiqueRepository.findByClasse(classe,anneeService.getAnneeCourante()).stream().sorted(byNom).collect(toList());

        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        String destfile = uploadedfile.getAbsolutePath() + File.separator + "ComptesEleve"
                + classe + ".pdf";

        FastReportBuilder drb = new FastReportBuilder();
        drb.addFirstPageImageBanner(resourceLoader.getResource("classpath:ischool/reports/logo-ecole.png").getFile().getAbsolutePath(), new Integer(300), new Integer(60), ImageBanner.Alignment.Center);
        drb.addColumn("Matricule ", "eleve.id", Long.class.getName(), 30)
                .addColumn("Nom", "eleve.nom", String.class.getName(), 30)
                .addColumn("Prenom", "eleve.prenom", String.class.getName(), 30)
                .addColumn("Sexe", "eleve.sexe", Sexe.class, 30)
                 .addColumn("Né(e) le", "eleve.dateNaissance", LocalDate.class.getName(), 30)
                .addColumn("Lieu Naissance", "eleve.lieuNaissance", String.class.getName(), 30)
                .addColumn("Solde", "solde", BigDecimal.class.getName(), 30)
                .setTitle("Liste des Comptes Eleves " + classe + "    Annee :" + anneeService.getAnneeCourante().getId())
                //.setSubtitle("This report was generated at " + LocalDate.now())
                .setPrintBackgroundOnOddRows(true)
                .setUseFullPageWidth(true);

        DynamicReport dr = drb.build();
        JRDataSource source = new JRBeanCollectionDataSource(eleves);

        JasperReport jr = DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager(), new HashMap());
        JasperPrint jp = JasperFillManager.fillReport(jr, new HashMap(), source);
        JasperExportManager.exportReportToPdfFile(jp, destfile);

        Resource resource = resourceLoader.getResource("file:" + destfile);
        InputStream in = resource.getInputStream();
        try {
            return new ResponseEntity<>(IOUtils.toByteArray(in), HeaderUtil.downloadAlert(resource), HttpStatus.OK);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }



}
