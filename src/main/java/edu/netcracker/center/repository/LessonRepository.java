package edu.netcracker.center.repository;

import edu.netcracker.center.domain.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Lesson entity.
 */
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    Page<Lesson> findByTimeTableId(Long id, Pageable pageable);

}
