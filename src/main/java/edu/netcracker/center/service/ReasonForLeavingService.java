package edu.netcracker.center.service;

import edu.netcracker.center.domain.ReasonForLeaving;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing ReasonForLeaving.
 */
public interface ReasonForLeavingService {

    /**
     * Save a reasonForLeaving.
     * @return the persisted entity
     */
    public ReasonForLeaving save(ReasonForLeaving reasonForLeaving);

    /**
     *  get all the reasonForLeavings.
     *  @return the list of entities
     */
    public Page<ReasonForLeaving> findAll(Pageable pageable);

    /**
     *  get the "id" reasonForLeaving.
     *  @return the entity
     */
    public ReasonForLeaving findOne(Long id);

    /**
     *  delete the "id" reasonForLeaving.
     */
    public void delete(Long id);
}
