package com.adenisov.jdbc.dao.connect;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.adenisov.jdbc.dao.parsers.PropertiesParser;
import com.adenisov.jdbc.model.Config;

public class Connect {

	private String url;
	private String user;
	private String password;
	private String driver;
	
	private Connection connection = null;
	private PropertiesParser parser = null;

	public Connect() throws ClassNotFoundException, IOException {
		parser = new PropertiesParser();
		assignConfig(parser.loadProperties());
		Class.forName(driver);
	}

	private void assignConfig(Config config) {
		url = config.getUrl();
		user = config.getUser();
		password = config.getPassword();
		driver = config.getDriver();
	}

	protected Connection getConnection() throws SQLException {
		return connection = DriverManager.getConnection(url, user, password);
	}

	protected PreparedStatement prepareStatement(String sql) throws SQLException {
		return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	}

	protected CallableStatement prepareCall(String sql) throws SQLException {
		return connection.prepareCall(sql);
	}

}
