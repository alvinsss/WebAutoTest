package com.fengjr.util;

import org.testng.annotations.Test;

import com.fengjr.util.*;
/**
 * redis链接测试类
 * 
 * @param String Key 
 * @author alvin
 * @version 1.0   
 * @since JDK 1.8
 */
@Test
public class LibRedisClientByKeyTest {

	public class LibRedisClientByKeyTestAction {
		@Test
	    public void main() {
	        // TODO Auto-generated method stub
	    	System.out.println("redis test");
//	        new LibRedisClientByKeys().show("B_LOAN_C47EF1B3-882E-4265-B51F-53809E4FF759"); 
	        LibRedisClientByKey redisClient = new LibRedisClientByKey();
	        
	        String getValues001 = redisClient.StringOperateBykey("B_LOAN_C47EF1B3-882E-4265-B51F-53809E4FF759");
	        String getValuesHmap002 = redisClient.HashOperateBykey("H_LOAN_C47EF1B3-882E-4265-B51F-53809E4FF759","status");
	        
	    	System.out.println("getValues001 is "+getValues001);
	    	System.out.println("getValues002 is "+getValuesHmap002);

	    	System.out.println("redis test over !");

	    }
	}


}
