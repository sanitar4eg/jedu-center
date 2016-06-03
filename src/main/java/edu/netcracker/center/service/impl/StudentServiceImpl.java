package edu.netcracker.center.service.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import edu.netcracker.center.domain.*;
import edu.netcracker.center.domain.util.OperationResult;
import edu.netcracker.center.repository.AuthorityRepository;
import edu.netcracker.center.repository.LearningResultRepository;
import edu.netcracker.center.repository.StudentRepository;
import edu.netcracker.center.repository.UserRepository;
import edu.netcracker.center.security.AuthoritiesConstants;
import edu.netcracker.center.service.MailService;
import edu.netcracker.center.service.StudentService;
import edu.netcracker.center.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.*;

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
    private MailService mailService;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private LearningResultRepository learningResultRepository;

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

    /**
     * get all the students by predicate.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Iterable<Student> findAll(Predicate predicate) {
        log.debug("Request to get all Students by predicate");
        return studentRepository.findAll(predicate);
    }

    /**
     * get all the students by predicate.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Student> findAll(Predicate predicate, Pageable pageable) {
        log.debug("Request to get all Students by predicate");
        return studentRepository.findAll(predicate, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Student> findByCurator(Curator curator, Pageable pageable) {
        log.debug("Request to get all Students by curator");
        return studentRepository.findAll(new BooleanBuilder().and(QStudent.student.curator.eq(curator))
            .and(QStudent.student.isActive.eq(true)), pageable);
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
            try {
                result = userRepository.findOneByLogin(email)
                    .map(user -> createResult(id, "Пользователь с таким логином уже зарегистрирован", email))
                    .orElseGet(() -> userRepository.findOneByEmail(email)
                        .map(user -> createResult(id, "Пользователь с таким e-mail уже зарегистрирован", email))
                        .orElseGet(() -> {
                            User user = createUser(student);
                            mailService.sendCreationEmail(user, baseUrl);
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

    @Transactional
    public Student archive(Student student) {
        Optional.ofNullable(student.getUser()).ifPresent(user -> {
            user = userRepository.findOne(user.getId());
            user.setActivated(false);
            userRepository.save(user);
        });
        student.getLearningResult().setCreationTime(ZonedDateTime.now());
        learningResultRepository.save(student.getLearningResult());
        student.setIsActive(false);
        return studentRepository.save(student);
    }

    @Transactional
    public Student unzip(Long id) {
        Student student = studentRepository.getOne(id);
        Optional.ofNullable(student.getUser()).ifPresent(user -> {
            user.setActivated(true);
            userRepository.save(user);
        });
        log.error(student.getLearningResult().toString());
        learningResultRepository.delete(student.getLearningResult());
        student.setIsActive(true);
        student.setLearningResult(null);
        return studentRepository.save(student);
    }

    private User createUser(Student student) {
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authorityRepository.findOne(AuthoritiesConstants.USER));
        authorities.add(authorityRepository.findOne(AuthoritiesConstants.STUDENT));
        return userService.createUserForEC(student.getFirstName(), student.getLastName(),
            student.getEmail(), authorities);
    }

    private OperationResult createResult(Long id, String message, String description) {
        return new OperationResult(id.toString(), message, description);
    }
}
