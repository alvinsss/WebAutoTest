package com.fengjr.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;



public class LibUpdataPhone {
	
	public boolean updataPhone(String registerPhoneNumber){
	Connection conn = null;
	Statement statement = null;
	ResultSet rs = null;
	int userId = 0;
	try {
		conn = LibJDBC.getConnection();
		statement = conn.createStatement();
		String sql = "select user_id,phone_number from users where phone_number='"+registerPhoneNumber+"'";
		rs = statement.executeQuery(sql);
		if(rs.next()){
			userId = rs.getInt("user_id");
			if(userId == 0){
				return true;
			}
		}
	} catch (Exception e1) {
		e1.printStackTrace();
	}finally {
		LibJDBC.release(rs, statement, conn);
	}
	LibRandomPhoneNumber  RandomPhone = new LibRandomPhoneNumber();				
	String randomPhoneNumber = RandomPhone.getRandomPhoneNumber();		
	if(userId != 0){
		try {
			conn = LibJDBC.getConnection();
			statement = conn.createStatement();
			String sql = "update users set phone_number='"+randomPhoneNumber+"' where user_id="+userId;
			statement.execute(sql);
			
			String changesql = "select user_id,phone_number from users where phone_number='"+randomPhoneNumber+"'";
			rs = statement.executeQuery(changesql);
			if(rs.next()){
				userId = rs.getInt("user_id");
				if(userId != 0){
					return true;
				}
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally {
			LibJDBC.release(rs, statement, conn);
		}
	}
	return false;
	}

}
