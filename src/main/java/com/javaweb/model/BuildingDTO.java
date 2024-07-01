package com.javaweb.model;

public class BuildingDTO {
	private String name, address, managername, managerphonenumber, servicefee, brokeragefee, rentarea, rentprice;
	private int floorarea, emptyarea, numberofbasement;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	

	public int getNumberofbasement() {
		return numberofbasement;
	}

	public void setNumberofbasement(int numberofbasement) {
		this.numberofbasement = numberofbasement;
	}

	public String getManagername() {
		return managername;
	}

	public void setManagername(String managername) {
		this.managername = managername;
	}

	public String getManagerphonenumber() {
		return managerphonenumber;
	}

	public void setManagerphonenumber(String managerphonenumber) {
		this.managerphonenumber = managerphonenumber;
	}

	public String getServicefee() {
		return servicefee;
	}

	public void setServicefee(String servicefee) {
		this.servicefee = servicefee;
	}

	public String getBrokeragefee() {
		return brokeragefee;
	}

	public void setBrokeragefee(String brokeragefee) {
		this.brokeragefee = brokeragefee;
	}

	public String getRentarea() {
		return rentarea;
	}

	public void setRentarea(String rentarea) {
		this.rentarea = rentarea;
	}

	public int getFloorarea() {
		return floorarea;
	}

	public void setFloorarea(int floorarea) {
		this.floorarea = floorarea;
	}

	public int getEmptyarea() {
		return emptyarea;
	}

	public void setEmptyarea(int emptyarea) {
		this.emptyarea = emptyarea;
	}

	public String getRentprice() {
		return rentprice;
	}

	public void setRentprice(String rentprice) {
		this.rentprice = rentprice;
	}

}
