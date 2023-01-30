package com.fengjr.util;
import java.math.BigDecimal;
public class LibKeepTwoDecimal {

	public static double KeepTwoDecimalAction(double v, int scale) { 
			if (scale < 0) { 	
				throw new IllegalArgumentException("规模必须是一个正整数或零"); //The scale must be a positive integer or zero	
			} 	
			BigDecimal b = new BigDecimal(Double.toString(v)); 	
			BigDecimal one = new BigDecimal("1"); 	
			return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		} 

}
