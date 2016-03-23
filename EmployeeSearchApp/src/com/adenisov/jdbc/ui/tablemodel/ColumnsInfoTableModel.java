package com.adenisov.jdbc.ui.tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.adenisov.jdbc.model.ColumnInfo;

public class ColumnsInfoTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private static final int COLUMN_INFO_OBJECT = -1;
	private static final int NAME_COLUMN = 0;
	private static final int TYPE_COLUMN = 1;
	private static final int NULLABLE_COLUMN = 2;
	private static final int AUTO_INCREMENT_COLUMN = 3;
	
	private String[] columnNames = {
			"Name",
			"Type",
			"Nullable",
			"Auto Increment"
	};
	
	private List<ColumnInfo> columnsInfoList;
	
	public ColumnsInfoTableModel(List<ColumnInfo> columnsInfoList) {
		this.columnsInfoList = columnsInfoList;
	}

	@Override
	public int getRowCount() {
		return columnsInfoList.size();
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
		
		ColumnInfo columnInfo = columnsInfoList.get(rowIndex);
		
		switch (columnIndex) {
		case NAME_COLUMN:
			return columnInfo.getName();
		case TYPE_COLUMN:
			return columnInfo.getType();
		case NULLABLE_COLUMN:
			return columnInfo.isNullable();
		case AUTO_INCREMENT_COLUMN:
			return columnInfo.isAutoIncrement();
		case COLUMN_INFO_OBJECT:
			return columnInfo;
		default:
			return columnInfo.getName();
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

}
