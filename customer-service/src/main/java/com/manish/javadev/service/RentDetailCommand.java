package com.manish.javadev.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.manish.javadev.controller.model.BookingDetail;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

@Service
public class RentDetailCommand extends HystrixCommand<BookingDetail> {
	RestTemplate restTemplate;
	int userId;
	int vehicleId;

	public RentDetailCommand(RestTemplate restTemplate, int userId, int vehicleId) {
		super(HystrixCommandGroupKey.Factory.asKey("default"));
		this.restTemplate = restTemplate;
		this.userId = userId;
		this.vehicleId = vehicleId;
	}

	@Override
	protected BookingDetail run() throws Exception {
		BookingDetail bookingDetail = restTemplate
				.getForObject("http://rent-service/api/rent/" + userId + "/" + vehicleId, BookingDetail.class);
		return bookingDetail;
	}

	@Override
	protected BookingDetail getFallback() {
		BookingDetail bookingDetail = new BookingDetail(vehicleId, userId, 0000.0, "LOCAL", "LOCAL");
		return bookingDetail;
	}

}
