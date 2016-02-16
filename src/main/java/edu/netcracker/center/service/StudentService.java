package edu.netcracker.center.service;

import edu.netcracker.center.domain.Student;

import java.util.Date;
import java.util.List;

/**
 * Service Interface for managing Student.
 */
public interface StudentService {

    /**
     * Save a student.
     *
     * @return the persisted entity
     */
    public Student save(Student student);

    /**
     * get all the students.
     *
     * @return the list of entities
     */
    public List<Student> findAll();

    /**
     * get the "id" student.
     *
     * @return the entity
     */
    public Student findOne(Long id);

    /**
     * delete the "id" student.
     */
    public void delete(Long id);
}
