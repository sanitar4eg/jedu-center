package edu.netcracker.center.repository;

import edu.netcracker.center.domain.Evaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Evaluation entity.
 */
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    Page<Evaluation> findByLessonId(Long id, Pageable pageable);
}
