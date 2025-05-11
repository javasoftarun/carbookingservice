package com.yatranow.CarAndBookingService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;

import com.yatranow.CarAndBookingService.entity.CabRegistrationDetails;
import com.yatranow.CarAndBookingService.repository.CabRegistrationDetailsRepository;
import com.yatranow.CarAndBookingService.request.CabSearchRequest;
import com.yatranow.CarAndBookingService.response.ApiResponse;
import com.yatranow.CarAndBookingService.response.CabSearchResponse;
import com.yatranow.CarAndBookingService.service.CabService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/cab/registration")
@Tag(name = "Cab Registration Details API", description = "API for managing cab registration details")
public class CabDetailsController {

    @Autowired
    private CabRegistrationDetailsRepository cabRegistrationDetailsRepository;
    
    @Autowired
    private CabService cabService;

    @PostMapping("/register")
    @Operation(summary = "Register a new cab", description = "Registers a new cab with the provided details.")
    public ResponseEntity<ApiResponse> registerCabDetails(
            @Parameter(description = "Cab registration details to be saved", required = true) @RequestBody CabRegistrationDetails cabRegistrationDetails) {
        try {
            CabRegistrationDetails savedDetails = cabRegistrationDetailsRepository.save(cabRegistrationDetails);
            return ResponseEntity.ok(new ApiResponse("success", new Object[]{savedDetails}, 200));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(e.getMessage(), null, 500));
        }
    }

    @GetMapping("/get/all")
    @Operation(summary = "Get all cab registration details", description = "Retrieves all registered cab details.")
    public ResponseEntity<ApiResponse> getAllCabRegistrationDetails() {
        try {
            List<CabRegistrationDetails> detailsList = cabRegistrationDetailsRepository.findAll();
            return ResponseEntity.ok(new ApiResponse("success", detailsList.toArray(), 200));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(e.getMessage(), null, 500));
        }
    }

    @GetMapping("/get/{registrationId}")
    @Operation(summary = "Get cab registration details by ID", description = "Retrieves cab details by the given registration ID.")
    public ResponseEntity<ApiResponse> getCabRegistrationDetailsById(
            @Parameter(description = "Registration ID of the cab", required = true) @PathVariable("registrationId") Long registrationId) {
        try {
            CabRegistrationDetails response = cabRegistrationDetailsRepository.findByRegistrationId(registrationId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cab details not found"));
            return new ResponseEntity<>(new ApiResponse("success", new Object[]{response}, 200), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(e.getMessage(), null, 500));
        }
    }

    @PutMapping("/update/{registrationId}")
    @Operation(summary = "Update cab registration details", description = "Updates the cab details for the given registration ID.")
    public ResponseEntity<ApiResponse> updateCabRegistrationDetails(
            @Parameter(description = "Registration ID of the cab to be updated", required = true) @PathVariable("registrationId") Long registrationId,
            @Parameter(description = "Updated cab registration details", required = true) @RequestBody CabRegistrationDetails updatedDetails) {
        try {
            return cabRegistrationDetailsRepository.findById(registrationId)
                    .map(existingDetails -> {
                        updatedDetails.setRegistrationId(existingDetails.getRegistrationId());
                        CabRegistrationDetails savedDetails = cabRegistrationDetailsRepository.save(updatedDetails);
                        return new ResponseEntity<>(new ApiResponse("success", new Object[]{savedDetails}, 200), HttpStatus.OK);
                    })
                    .orElse(new ResponseEntity<>(new ApiResponse("Cab registration details not found", null, 404), HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(e.getMessage(), null, 500));
        }
    }

    @DeleteMapping("/delete/{registrationId}")
    @Operation(summary = "Delete cab registration details", description = "Deletes the cab details for the given registration ID.")
    public ResponseEntity<ApiResponse> deleteCabRegistrationDetails(
            @Parameter(description = "Registration ID of the cab to be deleted", required = true) @PathVariable("registrationId") Long registrationId) {
        try {
            return cabRegistrationDetailsRepository.findByRegistrationId(registrationId)
                    .map(existingDetails -> {
                        cabRegistrationDetailsRepository.delete(existingDetails);
                        return new ResponseEntity<>(new ApiResponse("success", null, 200), HttpStatus.OK);
                    })
                    .orElse(new ResponseEntity<>(new ApiResponse("Cab registration details not found", null, 404), HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(e.getMessage(), null, 500));
        }
    }
    
    @PostMapping("/search")
    public ResponseEntity<ApiResponse> searchCabs(@RequestBody CabSearchRequest cabSearchRequest) {
    	try {
    		List<CabSearchResponse> cabs = cabService.searchCabs(cabSearchRequest);
            return ResponseEntity.ok(new ApiResponse("success", cabs.toArray(), 200));
    	} catch (Exception e) {
			return ResponseEntity.status(500).body(new ApiResponse(e.getMessage(), null, 500));
		}  
    }
    
    
}
