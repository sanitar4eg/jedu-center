package edu.netcracker.center.service;

import edu.netcracker.center.domain.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Note.
 */
public interface NoteService {

    /**
     * Save a note.
     * @return the persisted entity
     */
    public Note save(Note note);

    /**
     *  get all the notes.
     *  @return the list of entities
     */
    public Page<Note> findAll(Pageable pageable);

    /**
     *  get the "id" note.
     *  @return the entity
     */
    public Note findOne(Long id);

    /**
     *  delete the "id" note.
     */
    public void delete(Long id);
}
