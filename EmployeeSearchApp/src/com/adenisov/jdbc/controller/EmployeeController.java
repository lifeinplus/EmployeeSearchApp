package com.adenisov.jdbc.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.adenisov.jdbc.dao.operations.AuditHistoryConnect;
import com.adenisov.jdbc.dao.operations.DatabaseInfoConnect;
import com.adenisov.jdbc.dao.operations.EmployeeConnect;
import com.adenisov.jdbc.model.AuditHistory;
import com.adenisov.jdbc.model.DatabaseInfo;
import com.adenisov.jdbc.model.Employee;
import com.adenisov.jdbc.ui.dialog.AuditHistoryDialog;
import com.adenisov.jdbc.ui.dialog.EmployeeDialog;
import com.adenisov.jdbc.ui.dialog.IncreaseSalariesDialog;
import com.adenisov.jdbc.ui.frame.MainFrame;

public class EmployeeController {

	private MainFrame parent;
	private EmployeeConnect employeeConnect;
	private AuditHistoryConnect auditHistoryConnect;
	private DatabaseInfoConnect databaseInfoConnect;

	public EmployeeController(MainFrame parent) {
		this.parent = parent;
	}

	private List<Employee> getAllEmployees() throws SQLException {
		return employeeConnect.getAllEmployees();
	}

	private List<Employee> getEmployeesByLastName(String lastName) throws SQLException {
		return employeeConnect.getEmployeesByLastName(lastName);
	}

	private List<Employee> getEmployeesByDepartment(String department) throws SQLException {
		return employeeConnect.getEmployeesByDepartment(department);
	}

	private List<Employee> getEmployeesByLastNameAndDepartment(String lastName, String department) throws SQLException {
		return employeeConnect.getEmployeesByLastNameAndDepartment(lastName, department);
	}

	public void showDatabaseInfo() {
		try {
			databaseInfoConnect = new DatabaseInfoConnect();
			DatabaseInfo databaseInfo = databaseInfoConnect.getDatabaseInfo();
			parent.showInfoMessage(databaseInfo.toString(), "Database Info");
		} catch (SQLException | ClassNotFoundException | IOException e) {
			parent.showErrorMessage(e.getMessage(), "Error Retrieving Database Info");
		}
	}

	public List<Employee> getEmployees(String lastName, String department) {
		List<Employee> employees = null;
		try {
			employeeConnect = new EmployeeConnect();
			boolean lastNameEmpty = lastName.trim().length() == 0;
			boolean departmentEmpty = department.trim().length() == 0;
			
			/*
			 * Проверяем поля фамилии и департамента на содержимое
			 * – в зависимости от наличия обращаемся к базе данных.
			 * Если поля пустые, запрашиваем весь список.
			 */
			if (lastNameEmpty && departmentEmpty) {
				return getAllEmployees();
			} else if (departmentEmpty) {
				return getEmployeesByLastName(lastName);
			} else if (lastNameEmpty) {
				return getEmployeesByDepartment(department);
			} else {
				return getEmployeesByLastNameAndDepartment(lastName, department);
			}
		} catch (SQLException | ClassNotFoundException | IOException e) {
			parent.showErrorMessage(e.getMessage(), "Error Retrieving Employees");
		}
		return employees;
	}

	public int getCountEmployees(String department) {
		int count = 0;
		try {
			employeeConnect = new EmployeeConnect();
			count = employeeConnect.getCountEmployees(department);
		} catch (SQLException | ClassNotFoundException | IOException e) {
			parent.showErrorMessage(e.getMessage(), "Error Counting Employees");
		}
		return count;
	}

	public void showAuditHistory(Employee employee) {
		try {
			auditHistoryConnect = new AuditHistoryConnect();
			List<AuditHistory> auditHistoryList = auditHistoryConnect.getAuditHistory(employee.getId());
			AuditHistoryDialog dialog = new AuditHistoryDialog(parent);
			dialog.populate(employee.getFullName(), auditHistoryList);
			dialog.setVisible(true);
		} catch (SQLException | ClassNotFoundException | IOException e) {
			parent.showErrorMessage(e.getMessage(), "Error Retrieving Audit History");
		}
	}

	public void getDepartmentGreeting(String department) {
		try {
			employeeConnect = new EmployeeConnect();
			String message = employeeConnect.getDepartmentGreeting(department);
			parent.showInfoMessage(message, "Greet Department");
		} catch (SQLException | ClassNotFoundException | IOException e) {
			parent.showErrorMessage(e.getMessage(), "Error Greeting Department");
		}
	}

	public void addEmployee(int userId) {
		EmployeeDialog dialog = new EmployeeDialog(parent, userId);
		dialog.setVisible(true);
	}

	public void updateEmployee(int userId, Employee employee) {
		EmployeeDialog dialog = new EmployeeDialog(parent, userId, employee);
		dialog.setVisible(true);
	}

	public void increaseSalaries(int userId, String department) {
		IncreaseSalariesDialog dialog = new IncreaseSalariesDialog(parent, userId, department);
		dialog.setVisible(true);
	}

	public void deleteEmployee(Employee employee) {
		try {
			employeeConnect = new EmployeeConnect();
			employeeConnect.deleteEmployee(employee.getId());
			parent.showInfoMessage("Employee \"" + employee.getFullName() + "\" deleted successfully!", "Employee Deleted");
		} catch (SQLException | ClassNotFoundException | IOException e) {
			parent.showErrorMessage(e.getMessage(), "Error Deleting Employee");
		}
	}

}
