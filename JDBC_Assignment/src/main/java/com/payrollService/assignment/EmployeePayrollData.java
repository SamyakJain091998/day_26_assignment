package com.payrollService.assignment;

import java.time.LocalDate;
import java.util.Date;

public class EmployeePayrollData {

	private int id;
	private String name;
	private double salary;
	private LocalDate startDate;
	private int companyId;
	public String gender;

	public EmployeePayrollData() {

	}

	public EmployeePayrollData(int id, String name, double salary, LocalDate startDate) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.startDate = startDate;
	}

	public EmployeePayrollData(int id, String name, double salary, LocalDate startDate, int companyId) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.startDate = startDate;
		this.companyId = companyId;
	}

	public EmployeePayrollData(int id, String name, String gender, double salary, LocalDate startDate) {
		// TODO Auto-generated constructor stub
		this(id, name, salary, startDate);
		this.gender = gender;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int id) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	@Override
	public String toString() {
		return "EmployeePayrollData [ id -> " + getId() + " name -> " + getName() + " salary -> " + getSalary()
				+ " startDate -> " + getStartDate() + " companyId -> " + getCompanyId() + " ]";
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
