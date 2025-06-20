package com.yatranow.CarAndBookingService.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yatranow.CarAndBookingService.entity.CabBookingDetails;

@Repository
public interface CabBookingDetailsRepository extends JpaRepository<CabBookingDetails, Long> {

	@Query(value = "SELECT * FROM cab_booking_details c WHERE c.cab_registration_id = :cabRegistrationId AND "
			+ "((c.pickup_date_time BETWEEN DATE_SUB(:pickupDateTime, INTERVAL 5 HOUR) AND :dropDateTime) OR "
			+ "(c.drop_date_time BETWEEN :pickupDateTime AND :dropDateTime))", nativeQuery = true)
	public Optional<CabBookingDetails> findAvailableCabBetweenPickAndDropDate(
			@Param("cabRegistrationId") Long cabRegistrationId, @Param("pickupDateTime") LocalDateTime pickupDateTime,
			@Param("dropDateTime") LocalDateTime dropDateTime);

	@Query("SELECT c FROM CabBookingDetails c WHERE c.userId = :userId")
	public List<CabBookingDetails> findByUserId(@Param("userId") Long userId);
	
	@Query("SELECT c FROM CabBookingDetails c WHERE c.cabRegistrationId = :cabRegistrationId")
	public List<CabBookingDetails> findByCabRegistrationId(@Param("cabRegistrationId") Long cabRegistrationId);

	@Query("SELECT c FROM CabBookingDetails c WHERE c.cabRegistrationId = :cabRegistrationId AND c.bookingStatus = 'Pending'")
	public List<CabBookingDetails> findPendingBookingByCabRegistrationId(@Param("cabRegistrationId") Long cabRegistrationId);
	
	@Query("SELECT c FROM CabBookingDetails c WHERE c.cabRegistrationId = :cabRegistrationId AND c.bookingStatus not in ('Pending')")
	public List<CabBookingDetails> findAllBookingsByCabRegistrationId(@Param("cabRegistrationId") Long cabRegistrationId);

	List<CabBookingDetails> findByUserIdAndBookingStatus(Long userId, String bookingStatus);

	public Optional<CabBookingDetails> findByBookingId(Long bookingId);

}
