package com.yatranow.CarAndBookingService.service;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yatranow.CarAndBookingService.repository.CabRegistrationDetailsRepository;
import com.yatranow.CarAndBookingService.request.CabSearchRequest;
import com.yatranow.CarAndBookingService.response.CabSearchResponse;

@Service
public class CabService {

	Logger logger = Logger.getLogger(CabService.class.getName());

	@Autowired
	private CabRegistrationDetailsRepository cabDetailsRepository;

	@Autowired
	private RestTemplate template;
	
	@Value("${google.maps.api.key}")
	private String apiKey;

	public List<CabSearchResponse> searchCabs(CabSearchRequest cabSearchRequest) {
		List<CabSearchResponse> cabSearchResponses = cabDetailsRepository
				.findAvailableCabs(cabSearchRequest.getPickupDateTime(), cabSearchRequest.getDropDateTime()).stream().map(cab -> {
					CabSearchResponse cabSearchResponse = new CabSearchResponse();
					double distanceToPickup = 0;
					try {
						distanceToPickup = findDistance(cab.getAddress(), cabSearchRequest.getPickupLocation());
					} catch (JsonProcessingException e) {
						throw new RuntimeException(e);
					}

					if (distanceToPickup <= cabSearchRequest.getRadius()) {
						double distanceToDrop = 0;
						try {
							distanceToDrop = findDistance(cabSearchRequest.getPickupLocation(),
									cabSearchRequest.getDropLocation());
						} catch (JsonProcessingException e) {
							throw new RuntimeException(e);
						}

						System.out.println("distanceToDrop :: " + distanceToDrop);
						double totalDistance = distanceToPickup + distanceToDrop;
						double fare = calculateFareForRoundTrip(totalDistance, cab.getPerKmRate(), cab.getBaseFare());
						cabSearchResponse.setPickupLocation(cabSearchRequest.getPickupLocation());
						cabSearchResponse.setDropLocation(cabSearchRequest.getDropLocation());
						cabSearchResponse.setPickupDateTime(cabSearchRequest.getPickupDateTime());
						cabSearchResponse.setDropDateTime(cabSearchRequest.getDropDateTime());
						cabSearchResponse.setCabRegistrationId(cab.getRegistrationId());
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
						cabSearchResponse.setAC(cab.getCab().isAC());
						cabSearchResponse.setFuelType(cab.getCab().getFluelType());
						cabSearchResponse.setCabImageUrl(cab.getCab().getCabImageUrl());

						return cabSearchResponse;
					}
					return null;
				}).filter(response -> response != null).toList();

		// Remove duplicates based on cab registration ID
		Map<String, Long> registrationIdCount = cabSearchResponses.stream().collect(Collectors
				.groupingBy(response -> String.valueOf(response.getCabRegistrationId()), Collectors.counting()));

		return cabSearchResponses.stream()
				.filter(response -> registrationIdCount.get(String.valueOf(response.getCabRegistrationId())) == 1)
				.toList();
	}

	private double calculateFareForRoundTrip(double totalDistance, int perKmRate, double baseFare) {
		double fare = baseFare + (totalDistance * 2 * perKmRate);
		return fare;
	}

	private double findDistance(String fromLocation, String toLocation) throws JsonProcessingException {
		logger.info("In CabService.findDistance(-) : Finding distance using Google Maps API");
		ResponseEntity<String> response = template.getForEntity(
				"https://maps.googleapis.com/maps/api/distancematrix/json?destinations=" + toLocation + "&origins="
						+ fromLocation + "&units=imperial&key="+apiKey,
				String.class);

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(response.getBody());
		double distance = jsonNode.get("rows").get(0).get("elements").get(0).get("distance").get("value").asDouble()
				/ 1000;
		logger.info("In CabService.findDistance(-) : Distance from Google Maps API is " + distance);
		return distance;

	}
}
