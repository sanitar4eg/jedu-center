package edu.netcracker.center.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.netcracker.center.domain.Lesson;
import edu.netcracker.center.repository.LessonRepository;
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
 * REST controller for managing Lesson.
 */
@RestController
@RequestMapping("/api")
public class LessonResource {

    private final Logger log = LoggerFactory.getLogger(LessonResource.class);

    @Inject
    private LessonRepository lessonRepository;

    /**
     * POST  /lessons -> Create a new lesson.
     */
    @RequestMapping(value = "/lessons",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lesson> createLesson(@Valid @RequestBody Lesson lesson) throws URISyntaxException {
        log.debug("REST request to save Lesson : {}", lesson);
        if (lesson.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lesson", "idexists", "A new lesson cannot already have an ID")).body(null);
        }
        Lesson result = lessonRepository.save(lesson);
        return ResponseEntity.created(new URI("/api/lessons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lesson", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lessons -> Updates an existing lesson.
     */
    @RequestMapping(value = "/lessons",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lesson> updateLesson(@Valid @RequestBody Lesson lesson) throws URISyntaxException {
        log.debug("REST request to update Lesson : {}", lesson);
        if (lesson.getId() == null) {
            return createLesson(lesson);
        }
        Lesson result = lessonRepository.save(lesson);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lesson", lesson.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lessons -> get all the lessons.
     */
    @RequestMapping(value = "/lessons",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Lesson>> getAllLessons(Pageable pageable,
                                                      @RequestParam(required = false) Optional<Long> timeTable)
        throws URISyntaxException {
        if (timeTable.isPresent()) {
            log.debug("REST request to get a page of Lessons for timeTale: {}", timeTable);
            Page<Lesson> page = lessonRepository.findByTimeTableId(timeTable.get(), pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lessons");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        } else {
            log.debug("TimeTable is not present");
        }
        log.debug("REST request to get a page of Lessons");
        Page<Lesson> page = lessonRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lessons");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /lessons/:id -> get the "id" lesson.
     */
    @RequestMapping(value = "/lessons/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lesson> getLesson(@PathVariable Long id) {
        log.debug("REST request to get Lesson : {}", id);
        Lesson lesson = lessonRepository.findOne(id);
        return Optional.ofNullable(lesson)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lessons/:id -> delete the "id" lesson.
     */
    @RequestMapping(value = "/lessons/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        log.debug("REST request to delete Lesson : {}", id);
        lessonRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lesson", id.toString())).build();
    }
}
