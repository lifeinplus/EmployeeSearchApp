package com.adenisov.jdbc.dao.operations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.adenisov.jdbc.dao.connect.Connect;
import com.adenisov.jdbc.dao.parsers.ResultsParser;
import com.adenisov.jdbc.dao.querystock.EmployeeQueryStock;

public class LargeObjectConnect extends Connect {

	private EmployeeQueryStock stock;
	private ResultsParser parser;
	
	public LargeObjectConnect() throws ClassNotFoundException, IOException {
		super();
		stock = new EmployeeQueryStock();
		parser = new ResultsParser();
	}

	private void executeGetPdfResume(String sql, File file, String columnLabel, String email)
			throws SQLException, IOException, FileNotFoundException {
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				statement.setString(1, email);
				ResultSet resultSet = statement.executeQuery();
				parser.parsePdfResume(resultSet, file, columnLabel);
			}
		}
	}

	private void executeGetTxtResume(String sql, File file, String columnLabel, String email)
			throws SQLException, IOException {
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				statement.setString(1, email);
				ResultSet resultSet = statement.executeQuery();
				parser.parseTxtResume(resultSet, file, columnLabel);
			}
		}
	}

	private void executeAddPdfResume(String sql, File file, String email)
			throws SQLException, IOException, FileNotFoundException {
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				try (FileInputStream fileInputStream = new FileInputStream(file)) {
					statement.setBinaryStream(1, fileInputStream);
					statement.setString(2, email);
					statement.executeUpdate();
				}
			}
		}
	}
	
	private void executeAddTxtResume(String sql, File file, String email)
			throws SQLException, IOException, FileNotFoundException {
		try (Connection connection = super.getConnection()) {
			try (PreparedStatement statement = super.prepareStatement(sql)) {
				try (FileReader fileReader = new FileReader(file)) {
					statement.setCharacterStream(1, fileReader);
					statement.setString(2, email);
					statement.executeUpdate();
				}
			}
		}
	}

	public void getPdfResume(String email, String resumePath) throws IOException, SQLException {
		String sql = stock.getSelectPdfResumeByEmailQuery();
		File file = new File(resumePath);
		String columnLabel = "pdf_resume";
		executeGetPdfResume(sql, file, columnLabel, email);
	}

	public void getTxtResume(String email, String resumePath) throws SQLException, IOException {
		String sql = stock.getSelectTxtResumeByEmailQuery();
		File file = new File(resumePath);
		String columnLabel = "txt_resume";
		executeGetTxtResume(sql, file, columnLabel, email);
	}

	public void addPdfResume(String email, String resumePath) throws SQLException, FileNotFoundException, IOException {
		String sql = stock.getUpdatePdfResumeByEmailQuery();
		File file = new File(resumePath);
		executeAddPdfResume(sql, file, email);
		System.out.println("INFO: Completed successfully! File path: " + file);
	}

	public void addTxtResume(String email, String resumePath) throws SQLException, FileNotFoundException, IOException {
		String sql = stock.getUpdateTxtResumeByEmailQuery();
		File file = new File(resumePath);
		executeAddTxtResume(sql, file, email);
		System.out.println("INFO: Completed successfully! File path: " + file);
	}

}
