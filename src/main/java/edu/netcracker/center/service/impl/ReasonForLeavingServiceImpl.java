package edu.netcracker.center.service.impl;

import edu.netcracker.center.service.ReasonForLeavingService;
import edu.netcracker.center.domain.ReasonForLeaving;
import edu.netcracker.center.repository.ReasonForLeavingRepository;
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
 * Service Implementation for managing ReasonForLeaving.
 */
@Service
@Transactional
public class ReasonForLeavingServiceImpl implements ReasonForLeavingService{

    private final Logger log = LoggerFactory.getLogger(ReasonForLeavingServiceImpl.class);
    
    @Inject
    private ReasonForLeavingRepository reasonForLeavingRepository;
    
    /**
     * Save a reasonForLeaving.
     * @return the persisted entity
     */
    public ReasonForLeaving save(ReasonForLeaving reasonForLeaving) {
        log.debug("Request to save ReasonForLeaving : {}", reasonForLeaving);
        ReasonForLeaving result = reasonForLeavingRepository.save(reasonForLeaving);
        return result;
    }

    /**
     *  get all the reasonForLeavings.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ReasonForLeaving> findAll(Pageable pageable) {
        log.debug("Request to get all ReasonForLeavings");
        Page<ReasonForLeaving> result = reasonForLeavingRepository.findAll(pageable); 
        return result;
    }


    /**
     *  get all the reasonForLeavings where Student is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ReasonForLeaving> findAllWhereStudentIsNull() {
        log.debug("Request to get all reasonForLeavings where Student is null");
        return StreamSupport
            .stream(reasonForLeavingRepository.findAll().spliterator(), false)
            .filter(reasonForLeaving -> reasonForLeaving.getStudent() == null)
            .collect(Collectors.toList());
    }

    /**
     *  get one reasonForLeaving by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ReasonForLeaving findOne(Long id) {
        log.debug("Request to get ReasonForLeaving : {}", id);
        ReasonForLeaving reasonForLeaving = reasonForLeavingRepository.findOne(id);
        return reasonForLeaving;
    }

    /**
     *  delete the  reasonForLeaving by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReasonForLeaving : {}", id);
        reasonForLeavingRepository.delete(id);
    }
}
