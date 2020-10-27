package com.payrollService.assignment;

import java.time.LocalDate;
import java.util.Date;

public class EmployeePayrollData {

	private static int id;
	private static String name;
	private static double salary;
	private static LocalDate startDate;

	public EmployeePayrollData() {

	}

	public EmployeePayrollData(int id, String name, double salary, LocalDate startDate) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.startDate = startDate;
	}

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		EmployeePayrollData.id = id;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		EmployeePayrollData.name = name;
	}

	public static double getSalary() {
		return salary;
	}

	public static void setSalary(double salary) {
		EmployeePayrollData.salary = salary;
	}

	public static LocalDate getStartDate() {
		return startDate;
	}

	public static void setStartDate(LocalDate startDate) {
		EmployeePayrollData.startDate = startDate;
	}

	
	
	@Override
	public String toString() {
		return "EmployeePayrollData [ id -> " + id + " name -> " + name + " salary -> " + salary + " startDate -> " + startDate + " ]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		EmployeePayrollData that = (EmployeePayrollData) o;
		return id == that.id && Double.compare(that.salary, salary) == 0 && name.equals(that.name);
	}
}
