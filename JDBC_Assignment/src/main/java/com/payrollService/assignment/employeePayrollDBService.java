package com.payrollService.assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class employeePayrollDBService {

	private Connection getConnection() {
		Connection connection = null;
		final String DB_URL = "jdbc:mysql://localhost/payroll_service_assignment";
		final String USER = "root";
		final String PASS = "admin@123";
		try {
			System.out.println("Connecting to database: " + DB_URL);
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connection is successfull..!!" + connection);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return connection;
	}

	public List<EmployeePayrollData> readData() throws SQLException {
		String sql = "SELECT * from employee_payroll";
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		Connection connection = this.getConnection();
		System.out.println("Creating statement..");
		Statement statement = connection.createStatement();
		System.out.println("Statement created successfully..");
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			double salary = resultSet.getDouble("salary");
			LocalDate startDate = resultSet.getDate("start").toLocalDate();
			employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
			System.out.println(new EmployeePayrollData(id, name, salary, startDate));
		}
//		resultSet.close();
//		statement.close();
		connection.close();
		return employeePayrollList;
	}

}
