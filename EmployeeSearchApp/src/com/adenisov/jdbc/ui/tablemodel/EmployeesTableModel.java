package com.adenisov.jdbc.ui.tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.adenisov.jdbc.model.Employee;

public class EmployeesTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = -8082508361177136037L;

	public static final int EMPLOYEE_OBJECT = -1;
	public static final int FIRST_NAME_COLUMN = 0;
	public static final int LAST_NAME_COLUMN = 1;
	public static final int EMAIL_COLUMN = 2;
	public static final int DEPARTMENT_COLUMN = 3;
	public static final int SALARY_COLUMN = 4;

	private String[] columnNames = {
			"First Name",
			"Last Name",
			"Email",
			"Department",
			"Salary"
	};
	
	private List<Employee> listOfEmployees;
	
	public EmployeesTableModel(List<Employee> listOfEmployees) {
		this.listOfEmployees = listOfEmployees;
	}

	@Override
	public int getRowCount() {
		return listOfEmployees.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Employee employee = listOfEmployees.get(rowIndex);
		
		switch (columnIndex) {
		case FIRST_NAME_COLUMN:
			return employee.getFirstName();
		case LAST_NAME_COLUMN:
			return employee.getLastName();
		case EMAIL_COLUMN:
			return employee.getEmail();
		case DEPARTMENT_COLUMN:
			return employee.getDepartment();
		case SALARY_COLUMN:
			return employee.getSalary();
		case EMPLOYEE_OBJECT:
			return employee;
		default:
			return employee.getLastName();
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

}
