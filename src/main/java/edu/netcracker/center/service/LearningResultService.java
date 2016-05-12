package edu.netcracker.center.service;

import edu.netcracker.center.domain.LearningResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing LearningResult.
 */
public interface LearningResultService {

    /**
     * Save a learningResult.
     * @return the persisted entity
     */
    public LearningResult save(LearningResult learningResult);

    /**
     *  get all the learningResults.
     *  @return the list of entities
     */
    public Page<LearningResult> findAll(Pageable pageable);
    /**
     *  get all the learningResults where Student is null.
     *  @return the list of entities
     */
    public List<LearningResult> findAllWhereStudentIsNull();

    /**
     *  get the "id" learningResult.
     *  @return the entity
     */
    public LearningResult findOne(Long id);

    /**
     *  delete the "id" learningResult.
     */
    public void delete(Long id);
}
