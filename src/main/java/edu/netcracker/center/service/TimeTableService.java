package edu.netcracker.center.service;

import edu.netcracker.center.domain.TimeTable;

import java.util.List;

/**
 * Service Interface for managing TimeTable.
 */
public interface TimeTableService {

    /**
     * Save a timeTable.
     * @return the persisted entity
     */
    public TimeTable save(TimeTable timeTable);

    /**
     *  get all the timeTables.
     *  @return the list of entities
     */
    public List<TimeTable> findAll();
    /**
     *  get all the timeTables where GroupOfStudent is null.
     *  @return the list of entities
     */
    public List<TimeTable> findAllWhereGroupOfStudentIsNull();

    /**
     *  get the "id" timeTable.
     *  @return the entity
     */
    public TimeTable findOne(Long id);

    /**
     *  delete the "id" timeTable.
     */
    public void delete(Long id);
}
