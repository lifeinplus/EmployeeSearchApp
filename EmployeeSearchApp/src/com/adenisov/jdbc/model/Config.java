package com.adenisov.jdbc.model;

public class Config {

	private String url;
	private String user;
	private String password;
	private String driver;
	
	public Config() {
	}

	public Config(String url, String user, String password, String driver) {
		this.url = url;
		this.user = user;
		this.password = password;
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	@Override
	public String toString() {
		return "Config [url=" + url + ", user=" + user + ", password=" + password + ", driver=" + driver + "]";
	}
	
}
