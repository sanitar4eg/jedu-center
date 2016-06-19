package edu.netcracker.center.web.rest.util;


import com.codahale.metrics.annotation.Timed;
import edu.netcracker.center.domain.Curator;
import edu.netcracker.center.domain.Student;
import edu.netcracker.center.domain.User;
import edu.netcracker.center.security.AuthoritiesConstants;
import edu.netcracker.center.security.SecurityUtils;
import edu.netcracker.center.service.CuratorService;
import edu.netcracker.center.service.StudentService;
import edu.netcracker.center.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class CurrentEntityIdResource {

    private final Logger log = LoggerFactory.getLogger(CurrentEntityIdResource.class);

    private final UserService userService;
    private final CuratorService curatorService;
    private final StudentService studentService;

    @Inject
    CurrentEntityIdResource(UserService userService, CuratorService curatorService, StudentService studentService) {
        this.userService = userService;
        this.curatorService = curatorService;
        this.studentService = studentService;
    }


    /**
     * GET  /current -> get current entity.
     */
    @RequestMapping(value = "/current",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Object> getCurrentId() throws URISyntaxException {
        log.debug("REST request to get current entity");
        User user = userService.getUserWithAuthorities();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.CURATOR)) {
            Curator curator = curatorService.findByUser(user);
            log.debug("Curator present: {}", curator);
            return new ResponseEntity<>(curator, HttpStatus.OK);
        }
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.STUDENT)) {
            Student student = studentService.findByUser(user);
            log.debug("Student present: {}", student);
            return new ResponseEntity<>(student, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

