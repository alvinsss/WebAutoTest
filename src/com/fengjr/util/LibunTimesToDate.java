package com.fengjr.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LibunTimesToDate {
	public String getDate(String unTimes) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
		Long time=new Long(unTimes);
		String d = format.format(time);
		Date date=format.parse(d);
		System.out.println("Format To String(Date):"+d);
		System.out.println("Format To Date:"+date);
		return d;
	}
	
	

}
