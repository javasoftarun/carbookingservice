package com.yatranow.CarAndBookingService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yatranow.CarAndBookingService.repository.CabRegistrationDetailsRepository;
import com.yatranow.CarAndBookingService.request.CabSearchRequest;
import com.yatranow.CarAndBookingService.response.CabSearchResponse;

@Service
public class CabService {

    @Autowired
    private CabRegistrationDetailsRepository cabDetailsRepository;

    public List<CabSearchResponse> searchCabs(CabSearchRequest cabSearchRequest) {
        return cabDetailsRepository.findAvailableCabs(cabSearchRequest.getPickupDateTime()).stream()
				.map(cab -> {
					CabSearchResponse cabSearchResponse = new CabSearchResponse();
					double pickupLat = Double.parseDouble(cabSearchRequest.getPickupLat());
					double pickupLng = Double.parseDouble(cabSearchRequest.getPickuplng());
					double dropLat = Double.parseDouble(cabSearchRequest.getDropLat());
					double dropLng = Double.parseDouble(cabSearchRequest.getDropLng());
					double cabLat = Double.parseDouble(cab.getLatitude());
					double cabLng = Double.parseDouble(cab.getLongitude());
					
					double distanceToPickup = calculateDistance(pickupLat, pickupLng, cabLat, cabLng);
					System.out.println("distanceToPickup :: "+distanceToPickup);
					
					if (distanceToPickup <= cabSearchRequest.getRadius()) {
						double distanceToDrop = calculateDistance(dropLat, dropLng, pickupLat, pickupLng);
						System.out.println("distanceToDrop :: "+distanceToDrop);
						double totalDistance = distanceToPickup + distanceToDrop;
						double fare = calculateFareForRoundTrip(totalDistance, cab.getPerKmRate(), cab.getBaseFare());
						cabSearchResponse.setRegistrationId(cab.getRegistrationId());
						cabSearchResponse.setBaseFare(cab.getBaseFare());
						cabSearchResponse.setPerKmRate(cab.getPerKmRate());
						cabSearchResponse.setFare(fare);
						cabSearchResponse.setTotalDistance(totalDistance*2);
						cabSearchResponse.setCabName(cab.getCab().getCabName());
						cabSearchResponse.setCabType(cab.getCab().getCabType());
						cabSearchResponse.setCabModel(cab.getCab().getCabModel());
						cabSearchResponse.setCabColor(cab.getCab().getCabColor());
						cabSearchResponse.setCabInsurance(cab.getCab().getCabInsurance());
						cabSearchResponse.setCabCapacity(cab.getCab().getCabCapacity());
						cabSearchResponse.setCabImageUrl(cab.getCab().getCabImageUrl());
						
						return cabSearchResponse;
					}
					return null;
				})
				.toList();
    }
    
    private double calculateFareForRoundTrip(double totalDistance, int perKmRate, double baseFare) {
		double fare = baseFare + (totalDistance * 2 * perKmRate); 
		return fare;
	}

	private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Haversine formula to calculate distance between two points
        final int R = 6371; // Radius of the Earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in km
    }
}
