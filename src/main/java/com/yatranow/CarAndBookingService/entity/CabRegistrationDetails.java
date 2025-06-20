package com.yatranow.CarAndBookingService.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class CabRegistrationDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "registration_id")
	private Long registrationId;
	private Long userId;
	private String ownerName;
	private String driverName;
	private String driverContact;
	private String driverLicense;
	private String address;
	private String latitude;
	private String longitude;
	private int perKmRate;
	private double baseFare;
	private String status;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cabId", referencedColumnName = "cabId")
	private Cab cab;
	@JsonIgnore
	private LocalDateTime insertedAt;
	@JsonIgnore
	private LocalDateTime updatedAt;

	public CabRegistrationDetails() {
	}

	@PrePersist
	protected void onCreate() {
		insertedAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public int getPerKmRate() {
		return perKmRate;
	}

	public void setPerKmRate(int perKmRate) {
		this.perKmRate = perKmRate;
	}

	public Cab getCab() {
		return cab;
	}

	public void setCab(Cab cab) {
		this.cab = cab;
	}

	public LocalDateTime getInsertedAt() {
		return insertedAt;
	}

	public void setInsertedAt(LocalDateTime insertedAt) {
		this.insertedAt = insertedAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public double getBaseFare() {
		return Math.round(baseFare * 100.0) / 100.0;
	}

	public void setBaseFare(double baseFare) {
		this.baseFare = Math.round(baseFare * 100.0) / 100.0;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
