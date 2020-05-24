
package com.manish.javadev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.manish.javadev.model.BookingDetail;
import com.manish.javadev.model.Vehicle;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class BookingDetailFallBackService {

	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getBookingDetailsFallBack")
	public BookingDetail getBookingDetails(int userId, Vehicle vehical) {
		BookingDetail rentDetail = restTemplate.getForObject(
				"http://rent-service/api/rent/" + userId + "/" + vehical.getVehicalId(), BookingDetail.class);
		return rentDetail;
	}

	public BookingDetail getBookingDetailsFallBack(int userId, Vehicle vehical) {
		BookingDetail bookingDetail = new BookingDetail(vehical.getVehicalId(), userId, 0000.0, "LOCAL", "LOCAL");
		return bookingDetail;
	}
}
