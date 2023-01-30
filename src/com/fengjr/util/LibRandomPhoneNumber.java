package com.fengjr.util;
import java.util.Random;
public class LibRandomPhoneNumber {
	/**
	 * 生成随机手机号
	 * @return String类型
	 */
	public static String getRandomPhoneNumber(){
		String prefix = "12";
		Random random = new Random();
		int num = random.nextInt(999999999);
		String randomPhoneNumber = prefix + String.valueOf(num);	
		return randomPhoneNumber;
	}

}
