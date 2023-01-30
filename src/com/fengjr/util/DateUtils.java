package com.fengjr.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtils {
	public static final String getDateTime(String aMask, Date aDate) {
		if (aDate == null) {
			return "";
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat(aMask);
			return dateFormat.format(aDate);
		}
	}
}
