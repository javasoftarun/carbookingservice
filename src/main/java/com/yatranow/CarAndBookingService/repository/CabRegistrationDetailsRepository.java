package com.yatranow.CarAndBookingService.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yatranow.CarAndBookingService.entity.CabRegistrationDetails;

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

	@Query("SELECT c FROM CabRegistrationDetails c WHERE c.userId = :vendorId")
	List<CabRegistrationDetails> findByUserId(@Param("vendorId") Long vendorId);

	@Query("SELECT c.registrationId FROM CabRegistrationDetails c WHERE c.userId = :vendorId AND c.status = 'active'")
	List<Long> findRegistrationIdByUserId(@Param("vendorId") Long vendorId);

}
