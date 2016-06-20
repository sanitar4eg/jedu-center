package edu.netcracker.center.web.rest.util;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Sets;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import edu.netcracker.center.domain.QStudent;
import edu.netcracker.center.domain.Student;
import edu.netcracker.center.domain.StudentsSet;
import edu.netcracker.center.domain.util.OperationResult;
import edu.netcracker.center.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * REST controller for managing integrations of Student.
 */
@RestController
@RequestMapping("/api")
public class StudentIntegrationResource {

    private final Logger log = LoggerFactory.getLogger(StudentIntegrationResource.class);

    @Inject
    private StudentService studentService;

    @Inject
    private HistoryService historyService;

    @Inject
    private ImportService importService;

    @Inject
    private StudentsSetService studentsSetService;

    /**
     * GET  /history/students/:dateTime -> get history of students by "dateTime".
     */
    @RequestMapping(value = "/history/students/{dateTime:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Student> getHistoryOfStudents(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date dateTime) {
        log.debug("REST request to get history of Students by date : {}", dateTime);
        return historyService.getHistoryOfStudents(dateTime);
    }

    /**
     * GET  /export/students -> get XSL file with all students.
     */
    @RequestMapping(value = "/export/students/{id}",
        method = RequestMethod.GET)
    @Timed
    public ModelAndView getXslOfStudents(@PathVariable Long id) {
        log.debug("REST request to get XSL of Students for Set: {}", id);
        Predicate predicate;
        if (id == null) {
            predicate = QStudent.student.isActive.eq(true);
            return new ModelAndView(new StudentXslView(), "students", studentService.findAll(predicate));
        } else {
            StudentsSet set = studentsSetService.findOne(id);
            set.setStudents(Sets.newHashSet(studentService.findAll(QStudent.student.studentsSet.eq(set))));
            return new ModelAndView(new SetXslView(), "set", set);
        }
    }

    /**
     * POST  /import/students -> upload file with students for saving.
     */
    @RequestMapping(value = "/import/students/{id}",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public @ResponseBody Collection<OperationResult> handleImport(@RequestParam("file") MultipartFile file,
                                                                  @PathVariable Long id) {
        log.debug("REST request to upload XSL of Students for set: {}", id);
        return importService.handleImportOfStudents(file, id);
    }

}
