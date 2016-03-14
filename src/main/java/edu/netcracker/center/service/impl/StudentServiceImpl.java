package edu.netcracker.center.service.impl;

import edu.netcracker.center.service.StudentService;
import edu.netcracker.center.domain.Student;
import edu.netcracker.center.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Student.
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService{

    private final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);
    
    @Inject
    private StudentRepository studentRepository;
    
    /**
     * Save a student.
     * @return the persisted entity
     */
    public Student save(Student student) {
        log.debug("Request to save Student : {}", student);
        Student result = studentRepository.save(student);
        return result;
    }

    /**
     *  get all the students.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Student> findAll() {
        log.debug("Request to get all Students");
        List<Student> result = studentRepository.findAll();
        return result;
    }


    /**
     *  get all the students where Form is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Student> findAllWhereFormIsNull() {
        log.debug("Request to get all students where Form is null");
        return StreamSupport
            .stream(studentRepository.findAll().spliterator(), false)
            .filter(student -> student.getForm() == null)
            .collect(Collectors.toList());
    }

    /**
     *  get one student by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Student findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        Student student = studentRepository.findOne(id);
        return student;
    }

    /**
     *  delete the  student by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Student : {}", id);
        studentRepository.delete(id);
    }
}
