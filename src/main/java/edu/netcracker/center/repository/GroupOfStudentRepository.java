package edu.netcracker.center.repository;

import edu.netcracker.center.domain.GroupOfStudent;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GroupOfStudent entity.
 */
public interface GroupOfStudentRepository extends JpaRepository<GroupOfStudent,Long> {

}
