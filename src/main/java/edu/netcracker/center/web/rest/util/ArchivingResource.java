package edu.netcracker.center.web.rest.util;

import com.codahale.metrics.annotation.Timed;
import edu.netcracker.center.domain.Student;
import edu.netcracker.center.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URISyntaxException;

/**
 * REST controller for archiving Entities.
 */
@RestController
@RequestMapping("/api")
public class ArchivingResource {

    private final Logger log = LoggerFactory.getLogger(ArchivingResource.class);

    @Inject
    private StudentService studentService;

    /**
     * PUT  /students/archive -> Updates an existing student.
     */
    @RequestMapping(value = "/students/archive",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Student> archiveStudent(@Valid @RequestBody Student student) throws URISyntaxException {
        log.debug("REST request to archive Student : {}, result: {}", student, student.getLearningResult());
        Student result = studentService.archive(student);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("student", result.getId().toString()))
            .body(result);
    }

    /**
     * DELETE  /students/archive -> Updates an existing student.
     */
    @RequestMapping(value = "/students/archive",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Student> unzipStudent(Long id) throws URISyntaxException {
        log.debug("REST request to unzip Student ID: {}", id);
        Student result = studentService.unzip(id);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("student", result.getId().toString()))
            .body(result);
    }
}
