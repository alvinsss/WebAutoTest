package com.fengjr.testcase;

import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.fengjr.util.HttpUtils;
import com.fengjr.util.LibDataConfig;
import com.fengjr.util.LibLogger;

@Test
public class Mode {

	public static String apiIp = null;
	Properties testdataconfig = LibDataConfig.getInstance().getProperties();
	private static final LibLogger logger = LibLogger.getLogger(Mode.class);

	public boolean testAction(String userName, String passwd) {

		if (apiIp == null) {
			apiIp = testdataconfig.getProperty("testMURL.apiIp");
			logger.log(" Auto API Test ENV  is : " + apiIp);
		}
		String actualResults = null;
		String callUrls = "loginUser.do";
		String auths = "info={'username':'15811297594','loginPwd':'111111'}&auth="
				+ "{'source':'7','vendor':'100000','osver':'1','appver':'1','version':'20'}";
		StringBuffer sbs = new StringBuffer();
		sbs.append(apiIp).append(callUrls).append("?");
		String results2 = HttpUtils.doPostBody(sbs.toString(), auths, null,
				"utf-8", false);
		logger.log("sbs.toString()"+sbs.toString());
		logger.log("auths"+auths);
		actualResults = results2.replace('\"', '\'');
		logger.log("resultInfo is :" + actualResults);
		return true;

	}

	@Test
	public void test_001() {
		Mode loginUser = new Mode();
		boolean loginUserResult = loginUser.testAction("15811297594", "111111");
		if (loginUserResult) {
			logger.log("loginUserResult Fail_passwd is ok !");
		}
		Assert.assertEquals(loginUserResult, true, " loginUserResult  Fail ！");

	}
}
