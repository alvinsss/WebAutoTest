package com.fengjr.jsoup;

import org.testng.annotations.Test;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.Test;

/*
 * GET 方式
 *
 * */
@Test
public class Test002 {

	@Test
	public void testJsoup() {
			String testURL="http://test.fengjr.inc/financing/";
			
		try {
			
			Connection  jconn = Jsoup.connect( testURL+"list" );
//			System.out.println("Document is " + doc);
			
			jconn.data("type","yy");
		    Document doc_get = jconn.get();
			System.out.println(doc_get);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void main() {
		Test002 t = new Test002();
		t.testJsoup();
	}
}
