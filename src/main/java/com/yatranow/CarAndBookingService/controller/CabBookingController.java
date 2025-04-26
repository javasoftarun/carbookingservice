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
import com.yatranow.CarAndBookingService.response.ApiResponse;
import com.yatranow.CarAndBookingService.service.CabBookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
            CabBookingDetails createdBooking = cabBookingDetailsService.startCabBooking(cabBookingDetails);
            return ResponseEntity.ok(new ApiResponse("success", new Object[] { createdBooking }, 200));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("Error creating booking: " + e.getMessage(), null, 400));
        }
    }

    @Operation(summary = "Get cab booking by ID", description = "Fetches a cab booking by its ID.")
    @GetMapping("/find/{bookingId}")
    public ResponseEntity<ApiResponse> getCabBookingById(
            @Parameter(description = "ID of the cab booking to fetch") @PathVariable("bookingId") Long bookingId) {
        try {
            CabBookingDetails cabBookingDetails = cabBookingDetailsService.getCabBookingById(bookingId);
            return ResponseEntity.ok(new ApiResponse("success", new Object[] { cabBookingDetails }, 200));
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
}
