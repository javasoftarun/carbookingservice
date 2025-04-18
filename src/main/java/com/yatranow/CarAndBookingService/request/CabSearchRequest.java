package com.yatranow.CarAndBookingService.request;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Component
public class CabSearchRequest {

	private String pickupLocation;
	private String dropLocation;
	private String pickupLat;
	private String pickuplng;
	private String dropLat;
	private String dropLng;
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
	public String getPickupLat() {
		return pickupLat;
	}
	public void setPickupLat(String pickupLat) {
		this.pickupLat = pickupLat;
	}
	public String getPickuplng() {
		return pickuplng;
	}
	public void setPickuplng(String pickuplng) {
		this.pickuplng = pickuplng;
	}
	public String getDropLat() {
		return dropLat;
	}
	public void setDropLat(String dropLat) {
		this.dropLat = dropLat;
	}
	public String getDropLng() {
		return dropLng;
	}
	public void setDropLng(String dropLng) {
		this.dropLng = dropLng;
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
