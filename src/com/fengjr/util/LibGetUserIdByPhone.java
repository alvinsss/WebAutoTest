package com.fengjr.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.testng.annotations.Test;
import com.fengjr.util.HttpUtils;
import com.fengjr.util.LibDataConfig;
import com.fengjr.util.LibJDBC;
import com.fengjr.util.LibLogger;

@Test
public class LibGetUserIdByPhone {

	public static String apiIp = null;
	Properties testdataconfig = LibDataConfig.getInstance().getProperties();
	private static final LibLogger logger = LibLogger
			.getLogger(LibGetUserIdByPhone.class);

	public String getUserIdByPhone(String userName)  {

		if (apiIp == null) {
			apiIp = testdataconfig.getProperty("testMURL.apiIp");
			// logger.log(" Auto API Test ENV  is : " + apiIp);
		}
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		String userId = null;
		try {
			conn = LibJDBC.getConnection();
			statement = conn.createStatement();
			String sql = "select user_id from users where phone_number='"
					+ userName + "'";
			// logger.log("sql is :" + sql);
			rs = statement.executeQuery(sql);
			if (rs.next()) {
				userId = rs.getString("user_id");
				// logger.log(" userId is " + userId);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			LibJDBC.release(rs, statement, conn);
		}
//		if (userId != null) {
//			return userId;
//		}
		return userId;
	}
}
