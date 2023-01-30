package com.fengjr.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class LibRecharge {
	
	public void rechange(String userName , float money){
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		String residueMoney = null;
		String loanId = null;
		int updateResult = 0;
		
		try{
			conn = LibJDBC.getConnection();
			statement = conn.createStatement();
			String updateAccountSql = 
			"update account set acountBalance=acountBalance+'"+money+"',availableFunds=availableFunds+'"+money+"' where userid=(select user_id from users where phone_number='"+userName+"')";
			updateResult = statement.executeUpdate(updateAccountSql);
//			
//			while (rs.next()) {
//				residueMoney = rs.getString("residueMoney");	
//				loanId = rs.getString("loan_id");
//			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			LibJDBC.release(rs, statement, conn);
		}
	}
}
