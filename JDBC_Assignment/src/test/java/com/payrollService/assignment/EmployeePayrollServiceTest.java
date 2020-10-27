package com.payrollService.assignment;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() throws SQLException {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> EmployeePayrollData = employeePayrollService.readEmployeePayrollData();
		Assert.assertEquals(3, EmployeePayrollData.size());
	}

	@Test
	public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() throws SQLException {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> EmployeePayrollData = employeePayrollService.readEmployeePayrollData();
		employeePayrollService.updateEmployeeSalary("Terisa", 300000.0);
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
		Assert.assertTrue(result);
	}
}
