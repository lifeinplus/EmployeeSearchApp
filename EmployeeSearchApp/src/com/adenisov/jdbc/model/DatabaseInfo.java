package com.adenisov.jdbc.model;

public class DatabaseInfo {

	private String productName;
	private String productVersion;
	private String driverName;
	private String driverVersion;
	
	public DatabaseInfo() {
	}
	
	public DatabaseInfo(String productName, String productVersion, String driverName, String driverVersion) {
		this.productName = productName;
		this.productVersion = productVersion;
		this.driverName = driverName;
		this.driverVersion = driverVersion;
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductVersion() {
		return productVersion;
	}
	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverVersion() {
		return driverVersion;
	}
	public void setDriverVersion(String driverVersion) {
		this.driverVersion = driverVersion;
	}

	@Override
	public String toString() {
		return "Product Name: " + productName
				+ "\nProduct Version: " + productVersion
				+ "\nDriver Name: " + driverName
				+ "\nDriver Version: " + driverVersion;
	}
	
}
