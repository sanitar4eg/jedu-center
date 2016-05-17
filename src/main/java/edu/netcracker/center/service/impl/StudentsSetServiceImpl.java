package edu.netcracker.center.service.impl;

import com.mysema.query.types.Predicate;
import edu.netcracker.center.domain.StudentsSet;
import edu.netcracker.center.repository.StudentsSetRepository;
import edu.netcracker.center.service.StudentsSetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service Implementation for managing StudentsSet.
 */
@Service
@Transactional
public class StudentsSetServiceImpl implements StudentsSetService {

    private final Logger log = LoggerFactory.getLogger(StudentsSetServiceImpl.class);

    @Inject
    private StudentsSetRepository studentsSetRepository;

    /**
     * Save a studentsSet.
     *
     * @return the persisted entity
     */
    public StudentsSet save(StudentsSet studentsSet) {
        log.debug("Request to save StudentsSet : {}", studentsSet);
        StudentsSet result = studentsSetRepository.save(studentsSet);
        return result;
    }

    /**
     * get all the studentsSets.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StudentsSet> findAll(Predicate predicate, Pageable pageable) {
        log.debug("Request to get all StudentsSets");
        Page<StudentsSet> result = studentsSetRepository.findAll(predicate, pageable);
        return result;
    }

    /**
     * get one studentsSet by id.
     *
     * @return the entity
     */
    @Transactional(readOnly = true)
    public StudentsSet findOne(Long id) {
        log.debug("Request to get StudentsSet : {}", id);
        StudentsSet studentsSet = studentsSetRepository.findOne(id);
        return studentsSet;
    }

    /**
     * delete the  studentsSet by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete StudentsSet : {}", id);
        studentsSetRepository.delete(id);
    }
}
