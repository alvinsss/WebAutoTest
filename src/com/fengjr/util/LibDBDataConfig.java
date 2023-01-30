package com.fengjr.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**

 *
 */
public class LibDBDataConfig {
	
	private static LibDBDataConfig instance = null;
	private Properties properties = null;
	private static final LibLogger logger = LibLogger
	.getLogger(LibDBDataConfig.class);
	
	private LibDBDataConfig() {
		init();
	}
	
	public static LibDBDataConfig getInstance() {
		
		if (instance == null) {
			instance = new LibDBDataConfig();
		
		}
		return instance;
	}
	// init
	public void init(){
		
		try{
			properties = new Properties();
            InputStream is = new FileInputStream("src//com//fengjr//config//jdbc.properties");
			properties.load(is);
		}catch (Exception e){
			throw new RuntimeException("Failed to get testdata properties!");
		}
	}
	
	/**
	 *
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		return properties.getProperty(key);
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}
}
