package edu.netcracker.center.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mysema.query.types.Predicate;
import edu.netcracker.center.domain.GroupOfStudent;
import edu.netcracker.center.service.GroupOfStudentService;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GroupOfStudent.
 */
@RestController
@RequestMapping("/api")
public class GroupOfStudentResource {

    private final Logger log = LoggerFactory.getLogger(GroupOfStudentResource.class);

    @Inject
    private GroupOfStudentService groupOfStudentService;

    /**
     * POST  /groupOfStudents -> Create a new groupOfStudent.
     */
    @RequestMapping(value = "/groupOfStudents",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GroupOfStudent> createGroupOfStudent(@Valid @RequestBody GroupOfStudent groupOfStudent) throws URISyntaxException {
        log.debug("REST request to save GroupOfStudent : {}", groupOfStudent);
        if (groupOfStudent.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("groupOfStudent", "idexists", "A new groupOfStudent cannot already have an ID")).body(null);
        }
        GroupOfStudent result = groupOfStudentService.save(groupOfStudent);
        return ResponseEntity.created(new URI("/api/groupOfStudents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("groupOfStudent", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /groupOfStudents -> Updates an existing groupOfStudent.
     */
    @RequestMapping(value = "/groupOfStudents",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GroupOfStudent> updateGroupOfStudent(@Valid @RequestBody GroupOfStudent groupOfStudent) throws URISyntaxException {
        log.debug("REST request to update GroupOfStudent : {}", groupOfStudent);
        if (groupOfStudent.getId() == null) {
            return createGroupOfStudent(groupOfStudent);
        }
        GroupOfStudent result = groupOfStudentService.save(groupOfStudent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("groupOfStudent", groupOfStudent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /groupOfStudents -> get all the groupOfStudents.
     */
    @RequestMapping(value = "/groupOfStudents",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<GroupOfStudent>> getAllGroupOfStudents(@QuerydslPredicate(root = GroupOfStudent.class)
                                                                              Predicate predicate,
                                                                      Pageable pageable
                                                                      )
        throws URISyntaxException {
        log.debug("REST request to get a page of GroupOfStudents");
        Page<GroupOfStudent> page = groupOfStudentService.findAll(predicate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/groupOfStudents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /groupOfStudents/:id -> get the "id" groupOfStudent.
     */
    @RequestMapping(value = "/groupOfStudents/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GroupOfStudent> getGroupOfStudent(@PathVariable Long id) {
        log.debug("REST request to get GroupOfStudent : {}", id);
        GroupOfStudent groupOfStudent = groupOfStudentService.findOne(id);
        return Optional.ofNullable(groupOfStudent)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /groupOfStudents/:id -> delete the "id" groupOfStudent.
     */
    @RequestMapping(value = "/groupOfStudents/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGroupOfStudent(@PathVariable Long id) {
        log.debug("REST request to delete GroupOfStudent : {}", id);
        groupOfStudentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("groupOfStudent", id.toString())).build();
    }
}
