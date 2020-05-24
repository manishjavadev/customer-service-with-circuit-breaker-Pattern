package com.manish.javadev.model;

public class Vehicle {
	private int vehicalId;
	private String name;
	private String number;
	private String model;

	public Vehicle() {
	}

	public Vehicle(int vehicalId, String name, String number, String model) {
		super();
		this.vehicalId = vehicalId;
		this.name = name;
		this.number = number;
		this.model = model;
	}

	public int getVehicalId() {
		return vehicalId;
	}

	public void setVehicalId(int vehicalId) {
		this.vehicalId = vehicalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}
