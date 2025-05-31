package com.yatranow.CarAndBookingService.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CabBookingResponse {

	// Booking details
	private Long bookingId;
	private Long userId;
	private Long cabRegistrationId;
	private String pickupLocation;
	private String dropLocation;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime pickupDateTime;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime dropDateTime;
	private double fare;
	private double promoDiscount;
	private double tokenAmount;
	private double balanceAmount;
	private String bookingStatus;

	// Payment details
	private Long paymentId;

	// Cab registration details
	private String driverName;
	private String driverContact;
	private String driverLicense;
	private String address;

	// Cab details
	private Long cabId;
	private String cabName;
	private String cabType;
	private String cabNumber;
	private String cabModel;
	private String cabColor;
	private String cabInsurance;
	private String cabCapacity;
	private String cabImageUrl;
	private String cabCity;
	private String cabState;
	private boolean isAC;
	private String fuelType;
	private String bookingStatusUpdatedBy;
	private String paymentStatus;

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

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

	public double getFare() {
		return Math.round(fare * 100.0) / 100.0;
	}

	public void setFare(double fare) {
		this.fare = Math.round(fare * 100.0) / 100.0;
	}

	public double getPromoDiscount() {
		return Math.round(promoDiscount * 100.0) / 100.0;
	}

	public void setPromoDiscount(double promoDiscount) {
		this.promoDiscount = Math.round(promoDiscount * 100.0) / 100.0;
	}

	public double getTokenAmount() {
		return Math.round(tokenAmount * 100.0) / 100.0;
	}

	public void setTokenAmount(double tokenAmount) {
		this.tokenAmount = Math.round(tokenAmount * 100.0) / 100.0;
	}

	public double getBalanceAmount() {
		return Math.round(balanceAmount * 100.0) / 100.0;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = Math.round(balanceAmount * 100.0) / 100.0;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverContact() {
		return driverContact;
	}

	public void setDriverContact(String driverContact) {
		this.driverContact = driverContact;
	}

	public String getDriverLicense() {
		return driverLicense;
	}

	public void setDriverLicense(String driverLicense) {
		this.driverLicense = driverLicense;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getCabId() {
		return cabId;
	}

	public void setCabId(Long cabId) {
		this.cabId = cabId;
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

	public String getCabNumber() {
		return cabNumber;
	}

	public void setCabNumber(String cabNumber) {
		this.cabNumber = cabNumber;
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

	public String getCabCity() {
		return cabCity;
	}

	public void setCabCity(String cabCity) {
		this.cabCity = cabCity;
	}

	public String getCabState() {
		return cabState;
	}

	public void setCabState(String cabState) {
		this.cabState = cabState;
	}

	public String getBookingStatusUpdatedBy() {
		return bookingStatusUpdatedBy;
	}

	public void setBookingStatusUpdatedBy(String bookingStatusUpdatedBy) {
		this.bookingStatusUpdatedBy = bookingStatusUpdatedBy;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
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
