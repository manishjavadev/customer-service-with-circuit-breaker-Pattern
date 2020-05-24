package com.manish.javadev.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.manish.javadev.model.BookingDetail;
import com.manish.javadev.model.Customer;
import com.manish.javadev.model.CustomerResponseDetail;
import com.manish.javadev.model.Vehicle;
import com.manish.javadev.model.VehicleResponseDetails;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping(value = "/api")
public class CustomerServiceController3 {

	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;

	private Map<Integer, Customer> customerMap;

	public CustomerServiceController3() {
		customerMap = new HashMap<Integer, Customer>();
		Customer customer = new Customer(100, "Vaishali", 27);
		customerMap.put(new Integer(100), customer);
	}

	@RequestMapping(value = "/bookings2/{userId}")
	List<CustomerResponseDetail> findBookingDetails(@PathVariable("userId") int userId) {

		// TODO Rest call to get booking for Vehicles
		VehicleResponseDetails vehicleDetails = getVehicleDetails(userId);

		return vehicleDetails.getVehicleList().stream().map(vehical -> {
			// TODO Rest call to get booking price for each Vehicles
			BookingDetail bookingDetail = getBookingDetails(userId, vehical);
			/* System.out.println(bookingDetail); */

			return new CustomerResponseDetail(customerMap.get(new Integer(userId)), vehical, bookingDetail);
		}).collect(Collectors.toList());
	}

	@HystrixCommand(fallbackMethod = "getVehicleDetailsFallBack")
	private VehicleResponseDetails getVehicleDetails(int userId) {
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

	@HystrixCommand(fallbackMethod = "getBookingDetailsFallBack")
	private BookingDetail getBookingDetails(int userId, Vehicle vehical) {
		return restTemplate.getForObject("http://rent-service/api/rent/" + userId + "/" + vehical.getVehicalId(),
				BookingDetail.class);
	}

	private BookingDetail getBookingDetailsFallBack(int userId, Vehicle vehical) {
		BookingDetail bookingDetail = new BookingDetail(vehical.getVehicalId(), userId, 0000.0, "LOCAL", "LOCAL");
		return bookingDetail;
	}

	@RequestMapping(value = "/ping2")
	public String ping() {
		return "Configuration is working fine";
	}
}
