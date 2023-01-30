package com.fengjr.jsoup;

import org.testng.annotations.Test;
import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.Test;

/*
 * POST方式 + cookies
/*
loginName    loginName
password    111111
 * 
 * */

@Test
public class Test005_i {

	@Test
	public void testJsoup() {
			String testURL="http://test.fengjr.inc/account/invest";

		try {
			
			Connection  jconn = Jsoup.connect(testURL);
			jconn.data("loginName","alvin");
			jconn.data("password","psy025");
			jconn.timeout(3000);
		    Document login_post = jconn.post();	    
			System.out.println("doc_post" +login_post);

			jconn.method(Method.POST);
		    Response response = jconn.execute();
		    Map<String, String> cookies = response.cookies();
		    Document getInvest_get = Jsoup.connect("http://test.fengjr.inc/account/invest").cookies(cookies).timeout(30000).get();
			System.out.println("getInvest_get" +getInvest_get);

			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void main() {
		Test005_i t = new Test005_i();
		t.testJsoup();
	}
}
