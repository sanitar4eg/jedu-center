package edu.netcracker.center.service;

import edu.netcracker.center.domain.Evaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Evaluation.
 */
public interface EvaluationService {

    /**
     * Save a evaluation.
     * @return the persisted entity
     */
    public Evaluation save(Evaluation evaluation);

    /**
     *  get all the evaluations.
     *  @return the list of entities
     */
    public Page<Evaluation> findAll(Pageable pageable);

    /**
     *  get the "id" evaluation.
     *  @return the entity
     */
    public Evaluation findOne(Long id);

    /**
     *  delete the "id" evaluation.
     */
    public void delete(Long id);
}
