package com.payrollService.assignment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public List<EmployeePayrollData> readData() throws Exception {
		String sql = "SELECT * from employee_payroll";
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		Connection connection = this.getConnection();
		System.out.println("Creating statement..");
		Statement statement;
		try {
			statement = connection.createStatement();
			System.out.println("Statement created successfully..");
			ResultSet resultSet = statement.executeQuery(sql);
			employeePayrollList = this.getEmployeePayrollData(resultSet);

//			resultSet.close();
//			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EmployeePayrollException("Oops there's an exception!");
		}
		return employeePayrollList;
	}

	public int updateEmployeeData(String name, double salary) throws EmployeePayrollException, Exception {
		// TODO Auto-generated method stub
		return this.updateEmployeeDataUsingStatement(name, salary);
	}

	private int updateEmployeeDataUsingStatement(String name, double salary)
			throws EmployeePayrollException, Exception {
		// TODO Auto-generated method stub
		String sql = String.format("update employee_payroll set salary = %.2f where name = '%s';", salary, name);
		try (Connection connection = this.getConnection();) {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO: handle exception
			throw new EmployeePayrollException("Oops there's an exception!");
		}
	}

	private int updateEmployeeDataUsingPreparedStatement(String name, double salary) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<EmployeePayrollData> getEmployeePayrollData(String name) throws Exception {
		// TODO Auto-generated method stub
		List<EmployeePayrollData> employeePayrollList = null;
		if (this.employeePayrollDataStatement == null)
			try {
				String sql = "SELECT * FROM employee_payroll WHERE name = ? AND is_Active = 1";
				this.prepareStatementForEmployeeData(sql);
				employeePayrollDataStatement.setString(1, name);
				ResultSet resultSet = employeePayrollDataStatement.executeQuery();
				employeePayrollList = this.getEmployeePayrollData(resultSet);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new EmployeePayrollException("Oops there's an exception!");
			}
		return employeePayrollList;
	}

	public int setActiveStatusFalseInDB(String name) throws Exception {
		// TODO Auto-generated method stub
		List<EmployeePayrollData> employeePayrollList = null;
		int result = 0;
		if (this.employeePayrollDataStatement == null)
			try {
				String sql = "UPDATE employee_payroll SET is_Active = 0 WHERE name = ?";
				this.prepareStatementForEmployeeData(sql);
				employeePayrollDataStatement.setString(1, name);
				result = employeePayrollDataStatement.executeUpdate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new EmployeePayrollException("Oops there's an exception!");
			}
		return result;
	}

	private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) throws Exception {
		// TODO Auto-generated method stub
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		EmployeePayrollData empPayrollData = null;
		try {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double salary = resultSet.getDouble("salary");
				LocalDate startDate = resultSet.getDate("start").toLocalDate();
				empPayrollData = new EmployeePayrollData(id, name, salary, startDate);
				employeePayrollList.add(empPayrollData);
			}
			return employeePayrollList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EmployeePayrollException("Oops there's an exception!");
		}
	}

	private void prepareStatementForEmployeeData(String sql) throws Exception {
		Connection connection = this.getConnection();
		try {
			employeePayrollDataStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EmployeePayrollException("Oops there's an exception!");
		}
	}

	public List<EmployeePayrollData> getEmployeePayrollDataBasisDate(LocalDate date1, LocalDate date2)
			throws EmployeePayrollException, Exception {
		// TODO Auto-generated method stub
		List<EmployeePayrollData> employeePayrollList = null;
		if (this.employeePayrollDataStatement == null)
			try {
				String sql = "SELECT * FROM employee_payroll WHERE start BETWEEN CAST(? as date) and CAST(? as date)";
				this.prepareStatementForEmployeeData(sql);
				employeePayrollDataStatement.setDate(1, Date.valueOf(date1));
				employeePayrollDataStatement.setDate(2, Date.valueOf(date2));

				ResultSet resultSet = employeePayrollDataStatement.executeQuery();
				employeePayrollList = this.getEmployeePayrollData(resultSet);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new EmployeePayrollException("Oops there's an exception!");
			}
		return employeePayrollList;
	}

	public Map<String, Double> getAverageSalaryByGender() throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT gender, AVG(salary) as averageSalary from employee_payroll GROUP BY gender";
		Map<String, Double> genderAverageSalaryMap = new HashMap<>();
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String gender = resultSet.getString("gender");
				double salary = resultSet.getDouble("averageSalary");
				genderAverageSalaryMap.put(gender, salary);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EmployeePayrollException("Oops there's an exception here!");
		}

		return genderAverageSalaryMap;
	}

	public EmployeePayrollData addEmployeeToPayrollUC7(String name, double salary, LocalDate startDate, String gender)
			throws EmployeePayrollException, Exception {
		// TODO Auto-generated method stub
		int employeeId = -1;
		EmployeePayrollData employeePayrollData = null;
		String sql = String.format(
				"INSERT INTO employee_payroll (name, gender, salary, start) " + "VALUES ('%s', '%s', '%s', '%s')", name,
				gender, salary, Date.valueOf(startDate));
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if (rowAffected == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next())
					employeeId = resultSet.getInt(1);
			}
			employeePayrollData = new EmployeePayrollData(employeeId, name, salary, startDate);
		} catch (Exception e) {
			// TODO: handle exception
			throw new EmployeePayrollException("Oops there's an exception here!");

		}
		return employeePayrollData;
	}

	public EmployeePayrollData addEmployeeToPayrollUC8(String name, double salary, LocalDate startDate, String gender)
			throws EmployeePayrollException, Exception {
		int employeeId = -1;
		Connection connection = null;
		EmployeePayrollData employeePayrollData = null;
		try {
			connection = this.getConnection();
			connection.setAutoCommit(false);
		} catch (Exception e) {
			// TODO: handle exception
			throw new EmployeePayrollException("Oops there's an exception here!");

		}
		try (Statement statement = connection.createStatement();) {
			String sql = String.format(
					"INSERT INTO employee_payroll (name, gender, salary, start) " + "VALUES ('%s', '%s', '%s', '%s')",
					name, gender, salary, Date.valueOf(startDate));
			int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if (rowAffected == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next())
					employeeId = resultSet.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			connection.rollback();
			return employeePayrollData;
		}

		try (Statement statement = connection.createStatement();) {
			double deductions = salary * 0.2;
			double taxable_pay = salary - deductions;
			double tax = taxable_pay * 0.1;
			double net_pay = salary - tax;
			String sql = String.format("INSERT INTO payroll_details "
					+ "(employee_id, basic_pay, deductions, taxable_pay, tax, net_pay) VALUES "
					+ "( %s, %s, %s, %s, %s, %s )", employeeId, salary, deductions, taxable_pay, tax, net_pay);
			int rowAffected = statement.executeUpdate(sql);
			if (rowAffected == 1) {
				employeePayrollData = new EmployeePayrollData(employeeId, name, salary, startDate);
			}
		} catch (Exception e) {
			// TODO: handle exception
			connection.rollback();
			e.printStackTrace();
			throw new EmployeePayrollException("Oops there's an exception here!");
		}
		try {
			connection.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new EmployeePayrollException("Oops there's an exception here!");
		} finally {
			if (connection != null)
				connection.close();
		}
		return employeePayrollData;
	}

	public EmployeePayrollData addEmployeeToPayrollErDiagramModel(String name, double salary, LocalDate startDate,
			String gender, int companyId, int departmentId) throws EmployeePayrollException, Exception {
		int employeeId = -1;
		Connection connection = null;
		EmployeePayrollData employeePayrollData = null;
		if (!(companyId == 1 || companyId == 2) || !(departmentId == 1 || departmentId == 2)) {
			System.out.println("here,...");
			System.out.println("Oops! Check for the companyId or the departmentId you entered...");
			return null;
		}
		try {
			connection = this.getConnection();
			connection.setAutoCommit(false);
		} catch (Exception e) {
			// TODO: handle exception
			throw new EmployeePayrollException("Oops there's an exception here!");

		}
		try (Statement statement = connection.createStatement();) {
			String sql = String.format(
					"INSERT INTO employee_payroll (company_id, name, gender, salary, start) "
							+ "VALUES ('%s', '%s', '%s', '%s', '%s')",
					companyId, name, gender, salary, Date.valueOf(startDate));
			int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if (rowAffected == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next())
					employeeId = resultSet.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			connection.rollback();
			return employeePayrollData;
		}

		try (Statement statement = connection.createStatement();) {
			double deductions = salary * 0.2;
			double taxable_pay = salary - deductions;
			double tax = taxable_pay * 0.1;
			double net_pay = salary - tax;
			String sql = String.format("INSERT INTO payroll_details "
					+ "(employee_id, basic_pay, deductions, taxable_pay, tax, net_pay) VALUES "
					+ "( %s, %s, %s, %s, %s, %s )", employeeId, salary, deductions, taxable_pay, tax, net_pay);
			int rowAffected = statement.executeUpdate(sql);
			if (rowAffected == 1) {
				employeePayrollData = new EmployeePayrollData(employeeId, name, salary, startDate);
			}
		} catch (Exception e) {
			// TODO: handle exception
			connection.rollback();
			e.printStackTrace();
			throw new EmployeePayrollException("Oops there's an exception here!");
		}
		/////////////

		try (Statement statement = connection.createStatement();) {

			String sql = String.format(
					"INSERT INTO employee_department " + "(employee_id, department_id) VALUES " + "( %s, %s )",
					employeeId, departmentId);
			int rowAffected = statement.executeUpdate(sql);
			if (rowAffected == 1) {
				employeePayrollData = new EmployeePayrollData(employeeId, name, salary, startDate, companyId);
			}
		} catch (Exception e) {
			// TODO: handle exception
			connection.rollback();
			e.printStackTrace();
			throw new EmployeePayrollException("Oops there's an exception here!");
		}

		/////////////
		try {
			connection.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new EmployeePayrollException("Oops there's an exception here!");
		} finally {
			if (connection != null)
				connection.close();
		}
		return employeePayrollData;
	}
}
