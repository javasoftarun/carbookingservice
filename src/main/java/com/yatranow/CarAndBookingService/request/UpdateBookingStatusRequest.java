package com.yatranow.CarAndBookingService.request;

public class UpdateBookingStatusRequest {

	private Long bookingId;
	private Long cabRegistrationId;
	private String bookingStatus;
	private String paymentStatus;
	private String role;
	
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
	public String getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}
