package com.fengjr.util;

import java.math.BigDecimal;

public class LibStringToTwoDecimal {

	public static String StringToTwoDecimalAction(String number) {
		if (number.length() >= 3) {
			// String number = "100211";
			String number_b = number.substring(number.length() - 2,
					number.length());
			String number_a = number.substring(0, number.length() - 2);
			String numberFix = number_a + "." + number_b;
			return numberFix;

		} else {
			String numberFix = "0." + number;
			return numberFix;
		}
	}
}