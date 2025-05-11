package com.yatranow.CarAndBookingService.request;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Component
public class CabSearchRequest {

	private String pickupLocation;
	private String dropLocation;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime pickupDateTime;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime dropDateTime;
	private int radius;
	public String getPickupLocation() {
		return pickupLocation;
	}
	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}
	public String getDropLocation() {
		return dropLocation;
	}
	public void setDropLocation(String dropLocation) {
		this.dropLocation = dropLocation;
	}
	public LocalDateTime getPickupDateTime() {
		return pickupDateTime;
	}
	public void setPickupDateTime(LocalDateTime pickupDateTime) {
		this.pickupDateTime = pickupDateTime;
	}
	public LocalDateTime getDropDateTime() {
		return dropDateTime;
	}
	public void setDropDateTime(LocalDateTime dropDateTime) {
		this.dropDateTime = dropDateTime;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
}
