package com.adenisov.jdbc.dao.querystock;

public class AuditHistoryQueryStock {

	/**
	 * SELECT history.user_id, history.employee_id, history.action, history.action_date_time, users.first_name, users.last_name<br/>
	 * FROM audit_history history, users users<br/>
	 * WHERE history.user_id = users.id AND history.employee_id = ?;
	 */
	public String getSelectFromAuditHistoryAndUsersQuery() {
		return "SELECT history.user_id, history.employee_id, history.action, history.action_date_time, users.first_name, users.last_name"
				+ " FROM audit_history history, users users"
				+ " WHERE history.user_id = users.id AND history.employee_id = ?;";
	}

	/**
	 * INSERT INTO audit_history (user_id, employee_id, action, action_date_time)<br/>
	 * VALUES (?, ?, ?, ?);
	 */
	public String getInsertAuditHistoryQuery() {
		return "INSERT INTO audit_history (user_id, employee_id, action, action_date_time) VALUES (?, ?, ?, ?);";
	}

	/** DELETE FROM audit_history WHERE employee_id = ?; */
	public String getDeletAuditHistoryQuery() {
		return "DELETE FROM audit_history WHERE employee_id = ?;";
	}

}
