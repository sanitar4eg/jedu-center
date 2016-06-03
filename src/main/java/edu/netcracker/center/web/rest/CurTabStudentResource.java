package edu.netcracker.center.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Predicate;
import edu.netcracker.center.domain.Curator;
import edu.netcracker.center.domain.QCurator;
import edu.netcracker.center.domain.QStudent;
import edu.netcracker.center.domain.Student;
import edu.netcracker.center.service.CuratorService;
import edu.netcracker.center.service.StudentService;
import edu.netcracker.center.service.UserService;
import edu.netcracker.center.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.QueryDslUtils;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cur-tab")
public class CurTabStudentResource {

    private final Logger log = LoggerFactory.getLogger(CurTabStudentResource.class);

    private final StudentService studentService;
    private final UserService userService;
    private final CuratorService curatorService;

    @Inject
    public CurTabStudentResource(StudentService studentService, UserService userService,
                                 CuratorService curatorService) {
        this.studentService = studentService;
        this.userService = userService;
        this.curatorService = curatorService;
    }

    /**
     * GET  /students -> get all the students.
     */
    @RequestMapping(value = "/students",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Student>> getAllStudents(@QuerydslPredicate(root = Student.class) Predicate predicate,
                                                        Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Students for curator");
        Curator curator = curatorService.findByUser(userService.getUserWithAuthorities());
        log.debug("Curator present: {}", curator);
        if (curator == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Page<Student> page = studentService.findByCurator(curator, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cur-tab/students");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
