package com.fengjr.jsoup;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

class Test002_Thread extends Thread {

	private String name;
	static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式

	public Test002_Thread() {

	}

	public Test002_Thread(String name) {
		this.name = name;
	}

	public void run() {
		String testURL = "http://test.fengjr.inc/financing/";

		try {
			System.out.println("yy list 线程" + this.name + "开始执行时间："
					+ df.format(new Date()));
			Connection jconn = Jsoup.connect(testURL + "list");
			jconn.data("type", "yy");
			Document doc_get = jconn.get();
//			System.out.println(doc_get);

		} catch (IOException e) {
			e.printStackTrace();
		}finally{
		//TO DO
		}
		
	}

	public static void main(String[] args) {

		Test002_Thread t1 = new Test002_Thread("T1");
		Test002_Thread t2 = new Test002_Thread("T2");

		// t0.run();
		// t1.run();
		// t2.run();
		t1.start();
		t2.start();
		int i;
		for (i = 0; i < 10; i++) {
			Test002_Thread t0 = new Test002_Thread("T0");
			System.out.println("第几"+i+"执行");
			t0.start();
			System.out.println("All线程结束时间：" + df.format(new Date()));
		}

	}

}