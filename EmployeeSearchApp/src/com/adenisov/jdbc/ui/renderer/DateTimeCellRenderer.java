package com.adenisov.jdbc.ui.renderer;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DateTimeCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (value instanceof Date) {
			value = dateFormat.format(value);
		}
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}

}
