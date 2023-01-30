package com.fengjr.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;


public class LibGetSmsMessage {
	public static String apiIp = null;
	Properties testdataconfig = LibDataConfig.getInstance().getProperties();
	private static final LibLogger logger = LibLogger
			.getLogger(LibGetSmsMessage.class);

	public String getSmsMessageAction(String userName) {
		if (apiIp == null) {
			apiIp = testdataconfig.getProperty("testMURL.apiIp");
//			logger.log(" Auto API Test ENV  is : " + apiIp);
		}
		String actualResults;
		String checkCode = null;

		String callUrlsgetSmsMessge = "getSmsMessge.do";
		String auths = "info={'mobile':'"
				+ userName
				+ "','smstype':' 1'}&auth={'source':'7','vendor':'100000','osver':'1','appver':'1','version':'20'}";
		StringBuffer sbs = new StringBuffer();
		sbs.append(apiIp).append(callUrlsgetSmsMessge).append("?");
		String results2 = HttpUtils.doPostBody(sbs.toString(), auths, null,
				"utf-8", false);
		actualResults = results2.replace('\"', '\'');
		// logger.log("resultInfo is :" + actualResults);

		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			conn = LibJDBC.getConnection();
			statement = conn.createStatement();
			String sql = "SELECT smsContext from smsinfo where smsPhone='"
					+ userName + "' ORDER BY smsId desc LIMIT 1";
			logger.log("sql: " + sql);

			rs = statement.executeQuery(sql);
			while (rs.next()) {
				checkCode = rs.getString("smsContext");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LibJDBC.release(rs, statement, conn);
		}
		checkCode = checkCode.substring(4);
		logger.debug("checkCode is " + checkCode);

		return checkCode;
	}
}
