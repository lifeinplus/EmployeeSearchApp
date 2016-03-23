package com.adenisov.jdbc.dao.parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.adenisov.jdbc.model.AuditHistory;
import com.adenisov.jdbc.model.ColumnInfo;
import com.adenisov.jdbc.model.DatabaseInfo;
import com.adenisov.jdbc.model.Employee;
import com.adenisov.jdbc.model.User;

public class ResultsParser {

	private static final String ID_COLUMN = "id";
	private static final String FIRST_NAME_COLUMN = "first_name";
	private static final String LAST_NAME_COLUMN = "last_name";
	private static final String EMAIL_COLUMN = "email";
	private static final String DEPARTMENT_COLUMN = "department";
	private static final String ADMIN_COLUMN = "admin";
	private static final String SALARY_COLUMN = "salary";
	private static final String PASSWORD_COLUMN = "password";

	private void parseInputStream(FileOutputStream fileOutputStream, InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		while (inputStream.read(buffer) > 0) {
			fileOutputStream.write(buffer);
		}
	}

	private void parseReader(FileWriter fileWriter, Reader reader) throws IOException {
		int theChar;
		while ((theChar = reader.read()) > 0) {
			fileWriter.write(theChar);
		}
	}

	public List<User> parseUsers(ResultSet resultSet) throws SQLException {
		
		List<User> users = new ArrayList<>();
		
		while (resultSet.next()) {
			int id = resultSet.getInt(ID_COLUMN);
			String firstName = resultSet.getString(FIRST_NAME_COLUMN);
			String lastName = resultSet.getString(LAST_NAME_COLUMN);
			String email = resultSet.getString(EMAIL_COLUMN);
			boolean admin = resultSet.getBoolean(ADMIN_COLUMN);
			
			users.add(new User(id, firstName, lastName, email, admin));
		}
		return users;
	}

	public List<Employee> parseEmployees(ResultSet resultSet) throws SQLException {
		
		List<Employee> employees = new ArrayList<>();

		while (resultSet.next()) {
			int id = resultSet.getInt(ID_COLUMN);
			String firstName = resultSet.getString(FIRST_NAME_COLUMN);
			String lastName = resultSet.getString(LAST_NAME_COLUMN);
			String email = resultSet.getString(EMAIL_COLUMN);
			String department = resultSet.getString(DEPARTMENT_COLUMN);
			BigDecimal salary = resultSet.getBigDecimal(SALARY_COLUMN);
			
			employees.add(new Employee(id, firstName, lastName, email, department, salary));
		}
		return employees;
	}

	public ArrayList<String> parseDepartments(ResultSet resultSet) throws SQLException {

		ArrayList<String> departments = new ArrayList<>();
		
		while (resultSet.next()) {
			departments.add(resultSet.getString(DEPARTMENT_COLUMN));
		}
		
		return departments;
	}

	public String parsePassword(ResultSet resultSet) throws SQLException {
		String encryptedPassword = null;
		if (resultSet.next()) {
			encryptedPassword = resultSet.getString(PASSWORD_COLUMN);
		}
		return encryptedPassword;
	}

	public List<AuditHistory> parseAuditHistory(ResultSet resultSet) throws SQLException {
		
		List<AuditHistory> auditHistoryList = new ArrayList<>();
		
		while (resultSet.next()) {
			int userId = resultSet.getInt("history.user_id");
			int employeeId = resultSet.getInt("history.employee_id");
			String action = resultSet.getString("history.action");
			
			Timestamp timestamp = resultSet.getTimestamp("history.action_date_time");
			Date actionDateTime = new Date(timestamp.getTime());
			
			String userFirstName = resultSet.getString("users.first_name");
			String userLastName = resultSet.getString("users.last_name");
			
			auditHistoryList.add(new AuditHistory(userId, employeeId, action, actionDateTime, userFirstName, userLastName));
		}
		return auditHistoryList;
	}

	public DatabaseInfo parseDatabaseInfo(DatabaseMetaData metaData) throws SQLException {
		String productName = metaData.getDatabaseProductName();
		String productVersion = metaData.getDatabaseProductVersion();
		String driverName = metaData.getDriverName();
		String driverVersion = metaData.getDriverVersion();
		return new DatabaseInfo(productName, productVersion, driverName, driverVersion);
	}

	public List<ColumnInfo> parseColumnsInfo(ResultSetMetaData metaData) throws SQLException {
		
		List<ColumnInfo> columns = new ArrayList<>();

		for (int i = 1; i <= metaData.getColumnCount(); i++) {
			String name = metaData.getColumnName(i);
			String type = metaData.getColumnTypeName(i);
			boolean nullable = (metaData.isNullable(i) == 1) ? true : false;
			boolean autoIncrement = metaData.isAutoIncrement(i);
			columns.add(new ColumnInfo(name, type, nullable, autoIncrement));
		}
		return columns;
	}

	public void parsePdfResume(ResultSet resultSet, File file, String columnLabel)
			throws SQLException, IOException, FileNotFoundException {
		if (resultSet.next()) {
			try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
				try (InputStream inputStream = resultSet.getBinaryStream(columnLabel)) {
					if (inputStream != null) {
						parseInputStream(fileOutputStream, inputStream);
						System.out.println("INFO: Completed successfully! File path: " + file);
					} else {
						System.out.printf("INFO: The field %s does not contain data\n", columnLabel);
					}
				}
			}
		}
	}
	
	public void parseTxtResume(ResultSet resultSet, File file, String columnLabel) throws SQLException, IOException {
		if (resultSet.next()) {
			try (FileWriter fileWriter = new FileWriter(file)) {
				try (Reader reader = resultSet.getCharacterStream(columnLabel)) {
					if (reader != null) {
						parseReader(fileWriter, reader);
						System.out.println("INFO: Completed successfully! File path: " + file);
					} else {
						System.out.printf("INFO: The field %s does not contain data\n", columnLabel);
					}
				}
			}
		}
	}

}
