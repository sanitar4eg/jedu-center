package edu.netcracker.center.repository;

import edu.netcracker.center.domain.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Spring Data JPA repository for the Form entity.
 */
public interface FormRepository extends JpaRepository<Form, Long>, QueryDslPredicateExecutor<Form> {

}
