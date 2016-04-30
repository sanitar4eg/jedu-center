package edu.netcracker.center.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.netcracker.center.domain.Recall;
import edu.netcracker.center.service.RecallService;
import edu.netcracker.center.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Recall.
 */
@RestController
@RequestMapping("/api")
public class RecallResource {

    private final Logger log = LoggerFactory.getLogger(RecallResource.class);
        
    @Inject
    private RecallService recallService;
    
    /**
     * POST  /recalls -> Create a new recall.
     */
    @RequestMapping(value = "/recalls",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Recall> createRecall(@Valid @RequestBody Recall recall) throws URISyntaxException {
        log.debug("REST request to save Recall : {}", recall);
        if (recall.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("recall", "idexists", "A new recall cannot already have an ID")).body(null);
        }
        Recall result = recallService.save(recall);
        return ResponseEntity.created(new URI("/api/recalls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("recall", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recalls -> Updates an existing recall.
     */
    @RequestMapping(value = "/recalls",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Recall> updateRecall(@Valid @RequestBody Recall recall) throws URISyntaxException {
        log.debug("REST request to update Recall : {}", recall);
        if (recall.getId() == null) {
            return createRecall(recall);
        }
        Recall result = recallService.save(recall);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("recall", recall.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recalls -> get all the recalls.
     */
    @RequestMapping(value = "/recalls",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Recall> getAllRecalls() {
        log.debug("REST request to get all Recalls");
        return recallService.findAll();
            }

    /**
     * GET  /recalls/:id -> get the "id" recall.
     */
    @RequestMapping(value = "/recalls/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Recall> getRecall(@PathVariable Long id) {
        log.debug("REST request to get Recall : {}", id);
        Recall recall = recallService.findOne(id);
        return Optional.ofNullable(recall)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /recalls/:id -> delete the "id" recall.
     */
    @RequestMapping(value = "/recalls/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRecall(@PathVariable Long id) {
        log.debug("REST request to delete Recall : {}", id);
        recallService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("recall", id.toString())).build();
    }
}
