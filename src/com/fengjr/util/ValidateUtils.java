package com.fengjr.util;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

public class ValidateUtils {
	public static String encryptPwd(String username, String pwd) {

		return DigestUtils.sha256Hex("color" + username + pwd);
	}
	

	public static void main(String[] args) {
		
		//System.out.println(validateEmail(" 5165437159qq.com "));
		System.out.println(ValidateUtils.encryptPwd("0010060118","111111"));
//		System.out.println(Double.parseDouble("23.00"));
	}
}
