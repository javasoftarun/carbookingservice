package com.yatranow.CarAndBookingService.repository;

import com.yatranow.CarAndBookingService.entity.CabRegistrationDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CabRegistrationDetailsRepository extends JpaRepository<CabRegistrationDetails, Long> {

	@Query("SELECT c FROM CabRegistrationDetails c WHERE c.registrationId = :registrationId")
	Optional<CabRegistrationDetails> findByRegistrationId(@Param("registrationId") Long registrationId);

	@Query("SELECT c FROM CabRegistrationDetails c WHERE c.registrationId NOT IN ("
			+ "SELECT b.cabRegistrationId FROM CabBookingDetails b "
			+ "WHERE (:pickupDateTime BETWEEN b.pickupDateTime AND b.dropDateTime "
			+ "OR :dropDateTime BETWEEN b.pickupDateTime AND b.dropDateTime "
			+ "OR :pickupDateTime = b.pickupDateTime OR :dropDateTime = b.dropDateTime))")
	List<CabRegistrationDetails> findAvailableCabs(@Param("pickupDateTime") LocalDateTime pickupDateTime,
			@Param("dropDateTime") LocalDateTime dropDateTime);
}
