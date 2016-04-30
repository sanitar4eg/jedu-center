package edu.netcracker.center.service;

import edu.netcracker.center.domain.Recall;

import java.util.List;

/**
 * Service Interface for managing Recall.
 */
public interface RecallService {

    /**
     * Save a recall.
     * @return the persisted entity
     */
    public Recall save(Recall recall);

    /**
     *  get all the recalls.
     *  @return the list of entities
     */
    public List<Recall> findAll();

    /**
     *  get the "id" recall.
     *  @return the entity
     */
    public Recall findOne(Long id);

    /**
     *  delete the "id" recall.
     */
    public void delete(Long id);
}
