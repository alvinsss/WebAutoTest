package com.fengjr.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import com.fengjr.util.IDCheckUtil;
import com.fengjr.util.BindInfo;
import com.fengjr.util.User;
import com.fengjr.util.IncomeService;

public class Tools {

	private static String usernameReg = ".{1,20}";
	private static String pwdReg = "\\w{8}";
	private static String emailReg = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+";
	private static String realnameReg = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+";
	private static String phoneNum="^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
	private static Logger logger = Logger.getLogger(Tools.class);
	public static boolean isNotNull(Object... s) {
		for (int i = 0; i < s.length; i++) {
			if (s[i] instanceof String) {
				if (null == s[i] || "".equals(s[i])) {
					return false;
				}
			} else if (s[i] instanceof BindInfo) {
				BindInfo bf = (BindInfo) s[i];
				if (null == bf.getCurrInfo() || "".equals(bf.getCurrInfo())
						|| null == bf.getRandomCode()
						|| "".equals(bf.getRandomCode())) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean registerUseEmail(User user, HttpSession session,
			ModelMap map) {
		if (!Pattern.matches(usernameReg, user.getUsername())) {
			map.addAttribute("usernameError", "用户名不能超过20位");
			return false;
		}
		
		if (!Pattern.matches(emailReg, user.getEmail())) {
			map.addAttribute("error", "邮箱格式不正确");
			return false;
		}
		
		return true;
	}
	

	public static boolean registerValidate(User user, HttpSession session,
			ModelMap map) {
		if (!Pattern.matches(usernameReg, user.getUsername())) {
			map.addAttribute("usernameError", "用户名不能超过20位");
			return false;
		}

		if (!Pattern.matches(pwdReg, user.getPassword())) {
			map.addAttribute("pwdError", "密码为8位数字和字母组合");
			return false;
		}

		// if (!Pattern.matches(emailReg, user.getEmail())) {
		// map.addAttribute("error", "邮箱格式不正确");
		// return false;
		// }

		// if (!user.getRandomCode().equalsIgnoreCase((String)
		// (session.getAttribute(Constants.SessionAttribute.RANDOM_CODE)))) {
		// map.addAttribute("error", "验证码输入错误");
		// return false;
		// }

		BindInfo bf = (BindInfo) session.getAttribute("bindInfo");
		String smsMobile = (String) session.getAttribute("smsMobile");

		if (!isNotNull(bf)
				|| !user.getRandomCode().equalsIgnoreCase(bf.getRandomCode())) {
			map.addAttribute("rdCodeError", "动态验证码输入错误");
			return false;
		}

		if (!isNotNull(user.getUserRole())) {
			map.addAttribute("roleError", "角色不能为空");
			return false;
		}

		if (!isNotNull(user.getPhone_number())) {
			map.addAttribute("mobileError", "手机号码不能为空");
			return false;
		}

		if (null != smsMobile && !smsMobile.equals(user.getPhone_number())) {
			map.addAttribute("mobileError", "手机号码不匹配");
			return false;
		}

		return true;
	}

	public static boolean smsValidate(ModelMap map, String mobile,
			String randomCode, HttpSession session) {
		BindInfo bf = (BindInfo) session.getAttribute("bindInfo");
		if (!isNotNull(bf) || !randomCode.equalsIgnoreCase(bf.getRandomCode())) {
			map.put("error", "验证码输入错误");
			return false;
		}

		String smsMobile = (String) session.getAttribute("smsMobile");
		if (null != smsMobile && null != mobile && !mobile.equals(smsMobile)) {
			map.put("error", "手机号不匹配");
			return false;
		}

		return true;
	}

	public static boolean smsValidateMobile(String mobile, String randomCode,
			HttpSession session) {
		BindInfo bf = (BindInfo) session.getAttribute("bindInfo");
		if (null == bf || null == randomCode || null == bf.getRandomCode()
				|| !randomCode.equalsIgnoreCase(bf.getRandomCode())) {

			return false;
		}

		String smsMobile = (String) session.getAttribute("smsMobile");
		if (null != smsMobile && null != mobile && !mobile.equals(smsMobile)) {
			return false;
		}

		return true;
	}

	public static boolean imgRandomCodeValidate(ModelMap map,
			String reqRandomCode, HttpSession session) {

		String checkcode_session = (String) session
				.getAttribute("checkcode_session");
		if (null == checkcode_session || null == reqRandomCode
				|| !reqRandomCode.equalsIgnoreCase(checkcode_session)) {
			map.put("error", "验证码输入错误");
			return false;
		}

		return true;
	}

	public static String encryptPwd(String username, String pwd) {

		return DigestUtils.sha256Hex("color" + username + pwd);
	}

	public static boolean add_investorValidate(User user, User dbUser,
			ModelMap map) {
		if (null == user || null == dbUser
				|| !user.getPhone_number().equals(dbUser.getPhone_number())) {
			map.put("error", "用户名和手机号不匹配，用户名和手机号必须与注册时保持一致");
			return false;
		}
		return true;
	}

	public static boolean updateDebtUserValidate(User user) {

		return true;
	}

	public static boolean addInvestmentValidate(String checkCode,
			HttpSession session) {

		return true;
	}
	
	public static String addStr(String old, int length) {
		String ren=old;
		int oldLength=old.length();
		if (oldLength>=length) {
			ren=old.substring(length-old.length(), length);
		}
		else {
			for (int i = oldLength; i < length; i++) {
				ren="0"+ren; 
			}
		}
		
		return ren;
	}
	
	

	
	
	/*  
	 * email格式是否正确
	 * param          email为电子邮箱
	 * return         格式正确返回true 否则返回false
	 * author         zhangxiao
	 * 
	 * */
  public  static boolean validateEmail(String email){
	  if(Pattern.matches(emailReg, email.trim())){
		  return true;
	  }	  
	  return false;
  }
  /*  
	 * 手机号码格式是否正确
	 * param          phoneNum为手机号
	 * return         格式正确返回true 否则返回false
	 * author         zhangxiao
	 * 
	 * */
  public  static boolean validatePhoneNum(String phoneNum){
	  
	  Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
	  
	  Matcher m = p.matcher(phoneNum.trim());
	  if(m.matches()){
		  return true;
		 
	  }	 
	  
	  logger.error("手机号校验错误     处理前："+phoneNum+"处理后： "+phoneNum.trim());
	  
	  return false;
  }

	public static void main(String[] args) {
		System.out.println(Tools.encryptPwd("weijinsuo","111111"));
	}
}
