package com.manish.javadev.model;

public class Customer {
	private int customerId;
	private String name;
	private int age;

	public Customer() {
		super();
	}

	public Customer(int customerId, String name, int age) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.age = age;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
