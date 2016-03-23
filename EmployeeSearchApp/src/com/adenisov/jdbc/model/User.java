package com.adenisov.jdbc.model;

public class User {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private boolean admin;
	private String password;
	
	public User() {
	}

	public User(String firstName, String lastName, String email, boolean admin, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.admin = admin;
		this.password = password;
	}

	public User(int id, String firstName, String lastName, String email, boolean admin) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.admin = admin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}

	@Override
	public String toString() {
		return getFullName();
	}
	
}
