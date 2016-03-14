package edu.netcracker.center.repository;

import edu.netcracker.center.domain.Recall;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Recall entity.
 */
public interface RecallRepository extends JpaRepository<Recall,Long> {

}
