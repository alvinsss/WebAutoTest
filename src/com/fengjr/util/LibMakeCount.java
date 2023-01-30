package com.fengjr.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class LibMakeCount {
	
	/** 
	* 提供精确的小数位四舍五入处理。 
	* @param v 需要四舍五入的数字 
	* @param scale 小数点后保留几位 
	* @return 四舍五入后的结果 
	*/ 
	public static double round(double v, int scale) { 
		if (scale < 0) { 	
			throw new IllegalArgumentException("规模必须是一个正整数或零"); //The scale must be a positive integer or zero	
		} 	
		BigDecimal b = new BigDecimal(Double.toString(v)); 	
		BigDecimal one = new BigDecimal("1"); 	
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue(); 
	} 
	
	/**
	 * 
	 * @param month	要增加的月数，month为0时返回系统时间
	 * @return 		返回n个月后的时间戳
	 */
	public static String getTimestamp(int month){
		
		if(month<1){	//返回当前系统时间的时间戳
			return System.currentTimeMillis()+"";
		}
		
	    GregorianCalendar grc=new GregorianCalendar();
	    grc.add(GregorianCalendar.MONTH,month);		//获取n个月后的日期
        String datetime = new SimpleDateFormat("yyyy-MM-dd H:m").format(grc.getTime()); 
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:m"); 
        Date dt = null;
		try {
			dt = formatter.parse(datetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		return dt.getTime()+"";
	}
	
	/** 
	* 提供精确的加法运算。 
	* @param v1 被加数 
	* @param v2 加数 
	* @return 两个参数的和 
	*/ 
	public static double add(double v1, double v2) { 
		BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
		BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
		return b1.add(b2).doubleValue(); 
	} 
	
	/** 
	* 提供精确的减法运算。 
	* @param v1 被减数 
	* @param v2 减数 
	* @return 两个参数的差 
	*/ 
	public static double sub(double v1, double v2) { 
		BigDecimal b1 = new BigDecimal(Double.toString(v1)); 	
		BigDecimal b2 = new BigDecimal(Double.toString(v2)); 	
		return b1.subtract(b2).doubleValue(); 
	} 
	
	/**
	 * 生成随机手机号
	 * @return String类型
	 */
	public static String randomPhoneNumber(){
		String prefix = "12";
		Random random = new Random();
		int num = random.nextInt(999999999);
		String randomPhoneNumber = prefix + String.valueOf(num);	
		return randomPhoneNumber;
	}
	
	/**
	 * 生成随机身份证号
	 * @return String类型
	 */
	public static String randomIdCard(){
		Random random = new Random();
		int num1 = random.nextInt(999999999);
		int num2 = random.nextInt(999999999);
		String randomIdCard = String.valueOf(num1) + String.valueOf(num2);
		return randomIdCard;
	}
		
	/**
	 * 生成随机银行卡号
	 * @return String类型
	 */
	public static String randomCardNumber(){
		Random random = new Random();
		int num1 = random.nextInt(999999999);
		int num2 = random.nextInt(999999999);
		String randomCardNumber = String.valueOf(num1) + String.valueOf(num2);
		return randomCardNumber;
	}
}
