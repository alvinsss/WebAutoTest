package com.fengjr.jsoup;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.Test;

/*
 * 用Jsoup做接口测试的相关知识点发出来给大家参考下,输出结果就是当前页面的Document对象，然后再解析这个页面对象就可以了。
 * */
@Test
public class Test001 {

	public void testJsoup() {
			String testURL="http://mybeta.fengjr.com/corporate/login";
			
		try {
			
			Document doc = Jsoup.connect(
					testURL).get();
			System.out.println("Document is " + doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void main() {
		Test001 t = new Test001();
		t.testJsoup();
	}
}
