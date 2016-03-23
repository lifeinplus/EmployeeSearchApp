package com.adenisov.jdbc.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.adenisov.jdbc.dao.operations.DatabaseInfoConnect;
import com.adenisov.jdbc.model.ColumnInfo;
import com.adenisov.jdbc.ui.frame.MainFrame;

public class ColumnsInfoController {

	private MainFrame parent;
	private DatabaseInfoConnect databaseInfoConnect;

	public ColumnsInfoController(MainFrame parent) {
		this.parent = parent;
	}

	public List<ColumnInfo> getColumnsInfo() {
		List<ColumnInfo> columnsInfo = null;
		try {
			databaseInfoConnect = new DatabaseInfoConnect();
			columnsInfo = databaseInfoConnect.getColumnsInfo();
		} catch (SQLException | ClassNotFoundException | IOException e) {
			parent.showErrorMessage(e.getMessage(), "Error Retrieving Columns Info");
		}
		return columnsInfo;
	}

}
