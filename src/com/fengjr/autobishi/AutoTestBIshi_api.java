package com.fengjr.autobishi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.fengjr.util.HttpUtils;
import com.fengjr.util.LibDataConfig;
import com.fengjr.util.LibLogger;

@Test
public class AutoTestBIshi_api {

//	public static String apiIp = null;
	String apiIp ="http://www.xxx.com/";
	Properties testdataconfig = LibDataConfig.getInstance().getProperties();
	private static final LibLogger logger = LibLogger
			.getLogger(AutoTestBIshi_api.class);

	public boolean regUser(String userName, String passwd ) {
		if (apiIp == null) {
			apiIp = testdataconfig.getProperty("testMURL.apiIp");
		}
		String actualResults;
		String callUrlsgetSmsMessge = "api/v2/register";
		// 接口文档的参数
		String auths = "info={'loginName':'"+ userName+ "','passwd':'"+ passwd + "','mobile_captcha':'1234','mobile':'15400000011'}";
		StringBuffer sbs = new StringBuffer();
		sbs.append(apiIp).append(callUrlsgetSmsMessge).append("?");
		String results2 = HttpUtils.doPostBody(sbs.toString(), auths, null,
				"utf-8", false);
		actualResults = results2.replace('\"', '\'');
		logger.log("resultInfo is :" + actualResults);
			
		boolean regUserResult = actualResults.indexOf("登录成功") != -1;
		return  regUserResult;
	}

	@Test
	// 通过传用户名与密码，考虑各种异常测试
	public void addUsersS() {
		AutoTestBIshi_api AddUser = new AutoTestBIshi_api();
		boolean addUsersSOKResult = AddUser.regUser("alvin","123456");
		if (addUsersSOKResult) {
			logger.log(" addUsersSOKResult is ok !");
		}
		Assert.assertEquals( addUsersSOKResult , true ,"注册失败或已被注册！" );
	}
}