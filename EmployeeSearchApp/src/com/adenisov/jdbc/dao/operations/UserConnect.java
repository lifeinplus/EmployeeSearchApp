package com.adenisov.jdbc.dao.operations;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.adenisov.jdbc.dao.connect.Connect;
import com.adenisov.jdbc.dao.parsers.ResultsParser;
import com.adenisov.jdbc.dao.querystock.UserQueryStock;
import com.adenisov.jdbc.model.User;
import com.adenisov.jdbc.util.PasswordUtils;

public class UserConnect extends Connect {

	private UserQueryStock stock;
	private ResultsParser parser;
	
	public UserConnect() throws ClassNotFoundException, IOException {
		super();
		stock = new UserQueryStock();
		parser = new ResultsParser();
	}

	private String getEncryptedPassword(int id) throws SQLException {
		
		String encryptedPassword = null;
		String sql = stock.getSelectPasswordByIdQuery();
		
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				statement.setInt(1, id);
				ResultSet resultSet = statement.executeQuery();
				encryptedPassword = parser.parsePassword(resultSet);
			}
		}
		return encryptedPassword;
	}

	private List<User> executeGetAllUsers(String sql) throws SQLException {
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				ResultSet resultSet = statement.executeQuery();
				return parser.parseUsers(resultSet);
			}
		}
	}

	private List<User> executeGetUserById(String sql, int userId) throws SQLException {
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				statement.setInt(1, userId);
				ResultSet resultSet = statement.executeQuery();
				return parser.parseUsers(resultSet);
			}
		}
	}

	private void executeAddUser(String sql, User user) throws SQLException {
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				statement.setString(1, user.getFirstName());
				statement.setString(2, user.getLastName());
				statement.setString(3, user.getEmail());
				statement.setBoolean(4, user.isAdmin());
				statement.setString(5, PasswordUtils.encryptPassword(user.getPassword()));
				statement.executeUpdate();
			}
		}
	}

	private void executeUpdateUser(String sql, User user) throws SQLException {
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				statement.setString(1, user.getFirstName());
				statement.setString(2, user.getLastName());
				statement.setString(3, user.getEmail());
				statement.setBoolean(4, user.isAdmin());
				statement.setInt(5, user.getId());
				statement.executeUpdate();
			}
		}
	}

	private void executeDeleteUser(String sql, int userId) throws SQLException {
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				statement.setInt(1, userId);
				statement.executeUpdate();
			}
		}
	}

	private void executeChangePassword(String sql, User user) throws SQLException {
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				statement.setString(1, PasswordUtils.encryptPassword(user.getPassword()));
				statement.setInt(2, user.getId());
				statement.executeUpdate();
			}
		}
	}

	public boolean authenticate(int userId, String plainPassword) throws SQLException {
		String encryptedPassword = getEncryptedPassword(userId);
		return PasswordUtils.checkPassword(plainPassword, encryptedPassword);
	}

	public List<User> getAllUsers() throws SQLException {
		String sql = stock.getSelectAllQuery();
		return executeGetAllUsers(sql);
	}

	public List<User> getUserById(int userId) throws SQLException {
		String sql = stock.getSelectByIdQuery();
		return executeGetUserById(sql, userId);
	}

	public void addUser(User user) throws SQLException {
		String sql = stock.getInsertQuery();
		executeAddUser(sql, user);
	}

	public void updateUser(User user) throws SQLException {
		String sql = stock.getUpdateQuery();
		executeUpdateUser(sql, user);
	}

	public void deleteUser(int userId) throws SQLException {
		String sql = stock.getDeleteUserQuery();
		executeDeleteUser(sql, userId);
	}

	public void changePassword(User user) throws SQLException {
		String sql = stock.getUpdatePasswordByIdQuery();
		executeChangePassword(sql, user);
	}

}
