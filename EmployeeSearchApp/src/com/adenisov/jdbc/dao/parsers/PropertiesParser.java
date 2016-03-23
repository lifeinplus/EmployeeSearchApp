package com.adenisov.jdbc.dao.parsers;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.adenisov.jdbc.model.Config;

public class PropertiesParser {

	private static final String URL = "url";
	private static final String USER = "user";
	private static final String PASSWORD = "password";
	private static final String DRIVER = "driver";
	private static final String PROPERTIES_FILE_PATH = "res\\config.properties";
	
	private Properties properties;
	private Config config;
	
	public PropertiesParser() {
		properties = new Properties();
	}
	
	public Config loadProperties() throws IOException {
		
		File file = new File(PROPERTIES_FILE_PATH);
		
		if (file.exists() && !file.isDirectory()) {
			
			try (FileReader reader = new FileReader(file)) {
				properties.load(reader);
				String url = properties.getProperty(URL);
				String user = properties.getProperty(USER);
				String password = properties.getProperty(PASSWORD);
				String driver = properties.getProperty(DRIVER);
				config = new Config(url, user, password, driver);
			}
		}
		return config;
	}

}
