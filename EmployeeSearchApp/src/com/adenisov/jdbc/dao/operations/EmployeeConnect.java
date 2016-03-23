package com.adenisov.jdbc.dao.operations;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.adenisov.jdbc.dao.connect.Connect;
import com.adenisov.jdbc.dao.parsers.ResultsParser;
import com.adenisov.jdbc.dao.querystock.QueryStock;
import com.adenisov.jdbc.model.Employee;

public class EmployeeConnect extends Connect {

	private QueryStock stock;
	private ResultsParser parser;
	private AuditHistoryConnect auditHistoryConnect;
	
	public EmployeeConnect() throws ClassNotFoundException, IOException {
		super();
		stock = new QueryStock();
		parser = new ResultsParser();
	}

	private void getGeneratedId(PreparedStatement statement, Employee employee) throws SQLException {
		ResultSet generatedKeys = statement.getGeneratedKeys();
		if (generatedKeys.next()) {
			employee.setId(generatedKeys.getInt(1));
		} else {
			throw new SQLException("Error generating key for Employee.");
		}
	}

	private List<Employee> executeGetAllEmployees(String sql) throws SQLException {
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				ResultSet resultSet = statement.executeQuery();
				return parser.parseEmployees(resultSet);
			}
		}
	}

	private List<Employee> executeGetEmployeesByDepartment(String sql, String department) throws SQLException {
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				statement.setString(1, department);
				ResultSet resultSet = statement.executeQuery();
				return parser.parseEmployees(resultSet);
			}
		}
	}

	private List<Employee> executeGetEmployeesByLastNameAndDepartment(String sql, String lastName, String department)
			throws SQLException {
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				statement.setString(1, lastName);
				statement.setString(2, department);
				ResultSet resultSet = statement.executeQuery();
				return parser.parseEmployees(resultSet);
			}
		}
	}

	private ArrayList<String> executeGetDepartments(String sql) throws SQLException {
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				ResultSet resultSet = statement.executeQuery();
				return parser.parseDepartments(resultSet);
			}
		}
	}

	private int executeGetCountEmployees(String sql, String department) throws SQLException {
		try (Connection connection = super.getConnection()) {
			try (CallableStatement statement = super.prepareCall(sql)) {
				statement.setString(1, department);
				statement.registerOutParameter(2, Types.INTEGER);
				statement.execute();
				return statement.getInt(2);
			}
		}
	}

	private String executeGreetDepartment(String sql, String department) throws SQLException {
		try (Connection connection = super.getConnection()) {
			try (CallableStatement statement = super.prepareCall(sql)) {
				statement.registerOutParameter(1, Types.VARCHAR);
				statement.setString(1, department);
				statement.execute();
				return statement.getString(1);
			}
		}
	}

	private void executeAddEmployee(String sql, Employee employee) throws SQLException {
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				statement.setString(1, employee.getFirstName());
				statement.setString(2, employee.getLastName());
				statement.setString(3, employee.getEmail());
				statement.setString(4, employee.getDepartment());
				statement.setBigDecimal(5, employee.getSalary());
				statement.executeUpdate();
				
				getGeneratedId(statement, employee);
			}
		}
	}

	private void executeUpdateEmployee(String sql, Employee employee) throws SQLException {
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				statement.setString(1, employee.getFirstName());
				statement.setString(2, employee.getLastName());
				statement.setString(3, employee.getEmail());
				statement.setString(4, employee.getDepartment());
				statement.setBigDecimal(5, employee.getSalary());
				statement.setInt(6, employee.getId());
				statement.executeUpdate();
			}
		}
	}

	private void executeIncreaseSalaries(String sql, String department, double increaseAmount)
			throws SQLException {
		try (Connection connection = super.getConnection()) {
			try (CallableStatement statement = super.prepareCall(sql)) {
				statement.setString(1, department);
				statement.setDouble(2, increaseAmount);
				statement.execute();
			}
		}
	}

	private void executeDeleteEmployee(String sql, int employeeId) throws SQLException {
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				statement.setInt(1, employeeId);
				statement.executeUpdate();
			}
		}
	}

	public List<Employee> getAllEmployees() throws SQLException {
		String sql = stock.getSelectAllEmployeesQuery();
		return executeGetAllEmployees(sql);
	}

	public List<Employee> getEmployeesByLastName(String lastName) throws SQLException {
		String sql = stock.getSelectAllLikeLastNameQuery();
		lastName += "%";
		return executeGetEmployeesByDepartment(sql, lastName);
	}

	public List<Employee> getEmployeesByDepartment(String department) throws SQLException {
		String sql = stock.getSelectAllLikeDepartmentQuery();
		department += "%";
		return executeGetEmployeesByDepartment(sql, department);
	}

	public List<Employee> getEmployeesByLastNameAndDepartment(String lastName, String department) throws SQLException {
		String sql = stock.getSelectAllLikeLastNameAndDepartmentQuery();
		lastName += "%";
		department += "%";
		return executeGetEmployeesByLastNameAndDepartment(sql, lastName, department);
	}

	public ArrayList<String> getDepartments() throws SQLException {
		String sql = stock.getSelectDepartmentsQuery();
		return executeGetDepartments(sql);
	}

	public int getCountEmployees(String department) throws SQLException {
		String sql = stock.getCountEmployeesProcedure();
		return executeGetCountEmployees(sql, department);
	}

	public String getDepartmentGreeting(String department) throws SQLException {
		String sql = stock.getDepartmentGreetingProcedure();
		return executeGreetDepartment(sql, department);
	}

	public void addEmployee(int userId, Employee employee) throws SQLException, ClassNotFoundException, IOException {
		String sql = stock.getInsertEmployeeQuery();
		executeAddEmployee(sql, employee);
		auditHistoryConnect = new AuditHistoryConnect();
		auditHistoryConnect.addAuditHistoryForEmployee(userId, employee, "Added New Employee.");
	}

	public void updateEmployee(int userId, Employee employee) throws SQLException, ClassNotFoundException, IOException {
		String sql = stock.getUpdateEmployeeQuery();
		executeUpdateEmployee(sql, employee);
		auditHistoryConnect = new AuditHistoryConnect();
		auditHistoryConnect.addAuditHistoryForEmployee(userId, employee, "Updated Employee.");
	}

	public void increaseSalaries(int userId, String department, double increaseAmount) throws SQLException, ClassNotFoundException, IOException {
		String sql = stock.getIncreaseSalariesProcedure();
		executeIncreaseSalaries(sql, department, increaseAmount);
		auditHistoryConnect = new AuditHistoryConnect();
		List<Employee> employees = getEmployeesByDepartment(department);
		auditHistoryConnect.addAuditHistoryForEmployees(userId, employees, "Increase Salary.");
	}

	public void deleteEmployee(int employeeId) throws SQLException, ClassNotFoundException, IOException {
		auditHistoryConnect = new AuditHistoryConnect();
		auditHistoryConnect.deleteAuditHistory(employeeId);
		String sql = stock.getDeleteEmployeeQuery();
		executeDeleteEmployee(sql, employeeId);
	}

}
