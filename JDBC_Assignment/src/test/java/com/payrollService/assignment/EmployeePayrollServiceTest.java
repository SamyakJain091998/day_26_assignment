package com.payrollService.assignment;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

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
			employeePayrollService.updateEmployeeSalary("Terisa", 300000.0);
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
}
