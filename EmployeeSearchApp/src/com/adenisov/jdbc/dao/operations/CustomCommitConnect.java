package com.adenisov.jdbc.dao.operations;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.adenisov.jdbc.dao.connect.Connect;
import com.adenisov.jdbc.dao.querystock.EmployeeQueryStock;

public class CustomCommitConnect extends Connect {

	private EmployeeQueryStock stock;
	
	public CustomCommitConnect() throws ClassNotFoundException, IOException {
		super();
		stock = new EmployeeQueryStock();
	}

	private void updateDepartmentSalaries(String department, double salary) throws SQLException {

		int rowsAffected = 0;
		String sql = stock.getUpdateSalaryByDepartmentQuery();
		
		try (PreparedStatement statement = super.prepareStatement(sql)) {
			statement.setDouble(1, salary);
			statement.setString(2, department);
			rowsAffected = statement.executeUpdate();
		}
		System.out.println("INFO: Update complete. Rows affected: " + rowsAffected);
	}

	private void deleteDepartment(String department) throws SQLException {
		
		int rowsAffected = 0;
		String sql = stock.getDeleteByDepartmentQuery();
		
		try (PreparedStatement statement = super.prepareStatement(sql)) {
			statement.setString(1, department);
			rowsAffected = statement.executeUpdate();
		}
		System.out.println("\nINFO: Delete complete. Rows affected: " + rowsAffected);
	}

	private boolean askUserIfOkToSave() {
		
		System.out.println("\nIs it okay to save? y/n:");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		scanner.close();
		
		return input.equalsIgnoreCase("y");
	}

	public void deleteAndIncrease(String delete, String increase, double salary) {
		
		try (Connection connection = super.getConnection()) {
			connection.setAutoCommit(false);
			
			deleteDepartment(delete);
			updateDepartmentSalaries(increase, salary);
			
			boolean isOk = askUserIfOkToSave();
			
			if (isOk) {
				connection.commit();
				System.out.println("\nINFO: Transaction COMMITTED.\n");
			} else {
				connection.rollback();
				System.out.println("\nINFO: Transaction ROLLED BACK.\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
