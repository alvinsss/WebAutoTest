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
public class TCA002Fundsflow {
	String test;
	public static String apiIp = null;
	public static String auths = null;
	Properties testdataconfig = LibDataConfig.getInstance().getProperties();
	private static final LibLogger logger = LibLogger
			.getLogger(TCA002Fundsflow.class);

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
					
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			LibJDBC.release(rs, statement, conn);
		}
	}

	public String gettest() {
		return test;
	}

	public String getFundsflowInfo() {

		if (apiIp == null) {
			apiIp = testdataconfig.getProperty("testMURL.apiIp");
			logger.log(" Auto API Test ENV  is : " + apiIp);
		}
		String actualResults;
		String callUrls = "v2/user/MYSELF/funds";
		String auths = "'{'allOperation':'false','allStatus':'false'," +
				"'endDate':'1425615271349','page':'1','pageSize':'20','startDate':'1369843200000','type':'ALL'}'";
		
		StringBuffer sbs = new StringBuffer();
		sbs.append(apiIp).append(callUrls).append("?");
		logger.log("A IS " +sbs.toString());
		logger.log("B IS " +auths);
		String results2 = HttpUtils.doPostBody(sbs.toString(), auths, null,
				"utf-8", false);
		actualResults = results2.replace('\"', '\'');
		return actualResults;
	}
	@Test
	public void loginUserSuccessALL() {
		TCA002Fundsflow fundsflow = new TCA002Fundsflow();
		String getFundsflowInfoResult = fundsflow.getFundsflowInfo();
		if (getFundsflowInfoResult != "") {
			logger.log("getFundsflowInfoResult is Success !");
		}
		Assert.assertEquals(getFundsflowInfoResult, true, " getFundsflowInfoResult is Fail ！");

	}
}
