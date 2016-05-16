package edu.netcracker.center.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.netcracker.center.domain.LearningType;
import edu.netcracker.center.service.LearningTypeService;
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
 * REST controller for managing LearningType.
 */
@RestController
@RequestMapping("/api")
public class LearningTypeResource {

    private final Logger log = LoggerFactory.getLogger(LearningTypeResource.class);

    @Inject
    private LearningTypeService learningTypeService;

    /**
     * POST  /learningTypes -> Create a new learningType.
     */
    @RequestMapping(value = "/learningTypes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LearningType> createLearningType(@Valid @RequestBody LearningType learningType) throws URISyntaxException {
        log.debug("REST request to save LearningType : {}", learningType);
        if (learningType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("learningType", "idexists", "A new learningType cannot already have an ID")).body(null);
        }
        LearningType result = learningTypeService.save(learningType);
        return ResponseEntity.created(new URI("/api/learningTypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("learningType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /learningTypes -> Updates an existing learningType.
     */
    @RequestMapping(value = "/learningTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LearningType> updateLearningType(@Valid @RequestBody LearningType learningType) throws URISyntaxException {
        log.debug("REST request to update LearningType : {}", learningType);
        if (learningType.getId() == null) {
            return createLearningType(learningType);
        }
        LearningType result = learningTypeService.save(learningType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("learningType", learningType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /learningTypes -> get all the learningTypes.
     */
    @RequestMapping(value = "/learningTypes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<LearningType>> getAllLearningTypes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of LearningTypes");
        Page<LearningType> page = learningTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/learningTypes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /learningTypes/:id -> get the "id" learningType.
     */
    @RequestMapping(value = "/learningTypes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LearningType> getLearningType(@PathVariable Long id) {
        log.debug("REST request to get LearningType : {}", id);
        LearningType learningType = learningTypeService.findOne(id);
        return Optional.ofNullable(learningType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /learningTypes/:id -> delete the "id" learningType.
     */
    @RequestMapping(value = "/learningTypes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLearningType(@PathVariable Long id) {
        log.debug("REST request to delete LearningType : {}", id);
        learningTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("learningType", id.toString())).build();
    }
}
