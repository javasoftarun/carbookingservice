package com.yatranow.CarAndBookingService.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CabSearchResponse {

	private Long cabRegistrationId;
	private String pickupLocation;
	private String dropLocation;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime pickupDateTime;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime dropDateTime;
	private int perKmRate;
	private double baseFare;
	private double totalDistance;
	private double fare;
	private String cabName;
	private String cabType;
	private String cabModel;
	private String cabColor;
	private String cabInsurance;
	private String cabCapacity;
	private String cabImageUrl;
	private boolean isAC;
	private String fuelType;

	public Long getCabRegistrationId() {
		return cabRegistrationId;
	}

	public void setCabRegistrationId(Long cabRegistrationId) {
		this.cabRegistrationId = cabRegistrationId;
	}

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

	public int getPerKmRate() {
		return perKmRate;
	}

	public void setPerKmRate(int perKmRate) {
		this.perKmRate = perKmRate;
	}

	public double getBaseFare() {
		return Math.round(baseFare * 100.0) / 100.0;
	}

	public void setBaseFare(double baseFare) {
		this.baseFare = Math.round(baseFare * 100.0) / 100.0;
	}

	public double getTotalDistance() {
		return Math.round(totalDistance * 100.0) / 100.0;
	}

	public void setTotalDistance(double totalDistance) {
		this.totalDistance = Math.round(totalDistance * 100.0) / 100.0;
	}

	public double getFare() {
		return Math.round(fare * 100.0) / 100.0;
	}

	public void setFare(double fare) {
		this.fare = Math.round(fare * 100.0) / 100.0;
	}

	public String getCabName() {
		return cabName;
	}

	public void setCabName(String cabName) {
		this.cabName = cabName;
	}

	public String getCabType() {
		return cabType;
	}

	public void setCabType(String cabType) {
		this.cabType = cabType;
	}

	public String getCabModel() {
		return cabModel;
	}

	public void setCabModel(String cabModel) {
		this.cabModel = cabModel;
	}

	public String getCabColor() {
		return cabColor;
	}

	public void setCabColor(String cabColor) {
		this.cabColor = cabColor;
	}

	public String getCabInsurance() {
		return cabInsurance;
	}

	public void setCabInsurance(String cabInsurance) {
		this.cabInsurance = cabInsurance;
	}

	public String getCabCapacity() {
		return cabCapacity;
	}

	public void setCabCapacity(String cabCapacity) {
		this.cabCapacity = cabCapacity;
	}

	public String getCabImageUrl() {
		return cabImageUrl;
	}

	public void setCabImageUrl(String cabImageUrl) {
		this.cabImageUrl = cabImageUrl;
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

	public boolean isAC() {
		return isAC;
	}
	public void setAC(boolean isAC) {
		this.isAC = isAC;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

}
