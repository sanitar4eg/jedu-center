package edu.netcracker.center.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.netcracker.center.domain.Evaluation;
import edu.netcracker.center.service.EvaluationService;
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
 * REST controller for managing Evaluation.
 */
@RestController
@RequestMapping("/api")
public class EvaluationResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationResource.class);

    @Inject
    private EvaluationService evaluationService;

    /**
     * POST  /evaluations -> Create a new evaluation.
     */
    @RequestMapping(value = "/evaluations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Evaluation> createEvaluation(@Valid @RequestBody Evaluation evaluation) throws URISyntaxException {
        log.debug("REST request to save Evaluation : {}", evaluation);
        if (evaluation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("evaluation", "idexists", "A new evaluation cannot already have an ID")).body(null);
        }
        Evaluation result = evaluationService.save(evaluation);
        return ResponseEntity.created(new URI("/api/evaluations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("evaluation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /evaluations -> Updates an existing evaluation.
     */
    @RequestMapping(value = "/evaluations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Evaluation> updateEvaluation(@Valid @RequestBody Evaluation evaluation) throws URISyntaxException {
        log.debug("REST request to update Evaluation : {}", evaluation);
        if (evaluation.getId() == null) {
            return createEvaluation(evaluation);
        }
        Evaluation result = evaluationService.save(evaluation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("evaluation", evaluation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /evaluations -> get all the evaluations.
     */
    @RequestMapping(value = "/evaluations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Evaluation>> getAllEvaluations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Evaluations");
        Page<Evaluation> page = evaluationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/evaluations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /evaluations/:id -> get the "id" evaluation.
     */
    @RequestMapping(value = "/evaluations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Evaluation> getEvaluation(@PathVariable Long id) {
        log.debug("REST request to get Evaluation : {}", id);
        Evaluation evaluation = evaluationService.findOne(id);
        return Optional.ofNullable(evaluation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /evaluations/:id -> delete the "id" evaluation.
     */
    @RequestMapping(value = "/evaluations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long id) {
        log.debug("REST request to delete Evaluation : {}", id);
        evaluationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("evaluation", id.toString())).build();
    }
}
