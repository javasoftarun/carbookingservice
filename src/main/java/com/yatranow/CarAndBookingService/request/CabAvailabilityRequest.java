package com.yatranow.CarAndBookingService.request;

import java.time.LocalDateTime;

public class CabAvailabilityRequest {

	private Long cabRegistrationId;
	private LocalDateTime pickupDateTime;
	private LocalDateTime dropDateTime;
	public Long getCabRegistrationId() {
		return cabRegistrationId;
	}
	public void setCabRegistrationId(Long cabRegistrationId) {
		this.cabRegistrationId = cabRegistrationId;
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
}
