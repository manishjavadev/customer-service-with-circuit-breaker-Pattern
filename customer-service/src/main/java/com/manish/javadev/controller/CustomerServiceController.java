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
import com.manish.javadev.service.BookingDetailCommand;
import com.manish.javadev.service.VehicleDetailsCommand;

@RestController
@RequestMapping(value = "/api")
public class CustomerServiceController {

	@Autowired
	private RestTemplate restTemplate;

	private Map<Integer, Customer> customerMap;

	public CustomerServiceController() {
		customerMap = new HashMap<Integer, Customer>();
		Customer customer = new Customer(100, "Vaishali", 27);
		customerMap.put(new Integer(100), customer);
	}

	@RequestMapping(value = "/bookings/{userId}")
	List<CustomerResponseDetail> findBookingDetails(@PathVariable("userId") int userId) {

		// TODO Rest call to get booking for Vehicles
		VehicleResponseDetails vehicleDetails = getVehicleDetails(userId);

		return vehicleDetails.getVehicleList().stream().map(vehical -> {
			// TODO Rest call to get booking price for each Vehicles
			BookingDetail bookingDetail = getBookingDetails(userId, vehical.getVehicalId());

			return new CustomerResponseDetail(customerMap.get(new Integer(userId)), vehical, bookingDetail);
		}).collect(Collectors.toList());
	}

	private BookingDetail getBookingDetails(int userId, int vehicleId) {
		BookingDetailCommand bookingDetailCommand = new BookingDetailCommand(restTemplate, userId, vehicleId);
		return bookingDetailCommand.execute();
	}

	private VehicleResponseDetails getVehicleDetails(int userId) {
		VehicleDetailsCommand vehicleDetailsCommand = new VehicleDetailsCommand(restTemplate, userId);
		return vehicleDetailsCommand.execute();
	}

	@RequestMapping(value = "/ping")
	public String ping() {
		return "Configuration is working fine";
	}
}
