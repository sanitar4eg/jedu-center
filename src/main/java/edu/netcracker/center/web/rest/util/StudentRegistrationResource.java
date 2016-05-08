package edu.netcracker.center.web.rest.util;

import com.codahale.metrics.annotation.Timed;
import edu.netcracker.center.domain.Student;
import edu.netcracker.center.domain.util.OperationResult;
import edu.netcracker.center.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing integrations of Student.
 */
@RestController
@RequestMapping("/api")
public class StudentRegistrationResource {

    private final Logger log = LoggerFactory.getLogger(StudentIntegrationResource.class);

    @Inject
    private StudentService studentService;

    /**
     * POST  /register/students -> register students in system.
     */
    @RequestMapping(value = "/register/students",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Collection<OperationResult> register(@RequestBody List<Student> students, HttpServletRequest request) {
        log.debug("REST request to register Students: {}", students);
        String baseUrl = getBaseUrl(request);
        if (Optional.ofNullable(students).isPresent() && !students.isEmpty()) {
            return studentService.registerStudents(students, baseUrl);
        }
        return Collections.singletonList(new OperationResult("-1", "Студенты не выбраны", ""));
    }

    private String getBaseUrl(HttpServletRequest request) {
        return
            request.getScheme() +                   // "http"
            "://" +                                 // "://"
            request.getServerName() +               // "myhost"
            ":" +                                   // ":"
            request.getServerPort() +               // "80"
            request.getContextPath();               // "/myContextPath" or "" if deployed in root context
    }
}
