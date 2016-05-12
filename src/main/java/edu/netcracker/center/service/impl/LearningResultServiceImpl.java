package edu.netcracker.center.service.impl;

import edu.netcracker.center.service.LearningResultService;
import edu.netcracker.center.domain.LearningResult;
import edu.netcracker.center.repository.LearningResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing LearningResult.
 */
@Service
@Transactional
public class LearningResultServiceImpl implements LearningResultService{

    private final Logger log = LoggerFactory.getLogger(LearningResultServiceImpl.class);

    @Inject
    private LearningResultRepository learningResultRepository;

    /**
     * Save a learningResult.
     * @return the persisted entity
     */
    public LearningResult save(LearningResult learningResult) {
        log.debug("Request to save LearningResult : {}", learningResult);
        LearningResult result = learningResultRepository.save(learningResult);
        return result;
    }

    /**
     *  get all the learningResults.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LearningResult> findAll(Pageable pageable) {
        log.debug("Request to get all LearningResults");
        Page<LearningResult> result = learningResultRepository.findAll(pageable);
        return result;
    }


    /**
     *  get all the learningResults where Student is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<LearningResult> findAllWhereStudentIsNull() {
        log.debug("Request to get all learningResults where Student is null");
        return StreamSupport
            .stream(learningResultRepository.findAll().spliterator(), false)
            .filter(learningResult -> learningResult.getStudent() == null)
            .collect(Collectors.toList());
    }

    /**
     *  get one learningResult by id.
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public LearningResult findOne(Long id) {
        log.debug("Request to get LearningResult : {}", id);
        LearningResult learningResult = learningResultRepository.findOne(id);
        return learningResult;
    }

    /**
     *  delete the  learningResult by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete LearningResult : {}", id);
        learningResultRepository.delete(id);
    }
}
