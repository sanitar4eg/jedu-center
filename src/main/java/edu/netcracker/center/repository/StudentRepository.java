package edu.netcracker.center.repository;

import edu.netcracker.center.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Spring Data JPA repository for the Student entity.
 */
public interface StudentRepository extends JpaRepository<Student, Long>, QueryDslPredicateExecutor<Student> {

}
