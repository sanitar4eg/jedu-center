package edu.netcracker.center.repository;

import edu.netcracker.center.domain.LearningResult;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LearningResult entity.
 */
public interface LearningResultRepository extends JpaRepository<LearningResult,Long> {

}
