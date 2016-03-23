package com.adenisov.jdbc.dao.querystock;

public class UserQueryStock {

	/**
	 * SELECT * FROM users ORDER BY last_name;
	 */
	public String getSelectAllQuery() {
		return "SELECT * FROM users ORDER BY last_name;";
	}

	/**
	 * SELECT * FROM users WHERE id = ? ORDER BY last_name;
	 */
	public String getSelectByIdQuery() {
		return "SELECT * FROM users WHERE id = ? ORDER BY last_name;";
	}

	/**
	 * SELECT password FROM users WHERE id = ?;
	 */
	public String getSelectPasswordByIdQuery() {
		return "SELECT password FROM users WHERE id = ?;";
	}

	/**
	 * INSERT INTO users (first_name, last_name, email, admin, password)<br/>
	 * VALUES (?, ?, ?, ?, ?);
	 */
	public String getInsertQuery() {
		return "INSERT INTO users (first_name, last_name, email, admin, password)"
				+ " VALUES (?, ?, ?, ?, ?);";
	}

	/**
	 * UPDATE users
	 * <br/>SET first_name = ?, last_name = ?, email = ?, admin = ?
	 * <br/>WHERE id = ?;
	 */
	public String getUpdateQuery() {
		return "UPDATE users"
				+ " SET first_name = ?, last_name = ?, email = ?, admin = ?"
				+ " WHERE id = ?;";
	}

	/**
	 * UPDATE users SET password = ? WHERE id = ?;
	 */
	public String getUpdatePasswordByIdQuery() {
		return "UPDATE users SET password = ? WHERE id = ?;";
	}

	/**
	 * DELETE FROM users WHERE id = ?;
	 */
	public String getDeleteUserQuery() {
		return "DELETE FROM users WHERE id = ?;";
	}

}
