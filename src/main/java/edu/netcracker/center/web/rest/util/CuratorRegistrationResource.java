package edu.netcracker.center.web.rest.util;

import com.codahale.metrics.annotation.Timed;
import edu.netcracker.center.domain.Curator;
import edu.netcracker.center.domain.Student;
import edu.netcracker.center.domain.util.OperationResult;
import edu.netcracker.center.service.CuratorService;
import edu.netcracker.center.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing registration of Curators.
 */
@RestController
@RequestMapping("/api")
public class CuratorRegistrationResource {

    private final Logger log = LoggerFactory.getLogger(StudentIntegrationResource.class);

    @Inject
    private CuratorService curatorService;

    /**
     * POST  /register/students -> register students in system.
     */
    @RequestMapping(value = "/register/curators/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Curator> register(@RequestBody Curator curator, HttpServletRequest request) {
        log.debug("REST request to register Curator: {}", curator);
        String baseUrl = HeaderUtil.getBaseUrl(request);
        curator = curatorService.register(curator, baseUrl);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("curator", curator.getId().toString()))
            .body(curator);
    }

    @RequestMapping(value = "/register/curators/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Curator> disable(@PathVariable Long id) {
        log.debug("REST request to disable Curator: {}", id);
        Curator curator = curatorService.disable(id);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("curator", curator.getId().toString()))
            .body(curator);
    }
}
