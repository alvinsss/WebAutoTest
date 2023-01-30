package com.fengjr.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**

 *
 */
public class LibDataConfig {
	
	private static LibDataConfig instance = null;
	private Properties properties = null;
	private static final LibLogger logger = LibLogger
	.getLogger(LibDataConfig.class);
	
	private LibDataConfig() {
		init();
	}
	
	public static LibDataConfig getInstance() {
		
		if (instance == null) {
			instance = new LibDataConfig();
		
		}
		return instance;
	}
	// init
	public void init(){
		
		try{
			properties = new Properties();
            InputStream is = new FileInputStream("src//com//fengjr//testdata//testdata.properties");
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
