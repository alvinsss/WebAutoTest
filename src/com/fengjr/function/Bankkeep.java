package com.fengjr.function;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Bankkeep {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BigDecimal d = new BigDecimal(10000);
		BigDecimal r = new BigDecimal(0.001875*3);
		BigDecimal i = d.multiply(r).setScale(2,RoundingMode.HALF_EVEN);
//		System.out.println("季利息："+i);
		
		BigDecimal test = new BigDecimal(0.03333333);
		System.out.println("银行家："+test.setScale(2, RoundingMode.HALF_EVEN));


	}

}
