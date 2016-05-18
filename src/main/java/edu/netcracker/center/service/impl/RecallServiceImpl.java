package edu.netcracker.center.service.impl;

import com.mysema.query.types.Predicate;
import edu.netcracker.center.service.RecallService;
import edu.netcracker.center.domain.Recall;
import edu.netcracker.center.repository.RecallRepository;
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
 * Service Implementation for managing Recall.
 */
@Service
@Transactional
public class RecallServiceImpl implements RecallService{

    private final Logger log = LoggerFactory.getLogger(RecallServiceImpl.class);

    @Inject
    private RecallRepository recallRepository;

    /**
     * Save a recall.
     * @return the persisted entity
     */
    public Recall save(Recall recall) {
        log.debug("Request to save Recall : {}", recall);
        if (recall.getName() == null) {
            recall.setName(recall.getFile());
        }
        return recallRepository.save(recall);
    }

    /**
     *  get all the recalls.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Recall> findAll(Pageable pageable) {
        log.debug("Request to get all Recalls");
        Page<Recall> result = recallRepository.findAll(pageable);
        return result;
    }

    /**
     *  get all the recalls.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Recall> findAll(Predicate predicate, Pageable pageable) {
        log.debug("Request to get all Recalls by predicate");
        return recallRepository.findAll(predicate, pageable);
    }

    /**
     *  get one recall by id.
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Recall findOne(Long id) {
        log.debug("Request to get Recall : {}", id);
        Recall recall = recallRepository.findOne(id);
        return recall;
    }

    /**
     *  delete the  recall by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Recall : {}", id);
        recallRepository.delete(id);
    }
}
