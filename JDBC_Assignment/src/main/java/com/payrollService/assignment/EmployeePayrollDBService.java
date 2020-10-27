package com.payrollService.assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {
	private PreparedStatement employeePayrollDataStatement;
	private static EmployeePayrollDBService employeePayrollDBService;

	private EmployeePayrollDBService() {

	}

	public static EmployeePayrollDBService getInstance() {
		if (employeePayrollDBService == null) {
			employeePayrollDBService = new EmployeePayrollDBService();
		}
		return employeePayrollDBService;
	}

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
		employeePayrollList = this.getEmployeePayrollData(resultSet);

//		resultSet.close();
//		statement.close();
		connection.close();
		return employeePayrollList;
	}

	public int updateEmployeeData(String name, double salary) {
		// TODO Auto-generated method stub
		return this.updateEmployeeDataUsingStatement(name, salary);
	}

	private int updateEmployeeDataUsingStatement(String name, double salary) {
		// TODO Auto-generated method stub
		String sql = String.format("update employee_payroll set salary = %.2f where name = '%s';", salary, name);
		try (Connection connection = this.getConnection();) {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

	private int updateEmployeeDataUsingPreparedStatement(String name, double salary) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<EmployeePayrollData> getEmployeePayrollData(String name) throws SQLException {
		// TODO Auto-generated method stub
		List<EmployeePayrollData> employeePayrollList = null;
		if (this.employeePayrollDataStatement == null)
			this.prepareStatementForEmployeeDate();
		employeePayrollDataStatement.setString(1, name);
		ResultSet resultSet = employeePayrollDataStatement.executeQuery();
		employeePayrollList = this.getEmployeePayrollData(resultSet);

		return employeePayrollList;
	}

	private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			double salary = resultSet.getDouble("salary");
			LocalDate startDate = resultSet.getDate("start").toLocalDate();
			employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
		}
		return employeePayrollList;
	}

	private void prepareStatementForEmployeeDate() throws SQLException {
		Connection connection = this.getConnection();
		String sql = "SELECT * FROM employee_payroll WHERE name = ?";
		employeePayrollDataStatement = connection.prepareStatement(sql);
	}

}
