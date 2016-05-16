package edu.netcracker.center.service;

import edu.netcracker.center.domain.LearningType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing LearningType.
 */
public interface LearningTypeService {

    /**
     * Save a learningType.
     *
     * @return the persisted entity
     */
    public LearningType save(LearningType learningType);

    /**
     * get all the learningTypes.
     *
     * @return the list of entities
     */
    public Page<LearningType> findAll(Pageable pageable);

    /**
     * get the "id" learningType.
     *
     * @return the entity
     */
    public LearningType findOne(Long id);

    /**
     * delete the "id" learningType.
     */
    public void delete(Long id);
}
