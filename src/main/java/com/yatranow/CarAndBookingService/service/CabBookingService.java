package com.yatranow.CarAndBookingService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yatranow.CarAndBookingService.entity.CabBookingDetails;
import com.yatranow.CarAndBookingService.repository.CabBookingDetailsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CabBookingService {

    @Autowired
    private CabBookingDetailsRepository cabBookingDetailsRepository;

    // Create a new cab booking
    public CabBookingDetails startCabBooking(CabBookingDetails cabBookingDetails) {
        return cabBookingDetailsRepository.save(cabBookingDetails);
    }

    // Get cab booking by ID
    public CabBookingDetails getCabBookingById(Long id) {
        Optional<CabBookingDetails> cabBookingDetails = cabBookingDetailsRepository.findById(id);
        return cabBookingDetails.orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
    }

    // Get all cab bookings
    public List<CabBookingDetails> getAllCabBookings() {
        return cabBookingDetailsRepository.findAll();
    }

    // Update cab booking
    public CabBookingDetails updateCabBooking(Long id, CabBookingDetails updatedDetails) {
        CabBookingDetails existingBooking = getCabBookingById(id);
        existingBooking.setPickupLocation(updatedDetails.getPickupLocation());
        existingBooking.setDropLocation(updatedDetails.getDropLocation());
        existingBooking.setPickupDateTime(updatedDetails.getPickupDateTime());
        existingBooking.setDropDateTime(updatedDetails.getDropDateTime());
        existingBooking.setFare(updatedDetails.getFare());
        existingBooking.setPromoDiscount(updatedDetails.getPromoDiscount());
        existingBooking.setFinalFare(updatedDetails.getFinalFare());
        existingBooking.setPaymentDetails(updatedDetails.getPaymentDetails());
        return cabBookingDetailsRepository.save(existingBooking);
    }

    // Delete cab booking
    public void deleteCabBooking(Long id) {
        cabBookingDetailsRepository.deleteById(id);
    }
}

