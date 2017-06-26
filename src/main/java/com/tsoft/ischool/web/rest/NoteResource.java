package com.tsoft.ischool.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.ischool.config.ApplicationProperties;
import com.tsoft.ischool.config.ApplicationProperties.Ecole;
import com.tsoft.ischool.domain.Note;

import com.tsoft.ischool.repository.NoteRepository;
import com.tsoft.ischool.service.AnneeService;
import com.tsoft.ischool.service.NoteService;
import com.tsoft.ischool.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import java.io.File;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * REST controller for managing Note.
 */
@RestController
@RequestMapping("/api")
public class NoteResource {

    private final Logger log = LoggerFactory.getLogger(NoteResource.class);

    private static final String ENTITY_NAME = "note";

    private final NoteRepository noteRepository;

    private final NoteService noteService;
    
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    DataSource dataSource;
    @Autowired
    AnneeService as;
    @Autowired
    ApplicationProperties app;

    public NoteResource(NoteRepository noteRepository, NoteService noteService) {
        this.noteRepository = noteRepository;
        this.noteService = noteService;
    }

    /**
     * POST /notes : Create a new note.
     *
     * @param notes
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new note, or with status 400 (Bad Request) if the note has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/notes")
    @Timed
    public ResponseEntity<List<Note>> createNote(@RequestBody List<Note> notes) throws Exception {
        log.debug("REST request to save Note : {}");

        List<Note> result = noteService.save(notes);
        return new ResponseEntity<>(result,HeaderUtil.createSuccesMessage(), HttpStatus.OK);
    }

    /**
     * GET /notes : get all the notes.
     *
     * @param matiere
     * @param numseq
     * @param classe
     * @param pageable
     * @return the ResponseEntity with status 200 (OK) and the list of notes in
     * body
     * @throws java.lang.Exception
     */
    @GetMapping("/notes")
    @Timed
    public ResponseEntity<List<Note>> getAllNotes(@ApiParam Long matiere, @ApiParam String classe, @ApiParam Integer numseq, @ApiParam Pageable pageable) throws Exception {
        log.debug("REST request to get all Notes");
        List<Note> page = noteService.retrieveDatas(matiere, numseq, classe);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * GET /notes/:id : get the "id" note.
     *
     * @param id the id of the note to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the note,
     * or with status 404 (Not Found)
     */
    @GetMapping("/notes/{id}")
    @Timed
    public ResponseEntity<Note> getNote(@PathVariable Long id) {
        log.debug("REST request to get Note : {}", id);
        Note note = noteRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(note));
    }

    /**
     * DELETE /notes/:id : delete the "id" note.
     *
     * @param id the id of the note to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/notes/{id}")
    @Timed
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        log.debug("REST request to delete Note : {}", id);
        noteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    @GetMapping(value = "/printReleve/{classe}",
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<byte[]> printReleve(@PathVariable String classe) throws Exception {
        String reportfile = "classpath:ischool/reports/ReleveNote.jasper";
        //remplissage des parametres du report
        Map params = new HashMap();
        params.put("code_annee", as.getAnneeCourante().getId());
        params.put("code_classe", classe);

        //information about school
        Ecole ecole = app.getEcole();
        params.put("nom_ecole", ecole.getNom());
//        params.put("logo_ecole", FileUtils.getUploadedFile(ecole.getLogo()).getAbsolutePath());

        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        String destfile = uploadedfile.getAbsolutePath() + File.separator + "ReleveNote" + classe
                + ".pdf";
        //fill report
        JasperPrint jp = JasperFillManager.fillReport(
                resourceLoader.getResource(reportfile).getInputStream() //file jasper
                ,
                 params, //params report
                dataSource.getConnection());  //datasource

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
