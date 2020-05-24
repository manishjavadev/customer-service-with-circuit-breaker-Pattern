package com.manish.javadev.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.client.RestTemplate;

import com.manish.javadev.model.Vehicle;
import com.manish.javadev.model.VehicleResponseDetails;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

public class VehicleDetailsFallBackService {

	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getVehicleDetailsFallBack")
	public VehicleResponseDetails getVehicleDetails(int userId) {
		return restTemplate.getForObject("http://vehicle-service/api/vehicles/" + userId, VehicleResponseDetails.class);
	}

	private VehicleResponseDetails getVehicleDetailsFallBack(int userId) {
		VehicleResponseDetails responseDetails = new VehicleResponseDetails();
		List<Vehicle> vehicleList = new ArrayList<Vehicle>();
		Vehicle vehicle = new Vehicle(0000, "Dummy vehicle", "0000", "Dummy model");
		vehicleList.add(vehicle);
		responseDetails.setVehicleList(vehicleList);
		return responseDetails;
	}
}
