package edu.netcracker.center.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.netcracker.center.domain.TimeTable;
import edu.netcracker.center.repository.TimeTableRepository;
import edu.netcracker.center.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing TimeTable.
 */
@RestController
@RequestMapping("/api")
public class TimeTableResource {

    private final Logger log = LoggerFactory.getLogger(TimeTableResource.class);
        
    @Inject
    private TimeTableRepository timeTableRepository;
    
    /**
     * POST  /timeTables -> Create a new timeTable.
     */
    @RequestMapping(value = "/timeTables",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeTable> createTimeTable(@Valid @RequestBody TimeTable timeTable) throws URISyntaxException {
        log.debug("REST request to save TimeTable : {}", timeTable);
        if (timeTable.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("timeTable", "idexists", "A new timeTable cannot already have an ID")).body(null);
        }
        TimeTable result = timeTableRepository.save(timeTable);
        return ResponseEntity.created(new URI("/api/timeTables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("timeTable", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /timeTables -> Updates an existing timeTable.
     */
    @RequestMapping(value = "/timeTables",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeTable> updateTimeTable(@Valid @RequestBody TimeTable timeTable) throws URISyntaxException {
        log.debug("REST request to update TimeTable : {}", timeTable);
        if (timeTable.getId() == null) {
            return createTimeTable(timeTable);
        }
        TimeTable result = timeTableRepository.save(timeTable);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("timeTable", timeTable.getId().toString()))
            .body(result);
    }

    /**
     * GET  /timeTables -> get all the timeTables.
     */
    @RequestMapping(value = "/timeTables",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TimeTable> getAllTimeTables(@RequestParam(required = false) String filter) {
        if ("groupofstudent-is-null".equals(filter)) {
            log.debug("REST request to get all TimeTables where groupOfStudent is null");
            return StreamSupport
                .stream(timeTableRepository.findAll().spliterator(), false)
                .filter(timeTable -> timeTable.getGroupOfStudent() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all TimeTables");
        return timeTableRepository.findAll();
            }

    /**
     * GET  /timeTables/:id -> get the "id" timeTable.
     */
    @RequestMapping(value = "/timeTables/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeTable> getTimeTable(@PathVariable Long id) {
        log.debug("REST request to get TimeTable : {}", id);
        TimeTable timeTable = timeTableRepository.findOne(id);
        return Optional.ofNullable(timeTable)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /timeTables/:id -> delete the "id" timeTable.
     */
    @RequestMapping(value = "/timeTables/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTimeTable(@PathVariable Long id) {
        log.debug("REST request to delete TimeTable : {}", id);
        timeTableRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("timeTable", id.toString())).build();
    }
}
