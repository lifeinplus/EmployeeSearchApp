package com.adenisov.jdbc.util;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class PasswordUtils {

	private static StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
	
	public static boolean checkPassword(String plainPassword, String encryptedPassword) {
		return encryptor.checkPassword(plainPassword, encryptedPassword);
	}

	public static String encryptPassword(String password) {
		return encryptor.encryptPassword(password);
	}

}
