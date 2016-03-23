package com.adenisov.jdbc.model;

import java.util.Date;

public class AuditHistory {

	private int userId;
	private int employeeId;
	private String action;
	private Date actionDateTime;
	
	private String userFirstName;
	private String userLastName;
	
	public AuditHistory() {
	}

	public AuditHistory(int userId, int employeeId, String action, Date actionDateTime, String userFirstName,
			String userLastName) {
		super();
		this.userId = userId;
		this.employeeId = employeeId;
		this.action = action;
		this.actionDateTime = actionDateTime;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getActionDateTime() {
		return actionDateTime;
	}

	public void setActionDateTime(Date actionDateTime) {
		this.actionDateTime = actionDateTime;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserFullName() {
		return getUserFirstName() + " " + getUserLastName();
	}
	
	@Override
	public String toString() {
		return "AuditHistory [userId=" + userId + ", employeeId=" + employeeId + ", action=" + action
				+ ", actionDateTime=" + actionDateTime + ", userFirstName=" + userFirstName + ", userLastName="
				+ userLastName + "]";
	}
	
}
