package com.manish.javadev.service;

import org.springframework.web.client.RestTemplate;

import com.manish.javadev.model.BookingDetail;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class BookingDetailCommand extends HystrixCommand<BookingDetail> {

	private RestTemplate restTemplate;
	private int userId;
	private int vehicleId;

	public BookingDetailCommand(RestTemplate restTemplate, int userId, int vehicleId) {
		super(HystrixCommandGroupKey.Factory.asKey("default"));
		this.restTemplate = restTemplate;
		this.userId = userId;
		this.vehicleId = vehicleId;
	}

	@Override
	protected BookingDetail run() throws Exception {

		return restTemplate.getForObject("http://rent-service/api/rent/" + userId + "/" + vehicleId,
				BookingDetail.class);
	}

	@Override
	protected BookingDetail getFallback() {
		BookingDetail bookingDetail = new BookingDetail(vehicleId, userId, 0000.0, "LOCAL", "LOCAL");
		return bookingDetail;
	}

}
