package com.adenisov.jdbc.dao.operations;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import com.adenisov.jdbc.dao.connect.Connect;
import com.adenisov.jdbc.dao.parsers.ResultsParser;
import com.adenisov.jdbc.dao.querystock.EmployeeQueryStock;
import com.adenisov.jdbc.model.ColumnInfo;
import com.adenisov.jdbc.model.DatabaseInfo;

public class DatabaseInfoConnect extends Connect {

	private EmployeeQueryStock stock;
	private ResultsParser parser;
	
	public DatabaseInfoConnect() throws ClassNotFoundException, IOException {
		super();
		stock = new EmployeeQueryStock();
		parser = new ResultsParser();
	}

	private DatabaseInfo executeGetDatabaseInfo() throws SQLException {
		try (Connection connection = super.getConnection()) {
			DatabaseMetaData metaData = connection.getMetaData();
			return parser.parseDatabaseInfo(metaData);
		}
	}
	
	private List<ColumnInfo> executeGetColumnsInfo(String sql) throws SQLException {
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				ResultSet resultSet = statement.executeQuery();
				ResultSetMetaData metaData = resultSet.getMetaData();
				return parser.parseColumnsInfo(metaData);
			}
		}
	}

	public DatabaseInfo getDatabaseInfo() throws SQLException {
		return executeGetDatabaseInfo();
	}

	public List<ColumnInfo> getColumnsInfo() throws SQLException {
		String sql = stock.getSelectAllEmployeesQuery();
		return executeGetColumnsInfo(sql);
	}

}
