package edu.netcracker.center.web.rest.util;

import com.codahale.metrics.annotation.Timed;
import edu.netcracker.center.domain.Student;
import edu.netcracker.center.domain.util.OperationResult;
import edu.netcracker.center.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
    public String register(@ModelAttribute("students") StudentList students) {
        log.debug("REST request to register Students: {}", students.size());
//        Collection<OperationResult> results = studentService.registerStudents(students);
        return null;
    }

    public static class Constraints {
        private List<Student> items = new ArrayList<Student>();

        public List<Student> getItems() {
            return items;
        }

        public void setItems(List<Student> items) {
            this.items = items;
        }
    }

    public class StudentList extends ArrayList<Student> { }
}
