package com.manish.javadev.model;

import java.util.List;

public class VehicleResponseDetails {

	List<Vehicle> vehicleList;

	public VehicleResponseDetails() {
	}

	public List<Vehicle> getVehicleList() {
		return vehicleList;
	}

	public void setVehicleList(List<Vehicle> vehicleList) {
		this.vehicleList = vehicleList;
	}

	public VehicleResponseDetails(List<Vehicle> vehicleList) {
		this.vehicleList = vehicleList;
	}

	@Override
	public String toString() {
		return "VehicleResponseDetails [vehicleList=" + vehicleList + "]";
	}

}
