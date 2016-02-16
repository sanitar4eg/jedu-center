package edu.netcracker.center.service.impl;

import edu.netcracker.center.domain.Student;
import edu.netcracker.center.repository.StudentRepository;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.exception.RevisionDoesNotExistException;
import org.hibernate.envers.query.AuditEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Service Implementation for view history.
 */
@Service
@Transactional
public class HistoryServiceImpl {

    private final Logger log = LoggerFactory.getLogger(HistoryServiceImpl.class);

    @Inject
    private StudentRepository studentRepository;

    @Inject
    EntityManager entityManager;

    /**
     * get history of students by date.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Student> getHistoryOfStudents(Date date) {
//        Date convertedDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        log.debug("Request to get history of Students : {}", date);
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        List<Student> list;
        try {
            list = (List<Student>) auditReader.createQuery()
                .forRevisionsOfEntity(Student.class, true, true)
                .add(AuditEntity.revisionNumber().le(auditReader.getRevisionNumberForDate(date)))
                .add(AuditEntity.revisionNumber().maximize().computeAggregationInInstanceContext()
                    .add(AuditEntity.revisionNumber().le(auditReader.getRevisionNumberForDate(date))))
                .add(AuditEntity.revisionType().ne(RevisionType.DEL))
                .getResultList();
        } catch (RevisionDoesNotExistException exception) {
            list = Collections.EMPTY_LIST;
        }
        return list;
    }
}
