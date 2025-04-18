package com.yatranow.CarAndBookingService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yatranow.CarAndBookingService.entity.CabBookingDetails;

@Repository
public interface CabBookingDetailsRepository extends JpaRepository<CabBookingDetails, Long> {

}
