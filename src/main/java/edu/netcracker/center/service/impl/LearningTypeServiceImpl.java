package edu.netcracker.center.service.impl;

import edu.netcracker.center.service.LearningTypeService;
import edu.netcracker.center.domain.LearningType;
import edu.netcracker.center.repository.LearningTypeRepository;
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
 * Service Implementation for managing LearningType.
 */
@Service
@Transactional
public class LearningTypeServiceImpl implements LearningTypeService{

    private final Logger log = LoggerFactory.getLogger(LearningTypeServiceImpl.class);
    
    @Inject
    private LearningTypeRepository learningTypeRepository;
    
    /**
     * Save a learningType.
     * @return the persisted entity
     */
    public LearningType save(LearningType learningType) {
        log.debug("Request to save LearningType : {}", learningType);
        LearningType result = learningTypeRepository.save(learningType);
        return result;
    }

    /**
     *  get all the learningTypes.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<LearningType> findAll(Pageable pageable) {
        log.debug("Request to get all LearningTypes");
        Page<LearningType> result = learningTypeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one learningType by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public LearningType findOne(Long id) {
        log.debug("Request to get LearningType : {}", id);
        LearningType learningType = learningTypeRepository.findOne(id);
        return learningType;
    }

    /**
     *  delete the  learningType by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete LearningType : {}", id);
        learningTypeRepository.delete(id);
    }
}
