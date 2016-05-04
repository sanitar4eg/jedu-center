package edu.netcracker.center.service;

import com.mysema.query.types.Predicate;
import edu.netcracker.center.domain.Student;
import edu.netcracker.center.domain.util.OperationResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

/**
 * Service Interface for managing Student.
 */
public interface StudentService {

    /**
     * Save a student.
     * @return the persisted entity
     */
    public Student save(Student student);

    /**
     *  get all the students.
     *  @return the list of entities
     */
    public Page<Student> findAll(Pageable pageable);

    /**
     *  get all the students.
     *  @return the list of entities
     */
    public Iterable<Student> findAll(Predicate predicate);

    /**
     *  get all the students.
     *  @return the list of entities
     */
    public Page<Student> findAll(Predicate predicate, Pageable pageable);

    /**
     *  get all the students.
     *  @return the list of entities
     */
    public List<Student> findAll();

    /**
     *  get the "id" student.
     *  @return the entity
     */
    public Student findOne(Long id);

    /**
     *  delete the "id" student.
     */
    public void delete(Long id);

    /**
     *  register students.
     */
    Collection<OperationResult> registerStudents(Collection<Student> students, String baseUrl);
}
