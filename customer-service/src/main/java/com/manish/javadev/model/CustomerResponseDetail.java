package com.manish.javadev.model;

public class CustomerResponseDetail {

	private Vehicle vehical;
	private BookingDetail bookingDetail;
	private Customer customer;

	public CustomerResponseDetail(Customer customer, Vehicle vehical, BookingDetail bookingDetail) {
		this.customer = customer;
		this.vehical = vehical;
		this.bookingDetail = bookingDetail;
	}

	public Vehicle getVehical() {
		return vehical;
	}

	public void setVehical(Vehicle vehical) {
		this.vehical = vehical;
	}

	public BookingDetail getBookingDetail() {
		return bookingDetail;
	}

	public void setBookingDetail(BookingDetail bookingDetail) {
		this.bookingDetail = bookingDetail;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
