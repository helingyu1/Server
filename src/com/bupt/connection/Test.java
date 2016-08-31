package com.bupt.connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;


public class Test {
	private static final String path = "config/DB.properties";

	/**
	 * @param args
	 * @throws URISyntaxException
	 * @throws IOException 
	 */
	public static void main(String[] args) throws URISyntaxException, IOException {
		Properties prop = new Properties();
//		String propPath = Test.class.getClassLoader().getResource("").toURI()
//				.getPath()
//				+ path;
		FileInputStream fis = new FileInputStream(new File(path));
		prop.load(fis);
		System.out.println(prop.getProperty("jdbcDriver"));
		System.out.println(prop.getProperty("dbUrl"));
		System.out.println(prop.getProperty("username"));
		System.out.println(prop.getProperty("password"));		

	}

}
