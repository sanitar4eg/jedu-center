package edu.netcracker.center.service;

import com.mysema.query.types.Predicate;
import edu.netcracker.center.domain.StudentsSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing StudentsSet.
 */
public interface StudentsSetService {

    /**
     * Save a studentsSet.
     * @return the persisted entity
     */
    public StudentsSet save(StudentsSet studentsSet);

    /**
     *  get all the studentsSets.
     *  @return the list of entities
     */
    public Page<StudentsSet> findAll(Predicate predicate, Pageable pageable);

    /**
     *  get the "id" studentsSet.
     *  @return the entity
     */
    public StudentsSet findOne(Long id);

    /**
     *  delete the "id" studentsSet.
     */
    public void delete(Long id);
}
