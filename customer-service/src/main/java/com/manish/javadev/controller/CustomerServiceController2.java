package com.manish.javadev.controller;

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
import com.manish.javadev.model.VehicleResponseDetails;
import com.manish.javadev.service.BookingDetailFallBackService;
import com.manish.javadev.service.VehicleDetailsFallBackService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping(value = "/api")
public class CustomerServiceController2 {

	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;

	@Autowired
	private BookingDetailFallBackService bookingDetailsFallBackService;

	@Autowired
	private VehicleDetailsFallBackService vehicleDetailsFallBackService;

	private Map<Integer, Customer> customerMap;

	public CustomerServiceController2() {
		customerMap = new HashMap<Integer, Customer>();
		Customer customer = new Customer(100, "Vaishali", 27);
		customerMap.put(new Integer(100), customer);
	}

	@RequestMapping(value = "/bookings2/{userId}")
	@HystrixCommand(fallbackMethod = "findBookingDetailsFallBack")
	List<CustomerResponseDetail> findBookingDetails(@PathVariable("userId") int userId) {

		// TODO Rest call to get booking for Vehicles
		VehicleResponseDetails vehicleDetails = vehicleDetailsFallBackService.getVehicleDetails(userId);

		return vehicleDetails.getVehicleList().stream().map(vehical -> {
			// TODO Rest call to get booking price for each Vehicles
			BookingDetail bookingDetail = bookingDetailsFallBackService.getBookingDetails(userId, vehical);

			return new CustomerResponseDetail(customerMap.get(new Integer(userId)), vehical, bookingDetail);
		}).collect(Collectors.toList());
	}

	@RequestMapping(value = "/ping2")
	public String ping() {
		return "Configuration is working fine";
	}
}
