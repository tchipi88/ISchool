package com.tsoft.ischool.web.rest;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.domain.PersonEntity;
import com.tsoft.ischool.domain.Professeur;
import com.tsoft.ischool.domain.enumeration.Civilite;
import com.tsoft.ischool.domain.enumeration.Sexe;
import com.tsoft.ischool.domain.enumeration.TypePersonne;
import com.tsoft.ischool.repository.PersonRepository;
import com.tsoft.ischool.repository.ProfesseurRepository;
import com.tsoft.ischool.repository.search.ProfesseurSearchRepository;
import com.tsoft.ischool.service.AnneeService;
import com.tsoft.ischool.web.rest.util.HeaderUtil;
import com.tsoft.ischool.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import javax.validation.Valid;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.io.IOUtils;
import static org.elasticsearch.index.query.QueryBuilders.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing Professeur.
 */
@RestController
@RequestMapping("/api")
public class ProfesseurResource {

    private final Logger log = LoggerFactory.getLogger(ProfesseurResource.class);

    private static final String ENTITY_NAME = "professeur";

    private final ProfesseurRepository professeurRepository;

    private final ProfesseurSearchRepository professeurSearchRepository;
    @Autowired
    AnneeService annneService;
    @Autowired
    ResourceLoader resourceLoader;

    private final PersonRepository personRepository;

    public ProfesseurResource(ProfesseurRepository professeurRepository, ProfesseurSearchRepository professeurSearchRepository,
                              PersonRepository personRepository) {
        this.professeurRepository = professeurRepository;
        this.professeurSearchRepository = professeurSearchRepository;
        this.personRepository = personRepository;
    }

    /**
     * POST /professeurs : Create a new professeur.
     *
     * @param professeur the professeur to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new professeur, or with status 400 (Bad Request) if the professeur has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/professeurs")
    @Timed
    public ResponseEntity<Professeur> createProfesseur(@Valid @RequestBody Professeur professeur) throws URISyntaxException {
        log.debug("REST request to save Professeur : {}", professeur);
        if (professeur.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new professeur cannot already have an ID")).body(null);
        }

        Civilite civil = professeur.getCivilite();
        Sexe sexe = civil==null? null : civil.equals(Civilite.MR) ? Sexe.G : Sexe.F;
        String nom = professeur.getNom()+ (professeur.getPrenom()!=null? " "+professeur.getPrenom() : "");
        PersonEntity person = new PersonEntity(nom, TypePersonne.STAFF, sexe);
        person.setCivilite(civil);
        person = personRepository.save(person);

        professeur.setPerson(person);

        //@todo  un professeur est un user
        Professeur result = professeurRepository.save(professeur);
        professeurSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/professeurs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId() + ""))
                .body(result);
    }

    /**
     * PUT /professeurs : Updates an existing professeur.
     *
     * @param professeur the professeur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * professeur, or with status 400 (Bad Request) if the professeur is not
     * valid, or with status 500 (Internal Server Error) if the professeur
     * couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/professeurs")
    @Timed
    public ResponseEntity<Professeur> updateProfesseur(@Valid @RequestBody Professeur professeur) throws URISyntaxException {
        log.debug("REST request to update Professeur : {}", professeur);
        if (professeur.getId() == null) {
            return createProfesseur(professeur);
        }
        Professeur result = professeurRepository.save(professeur);
        professeurSearchRepository.save(result);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, professeur.getId() + ""))
                .body(result);
    }

    /**
     * GET /professeurs : get all the professeurs.
     *
     * @param pageable
     * @return the ResponseEntity with status 200 (OK) and the list of
     * professeurs in body
     */
    @GetMapping("/professeurs")
    @Timed
    public ResponseEntity<List<Professeur>> getAllProfesseurs(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Professeurs");
        Page<Professeur> page = professeurRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/professeurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/professeurss")
    @Timed
    public ResponseEntity<List<Professeur>> getAllProfesseurs() {
        log.debug("REST request to get all Professeurs");
        List<Professeur> page = professeurRepository.findAll();
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * GET /professeurs/:id : get the "id" professeur.
     *
     * @param id the id of the professeur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * professeur, or with status 404 (Not Found)
     */
    @GetMapping("/professeurs/{id}")
    @Timed
    public ResponseEntity<Professeur> getProfesseur(@PathVariable Long id) {
        log.debug("REST request to get Professeur : {}", id);
        Professeur professeur = professeurRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(professeur));
    }

    /**
     * DELETE /professeurs/:id : delete the "id" professeur.
     *
     * @param id the id of the professeur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/professeurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteProfesseur(@PathVariable Long id) {
        log.debug("REST request to delete Professeur : {}", id);
        professeurRepository.delete(id);
        professeurSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id + "")).build();
    }

    /**
     * SEARCH /_search/professeurs?query=:query : search for the professeur
     * corresponding to the query.
     *
     * @param query the query of the professeur search
     * @param pageable
     * @return the result of the search
     */
    @GetMapping("/_search/professeurs")
    @Timed
    public ResponseEntity<List<Professeur>> searchProfesseurs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search Professeurs for query {}", query);
        Page<Professeur> page = professeurSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/professeurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /professeurs-print : get all the classeEleves.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     * classeEleves in body
     * @throws java.lang.Exception
     */
    @GetMapping(path = "/professeurs-print", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    @Timed
    public ResponseEntity<byte[]> printAllProfs() throws Exception {
        log.debug("REST request to print all PRofs  {}");
        Comparator<Professeur> byNom = (Professeur p1, Professeur p2) -> p1.getNom().compareTo(p2.getNom());
        List<Professeur> profs = professeurRepository.findAll().stream().sorted(byNom).collect(toList());

        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        String destfile = uploadedfile.getAbsolutePath() + File.separator + "Professeurs"
                + ".pdf";

        FastReportBuilder drb = new FastReportBuilder();
        drb.addFirstPageImageBanner(resourceLoader.getResource("classpath:ischool/reports/logo-ecole.png").getFile().getAbsolutePath(), new Integer(300), new Integer(60), ImageBanner.ALIGN_RIGHT);
        drb.addColumn("Matricule ", "id", Long.class.getName(), 30)
                .addColumn("Civilité", "civilite", Civilite.class, 15)
                .addColumn("Nom", "nom", String.class.getName(), 30)
                .addColumn("Prenom", "prenom", String.class.getName(), 30)
                .addColumn("Tel", "telephone", String.class.getName(), 30)
                .addColumn("Matiere", "matiere.libelle", String.class.getName(), 30)
                .setTitle("Liste Professeurs     Annee :" + annneService.getAnneeCourante().getId())
                //.setSubtitle("This report was generated at " + LocalDate.now())
                .setPrintBackgroundOnOddRows(true)
                .setUseFullPageWidth(true);

        DynamicReport dr = drb.build();
        JRDataSource source = new JRBeanCollectionDataSource(profs);

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
