package edu.netcracker.center.service;

import edu.netcracker.center.domain.StudentsSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
    public Page<StudentsSet> findAll(Pageable pageable);

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
