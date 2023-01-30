package com.fengjr.users;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.fengjr.util.*;

@Test
public class TCA001LoginUser {
	String test;
	public static String apiIp = null;
	public static String auths = null;
	Properties testdataconfig = LibDataConfig.getInstance().getProperties();
	private static final LibLogger logger = LibLogger
			.getLogger(TCA001LoginUser.class);

	@BeforeClass
	public void setUp() throws Exception {
		logger.log(" Auto Test Data Init ! ");

		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		int rs_del = 0;

		try {
			conn = LibJDBC.getConnection();
			statement = conn.createStatement();
			logger.log("sql exec");
//			String sql1 = "UPDATE  users set  phone_number=13611293208 ,password='fcbdf647ea2ae6bff9c859e0403cd219648349d27e11d9fc0304c0d681538eb4' WHERE  user_id='3618'";
//			rs_del = statement.executeUpdate(sql1);
//
//			String sql2 = "UPDATE  users set phone_number=15811297594 , password='1b94d3fef4fc0a7b5707b761e7fbd74b8e0ebfaf14caa013c9c763ff02f85552'  WHERE  user_id='2335'";
//			rs_del = statement.executeUpdate(sql2);
//
//			String sql3 = "DELETE from users WHERE phone_number in (13800000000,13800000001,13500000000,13500000001,15800000000,15800000001)";
//			rs_del = statement.executeUpdate(sql3);
////			logger.log("sql3 is :"+sql3);
//			String sql4 = "DELETE from user_upgrade WHERE userid in (select user_id from  users where phone_number=13800000001)";
//			rs_del = statement.executeUpdate(sql4);
//
//			String sql5 = "DELETE from  card_infomation WHERE card_number in ('6226900716487356','6225551373096278','6226900716487352','6226900716487351')";
//			rs_del = statement.executeUpdate(sql5);
//			
//			String sql6 = "UPDATE  users set  phone_number=174641 ,password='a34f3b961fb17c53391f4b2f7bbdee14d1b31ab376258aa0e8a496e8f90d1117' WHERE  user_id='174641'";
//			rs_del = statement.executeUpdate(sql6);
//			
//			String sql7 = "UPDATE  users set  phone_number=173750,password='fe5fc3fa63091bf4cfe6ecc01ea4b869884ffdd6634b7a69445455c360f83ab3' WHERE  user_id='173750'";
//			rs_del = statement.executeUpdate(sql7);
//			
//			String sql8 = "UPDATE  users set  phone_number=16162,password='02d8def811687c4d8e0573d5b67ddc9d731460b6b7059bdbd65bbd38959888c1' WHERE  user_id='16162'";
//			rs_del = statement.executeUpdate(sql8);
//						
//			String sql9 = "DELETE from account_log WHERE orderid=2014121210125600";
//			rs_del = statement.executeUpdate(sql9);
//			
//			String sql10 = "UPDATE  users set  phone_number=7393 ,password='4feb2de1f8e2a162377abf640662d260427de7af44aabc9eba0ff9294150d4f8' WHERE  user_id='7393'";
//			rs_del = statement.executeUpdate(sql10);
//			
//			String sql11 = "UPDATE  users set  phone_number=963 ,password='c4b7c0174471234f99efff27b5bdef6ef285f6c5e137c91b90607904228102c4' WHERE  user_id='963'";
//			rs_del = statement.executeUpdate(sql11);
			
			
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			LibJDBC.release(rs, statement, conn);
		}
	}

	public String gettest() {
		return test;
	}

	public String getLoginResultInfo(String userName, String passwd) {

		if (apiIp == null) {
			apiIp = testdataconfig.getProperty("testMURL.apiIp");
			logger.log(" Auto API Test ENV  is : " + apiIp);
		}
		String client_id = "bddc1dbb-0fe9-44ef-a3dc-6bdce55bd95e";
		// String apiIp="http://115.28.36.45:85/";
		String actualResults;
		String callUrls = "v2/token";
//		String auths = "'+&+''client_id':'"
//				+ client_id
//				+ "','loginPwd':'"
//				+ passwd
//				+ "'}&auth="
//				+ "{'source':'7','vendor':'100000','osver':'1','appver':'1','version':'20'}";
		
		String auths = "'{'client_id':'bddc1dbb-0fe9-44ef-a3dc-6bdce55bd95e','client_secret':'118b58a26b5759bc68db33f196430d567ec4fd03e38a105cf8e6c8b75964a950'," +
				"'grant_type':'password','username':'"+userName+"','password':'"+passwd+"'}'";
		
		StringBuffer sbs = new StringBuffer();
		sbs.append(apiIp).append(callUrls).append("?");
		logger.log("A IS " +sbs.toString());
		logger.log("B IS " +auths);
		String results2 = HttpUtils.doPostBody(sbs.toString(), auths, null,
				"utf-8", false);
		actualResults = results2.replace('\"', '\'');
		return actualResults;
	}

//	public String getUserSession(String userName, String passwd) {
//		TCA001LoginUser LoginUser = new TCA001LoginUser();
//		String actualResults = LoginUser.getLoginResultInfo(userName, passwd);
//		logger.log("Login actualResults is:" + actualResults);
//		JSONObject dataJson = JSONObject.fromObject(actualResults);
//		String resultInfo = dataJson.getString("result");
//
//		JSONObject resultInfoBody = JSONObject.fromObject(resultInfo);
//		String userSession = resultInfoBody.getString("userSession");
//		// logger.log("resultInfo is :" +resultInfo );
//		if( userSession.equals("null")){
//		logger.log("userSession is :" + userSession);
//		return null;
//		}
//		return userSession;
//	}

	// public String getVipUserClientManager(String userName ,String passwd) {
	// TCA001LoginUser LoginUser = new TCA001LoginUser();
	// String actualResults=LoginUser.getLoginResultInfo(userName,passwd);
	// logger.log("Login actualResults is:"+actualResults);
	// JSONObject dataJson = JSONObject.fromObject(actualResults);
	// String resultInfo = dataJson.getString("result");
	// JSONObject resultInfoBody = JSONObject.fromObject(resultInfo);
	// String userSession = resultInfoBody.getString("userSession");
	// logger.log("resultInfo is :" +resultInfo );
	// logger.log("userSession is :" +userSession );
	// return userSession;
	// }
//	public String getUserId(String userName, String passwd) {
//		TCA001LoginUser LoginUser = new TCA001LoginUser();
//		String actualResults = LoginUser.getLoginResultInfo(userName, passwd);
//		// logger.log("替换之后" + actualResults);
//		JSONObject dataJson = JSONObject.fromObject(actualResults);
//		String resultInfo = dataJson.getString("result");
//		JSONObject resultInfoBody = JSONObject.fromObject(resultInfo);
//		String userId = resultInfoBody.getString("userId");
//		return userId;
//	}
//
//
//	public boolean loginUser(String userName, String passwd) {
//		if (apiIp == null) {
//			apiIp = testdataconfig.getProperty("testMURL.apiIp");
//			logger.log(" apiIp is : " + apiIp);
//		}
//		// String apiIp="http://115.28.36.45:85/";
//		String actualResults;
//		String callUrls = "loginUser.do";
//		String auths = "info={'username':'"
//				+ userName
//				+ "','loginPwd':'"
//				+ passwd
//				+ "'}&auth="
//				+ "{'source':'7','vendor':'100000','osver':'1','appver':'1','version':'20'}";
//		StringBuffer sbs = new StringBuffer();
//		sbs.append(apiIp).append(callUrls).append("?");
//		logger.log("sbs.toString() :"+sbs.toString());
//		String results2 = HttpUtils.doPostBody(sbs.toString(), auths, null,
//				"utf-8", false);
//		actualResults = results2.replace('\"', '\'');
//		logger.log("LoginUser Results is " + actualResults);
//		boolean contains = actualResults.indexOf("登录成功") != -1;
//		logger.log(" LoginResult is " + contains);
//		return contains;
//	}

//	public boolean loginUserAll(String userName, String passwd) {
//		if (apiIp == null) {
//			apiIp = testdataconfig.getProperty("testMURL.apiIp");
//			logger.log(" apiIp is : " + apiIp);
//		}
//		// String apiIp="http://115.28.36.45:85/";
//		String actualResults;
//		String callUrls = "loginUser.do";
//		String auths = "info={'username':'"
//				+ userName
//				+ "','loginPwd':'"
//				+ passwd
//				+ "'}&auth="
//				+ "{'source':'7','vendor':'100000','osver':'1','appver':'1','version':'20'}";
//		StringBuffer sbs = new StringBuffer();
//		sbs.append(apiIp).append(callUrls).append("?");
//		String results2 = HttpUtils.doPostBody(sbs.toString(), auths, null,
//				"utf-8", false);
//		actualResults = results2.replace('\"', '\'');
//		JSONObject dataJson = JSONObject.fromObject(actualResults);
//		String resultInfo = dataJson.getString("result");
//		JSONObject resultInfoBody = JSONObject.fromObject(resultInfo);
//		String userId = resultInfoBody.getString("userId");
//		String username = resultInfoBody.getString("username");
//		String phone = resultInfoBody.getString("phone");
//		String email = resultInfoBody.getString("email");
//		String userLevel = resultInfoBody.getString("userLevel");
//		String realName = resultInfoBody.getString("realName");
//		String totalInvest = resultInfoBody.getString("totalInvest");
//
//		Connection conn = null;
//		Statement statement = null;
//		ResultSet rs = null;
//		String userIddb = null;
//		String usernamedb = null;
//		String phonedb = null;
//		String emaildb = null;
//		String userLeveldb = null;
//		String realNamedb = null;
//		String accountStatusdb = null;
//		String totalInvestdb = null;
//
//		try {
//			conn = LibJDBC.getConnection();
//			statement = conn.createStatement();
//			String sqluserId = "select user_id,phone_number from users where phone_number='"
//					+ userName + "'";
//			logger.log("sqluserId is:" + sqluserId);
//
//			rs = statement.executeQuery(sqluserId);
//			if (rs.next()) {
//				userIddb = rs.getString("user_id");
//				logger.log("userIddb is:" + userIddb);
//			}
//			if (userIddb != null) {
//				String sqluserinfo = "select username,phone_number,email,user_level,realName from users where user_id='"
//						+ userIddb + "'";
//				logger.log("sqlusername is:" + sqluserinfo);
//
//				rs = statement.executeQuery(sqluserinfo);
//				if (rs.next()) {
//					usernamedb = rs.getString("username");
//					phonedb = rs.getString("phone_number");
//					emaildb = rs.getString("email");
//					userLeveldb = rs.getString("user_level");
//					realNamedb = rs.getString("realName");
//
//					// logger.log("usernamedb is:" + usernamedb);
//					// logger.log("phonedb is:" + phonedb);
//					// logger.log("emaildb is:" + emaildb);
//					// logger.log("userIddb is:" + userIddb);
//					// logger.log("userLeveldb is:" + userLeveldb);
//					// logger.log("realNamedb is:" + realNamedb);
//				}
//				String sqltotalInvest = "SELECT sum(amountOfInvest) as amountOfInvest  from investment WHERE userId='"
//						+ userIddb + "'";
//				logger.log("sqltotalInvest is:" + sqltotalInvest);
//
//				rs = statement.executeQuery(sqltotalInvest);
//				if (rs.next()) {
//					totalInvestdb = rs.getString("amountOfInvest");
//					logger.log("totalInvestdb is:" + totalInvestdb);
//				}
//
//			}
//
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		} finally {
//			LibJDBC.release(rs, statement, conn);
//		}
//		// phonedb = "10";
//		// LibKeepTwoDecimal.KeepTwoDecimalAction(Double.parseDouble(totalInvest),
//
//		// LibKeepTwoDecimal.KeepTwoDecimalAction(Double.parseDouble(totalInvestdb),
//
//		if (LibKeepTwoDecimal.KeepTwoDecimalAction(
//				Double.parseDouble(totalInvest), 2) == LibKeepTwoDecimal
//				.KeepTwoDecimalAction(Double.parseDouble(totalInvestdb), 2)) {
//			logger.log("Json totalInvest , DB totalInvestdb 一致");
//			if (userId.equals(userIddb) && (username.equals(usernamedb))
//					&& (phone.equals(phonedb)) && (email.equals(emaildb))
//					&& (userLevel.equals(userLeveldb))
//					&& (realName.equals(realNamedb))) {
//				return true;
//			}
//		} else {
//			logger.log("Json ， DB  不一致");
//			return false;
//		}
//		return false;
//	}
//
//	@Test
//	public void loginUserSuccess() {
//		TCA001LoginUser LoginUser = new TCA001LoginUser();
//		boolean LoginUserResult = LoginUser.loginUser("15811297594", "111111");
//		if (!LoginUserResult) {
//			logger.log("LoginUserResult is Success !");
//		}
//		Assert.assertEquals(LoginUserResult, true, " LoginUserResult is Fail ！");
//
//	}
//				"'grant_type':'password','username':'wlc','password':'psy025'}'";

	@Test
	public void loginUserSuccessALL() {
		TCA001LoginUser LoginUser = new TCA001LoginUser();
		String LoginUserResult = LoginUser.getLoginResultInfo("wlc","psy025");
		if (LoginUserResult != "") {
			logger.log("LoginUserResult is Success !");
		}
		Assert.assertEquals(LoginUserResult, true, " LoginUserResult is Fail ！");

	}
//
//	@Test
//	public void loginUserFail_passwd() {
//		TCA001LoginUser LoginUser = new TCA001LoginUser();
//		boolean LoginUserResult = LoginUser.loginUser("15811297594", "111111");
//		if (!LoginUserResult) {
//			logger.log("LoginUserResult Fail_passwd is ok !");
//		}
//		Assert.assertEquals(LoginUserResult, true,
//				" LoginUserFail_passwd is Fail ！");
//
//	}
//
//	@Test
//	public void loginUserAgain() {
//		TCA001LoginUser LoginUser = new TCA001LoginUser();
//		boolean LoginUserResult = LoginUser.loginUser("15811297594", "111111");
//		if (LoginUserResult) {
//			logger.log("LoginUserResult is Success !");
//		}
//		Assert.assertEquals(LoginUserResult, true,
//				" LoginUserSuccessAgain is Fail ！");
//
//	}
//
//	@Test
//	public void loginUserFailPasswdIsNull() {
//		TCA001LoginUser LoginUser = new TCA001LoginUser();
//		boolean LoginUserResult = LoginUser.loginUser("15811297594", "");
//		if (LoginUserResult) {
//			logger.log("LoginUserFailPasswdIsNull is ok  !");
//		}
//		Assert.assertEquals(!LoginUserResult, true,
//				" LoginUserFailPasswdIsNull is Fail ！");
//
//	}
//
//	@Test
//	public void loginUserFailNamePasswdIsNull() {
//		TCA001LoginUser LoginUser = new TCA001LoginUser();
//		boolean LoginUserResult = LoginUser.loginUser("", "");
//		if (LoginUserResult) {
//			logger.log("LoginUserFailNamePasswdIsNull is ok  !");
//		}
//		Assert.assertEquals(!LoginUserResult, true,
//				" LoginUserFailNamePasswdIsNull is Fail ！");
//
//	}
	
//	@Test
//	public void userSessionTest() {
//		TCA001LoginUser LoginUser = new TCA001LoginUser();
//		String LoginUserResult = LoginUser.getUserSession("15811297594", "222222");
//		if (LoginUserResult.equals("null")) {
//			logger.log("ss is ok  !");
//		}
//		logger.log("LoginUserResult is:"+LoginUserResult);
//
//	}
}
