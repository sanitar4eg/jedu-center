package edu.netcracker.center.service;

import com.mysema.query.types.Predicate;
import edu.netcracker.center.domain.Recall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    public Page<Recall> findAll(Pageable pageable);

    /**
     *  get all the recalls.
     *  @return the list of entities
     */
    public Page<Recall> findAll(Predicate predicate,Pageable pageable);

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
