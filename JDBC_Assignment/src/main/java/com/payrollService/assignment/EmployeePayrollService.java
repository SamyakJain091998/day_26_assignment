package com.payrollService.assignment;

import java.sql.SQLException;
import java.util.List;

public class EmployeePayrollService {

	private List<EmployeePayrollData> employeePayrollList;

	public List<EmployeePayrollData> readEmployeePayrollData() throws SQLException {
		// TODO Auto-generated method stub
		this.employeePayrollList = new employeePayrollDBService().readData();

		return this.employeePayrollList;
	}

}
