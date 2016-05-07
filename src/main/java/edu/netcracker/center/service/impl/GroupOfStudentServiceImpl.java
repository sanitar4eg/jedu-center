package edu.netcracker.center.service.impl;

import edu.netcracker.center.service.GroupOfStudentService;
import edu.netcracker.center.domain.GroupOfStudent;
import edu.netcracker.center.repository.GroupOfStudentRepository;
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
 * Service Implementation for managing GroupOfStudent.
 */
@Service
@Transactional
public class GroupOfStudentServiceImpl implements GroupOfStudentService{

    private final Logger log = LoggerFactory.getLogger(GroupOfStudentServiceImpl.class);

    @Inject
    private GroupOfStudentRepository groupOfStudentRepository;

    /**
     * Save a groupOfStudent.
     * @return the persisted entity
     */
    public GroupOfStudent save(GroupOfStudent groupOfStudent) {
        log.debug("Request to save GroupOfStudent : {}", groupOfStudent);
        GroupOfStudent result = groupOfStudentRepository.save(groupOfStudent);
        return result;
    }

    /**
     *  get all the groupOfStudents.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GroupOfStudent> findAll(Pageable pageable) {
        log.debug("Request to get all GroupOfStudents");
        Page<GroupOfStudent> result = groupOfStudentRepository.findAll(pageable);
        return result;
    }

    /**
     *  get one groupOfStudent by id.
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public GroupOfStudent findOne(Long id) {
        log.debug("Request to get GroupOfStudent : {}", id);
        GroupOfStudent groupOfStudent = groupOfStudentRepository.findOne(id);
        return groupOfStudent;
    }

    /**
     *  delete the  groupOfStudent by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete GroupOfStudent : {}", id);
        GroupOfStudent group = groupOfStudentRepository.findOne(id);
        group.getStudents().forEach(student -> student.setGroupOfStudent(null));
        group.getStudents().clear();
        groupOfStudentRepository.delete(group);
    }
}
