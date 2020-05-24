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

import com.manish.javadev.controller.model.Customer;
import com.manish.javadev.controller.model.CustomerResponseDetail;
import com.manish.javadev.controller.model.BookingDetail;
import com.manish.javadev.controller.model.VehicleResponseDetails;
import com.manish.javadev.service.RentDetailCommand;
import com.manish.javadev.service.VehicleDetailsCommand;

@RestController
@RequestMapping(value = "/api")
public class CustomerServiceController {

	@Autowired
	@LoadBalanced
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
			BookingDetail bookingDetail = getRentDetails(userId, vehical.getVehicalId());

			return new CustomerResponseDetail(customerMap.get(new Integer(userId)), vehical, bookingDetail);
		}).collect(Collectors.toList());
	}

	private BookingDetail getRentDetails(int userId, int vehicleId) {
		RentDetailCommand rentDetailCommand = new RentDetailCommand(restTemplate, userId, vehicleId);
		return rentDetailCommand.execute();
	}

	private VehicleResponseDetails getVehicleDetails(int userId) {
		VehicleDetailsCommand vehicleDetailsCommand = new VehicleDetailsCommand(restTemplate, userId);
		return vehicleDetailsCommand.execute();
	}

	@RequestMapping(value = "/ping1")
	public String ping() {
		return "Configuration is working fine";
	}
}
