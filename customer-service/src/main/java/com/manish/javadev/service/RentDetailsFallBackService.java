
package com.manish.javadev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.manish.javadev.controller.model.BookingDetail;
import com.manish.javadev.controller.model.Vehicle;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class RentDetailsFallBackService {

	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getRentDetailsFallBack")
	public BookingDetail getRentDetails(int userId, Vehicle vehical) {
		BookingDetail rentDetail = restTemplate.getForObject(
				"http://rent-service/api/rent/" + userId + "/" + vehical.getVehicalId(), BookingDetail.class);
		return rentDetail;
	}

	public BookingDetail getRentDetailsFallBack(int userId, Vehicle vehical) {
		BookingDetail bookingDetail = new BookingDetail(vehical.getVehicalId(), userId, 0000.0, "LOCAL", "LOCAL");
		return bookingDetail;
	}
}
