package edu.netcracker.center.service.impl;

import com.mysema.query.types.Predicate;
import edu.netcracker.center.domain.Student;
import edu.netcracker.center.domain.User;
import edu.netcracker.center.domain.util.OperationResult;
import edu.netcracker.center.repository.StudentRepository;
import edu.netcracker.center.repository.UserRepository;
import edu.netcracker.center.service.MailService;
import edu.netcracker.center.service.StudentService;
import edu.netcracker.center.service.UserService;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Service Implementation for managing Student.
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Inject
    private StudentRepository studentRepository;

    @Inject
    MailService mailService;

    @Inject
    UserRepository userRepository;

    @Inject
    UserService userService;

    /**
     * Save a student.
     *
     * @return the persisted entity
     */
    public Student save(Student student) {
        log.debug("Request to save Student : {}", student);
        Student result = studentRepository.save(student);
        return result;
    }

    /**
     * get all the students.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Student> findAll(Pageable pageable) {
        log.debug("Request to get all Students");
        Page<Student> result = studentRepository.findAll(pageable);
        return result;
    }

    @Override
    public Page<Student> findAll(Predicate predicate, Pageable pageable) {
        return studentRepository.findAll(predicate, pageable);
    }

    /**
     * get all the students.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        log.debug("Request to get all Students");
        List<Student> result = studentRepository.findAll();
        return result;
    }

    /**
     * get one student by id.
     *
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Student findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        Student student = studentRepository.findOne(id);
        return student;
    }

    /**
     * delete the  student by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Student : {}", id);
        studentRepository.delete(id);
    }

    /**
     * register students
     */
    @Transactional
    public Collection<OperationResult> registerStudents(Collection<Student> students, String baseUrl) {
        log.debug("Request to register Students : {}", students);
        Collection<OperationResult> results = new LinkedList<>();
        for (Student student : students) {
            OperationResult result;
            Long id = student.getId();
            String email = student.getEmail();
            String password = RandomStringUtils.random(10, true, true);
            try {
                result = userRepository.findOneByLogin(email)
                    .map(user -> createResult(id, "Пользователь с таким логином уже зарегистрирован", email))
                    .orElseGet(() -> userRepository.findOneByEmail(email)
                        .map(user -> createResult(id, "Пользователь с таким e-mail уже зарегистрирован", email))
                        .orElseGet(() -> {
                            User user = createUser(student, password);
                            mailService.sendCenterActivationEmail(user, baseUrl, password);
                            student.setUser(user);
                            studentRepository.save(student);
                            return createResult(id, "Пользователь создан", email);
                        })
                    );
            } catch (RuntimeException e) {
                log.error("Exception when creating user for student", e);
                result = createResult(id, e.getMessage(), email);
            }
            results.add(result);
        }
        return results;
    }

    private User createUser(Student student, String password) {
        return userService.createUserForStudent(student.getFirstName(), student.getLastName(), student.getEmail(),
            password);
    }

    private OperationResult createResult(Long id, String message, String description) {
        OperationResult result = new OperationResult();
        result.setIdentifier(id.toString());
        result.setMessage(message);
        result.setDescription(description);
        return result;
    }
}
