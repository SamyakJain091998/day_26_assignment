package com.payrollService.assignment;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.geometry.euclidean.oned.Interval;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import java.time.Duration;

public class EmployeePayrollServiceTest {

	@Ignore
	@Test
	public void test() {
		Assert.assertTrue(true);
	}

	@Ignore
	@Test
	public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() throws Exception {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> EmployeePayrollData = employeePayrollService.readEmployeePayrollData();
		Assert.assertEquals(3, EmployeePayrollData.size());
	}

	@Ignore
	@Test
	public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() throws Exception {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		try {
			List<EmployeePayrollData> EmployeePayrollData = employeePayrollService.readEmployeePayrollData();
			employeePayrollService.updateEmployeeSalary("Terisa", 900000.0);
			boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
			Assert.assertTrue(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EmployeePayrollException("Oops there's an exception!");
		}
	}

	@Ignore
	@Test
	public void givenEmployeePayrollInDB_WhenRetrievedBasedOnJoiningDate_ShouldSyncWithDB() throws Exception {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		try {
			List<EmployeePayrollData> EmployeePayrollData = employeePayrollService.readEmployeePayrollData();

			LocalDate date1 = LocalDate.of(2017, 1, 1);
			LocalDate date2 = LocalDate.of(2020, 12, 30);
			List<String> employeeNameList = employeePayrollService.getEmployeeNameListSyncWithDB(date1, date2);
			Assert.assertEquals(3, employeeNameList.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EmployeePayrollException("Oops there's an exception !!!!!");
		}
	}

	@Ignore
	@Test
	public void givenEmployeePayrollDate_WhenAverageSalaryRetrievedByGender_ShouldReturnAppropriateValues()
			throws Exception {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		try {
			List<EmployeePayrollData> EmployeePayrollData = employeePayrollService.readEmployeePayrollData();
			Map<String, Double> averageSalaryBasisGender = employeePayrollService.readAverageSalaryByGender();
			System.out.println("Map is : " + averageSalaryBasisGender);
			Assert.assertTrue(averageSalaryBasisGender.get("M").equals(200000.0)
					&& averageSalaryBasisGender.get("F").equals(300000.0));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EmployeePayrollException("Oops there's an exception!");
		}
	}

	@Ignore
	@Test
	public void givenNewEmployee_WhenAdded_ShouldSyncWithDB_UC7() throws Exception {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData();
		employeePayrollService.addEmployeeToPayrollUC7("Mark", 5000000.00, LocalDate.now(), "M");
		System.out.println("here");
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Mark");
		Assert.assertTrue(result);
	}

	@Ignore
	@Test
	public void givenNewEmployee_WhenAdded_ShouldSyncWithDB_UC8() throws Exception {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData();
		employeePayrollService.addEmployeeToPayrollUC8("Mark", 5000000.00, LocalDate.now(), "M");
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Mark");
		Assert.assertTrue(result);
	}

	@Ignore
	@Test
	public void givenNewEmployee_WhenAdded_ShouldSyncWithDB_ERDiagramModel() throws Exception {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData();
		employeePayrollService.addEmployeeToPayrollERDiagramModel("Hailey", 1000000.00, LocalDate.now(), "F", 1, 2);
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Dest");
		Assert.assertTrue(result);
	}

	@Ignore
	@Test
	public void givenEmployeePayrollDate_WhenGivenEmployeeName_ShouldSetTheActiveStatusFalse_ERDiagramModel()
			throws Exception {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData();
		employeePayrollService.setActiveStatusFalseERDiagramModel("Hailey");
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB_WithActiveStatusOption("Hailey");
		Assert.assertEquals(true, result);
	}

	@Test
	public void given6Employees_WhenAdded_ShouldMatchTheNumberOfEmployeeEntries() throws Exception {
		EmployeePayrollData[] arrayOfEmployees = { new EmployeePayrollData(0, "Jeff", "M", 100000.0, LocalDate.now()),
				new EmployeePayrollData(0, "Bill", "M", 200000.0, LocalDate.now()),
				new EmployeePayrollData(0, "Mark Z.", "M", 300000.0, LocalDate.now()),
				new EmployeePayrollData(0, "sundar", "M", 600000.0, LocalDate.now()),
				new EmployeePayrollData(0, "Mukesh", "M", 1000000.0, LocalDate.now()),
				new EmployeePayrollData(0, "Anil", "M", 2000000.0, LocalDate.now()) };

		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData();
		Instant start = Instant.now();
		employeePayrollService.addEmployeesToPayroll(Arrays.asList(arrayOfEmployees));
		Instant end = Instant.now();
		System.out.println("Duration without thread : " + Duration.between(start, end));

		Instant threadStart = Instant.now();
		employeePayrollService.addEmployeesToPayrollWithThread(Arrays.asList(arrayOfEmployees));
		Instant threadEnd = Instant.now();
		System.out.println("Duration without thread : " + Duration.between(threadStart, threadEnd));
		List<EmployeePayrollData> EmployeePayrollData = employeePayrollService.readEmployeePayrollData();
		System.out.println(EmployeePayrollData);
		Assert.assertEquals(13, EmployeePayrollData.size());
	}
}
