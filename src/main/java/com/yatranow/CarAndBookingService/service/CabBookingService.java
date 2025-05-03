package com.yatranow.CarAndBookingService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yatranow.CarAndBookingService.entity.CabBookingDetails;
import com.yatranow.CarAndBookingService.repository.CabBookingDetailsRepository;
import com.yatranow.CarAndBookingService.repository.CabRegistrationDetailsRepository;
import com.yatranow.CarAndBookingService.response.CabBookingResponse;

@Service
public class CabBookingService {

    @Autowired
    private CabBookingDetailsRepository cabBookingDetailsRepository;
    
    @Autowired
    private CabRegistrationDetailsRepository cabRegDetailsRepo;

    // Create a new cab booking
    public CabBookingResponse startCabBooking(CabBookingDetails cabBookingDetails) {
    	CabBookingResponse cabBookingResponse = new CabBookingResponse();
       // Validate the booking details (e.g., check if cab is available, etc.)
    	double balanceAmt = calculateBalanceAmount(cabBookingDetails.getFare(), cabBookingDetails.getPromoDiscount(), cabBookingDetails.getTokenAmount());
    	cabBookingDetails.setBalanceAmount(balanceAmt);
    	cabBookingDetails.setBookingStatus("Pending");
		// Save the booking details to the database
    	CabBookingDetails savedData = cabBookingDetailsRepository.save(cabBookingDetails);
    	cabBookingResponse.setUserId(savedData.getUserId());
    	cabBookingResponse.setBookingId(savedData.getBookingId());
    	cabBookingResponse.setCabRegistrationId(savedData.getCabRegistrationId());
    	cabBookingResponse.setPickupLocation(savedData.getPickupLocation());
    	cabBookingResponse.setDropLocation(savedData.getDropLocation());
    	cabBookingResponse.setPickupDateTime(savedData.getPickupDateTime());
    	cabBookingResponse.setDropDateTime(savedData.getDropDateTime());
    	cabBookingResponse.setFare(savedData.getFare());
    	cabBookingResponse.setPromoDiscount(savedData.getPromoDiscount());
    	cabBookingResponse.setTokenAmount(savedData.getTokenAmount());
    	cabBookingResponse.setBalanceAmount(savedData.getBalanceAmount());
    	cabBookingResponse.setBookingStatus(savedData.getBookingStatus());
    	cabBookingResponse.setBalanceAmount(balanceAmt);
   
    	cabRegDetailsRepo.findByRegistrationId(cabBookingDetails.getCabRegistrationId()).ifPresent(cabRegDetails -> {
    		cabBookingResponse.setDriverName(cabRegDetails.getDriverName());
    		cabBookingResponse.setDriverContact(cabRegDetails.getDriverContact());
    		cabBookingResponse.setDriverLicense(cabRegDetails.getDriverLicense());
    		cabBookingResponse.setAddress(cabRegDetails.getAddress());
    		cabBookingResponse.setCabId(cabRegDetails.getCab().getCabId());
    		cabBookingResponse.setCabName(cabRegDetails.getCab().getCabName());
    		cabBookingResponse.setCabType(cabRegDetails.getCab().getCabType());
    		cabBookingResponse.setCabNumber(cabRegDetails.getCab().getCabNumber());
    		cabBookingResponse.setCabColor(cabRegDetails.getCab().getCabColor());
    		cabBookingResponse.setCabInsurance(cabRegDetails.getCab().getCabInsurance());
    		cabBookingResponse.setCabCapacity(cabRegDetails.getCab().getCabCapacity());
    		cabBookingResponse.setCabImageUrl(cabRegDetails.getCab().getCabImageUrl());
    		cabBookingResponse.setCabCity(cabRegDetails.getCab().getCabCity());
    		cabBookingResponse.setCabState(cabRegDetails.getCab().getCabState());
    		cabBookingResponse.setCabModel(cabRegDetails.getCab().getCabModel());
    	});
    	
        return cabBookingResponse;
    }

	private double calculateBalanceAmount(double fare, double promoDiscount, double tokenAmount) {
		double balanceAmt = fare - promoDiscount - tokenAmount;
		return balanceAmt;
	}

	// Get cab booking by ID
    public CabBookingDetails getCabBookingById(Long id) {
        Optional<CabBookingDetails> cabBookingDetails = cabBookingDetailsRepository.findById(id);
        return cabBookingDetails.orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
    }
    
    public CabBookingResponse getCabBookingWithDriverDetailsById(Long id) {
    	CabBookingResponse cabBookingResponse = new CabBookingResponse();
        cabBookingDetailsRepository.findById(id).ifPresent(cabBookingDetails -> {
        	cabBookingResponse.setBookingId(cabBookingDetails.getBookingId());
        	cabBookingResponse.setCabRegistrationId(cabBookingDetails.getCabRegistrationId());
        	cabBookingResponse.setPickupLocation(cabBookingDetails.getPickupLocation());
        	cabBookingResponse.setDropLocation(cabBookingDetails.getDropLocation());
        	cabBookingResponse.setPickupDateTime(cabBookingDetails.getPickupDateTime());
        	cabBookingResponse.setDropDateTime(cabBookingDetails.getDropDateTime());
        	cabBookingResponse.setFare(cabBookingDetails.getFare());
        	cabBookingResponse.setPromoDiscount(cabBookingDetails.getPromoDiscount());
        	cabBookingResponse.setTokenAmount(cabBookingDetails.getTokenAmount());
        	cabBookingResponse.setBalanceAmount(cabBookingDetails.getBalanceAmount());
       
        	cabRegDetailsRepo.findByRegistrationId(cabBookingDetails.getCabRegistrationId()).ifPresent(cabRegDetails -> {
        		cabBookingResponse.setDriverName(cabRegDetails.getDriverName());
        		cabBookingResponse.setDriverContact(cabRegDetails.getDriverContact());
        		cabBookingResponse.setDriverLicense(cabRegDetails.getDriverLicense());
        		cabBookingResponse.setAddress(cabRegDetails.getAddress());
        		cabBookingResponse.setCabId(cabRegDetails.getCab().getCabId());
        		cabBookingResponse.setCabName(cabRegDetails.getCab().getCabName());
        		cabBookingResponse.setCabType(cabRegDetails.getCab().getCabType());
        		cabBookingResponse.setCabNumber(cabRegDetails.getCab().getCabNumber());
        		cabBookingResponse.setCabColor(cabRegDetails.getCab().getCabColor());
        		cabBookingResponse.setCabInsurance(cabRegDetails.getCab().getCabInsurance());
        		cabBookingResponse.setCabCapacity(cabRegDetails.getCab().getCabCapacity());
        		cabBookingResponse.setCabImageUrl(cabRegDetails.getCab().getCabImageUrl());
        		cabBookingResponse.setCabCity(cabRegDetails.getCab().getCabCity());
        		cabBookingResponse.setCabState(cabRegDetails.getCab().getCabState());
        		cabBookingResponse.setCabModel(cabRegDetails.getCab().getCabModel());
        	});
		});
        return cabBookingResponse;
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
        existingBooking.setPaymentDetails(updatedDetails.getPaymentDetails());
        return cabBookingDetailsRepository.save(existingBooking);
    }

    // Delete cab booking
    public void deleteCabBooking(Long id) {
        cabBookingDetailsRepository.deleteById(id);
    }
}

