package com.fengjr.jsoup;

import org.testng.annotations.Test;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.Test;

/*
 * POST方式 + 超时
 *
/*
loginName    loginName
password    111111
 * 
 * */
@Test
public class Test004 {

	@Test
	public void testJsoup() {
			String testURL="http://test.fengjr.inc/login";

		try {
			
			Connection  jconn = Jsoup.connect( testURL);
//			System.out.println("Document is " + doc);
	
			jconn.data("loginName","alvin");
			jconn.data("password","psy025");
			//模拟超时
//			jconn.timeout(3000);
			jconn.timeout(30000);

		    Document doc_post = jconn.post();
		    
			System.out.println("doc_post" +doc_post);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void main() {
		Test004 t = new Test004();
		t.testJsoup();
	}
}
