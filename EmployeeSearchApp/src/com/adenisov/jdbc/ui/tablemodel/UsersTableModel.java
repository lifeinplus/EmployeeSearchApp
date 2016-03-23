package com.adenisov.jdbc.ui.tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.adenisov.jdbc.model.User;

public class UsersTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	
	public static final int USER_OBJECT = -1;
	public static final int FIRST_NAME_COLUMN = 0;
	public static final int LAST_NAME_COLUMN = 1;
	public static final int EMAIL_COLUMN = 2;
	public static final int ADMIN_COLUMN = 3;

	private String[] columnNames = {
			"First Name",
			"Last Name",
			"Email",
			"Admin"
	};
	
	private List<User> users;
	
	public UsersTableModel(List<User> users) {
		this.users = users;
	}

	@Override
	public int getRowCount() {
		return users.size();
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
		
		User user = users.get(rowIndex);
		
		switch (columnIndex) {
		case FIRST_NAME_COLUMN:
			return user.getFirstName();
		case LAST_NAME_COLUMN:
			return user.getLastName();
		case EMAIL_COLUMN:
			return user.getEmail();
		case ADMIN_COLUMN:
			return user.isAdmin();
		case USER_OBJECT:
			return user;
		default:
			return user.getLastName();
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

}
