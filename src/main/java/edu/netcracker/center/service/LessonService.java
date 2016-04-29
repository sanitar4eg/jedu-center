package edu.netcracker.center.service;

import edu.netcracker.center.domain.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Lesson.
 */
public interface LessonService {

    /**
     * Save a lesson.
     * @return the persisted entity
     */
    public Lesson save(Lesson lesson);

    /**
     *  get all the lessons.
     *  @return the list of entities
     */
    public Page<Lesson> findAll(Pageable pageable);

    /**
     *  get the "id" lesson.
     *  @return the entity
     */
    public Lesson findOne(Long id);

    /**
     *  delete the "id" lesson.
     */
    public void delete(Long id);

    Page<Lesson> findByTimeTableId(Long id, Pageable pageable);
}
