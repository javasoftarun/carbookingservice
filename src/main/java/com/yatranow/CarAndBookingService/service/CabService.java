package com.yatranow.CarAndBookingService.service;

import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yatranow.CarAndBookingService.repository.CabRegistrationDetailsRepository;
import com.yatranow.CarAndBookingService.request.CabSearchRequest;
import com.yatranow.CarAndBookingService.response.CabSearchResponse;
import org.springframework.web.client.RestTemplate;

@Service
public class CabService {

	Logger logger = Logger.getLogger(CabService.class.getName());

    @Autowired
    private CabRegistrationDetailsRepository cabDetailsRepository;

    @Autowired
    private RestTemplate template;

    public List<CabSearchResponse> searchCabs(CabSearchRequest cabSearchRequest) {
        return cabDetailsRepository.findAvailableCabs(cabSearchRequest.getPickupDateTime()).stream()
                .map(cab -> {
                    CabSearchResponse cabSearchResponse = new CabSearchResponse();
//                    double pickupLat = Double.parseDouble(cabSearchRequest.getPickupLat());
//                    double pickupLng = Double.parseDouble(cabSearchRequest.getPickuplng());
//                    double dropLat = Double.parseDouble(cabSearchRequest.getDropLat());
//                    double dropLng = Double.parseDouble(cabSearchRequest.getDropLng());
//                    double cabLat = Double.parseDouble(cab.getLatitude());
//                    double cabLng = Double.parseDouble(cab.getLongitude());

                    //double distanceToPickup = calculateDistance(pickupLat, pickupLng, cabLat, cabLng);
					double distanceToPickup = 0;
					try {
						distanceToPickup = findDistance(cab.getAddress(), cabSearchRequest.getPickupLocation());
					} catch (JsonProcessingException e) {
						throw new RuntimeException(e);
					}


					if (distanceToPickup <= cabSearchRequest.getRadius()) {
                        //	double distanceToDrop = calculateDistance(dropLat, dropLng, pickupLat, pickupLng);
						double distanceToDrop = 0;
						try {
							distanceToDrop = findDistance(cabSearchRequest.getPickupLocation(), cabSearchRequest.getDropLocation());
						} catch (JsonProcessingException e) {
							throw new RuntimeException(e);
						}

						System.out.println("distanceToDrop :: " + distanceToDrop);
                        double totalDistance = distanceToPickup + distanceToDrop;
                        double fare = calculateFareForRoundTrip(totalDistance, cab.getPerKmRate(), cab.getBaseFare());
                        cabSearchResponse.setRegistrationId(cab.getRegistrationId());
                        cabSearchResponse.setBaseFare(cab.getBaseFare());
                        cabSearchResponse.setPerKmRate(cab.getPerKmRate());
                        cabSearchResponse.setFare(fare);
                        cabSearchResponse.setTotalDistance(totalDistance * 2);
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

//    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
//        // Haversine formula to calculate distance between two points
//        final int R = 6371; // Radius of the Earth in km
//        double latDistance = Math.toRadians(lat2 - lat1);
//        double lonDistance = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
//                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
//                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        return R * c; // Distance in km
//    }

    private double findDistance(String fromLocation, String toLocation) throws JsonProcessingException {
		logger.info("In CabService.findDistance(-) : Finding distance using Google Maps API");
        ResponseEntity<String> response = template
                .getForEntity("https://maps.googleapis.com/maps/api/distancematrix/json?destinations="
                        + toLocation + "&origins=" + fromLocation
                        + "&units=imperial&key=AIzaSyBPNLDkybaqr6BXXpteScrvStXRPrwHD6E", String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        double distance = jsonNode.get("rows").get(0).get("elements").get(0).get("distance").get("value").asDouble()/1000;
		logger.info("In CabService.findDistance(-) : Distance from Google Maps API is " + distance);
        return distance;

    }
}
