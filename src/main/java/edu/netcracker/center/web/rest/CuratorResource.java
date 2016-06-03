package edu.netcracker.center.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mysema.query.types.Predicate;
import edu.netcracker.center.domain.Curator;
import edu.netcracker.center.service.CuratorService;
import edu.netcracker.center.web.rest.util.HeaderUtil;
import edu.netcracker.center.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Curator.
 */
@RestController
@RequestMapping("/api")
public class CuratorResource {

    private final Logger log = LoggerFactory.getLogger(CuratorResource.class);

    @Inject
    private CuratorService curatorService;

    /**
     * POST  /curators -> Create a new curator.
     */
    @RequestMapping(value = "/curators",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Curator> createCurator(@Valid @RequestBody Curator curator,
                                                 HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save Curator : {}", curator);
        if (curator.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("curator", "idexists", "A new curator cannot already have an ID")).body(null);
        }
        String baseUrl = HeaderUtil.getBaseUrl(request);
        Curator result = curatorService.register(curator, baseUrl);
        return ResponseEntity.created(new URI("/api/curators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("curator", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /curators -> Updates an existing curator.
     */
    @RequestMapping(value = "/curators",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Curator> updateCurator(@Valid @RequestBody Curator curator,
                                                 HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to update Curator : {}", curator);
        if (curator.getId() == null) {
            return createCurator(curator, request);
        }
        Curator result = curatorService.save(curator);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("curator", curator.getId().toString()))
            .body(result);
    }

    /**
     * GET  /curators -> get all the curators.
     */
    @RequestMapping(value = "/curators",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Curator>> getAllCurators(Pageable pageable,
                                                        @QuerydslPredicate (root = Curator.class)Predicate predicate)
        throws URISyntaxException {
        log.debug("REST request to get a page of Curators");
        Page<Curator> page = curatorService.findAll(predicate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/curators");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /curators/:id -> get the "id" curator.
     */
    @RequestMapping(value = "/curators/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Curator> getCurator(@PathVariable Long id) {
        log.debug("REST request to get Curator : {}", id);
        Curator curator = curatorService.findOne(id);
        return Optional.ofNullable(curator)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /curators/:id -> delete the "id" curator.
     */
    @RequestMapping(value = "/curators/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCurator(@PathVariable Long id) {
        log.debug("REST request to delete Curator : {}", id);
        curatorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("curator", id.toString())).build();
    }
}
