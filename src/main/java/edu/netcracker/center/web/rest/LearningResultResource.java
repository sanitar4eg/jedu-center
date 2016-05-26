package edu.netcracker.center.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.netcracker.center.domain.LearningResult;
import edu.netcracker.center.service.LearningResultService;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing LearningResult.
 */
@RestController
@RequestMapping("/api")
public class LearningResultResource {

    private final Logger log = LoggerFactory.getLogger(LearningResultResource.class);
        
    @Inject
    private LearningResultService learningResultService;
    
    /**
     * POST  /learningResults -> Create a new learningResult.
     */
    @RequestMapping(value = "/learningResults",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LearningResult> createLearningResult(@Valid @RequestBody LearningResult learningResult) throws URISyntaxException {
        log.debug("REST request to save LearningResult : {}", learningResult);
        if (learningResult.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("learningResult", "idexists", "A new learningResult cannot already have an ID")).body(null);
        }
        LearningResult result = learningResultService.save(learningResult);
        return ResponseEntity.created(new URI("/api/learningResults/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("learningResult", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /learningResults -> Updates an existing learningResult.
     */
    @RequestMapping(value = "/learningResults",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LearningResult> updateLearningResult(@Valid @RequestBody LearningResult learningResult) throws URISyntaxException {
        log.debug("REST request to update LearningResult : {}", learningResult);
        if (learningResult.getId() == null) {
            return createLearningResult(learningResult);
        }
        LearningResult result = learningResultService.save(learningResult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("learningResult", learningResult.getId().toString()))
            .body(result);
    }

    /**
     * GET  /learningResults -> get all the learningResults.
     */
    @RequestMapping(value = "/learningResults",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<LearningResult>> getAllLearningResults(Pageable pageable, @RequestParam(required = false) String filter)
        throws URISyntaxException {
        if ("student-is-null".equals(filter)) {
            log.debug("REST request to get all LearningResults where student is null");
            return new ResponseEntity<>(learningResultService.findAllWhereStudentIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of LearningResults");
        Page<LearningResult> page = learningResultService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/learningResults");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /learningResults/:id -> get the "id" learningResult.
     */
    @RequestMapping(value = "/learningResults/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LearningResult> getLearningResult(@PathVariable Long id) {
        log.debug("REST request to get LearningResult : {}", id);
        LearningResult learningResult = learningResultService.findOne(id);
        return Optional.ofNullable(learningResult)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /learningResults/:id -> delete the "id" learningResult.
     */
    @RequestMapping(value = "/learningResults/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLearningResult(@PathVariable Long id) {
        log.debug("REST request to delete LearningResult : {}", id);
        learningResultService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("learningResult", id.toString())).build();
    }
}
