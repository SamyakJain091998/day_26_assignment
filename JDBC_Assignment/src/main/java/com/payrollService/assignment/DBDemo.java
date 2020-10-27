package com.payrollService.assignment;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Enumeration;

public class DBDemo {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/payroll_service_assignment";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "admin@123";

	private static void listDrivers() {
		// TODO Auto-generated method stub
		Enumeration<Driver> driverList = DriverManager.getDrivers();
		while (driverList.hasMoreElements()) {
			Driver driverClass = (Driver) driverList.nextElement();
			System.out.println("	" + driverClass.getClass().getName());
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection connection = null;
		Statement stmt = null;

		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			throw new IllegalStateException("Cannot find the driver in the classpath.", e);
		}

		listDrivers();

		try {
			System.out.println("Connecting to database: " + DB_URL);
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connection is successfull..!!" + connection);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
