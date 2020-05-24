package com.manish.javadev.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.manish.javadev.model.Vehicle;
import com.manish.javadev.model.VehicleResponseDetails;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class VehicleDetailsCommand extends HystrixCommand<VehicleResponseDetails> {

	private RestTemplate restTemplate;
	private int userId;

	public VehicleDetailsCommand(RestTemplate restTemplate, int userId) {
		super(HystrixCommandGroupKey.Factory.asKey("default"));
		this.restTemplate = restTemplate;
		this.userId = userId;
	}

	@Override
	protected VehicleResponseDetails run() throws Exception {
		return restTemplate.getForObject("http://vehicle-service/api/vehicles/" + userId, VehicleResponseDetails.class);
	}

	@Override
	protected VehicleResponseDetails getFallback() {
		// Preparing default response
		VehicleResponseDetails responseDetails = new VehicleResponseDetails();
		List<Vehicle> vehicleList = new ArrayList<Vehicle>();
		Vehicle vehicle = new Vehicle(0000, "Dummy Car", "0000", "Dummy Model");
		vehicleList.add(vehicle);
		responseDetails.setVehicleList(vehicleList);
		return responseDetails;
	}
}
