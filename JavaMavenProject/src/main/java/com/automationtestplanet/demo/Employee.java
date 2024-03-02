package com.automationtestplanet.demo;

public class Employee implements Comparable<Employee>{
	String name;
	long empId;
	long salary;

	public String getName() {
		return name;
	}

	public long getEmpId() {
		return empId;
	}

	public long getSalary() {
		return salary;
	}

	public void displayEmployDetails() {
		System.out.println("Name: " + name);
		System.out.println("Emp Id: " + empId);
		System.out.println("Salary: " + salary);
	}

	@Override
	public int compareTo(Employee emp1) {
		if(emp1.getSalary() <  this.getSalary()) return 0;else return -1 ;
	}

}
