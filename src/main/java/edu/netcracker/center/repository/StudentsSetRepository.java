package edu.netcracker.center.repository;

import edu.netcracker.center.domain.StudentsSet;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StudentsSet entity.
 */
public interface StudentsSetRepository extends JpaRepository<StudentsSet,Long> {

}
