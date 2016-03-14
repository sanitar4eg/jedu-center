package edu.netcracker.center.repository;

import edu.netcracker.center.domain.Form;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Form entity.
 */
public interface FormRepository extends JpaRepository<Form,Long> {

}
