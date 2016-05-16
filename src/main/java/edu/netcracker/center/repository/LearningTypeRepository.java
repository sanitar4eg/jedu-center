package edu.netcracker.center.repository;

import edu.netcracker.center.domain.LearningType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LearningType entity.
 */
public interface LearningTypeRepository extends JpaRepository<LearningType,Long> {

}
