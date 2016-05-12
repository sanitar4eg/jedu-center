package edu.netcracker.center.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.netcracker.center.domain.ReasonForLeaving;
import edu.netcracker.center.service.ReasonForLeavingService;
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
 * REST controller for managing ReasonForLeaving.
 */
@RestController
@RequestMapping("/api")
public class ReasonForLeavingResource {

    private final Logger log = LoggerFactory.getLogger(ReasonForLeavingResource.class);
        
    @Inject
    private ReasonForLeavingService reasonForLeavingService;
    
    /**
     * POST  /reasonForLeavings -> Create a new reasonForLeaving.
     */
    @RequestMapping(value = "/reasonForLeavings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ReasonForLeaving> createReasonForLeaving(@Valid @RequestBody ReasonForLeaving reasonForLeaving) throws URISyntaxException {
        log.debug("REST request to save ReasonForLeaving : {}", reasonForLeaving);
        if (reasonForLeaving.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("reasonForLeaving", "idexists", "A new reasonForLeaving cannot already have an ID")).body(null);
        }
        ReasonForLeaving result = reasonForLeavingService.save(reasonForLeaving);
        return ResponseEntity.created(new URI("/api/reasonForLeavings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("reasonForLeaving", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reasonForLeavings -> Updates an existing reasonForLeaving.
     */
    @RequestMapping(value = "/reasonForLeavings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ReasonForLeaving> updateReasonForLeaving(@Valid @RequestBody ReasonForLeaving reasonForLeaving) throws URISyntaxException {
        log.debug("REST request to update ReasonForLeaving : {}", reasonForLeaving);
        if (reasonForLeaving.getId() == null) {
            return createReasonForLeaving(reasonForLeaving);
        }
        ReasonForLeaving result = reasonForLeavingService.save(reasonForLeaving);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("reasonForLeaving", reasonForLeaving.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reasonForLeavings -> get all the reasonForLeavings.
     */
    @RequestMapping(value = "/reasonForLeavings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ReasonForLeaving>> getAllReasonForLeavings(Pageable pageable, @RequestParam(required = false) String filter)
        throws URISyntaxException {
        if ("student-is-null".equals(filter)) {
            log.debug("REST request to get all ReasonForLeavings where student is null");
            return new ResponseEntity<>(reasonForLeavingService.findAllWhereStudentIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of ReasonForLeavings");
        Page<ReasonForLeaving> page = reasonForLeavingService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reasonForLeavings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /reasonForLeavings/:id -> get the "id" reasonForLeaving.
     */
    @RequestMapping(value = "/reasonForLeavings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ReasonForLeaving> getReasonForLeaving(@PathVariable Long id) {
        log.debug("REST request to get ReasonForLeaving : {}", id);
        ReasonForLeaving reasonForLeaving = reasonForLeavingService.findOne(id);
        return Optional.ofNullable(reasonForLeaving)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /reasonForLeavings/:id -> delete the "id" reasonForLeaving.
     */
    @RequestMapping(value = "/reasonForLeavings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteReasonForLeaving(@PathVariable Long id) {
        log.debug("REST request to delete ReasonForLeaving : {}", id);
        reasonForLeavingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("reasonForLeaving", id.toString())).build();
    }
}
