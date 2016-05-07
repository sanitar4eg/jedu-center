package edu.netcracker.center.service.impl;

import edu.netcracker.center.service.CuratorService;
import edu.netcracker.center.domain.Curator;
import edu.netcracker.center.repository.CuratorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Curator.
 */
@Service
@Transactional
public class CuratorServiceImpl implements CuratorService{

    private final Logger log = LoggerFactory.getLogger(CuratorServiceImpl.class);

    @Inject
    private CuratorRepository curatorRepository;

    /**
     * Save a curator.
     * @return the persisted entity
     */
    public Curator save(Curator curator) {
        log.debug("Request to save Curator : {}", curator);
        Curator result = curatorRepository.save(curator);
        return result;
    }

    /**
     *  get all the curators.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Curator> findAll(Pageable pageable) {
        log.debug("Request to get all Curators");
        Page<Curator> result = curatorRepository.findAll(pageable);
        return result;
    }

    /**
     *  get one curator by id.
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Curator findOne(Long id) {
        log.debug("Request to get Curator : {}", id);
        Curator curator = curatorRepository.findOne(id);
        return curator;
    }

    /**
     *  delete the  curator by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Curator : {}", id);
        Curator curator = curatorRepository.findOne(id);
        curator.getRecalls().forEach(recall -> recall.setCurator(null));
        curator.getRecalls().clear();
        curatorRepository.delete(id);
    }
}
