package com.yatranow.CarAndBookingService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yatranow.CarAndBookingService.entity.CabBookingDetails;
import com.yatranow.CarAndBookingService.request.CabAvailabilityRequest;
import com.yatranow.CarAndBookingService.request.UpdateBookingStatusRequest;
import com.yatranow.CarAndBookingService.response.ApiResponse;
import com.yatranow.CarAndBookingService.response.CabBookingResponse;
import com.yatranow.CarAndBookingService.service.CabBookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/cab/booking")
@Tag(name = "Cab Booking Controller", description = "Endpoints for managing cab bookings")
public class CabBookingController {

    @Autowired
    private CabBookingService cabBookingDetailsService;

    @Operation(summary = "Create a new cab booking", description = "Creates a new cab booking and returns the created booking details.")
    @PostMapping("/startbooking")
    public ResponseEntity<ApiResponse> startCabBooking(
            @RequestBody CabBookingDetails cabBookingDetails) {
        try {
            CabBookingResponse bookingResponse = cabBookingDetailsService.startCabBooking(cabBookingDetails);
            return ResponseEntity.ok(new ApiResponse("success", new Object[] { bookingResponse }, 200));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("Error creating booking: " + e.getMessage(), null, 400));
        }
    }

    @Operation(summary = "Get booking details with Driver details by booking ID", description = "Fetches a cab booking by its booking ID.")
    @GetMapping("/find/{bookingId}")
    public ResponseEntity<ApiResponse> getCabBookingById(
            @Parameter(description = "ID of the cab booking to fetch") @PathVariable("bookingId") Long bookingId) {
        try {
            CabBookingResponse bookingResponse = cabBookingDetailsService.getCabBookingWithDriverDetailsById(bookingId);
            return ResponseEntity.ok(new ApiResponse("success", new Object[] { bookingResponse }, 200));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("Error fetching booking: " + e.getMessage(), null, 400));
        }
    }

    @Operation(summary = "Get all cab bookings", description = "Fetches all cab bookings.")
    @GetMapping("/find/all")
    public ResponseEntity<ApiResponse> getAllCabBookings() {
        try {
            List<CabBookingDetails> cabBookings = cabBookingDetailsService.getAllCabBookings();
            return ResponseEntity.ok(new ApiResponse("success", cabBookings.toArray(), 200));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("Error fetching bookings: " + e.getMessage(), null, 400));
        }
    }

    @Operation(summary = "Update cab booking", description = "Updates an existing cab booking by its ID.")
    @PutMapping("/update/{bookingId}")
    public ResponseEntity<ApiResponse> updateCabBooking(
            @Parameter(description = "ID of the cab booking to update") @PathVariable("bookingId") Long bookingId,
            @RequestBody CabBookingDetails cabBookingDetails) {
        try {
            CabBookingDetails updatedBooking = cabBookingDetailsService.updateCabBooking(bookingId, cabBookingDetails);
            return ResponseEntity.ok(new ApiResponse("success", new Object[] { updatedBooking }, 200));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("Error updating booking: " + e.getMessage(), null, 400));
        }
    }

    @Operation(summary = "Delete cab booking", description = "Deletes a cab booking by its ID.")
    @DeleteMapping("/delete/{bookingId}")
    public ResponseEntity<ApiResponse> deleteCabBooking(
            @Parameter(description = "ID of the cab booking to delete") @PathVariable("bookingId") Long bookingId) {
        try {
            cabBookingDetailsService.deleteCabBooking(bookingId);
            return ResponseEntity.ok(new ApiResponse("success", null, 200));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("Error deleting booking: " + e.getMessage(), null, 400));
        }
    }
    
    @Operation(summary = "Check cab availability", description = "Checks if a cab is available for the given time.")
    @PostMapping("/availablecab-by-registrationId")
    public ResponseEntity<ApiResponse> checkCabAvailablity(@RequestBody CabAvailabilityRequest request) {
		try {
			boolean isAvailable = cabBookingDetailsService.checkCabAvailability(request);
			if (!isAvailable) {
				return ResponseEntity.status(400)
						.body(new ApiResponse("Cab is not available for the selected date", null, 400));
			}
			return ResponseEntity.ok(new ApiResponse("success", new Object[] { isAvailable }, 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new ApiResponse("Error checking availability: " + e.getMessage(), null, 400));
		}
	}

	@Operation(summary = "Get cab booking details by user ID", description = "Fetches a cab booking by its ID.")
	@GetMapping("/get-by-userid/{userId}")
	public ResponseEntity<ApiResponse> getCabBookingDetailsByUserId(
			@Parameter(description = "userID of the cab booking to fetch") @PathVariable("userId") Long userId) {
		try {
			List<CabBookingResponse> bookingResponse = cabBookingDetailsService.getCabBookingDetailsByUserId(userId);
			return ResponseEntity.ok(new ApiResponse("success", new Object[] { bookingResponse }, 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new ApiResponse("Error fetching booking: " + e.getMessage(), null, 400));
		}
	}
	
	@Operation(summary = "Get cab booking details by cab registration ID", description = "Fetches a cab booking by its cab registration ID.")
	@GetMapping("/get-by-cabregistrationid/{cabRegistrationId}")
	public ResponseEntity<ApiResponse> getCabBookingDetailsByCabRegistrationId(
			@Parameter(description = "cabRegistrationId of the cab booking to fetch") @PathVariable("cabRegistrationId") Long cabRegistrationId) {
		try {
			List<CabBookingResponse> bookingResponse = cabBookingDetailsService.getCabBookingDetailsByCabRegistrationId(cabRegistrationId);
			return ResponseEntity.ok(new ApiResponse("success", new Object[] { bookingResponse }, 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new ApiResponse("Error fetching booking: " + e.getMessage(), null, 400));
		}
	}
	
	@GetMapping("/get-by-userid-and-status/{userId}/{bookingStatus}")
    public ResponseEntity<ApiResponse> getBookingsByUserIdAndStatus(
            @PathVariable("userId") Long userId,
            @PathVariable("bookingStatus") String bookingStatus) {
        try {
            List<CabBookingDetails> bookingList = cabBookingDetailsService.getBookingsByUserIdAndStatus(userId, bookingStatus);
            return ResponseEntity.ok(new ApiResponse("success", bookingList.toArray(), 200));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("Error fetching bookings: " + e.getMessage(), null, 400));
        }
    }
	
	@PutMapping("/update-booking-status")
    public ResponseEntity<ApiResponse> updateBookingStatusByRoleAndBookingId(@RequestBody UpdateBookingStatusRequest request) {
        try {
            CabBookingDetails updatedBooking = cabBookingDetailsService.updateBookingStatusByRoleAndBookingId(request);
            return ResponseEntity.ok(new ApiResponse("success", new Object[] { updatedBooking }, 200));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("Error updating booking status: " + e.getMessage(), null, 400));
        }
    }
	
	@Operation(summary = "Get cab booking details by vendor ID", description = "Fetches a cab booking by Vendor ID.")
	@GetMapping("/find-pending-request/{vendorId}")
	public ResponseEntity<ApiResponse> getCabBookingRequestForVendorId(
			@Parameter(description = "vendorId of the cab booking to fetch") @PathVariable("vendorId") Long vendorId) {
		try {
			List<CabBookingResponse> bookingResponse = cabBookingDetailsService.getCabBookingRequestForVendorId(vendorId);
			return ResponseEntity.ok(new ApiResponse("success", new Object[] { bookingResponse }, 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new ApiResponse("Error fetching booking: " + e.getMessage(), null, 400));
		}
	}
	
	@Operation(summary = "Get cab booking history by user ID", description = "Fetches a cab booking history by its ID.")
	@GetMapping("/find-all-bookings/{vendorId}")
	public ResponseEntity<ApiResponse> getCabBookingHistoryForVendorId(
			@Parameter(description = "vendorId of the cab booking to fetch history") @PathVariable("vendorId") Long vendorId) {
		try {
			List<CabBookingResponse> bookingResponse = cabBookingDetailsService.getCabBookingHistoryForVendorId(vendorId);
			return ResponseEntity.ok(new ApiResponse("success", new Object[] { bookingResponse }, 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new ApiResponse("Error fetching booking history: " + e.getMessage(), null, 400));
		}
	}
    
}
