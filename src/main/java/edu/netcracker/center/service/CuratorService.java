package edu.netcracker.center.service;

import com.mysema.query.types.Predicate;
import edu.netcracker.center.domain.Curator;
import edu.netcracker.center.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    public Page<Curator> findAll(Predicate predicate, Pageable pageable);

    /**
     *  get the "id" curator.
     *  @return the entity
     */
    public Curator findOne(Long id);
    /**
     *  delete the "id" curator.
     */
    public void delete(Long id);

    /**
     *  get the curator by User.
     */
    Curator findByUser(User user);
}
