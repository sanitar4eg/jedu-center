package edu.netcracker.center.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.netcracker.center.domain.StudentsSet;
import edu.netcracker.center.service.StudentsSetService;
import edu.netcracker.center.web.rest.util.HeaderUtil;
import edu.netcracker.center.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StudentsSet.
 */
@RestController
@RequestMapping("/api")
public class StudentsSetResource {

    private final Logger log = LoggerFactory.getLogger(StudentsSetResource.class);
        
    @Inject
    private StudentsSetService studentsSetService;
    
    /**
     * POST  /studentsSets -> Create a new studentsSet.
     */
    @RequestMapping(value = "/studentsSets",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StudentsSet> createStudentsSet(@Valid @RequestBody StudentsSet studentsSet) throws URISyntaxException {
        log.debug("REST request to save StudentsSet : {}", studentsSet);
        if (studentsSet.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("studentsSet", "idexists", "A new studentsSet cannot already have an ID")).body(null);
        }
        StudentsSet result = studentsSetService.save(studentsSet);
        return ResponseEntity.created(new URI("/api/studentsSets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("studentsSet", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /studentsSets -> Updates an existing studentsSet.
     */
    @RequestMapping(value = "/studentsSets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StudentsSet> updateStudentsSet(@Valid @RequestBody StudentsSet studentsSet) throws URISyntaxException {
        log.debug("REST request to update StudentsSet : {}", studentsSet);
        if (studentsSet.getId() == null) {
            return createStudentsSet(studentsSet);
        }
        StudentsSet result = studentsSetService.save(studentsSet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("studentsSet", studentsSet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /studentsSets -> get all the studentsSets.
     */
    @RequestMapping(value = "/studentsSets",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<StudentsSet>> getAllStudentsSets(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of StudentsSets");
        Page<StudentsSet> page = studentsSetService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/studentsSets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /studentsSets/:id -> get the "id" studentsSet.
     */
    @RequestMapping(value = "/studentsSets/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StudentsSet> getStudentsSet(@PathVariable Long id) {
        log.debug("REST request to get StudentsSet : {}", id);
        StudentsSet studentsSet = studentsSetService.findOne(id);
        return Optional.ofNullable(studentsSet)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /studentsSets/:id -> delete the "id" studentsSet.
     */
    @RequestMapping(value = "/studentsSets/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStudentsSet(@PathVariable Long id) {
        log.debug("REST request to delete StudentsSet : {}", id);
        studentsSetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("studentsSet", id.toString())).build();
    }
}
