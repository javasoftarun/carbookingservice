package com.yatranow.CarAndBookingService.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class Cab {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cabId;
	private String cabName;
	private String cabType;
	private String cabNumber;
	private String cabModel;
	private String cabColor;
	private String cabInsurance;
	private String cabCapacity;
	private String fluelType;
	private boolean isAC;
	private String cabImageUrl;
	private String cabCity;
	private String cabState;
	
	@OneToOne(mappedBy = "cab", cascade = CascadeType.ALL)
	@JsonIgnore
	private CabRegistrationDetails cabRegistrationDetails;
	
	@JsonIgnore
	private LocalDateTime insertedAt;
	@JsonIgnore
    private LocalDateTime updatedAt;
    
	@PrePersist
    protected void onCreate() {
		insertedAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
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

	public String getFluelType() {
		return fluelType;
	}

	public void setFluelType(String fluelType) {
		this.fluelType = fluelType;
	}

	public boolean isAC() {
		return isAC;
	}

	public void setAC(boolean isAC) {
		this.isAC = isAC;
	}

	
}
