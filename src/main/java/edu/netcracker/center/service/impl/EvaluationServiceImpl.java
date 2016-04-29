package edu.netcracker.center.service.impl;

import edu.netcracker.center.service.EvaluationService;
import edu.netcracker.center.domain.Evaluation;
import edu.netcracker.center.repository.EvaluationRepository;
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
 * Service Implementation for managing Evaluation.
 */
@Service
@Transactional
public class EvaluationServiceImpl implements EvaluationService{

    private final Logger log = LoggerFactory.getLogger(EvaluationServiceImpl.class);
    
    @Inject
    private EvaluationRepository evaluationRepository;
    
    /**
     * Save a evaluation.
     * @return the persisted entity
     */
    public Evaluation save(Evaluation evaluation) {
        log.debug("Request to save Evaluation : {}", evaluation);
        Evaluation result = evaluationRepository.save(evaluation);
        return result;
    }

    /**
     *  get all the evaluations.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Evaluation> findAll(Pageable pageable) {
        log.debug("Request to get all Evaluations");
        Page<Evaluation> result = evaluationRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one evaluation by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Evaluation findOne(Long id) {
        log.debug("Request to get Evaluation : {}", id);
        Evaluation evaluation = evaluationRepository.findOne(id);
        return evaluation;
    }

    /**
     *  delete the  evaluation by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Evaluation : {}", id);
        evaluationRepository.delete(id);
    }
}
