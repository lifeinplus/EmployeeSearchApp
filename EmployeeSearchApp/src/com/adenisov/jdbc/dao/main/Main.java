package com.adenisov.jdbc.dao.main;

import java.io.IOException;
import java.sql.SQLException;

import com.adenisov.jdbc.dao.operations.CustomCommitConnect;
import com.adenisov.jdbc.dao.operations.LargeObjectConnect;

public class Main {

	private static void performCustomCommit() {
		
		CustomCommitConnect connect = null;
		try {
			connect = new CustomCommitConnect();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		String delete = "HR";
		String increase = "Engineering";
		double salary = 300000;
		System.out.printf("\n*** Delete %s department and increase %s salaries on $%.2f ***\n", delete, increase, salary);
		connect.deleteAndIncrease(delete, increase, salary);
	}

	private static void performLargeObjectOperations() {
		
		String email = "john.doe@foo.com";
		
		LargeObjectConnect connect = null;
		try {
			connect = new LargeObjectConnect();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("\n*** Storing TXT resume in database ***");
			connect.addTxtResume(email, "res\\sample_resume.txt");
			
			System.out.println("\n*** Storing PDF resume in database ***");
			connect.addPdfResume(email, "res\\sample_resume.pdf");
			
			System.out.println("\n*** Saving TXT resume to file from database ***");
			connect.getTxtResume(email, "res\\resume_from_db.txt");
			
			System.out.println("\n*** Saving PDF resume to file from database ***");
			connect.getPdfResume(email, "res\\resume_from_db.pdf");
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		performLargeObjectOperations();
		performCustomCommit();
	}

}
