package edu.netcracker.center.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.netcracker.center.domain.Note;
import edu.netcracker.center.repository.NoteRepository;
import edu.netcracker.center.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

/**
 * REST controller for managing Note of main page.
 */
@RestController
@RequestMapping("/api")
public class MainNoteResource {

    private final Logger log = LoggerFactory.getLogger(MainNoteResource.class);

    @Inject
    private NoteRepository noteRepository;

    /**
     * GET  /main/notes -> get all the notes for unauthorized users.
     */
    @RequestMapping(value = "/main/notes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Note>> getMainAllNotes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Notes for main.html");
        Page<Note> page = noteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/notes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
