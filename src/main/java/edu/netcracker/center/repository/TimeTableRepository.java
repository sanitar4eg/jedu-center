package edu.netcracker.center.repository;

import edu.netcracker.center.domain.TimeTable;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TimeTable entity.
 */
public interface TimeTableRepository extends JpaRepository<TimeTable,Long> {

}
