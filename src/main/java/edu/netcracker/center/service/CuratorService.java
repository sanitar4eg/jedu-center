package edu.netcracker.center.service;

import edu.netcracker.center.domain.Curator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Curator.
 */
public interface CuratorService {

    /**
     * Save a curator.
     * @return the persisted entity
     */
    public Curator save(Curator curator);

    /**
     *  get all the curators.
     *  @return the list of entities
     */
    public Page<Curator> findAll(Pageable pageable);

    /**
     *  get the "id" curator.
     *  @return the entity
     */
    public Curator findOne(Long id);

    /**
     *  delete the "id" curator.
     */
    public void delete(Long id);
}
