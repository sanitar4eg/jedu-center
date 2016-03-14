package edu.netcracker.center.repository;

import edu.netcracker.center.domain.Lesson;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Lesson entity.
 */
public interface LessonRepository extends JpaRepository<Lesson,Long> {

}
