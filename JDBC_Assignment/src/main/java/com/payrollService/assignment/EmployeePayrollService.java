package com.payrollService.assignment;

import java.sql.SQLException;
import java.util.List;

public class EmployeePayrollService {

	private List<EmployeePayrollData> employeePayrollList;

	private EmployeePayrollDBService employeePayrollDBService;

	public EmployeePayrollService() {
		employeePayrollDBService = EmployeePayrollDBService.getInstance();
	}

	public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList) {
		this();
		this.employeePayrollList = employeePayrollList;
	}

	public List<EmployeePayrollData> readEmployeePayrollData() throws Exception {
		// TODO Auto-generated method stub
		this.employeePayrollList = employeePayrollDBService.readData();

		return this.employeePayrollList;
	}

	public void updateEmployeeSalary(String name, double salary) {
		// TODO Auto-generated method stub
		int result;
		try {
			result = employeePayrollDBService.updateEmployeeData(name, salary);
			if (result == 0)
				return;
			EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
			if (employeePayrollData != null)
				employeePayrollData.setSalary(salary);
		} catch (EmployeePayrollException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private EmployeePayrollData getEmployeePayrollData(String name) {
		// TODO Auto-generated method stub
		EmployeePayrollData employeePayrollData;
		employeePayrollData = this.employeePayrollList.stream()
				.filter(employeePayrollDataItem -> employeePayrollDataItem.getName().equals(name)).findFirst()
				.orElse(null);
		return employeePayrollData;
	}

	public boolean checkEmployeePayrollInSyncWithDB(String name) throws Exception {
		List<EmployeePayrollData> employeePayrollDataList;
		try {
			employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EmployeePayrollException("Oops there's an exception!");
		}
		// TODO Auto-generated method stub
		return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
	}

}
