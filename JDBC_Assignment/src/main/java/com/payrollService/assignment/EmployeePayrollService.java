package com.payrollService.assignment;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public boolean checkEmployeePayrollInSyncWithDB_WithActiveStatusOption(String name) throws Exception {
		List<EmployeePayrollData> employeePayrollDataList;
		try {
			employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EmployeePayrollException("Oops there's an exception!");
		}
		// TODO Auto-generated method stub
		if (employeePayrollDataList == null) {
			return false;
		}
		return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
	}

	public List<String> getEmployeeNameListSyncWithDB(LocalDate date1, LocalDate date2) throws Exception {
		List<EmployeePayrollData> employeePayrollDataList;
		try {
			employeePayrollDataList = employeePayrollDBService.getEmployeePayrollDataBasisDate(date1, date2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EmployeePayrollException("Oops there's an exception!");
		}
		List<String> employeeNameList = new ArrayList<>();
		for (int i = 0; i < employeePayrollDataList.size(); i++) {
			employeeNameList.add(employeePayrollDataList.get(i).getName());
		}
		return employeeNameList;
	}

	public Map<String, Double> readAverageSalaryByGender() throws Exception {
		// TODO Auto-generated method stub
		return employeePayrollDBService.getAverageSalaryByGender();
	}

	public void addEmployeeToPayrollUC7(String name, double salary, LocalDate startDate, String gender)
			throws EmployeePayrollException, Exception {
		// TODO Auto-generated method stub
		employeePayrollList.add(employeePayrollDBService.addEmployeeToPayrollUC7(name, salary, startDate, gender));
	}

	public void addEmployeesToPayroll(List<EmployeePayrollData> employeePayrollDataList) {
		// TODO Auto-generated method stub
		employeePayrollDataList.forEach(employeePayrollData -> {
			try {
				System.out.println("Employee being added : " + employeePayrollData.getName());
				this.addEmployeeToPayrollUC8(employeePayrollData.getName(), employeePayrollData.getSalary(),
						employeePayrollData.getStartDate(), employeePayrollData.getGender());
				System.out.println("Employee added : " + employeePayrollData.getName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		System.out.println(this.employeePayrollList);
	}

	public void addEmployeesToPayrollWithThread(List<EmployeePayrollData> employeePayrollDataList)
			throws EmployeePayrollException, Exception {
		// TODO Auto-generated method stub
		Map<Integer, Boolean> employeeAdditionStatus = new HashMap<Integer, Boolean>();
		employeePayrollDataList.forEach(employeePayrollData -> {
			Runnable task = () -> {
				employeeAdditionStatus.put(employeePayrollData.hashCode(), false);
				try {
					System.out.println("Employee being added : " + Thread.currentThread().getName());
					this.addEmployeeToPayrollUC8(employeePayrollData.getName(), employeePayrollData.getSalary(),
							employeePayrollData.getStartDate(), employeePayrollData.getGender());
					employeeAdditionStatus.put(employeePayrollData.hashCode(), true);
					System.out.println("Employee added : " + Thread.currentThread().getName());
				} catch (EmployeePayrollException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
			Thread thread = new Thread(task, employeePayrollData.getName());
			thread.start();
		});
		while (employeeAdditionStatus.containsValue(false)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		System.out.println(this.employeePayrollList);
	}

	public void addEmployeeToPayrollUC8(String name, double salary, LocalDate startDate, String gender)
			throws EmployeePayrollException, Exception {
		// TODO Auto-generated method stub
		employeePayrollList.add(employeePayrollDBService.addEmployeeToPayrollUC8(name, salary, startDate, gender));
	}

	public void addEmployeeToPayrollERDiagramModel(String name, double salary, LocalDate startDate, String gender,
			int companyId, int departmentId) throws EmployeePayrollException, Exception {
		// TODO Auto-generated method stub
		employeePayrollList.add(employeePayrollDBService.addEmployeeToPayrollErDiagramModel(name, salary, startDate,
				gender, companyId, departmentId));
	}

	public void setActiveStatusFalseERDiagramModel(String name) throws Exception {
		// TODO Auto-generated method stub
		int result = employeePayrollDBService.setActiveStatusFalseInDB(name);
		if (result == 0) {
			throw new EmployeePayrollException("Oops there's an exception!");
		}
	}

}
