package edu.netcracker.center.service.impl;

import edu.netcracker.center.service.NoteService;
import edu.netcracker.center.domain.Note;
import edu.netcracker.center.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Note.
 */
@Service
@Transactional
public class NoteServiceImpl implements NoteService{

    private final Logger log = LoggerFactory.getLogger(NoteServiceImpl.class);
    
    @Inject
    private NoteRepository noteRepository;
    
    /**
     * Save a note.
     * @return the persisted entity
     */
    public Note save(Note note) {
        log.debug("Request to save Note : {}", note);
        Note result = noteRepository.save(note);
        return result;
    }

    /**
     *  get all the notes.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Note> findAll(Pageable pageable) {
        log.debug("Request to get all Notes");
        Page<Note> result = noteRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one note by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Note findOne(Long id) {
        log.debug("Request to get Note : {}", id);
        Note note = noteRepository.findOne(id);
        return note;
    }

    /**
     *  delete the  note by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Note : {}", id);
        noteRepository.delete(id);
    }
}
