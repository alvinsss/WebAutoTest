package com.fengjr.jsoup;

import org.testng.annotations.Test;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.Test;

/*
 * POST方式
/*
loginName    loginName
password    111111
 * 
 * */

@Test
public class Test003 {

	@Test
	public void testJsoup() {
			String testURL="http://test.fengjr.inc/login";

		try {
			
			Connection  jconn = Jsoup.connect( testURL);
//			System.out.println("Document is " + doc);
			

			jconn.data("loginName","alvin");
			jconn.data("password","psy025");
		    Document doc_post = jconn.post();
		    
			System.out.println("doc_post" +doc_post);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void main() {
		Test003 t = new Test003();
		t.testJsoup();
	}
}
