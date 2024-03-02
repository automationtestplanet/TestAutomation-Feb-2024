package com.automationtestplanet.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee2 implements Comparable<Employee2>{
	
	@JsonProperty("name")
	String name;
	
	@JsonProperty("empId")
	long empId;
	
	@JsonProperty("salary")
	long salary;

	public synchronized String getName() {
		return name;
	}

	public synchronized long getEmpId() {
		return empId;
	}

	public synchronized long getSalary() {
		return salary;
	}

	public void displayEmployDetails() {
		System.out.println("Name: " + name);
		System.out.println("Emp Id: " + empId);
		System.out.println("Salary: " + salary);
	}

	@Override
	public int compareTo(Employee2 emp1) {
		if(emp1.getSalary() <  this.getSalary()) return 0;else return -1 ;
	}

}
