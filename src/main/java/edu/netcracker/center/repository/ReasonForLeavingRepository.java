package edu.netcracker.center.repository;

import edu.netcracker.center.domain.ReasonForLeaving;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ReasonForLeaving entity.
 */
public interface ReasonForLeavingRepository extends JpaRepository<ReasonForLeaving,Long> {

}
