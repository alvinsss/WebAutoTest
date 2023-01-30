package com.fengjr.util;

import java.io.*;

public class UseResourceTest {
//	public static void main(String[] args) throws Exception {
//		UseResourceTest test = new UseResourceTest();
//		InputStream inStream = UseResourceTest.class.getResourceAsStream("jdbc.properties");
//		InputStream inStream = UseResourceTest.class.getResourceAsStream("testdata.properties");
//		InputStream inStream = UseResourceTest.class.getResourceAsStream("jdbc.properties");
//		InputStream inStream = UseResourceTest.class.getClassLoader().getResourceAsStream("com/mfexchange/config/jdbc.properties");
//		InputStream inStream = UseResourceTest.class.getClassLoader().getResourceAsStream("/com/mfexchange/config/testdata.properties");
//		InputStream inStream = UseResourceTest.class.getClassLoader().getResourceAsStream("jdbc.properties");

//		System.out.println(inStream);
//	}
//	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		try{
			InputStream in = new FileInputStream("src//com//mfexchange//config//jdbc.properties");	
		}catch (Exception e){
			throw new RuntimeException("Failed to get testdata properties!");
		}finally{
			System.out.println("InputStream");		
		}
	}
}