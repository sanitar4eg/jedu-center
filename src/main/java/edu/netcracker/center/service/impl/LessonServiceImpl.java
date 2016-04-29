package edu.netcracker.center.service.impl;

import edu.netcracker.center.service.LessonService;
import edu.netcracker.center.domain.Lesson;
import edu.netcracker.center.repository.LessonRepository;
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
 * Service Implementation for managing Lesson.
 */
@Service
@Transactional
public class LessonServiceImpl implements LessonService{

    private final Logger log = LoggerFactory.getLogger(LessonServiceImpl.class);

    @Inject
    private LessonRepository lessonRepository;

    /**
     * Save a lesson.
     * @return the persisted entity
     */
    public Lesson save(Lesson lesson) {
        log.debug("Request to save Lesson : {}", lesson);
        Lesson result = lessonRepository.save(lesson);
        return result;
    }

    /**
     *  get all the lessons.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Lesson> findAll(Pageable pageable) {
        log.debug("Request to get all Lessons");
        Page<Lesson> result = lessonRepository.findAll(pageable);
        return result;
    }

    /**
     *  get one lesson by id.
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Lesson findOne(Long id) {
        log.debug("Request to get Lesson : {}", id);
        Lesson lesson = lessonRepository.findOne(id);
        return lesson;
    }

    /**
     *  delete the  lesson by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Lesson : {}", id);
        lessonRepository.delete(id);
    }

    @Override
    public Page<Lesson> findByTimeTableId(Long id, Pageable pageable) {
        log.debug("Request to get Lessons by TimeTable id: {}", id);
        return lessonRepository.findByTimeTableId(id, pageable);
    }
}
