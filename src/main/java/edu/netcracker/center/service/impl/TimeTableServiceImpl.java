package edu.netcracker.center.service.impl;

import edu.netcracker.center.service.TimeTableService;
import edu.netcracker.center.domain.TimeTable;
import edu.netcracker.center.repository.TimeTableRepository;
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
 * Service Implementation for managing TimeTable.
 */
@Service
@Transactional
public class TimeTableServiceImpl implements TimeTableService{

    private final Logger log = LoggerFactory.getLogger(TimeTableServiceImpl.class);
    
    @Inject
    private TimeTableRepository timeTableRepository;
    
    /**
     * Save a timeTable.
     * @return the persisted entity
     */
    public TimeTable save(TimeTable timeTable) {
        log.debug("Request to save TimeTable : {}", timeTable);
        TimeTable result = timeTableRepository.save(timeTable);
        return result;
    }

    /**
     *  get all the timeTables.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<TimeTable> findAll(Pageable pageable) {
        log.debug("Request to get all TimeTables");
        Page<TimeTable> result = timeTableRepository.findAll(pageable); 
        return result;
    }


    /**
     *  get all the timeTables where GroupOfStudent is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TimeTable> findAllWhereGroupOfStudentIsNull() {
        log.debug("Request to get all timeTables where GroupOfStudent is null");
        return StreamSupport
            .stream(timeTableRepository.findAll().spliterator(), false)
            .filter(timeTable -> timeTable.getGroupOfStudent() == null)
            .collect(Collectors.toList());
    }

    /**
     *  get one timeTable by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TimeTable findOne(Long id) {
        log.debug("Request to get TimeTable : {}", id);
        TimeTable timeTable = timeTableRepository.findOne(id);
        return timeTable;
    }

    /**
     *  delete the  timeTable by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete TimeTable : {}", id);
        timeTableRepository.delete(id);
    }
}
