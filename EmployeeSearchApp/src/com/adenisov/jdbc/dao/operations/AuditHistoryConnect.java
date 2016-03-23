package com.adenisov.jdbc.dao.operations;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.adenisov.jdbc.dao.connect.Connect;
import com.adenisov.jdbc.dao.parsers.ResultsParser;
import com.adenisov.jdbc.dao.querystock.AuditHistoryQueryStock;
import com.adenisov.jdbc.model.AuditHistory;
import com.adenisov.jdbc.model.Employee;

public class AuditHistoryConnect extends Connect {

	private AuditHistoryQueryStock stock;
	private ResultsParser parser;
	
	public AuditHistoryConnect() throws ClassNotFoundException, IOException {
		super();
		stock = new AuditHistoryQueryStock();
		parser = new ResultsParser();
	}

	private void executeUpdate(PreparedStatement statement, int userId, int employeeId, String action)
			throws SQLException {
		statement.setInt(1, userId);
		statement.setInt(2, employeeId);
		statement.setString(3, action);
		statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
		statement.executeUpdate();
	}

	private List<AuditHistory> executeGetAuditHistory(String sql, int employeeId) throws SQLException {
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				statement.setInt(1, employeeId);
				ResultSet resultSet = statement.executeQuery();
				return parser.parseAuditHistory(resultSet);
			}
		}
	}

	private void executeAddAuditHistoryForEmployee(String sql, int userId, Employee employee, String action) throws SQLException {
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				executeUpdate(statement, userId, employee.getId(), action);
			}
		}
	}

	private void executeAddAuditHistoryForEmployees(String sql, int userId, List<Employee> employees, String action) throws SQLException {
		// TODO Auto-generated method stub
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				for (Employee employee : employees) {
					executeUpdate(statement, userId, employee.getId(), action);
				}
			}
		}
	}

	private void executeDeleteAuditHistory(String sql, int employeeId) throws SQLException {
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				statement.setInt(1, employeeId);
				statement.executeUpdate();
			}
		}
	}

	public List<AuditHistory> getAuditHistory(int employeeId) throws SQLException {
		String sql = stock.getSelectFromAuditHistoryAndUsersQuery();
		return executeGetAuditHistory(sql, employeeId);
	}

	public void addAuditHistoryForEmployee(int userId, Employee employee, String action) throws SQLException {
		String sql = stock.getInsertAuditHistoryQuery();
		executeAddAuditHistoryForEmployee(sql, userId, employee, action);
	}

	public void addAuditHistoryForEmployees(int userId, List<Employee> employees, String action) throws SQLException, ClassNotFoundException, IOException {
		String sql = stock.getInsertAuditHistoryQuery();
		executeAddAuditHistoryForEmployees(sql, userId, employees, action);
	}

	public void deleteAuditHistory(int employeeId) throws SQLException {
		String sql = stock.getDeletAuditHistoryQuery();
		executeDeleteAuditHistory(sql, employeeId);
	}

}
