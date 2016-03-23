package com.adenisov.jdbc.ui.tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.adenisov.jdbc.model.AuditHistory;

public class AuditHistoryTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 5307681063541895761L;

	public static final int AUDIT_HISTORY_OBJECT = -1;
	public static final int ACTION_DATE_TIME_COLUMN = 0;
	public static final int ACTION_COLUMN = 1;
	public static final int USERNAME_COLUMN = 2;
	
	private List<AuditHistory> listOfAuditHistory;
	
	private String[] columnNames = {
			"Date/Time",
			"Action",
			"Username"
	};
	
	public AuditHistoryTableModel(List<AuditHistory> listOfAuditHistory) {
		this.listOfAuditHistory = listOfAuditHistory;
	}

	@Override
	public int getRowCount() {
		return listOfAuditHistory.size();
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
		
		AuditHistory auditHistory = listOfAuditHistory.get(rowIndex);
		
		switch (columnIndex) {
		case ACTION_DATE_TIME_COLUMN:
			return auditHistory.getActionDateTime();
		case ACTION_COLUMN:
			return auditHistory.getAction();
		case USERNAME_COLUMN:
			return auditHistory.getUserFullName();
		case AUDIT_HISTORY_OBJECT:
			return auditHistory;
		default:
			return auditHistory.getUserLastName();
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

}
