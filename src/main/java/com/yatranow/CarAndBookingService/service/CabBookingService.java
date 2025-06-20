package com.yatranow.CarAndBookingService.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yatranow.CarAndBookingService.entity.CabBookingDetails;
import com.yatranow.CarAndBookingService.repository.CabBookingDetailsRepository;
import com.yatranow.CarAndBookingService.repository.CabRegistrationDetailsRepository;
import com.yatranow.CarAndBookingService.request.CabAvailabilityRequest;
import com.yatranow.CarAndBookingService.request.UpdateBookingStatusRequest;
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
		double balanceAmt = calculateBalanceAmount(cabBookingDetails.getFare(), cabBookingDetails.getPromoDiscount(),
				cabBookingDetails.getTokenAmount());
		cabBookingDetails.setBalanceAmount(balanceAmt);
		cabBookingDetails.setBookingStatus("Pending");
		if (cabBookingDetails.getTokenAmount() > 0) {
			cabBookingDetails.setPaymentStatus("partial");
		}
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
			cabBookingResponse.setAC(cabRegDetails.getCab().isAC());
			cabBookingResponse.setFuelType(cabRegDetails.getCab().getFluelType());
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
			cabBookingResponse.setPaymentStatus(cabBookingDetails.getPaymentStatus());
			cabBookingResponse.setBookingStatus(cabBookingDetails.getBookingStatus());
			cabBookingResponse.setBookingStatusUpdatedBy(cabBookingDetails.getBookingStatusUpdatedBy());
			cabBookingResponse.setCabRegistrationId(cabBookingDetails.getCabRegistrationId());
			cabBookingResponse.setPickupLocation(cabBookingDetails.getPickupLocation());
			cabBookingResponse.setDropLocation(cabBookingDetails.getDropLocation());
			cabBookingResponse.setPickupDateTime(cabBookingDetails.getPickupDateTime());
			cabBookingResponse.setDropDateTime(cabBookingDetails.getDropDateTime());
			cabBookingResponse.setFare(cabBookingDetails.getFare());
			cabBookingResponse.setPromoDiscount(cabBookingDetails.getPromoDiscount());
			cabBookingResponse.setTokenAmount(cabBookingDetails.getTokenAmount());
			cabBookingResponse.setBalanceAmount(cabBookingDetails.getBalanceAmount());

			cabRegDetailsRepo.findByRegistrationId(cabBookingDetails.getCabRegistrationId())
					.ifPresent(cabRegDetails -> {
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
						cabBookingResponse.setAC(cabRegDetails.getCab().isAC());
						cabBookingResponse.setFuelType(cabRegDetails.getCab().getFluelType());
					});
		});
		return cabBookingResponse;
	}

	public List<CabBookingResponse> getCabBookingDetailsByUserId(Long userId) {
		List<CabBookingResponse> bookingList = new ArrayList<>();
		cabBookingDetailsRepository.findByUserId(userId).stream().forEach(cabBookingDetails -> {
			CabBookingResponse cabBookingResponse = new CabBookingResponse();
			cabBookingResponse.setUserId(userId);
			cabBookingResponse.setBookingId(cabBookingDetails.getBookingId());
			cabBookingResponse.setCabRegistrationId(cabBookingDetails.getCabRegistrationId());
			cabBookingResponse.setPaymentStatus(cabBookingDetails.getPaymentStatus());
			cabBookingResponse.setBookingStatus(cabBookingDetails.getBookingStatus());
			cabBookingResponse.setBookingStatusUpdatedBy(cabBookingDetails.getBookingStatusUpdatedBy());
			cabBookingResponse.setPickupLocation(cabBookingDetails.getPickupLocation());
			cabBookingResponse.setDropLocation(cabBookingDetails.getDropLocation());
			cabBookingResponse.setPickupDateTime(cabBookingDetails.getPickupDateTime());
			cabBookingResponse.setDropDateTime(cabBookingDetails.getDropDateTime());
			cabBookingResponse.setFare(cabBookingDetails.getFare());
			cabBookingResponse.setPromoDiscount(cabBookingDetails.getPromoDiscount());
			cabBookingResponse.setTokenAmount(cabBookingDetails.getTokenAmount());
			cabBookingResponse.setBalanceAmount(cabBookingDetails.getBalanceAmount());

			cabRegDetailsRepo.findByRegistrationId(cabBookingDetails.getCabRegistrationId())
					.ifPresent(cabRegDetails -> {
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
						cabBookingResponse.setAC(cabRegDetails.getCab().isAC());
						cabBookingResponse.setFuelType(cabRegDetails.getCab().getFluelType());
					});
			bookingList.add(cabBookingResponse);
		});
		return bookingList;
	}

	// Get all cab bookings
	public List<CabBookingDetails> getAllCabBookings() {
		return cabBookingDetailsRepository.findAll();
	}

	// Update cab booking
	public CabBookingDetails updateCabBooking(Long id, CabBookingDetails updatedDetails) {
		CabBookingDetails existingBooking = getCabBookingById(id);
		existingBooking.setCabRegistrationId(updatedDetails.getCabRegistrationId());
		existingBooking.setPickupLocation(updatedDetails.getPickupLocation());
		existingBooking.setDropLocation(updatedDetails.getDropLocation());
		existingBooking.setPickupDateTime(updatedDetails.getPickupDateTime());
		existingBooking.setDropDateTime(updatedDetails.getDropDateTime());
		existingBooking.setFare(updatedDetails.getFare());
		existingBooking.setTokenAmount(updatedDetails.getTokenAmount());
		existingBooking.setBalanceAmount(updatedDetails.getBalanceAmount());
		existingBooking.setPromoDiscount(updatedDetails.getPromoDiscount());
		existingBooking.setPaymentDetails(updatedDetails.getPaymentDetails());
		return cabBookingDetailsRepository.save(existingBooking);
	}

	// Delete cab booking
	public void deleteCabBooking(Long id) {
		cabBookingDetailsRepository.deleteById(id);
	}

	public boolean checkCabAvailability(CabAvailabilityRequest request) {
		Long cabRegistrationId = request.getCabRegistrationId();
		LocalDateTime pickupDateTime = request.getPickupDateTime();
		LocalDateTime dropDateTime = request.getDropDateTime();
		Optional<CabBookingDetails> cabBookingDetails = cabBookingDetailsRepository
				.findAvailableCabBetweenPickAndDropDate(cabRegistrationId, pickupDateTime, dropDateTime);
		return cabBookingDetails.isEmpty();
	}

	public List<CabBookingResponse> getCabBookingDetailsByCabRegistrationId(Long cabRegistrationId) {
		List<CabBookingResponse> bookingList = new ArrayList<>();
		cabBookingDetailsRepository.findByCabRegistrationId(cabRegistrationId).stream().forEach(cabBookingDetails -> {
			CabBookingResponse cabBookingResponse = new CabBookingResponse();
			cabBookingResponse.setUserId(cabBookingDetails.getUserId());
			cabBookingResponse.setBookingId(cabBookingDetails.getBookingId());
			cabBookingResponse.setCabRegistrationId(cabBookingDetails.getCabRegistrationId());
			cabBookingResponse.setPaymentStatus(cabBookingDetails.getPaymentStatus());
			cabBookingResponse.setBookingStatus(cabBookingDetails.getBookingStatus());
			cabBookingResponse.setBookingStatusUpdatedBy(cabBookingDetails.getBookingStatusUpdatedBy());
			cabBookingResponse.setPickupLocation(cabBookingDetails.getPickupLocation());
			cabBookingResponse.setDropLocation(cabBookingDetails.getDropLocation());
			cabBookingResponse.setPickupDateTime(cabBookingDetails.getPickupDateTime());
			cabBookingResponse.setDropDateTime(cabBookingDetails.getDropDateTime());
			cabBookingResponse.setFare(cabBookingDetails.getFare());
			cabBookingResponse.setPromoDiscount(cabBookingDetails.getPromoDiscount());
			cabBookingResponse.setTokenAmount(cabBookingDetails.getTokenAmount());
			cabBookingResponse.setBalanceAmount(cabBookingDetails.getBalanceAmount());

			cabRegDetailsRepo.findByRegistrationId(cabBookingDetails.getCabRegistrationId())
					.ifPresent(cabRegDetails -> {
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
						cabBookingResponse.setAC(cabRegDetails.getCab().isAC());
						cabBookingResponse.setFuelType(cabRegDetails.getCab().getFluelType());
					});
			bookingList.add(cabBookingResponse);
		});
		return bookingList;
	}

	public List<CabBookingDetails> getBookingsByUserIdAndStatus(Long userId, String bookingStatus) {
		return cabBookingDetailsRepository.findByUserIdAndBookingStatus(userId, bookingStatus);
	}

	public CabBookingDetails updateBookingStatusByRoleAndBookingId(UpdateBookingStatusRequest request)
			throws Exception {
		// Fetch the booking details by cabRegistrationId
		CabBookingDetails bookingDetails = cabBookingDetailsRepository.findByBookingId(request.getBookingId())
				.orElseThrow(() -> new Exception("Booking not found for cabRegistrationId: " + request.getBookingId()));

		if (request.getCabRegistrationId() != null && request.getCabRegistrationId() != 0
				&& ("SUPERADMIN".equalsIgnoreCase(request.getRole()) || "ADMIN".equalsIgnoreCase(request.getRole()))) {
			bookingDetails.setCabRegistrationId(request.getCabRegistrationId());
		}
		if (request.getBookingStatus() != null && !request.getBookingStatus().isEmpty()) {
			bookingDetails.setBookingStatus(request.getBookingStatus());
		}
		if (request.getPaymentStatus() != null && !request.getPaymentStatus().isEmpty()) {
			bookingDetails.setPaymentStatus(request.getPaymentStatus());
		}
		bookingDetails.setBookingStatusUpdatedBy(request.getRole());
		return cabBookingDetailsRepository.save(bookingDetails);
	}

	public List<CabBookingResponse> getCabBookingRequestForVendorId(Long vendorId) {
		List<CabBookingResponse> bookingList = new ArrayList<>();
		cabRegDetailsRepo.findRegistrationIdByUserId(vendorId).stream().forEach(cabRegistrationId -> {

			cabBookingDetailsRepository.findPendingBookingByCabRegistrationId(cabRegistrationId).stream()
					.forEach(cabBookingDetails -> {
						CabBookingResponse cabBookingResponse = new CabBookingResponse();
						cabBookingResponse.setUserId(cabBookingDetails.getUserId());
						cabBookingResponse.setBookingId(cabBookingDetails.getBookingId());
						cabBookingResponse.setCabRegistrationId(cabBookingDetails.getCabRegistrationId());
						cabBookingResponse.setPaymentStatus(cabBookingDetails.getPaymentStatus());
						cabBookingResponse.setBookingStatus(cabBookingDetails.getBookingStatus());
						cabBookingResponse.setBookingStatusUpdatedBy(cabBookingDetails.getBookingStatusUpdatedBy());
						cabBookingResponse.setPickupLocation(cabBookingDetails.getPickupLocation());
						cabBookingResponse.setDropLocation(cabBookingDetails.getDropLocation());
						cabBookingResponse.setPickupDateTime(cabBookingDetails.getPickupDateTime());
						cabBookingResponse.setDropDateTime(cabBookingDetails.getDropDateTime());
						cabBookingResponse.setFare(cabBookingDetails.getFare());
						cabBookingResponse.setPromoDiscount(cabBookingDetails.getPromoDiscount());
						cabBookingResponse.setTokenAmount(cabBookingDetails.getTokenAmount());
						cabBookingResponse.setBalanceAmount(cabBookingDetails.getBalanceAmount());

						cabRegDetailsRepo.findByRegistrationId(cabBookingDetails.getCabRegistrationId())
								.ifPresent(cabRegDetails -> {
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
									cabBookingResponse.setAC(cabRegDetails.getCab().isAC());
									cabBookingResponse.setFuelType(cabRegDetails.getCab().getFluelType());
								});
						bookingList.add(cabBookingResponse);
					});
		});
		return bookingList;
	}

	public List<CabBookingResponse> getCabBookingHistoryForVendorId(Long vendorId) {
		List<CabBookingResponse> bookingList = new ArrayList<>();
		cabRegDetailsRepo.findRegistrationIdByUserId(vendorId).stream().forEach(cabRegistrationId -> {

			cabBookingDetailsRepository.findAllBookingsByCabRegistrationId(cabRegistrationId).stream()
					.forEach(cabBookingDetails -> {
						CabBookingResponse cabBookingResponse = new CabBookingResponse();
						cabBookingResponse.setUserId(cabBookingDetails.getUserId());
						cabBookingResponse.setBookingId(cabBookingDetails.getBookingId());
						cabBookingResponse.setCabRegistrationId(cabBookingDetails.getCabRegistrationId());
						cabBookingResponse.setPaymentStatus(cabBookingDetails.getPaymentStatus());
						cabBookingResponse.setBookingStatus(cabBookingDetails.getBookingStatus());
						cabBookingResponse.setBookingStatusUpdatedBy(cabBookingDetails.getBookingStatusUpdatedBy());
						cabBookingResponse.setPickupLocation(cabBookingDetails.getPickupLocation());
						cabBookingResponse.setDropLocation(cabBookingDetails.getDropLocation());
						cabBookingResponse.setPickupDateTime(cabBookingDetails.getPickupDateTime());
						cabBookingResponse.setDropDateTime(cabBookingDetails.getDropDateTime());
						cabBookingResponse.setFare(cabBookingDetails.getFare());
						cabBookingResponse.setPromoDiscount(cabBookingDetails.getPromoDiscount());
						cabBookingResponse.setTokenAmount(cabBookingDetails.getTokenAmount());
						cabBookingResponse.setBalanceAmount(cabBookingDetails.getBalanceAmount());

						cabRegDetailsRepo.findByRegistrationId(cabBookingDetails.getCabRegistrationId())
								.ifPresent(cabRegDetails -> {
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
									cabBookingResponse.setAC(cabRegDetails.getCab().isAC());
									cabBookingResponse.setFuelType(cabRegDetails.getCab().getFluelType());
								});
						bookingList.add(cabBookingResponse);
					});
		});
		return bookingList;
	}
}
