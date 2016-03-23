package com.adenisov.jdbc.model;

public class ColumnInfo {

	private String name;
	private String type;
	private boolean nullable;
	private boolean autoIncrement;
	
	public ColumnInfo() {
	}

	public ColumnInfo(String name, String type, boolean nullable, boolean autoIncrement) {
		this.name = name;
		this.type = type;
		this.nullable = nullable;
		this.autoIncrement = autoIncrement;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	@Override
	public String toString() {
		return "ColumnInfo [name=" + name + ", type=" + type + ", nullable=" + nullable + ", autoIncrement="
				+ autoIncrement + "]";
	}
	
}
