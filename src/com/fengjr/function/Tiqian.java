package com.fengjr.function;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import net.sf.json.JSONObject;
import java.util.Date;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.fengjr.util.*;

@Test
public class Tiqian {
	String test;
	public static String apiIp = null;
	public static String auths = null;
	Properties testdataconfig = LibDataConfig.getInstance().getProperties();
	private static final LibLogger logger = LibLogger.getLogger(Tiqian.class);

	// 提前还款的金额=当期还款本息+剩余本金+提前还款罚息。
	// 当期还款本息=原还款计划中当天应还款的本金+利息。
	// 剩余本金=还未归还给投资人的本金。从下一次还款日计算。
	// 提前还款罚息=以后每期的应还利息总和，的一定比例。罚息比例为20%，15%或者0。
	// @BeforeClass
	public String GetTiqianInfo_Total(String id) throws Exception {

		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		ResultSet rs_benji = null;
		int rs_del = 0;
		int daysValue = 0;
		int loan_years = 0;
		int loan_months = 0;
		int loan_days = 0;
		int loan_qishu = 0;
		int weihaikuanCounts = 0;
		int faixiBili6 = 60;
		int faixiBiliD6 = 55;

		String methodKey = null;
		String benjiValue = "";
		String lixiValue = "";
		String lixi_a = "";
		String lixi_b = "";
		String wlixiValue = "";
		String qishu = "";
		String dataIiqianValue = "";
		String equalInstallmentValues = "";

		String loan_title = "";
		String loan_id = "";
		String loan_amount = "";

		float faixiValue = 0.00f;
		float lixiValue_a = 0.00f;
		float lixiValue_b = 0.00f;

		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
		String day_now = dateFormat.format(now);

		// System.out.println(day_now);

		try {
			conn = LibJDBC.getConnection();
			statement = conn.createStatement();
			// logger.log("sql get rep info ");
			String sql = "SELECT METHOD,TITLE,AMOUNT,ID,YEARS*12 as YEARSAll ,MONTHS,DAYS FROM TB_LOAN  WHERE ID='"
					+ id + "'";
			logger.log("sql get rep info " + sql);
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				methodKey = rs.getString("METHOD");
				loan_amount = rs.getString("AMOUNT");
				loan_id = rs.getString("ID");
				loan_years = rs.getInt("YEARSAll");
				loan_months = rs.getInt("MONTHS");
				loan_days = rs.getInt("DAYS");
				logger.log("还款方式:" + methodKey);
			}
			loan_qishu = loan_years + loan_months;
			logger.log("get loan_qishu info --- " + loan_qishu);

		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			// LibJDBC.release(rs, statement, conn);
		}

		if (methodKey.equals("MonthlyInterest")) {

			String sql_benji = "SELECT sum(r.AMOUNTPRINCIPAL) as total_benjin , count(*) as counts  from TB_LOAN_REPAYMENT r WHERE  r.LOAN_ID='"
					+ id + "'  and r.status='UNDUE'";
			logger.log("get sql_benji info --- " + sql_benji);
			rs = statement.executeQuery(sql_benji);
			while (rs.next()) {
				benjiValue = rs.getString("total_benjin");
				qishu = rs.getString("counts");
				logger.log("还款本金:" + benjiValue + "未还期数:" + qishu);
			}

			String sql_lixi = "SELECT r.DUEDATE as dateaction , r.AMOUNTINTEREST as  lixi_a   from TB_LOAN_REPAYMENT r WHERE  r.LOAN_ID='"
					+ id
					+ "'  and r.status='UNDUE' ORDER BY r.CURRENTPERIOD LIMIT 1;";
			logger.log("get sql_lixi info --- " + sql_lixi);
			rs = statement.executeQuery(sql_lixi);
			while (rs.next()) {
				dataIiqianValue = rs.getString("dateaction");
				lixi_a = rs.getString("lixi_a");
				logger.log("lixi_a:" + lixi_a + "最近需要还款日期:" + dataIiqianValue);
			}

			String str = dataIiqianValue;
			Date dt = dateFormat.parse(str);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			// rightNow.add(Calendar.YEAR,+1);//日期加1年
			rightNow.add(Calendar.MONTH, -1);// 日期减1个月
			Date dt1 = rightNow.getTime();
			String reStr = dateFormat.format(dt1);

			Date fDate = dateFormat.parse(reStr); //   计算日期
			Date oDate = dateFormat.parse(day_now); // 目前日期
//			Date oDate = dateFormat.parse("2015-06-10"); // 目前日期
			
			System.out.println("最近计算开始日期" + reStr);
			System.out.println("最近计算结束日期" + day_now);

			LibGetDayValue LibGetDayValue = new LibGetDayValue();
			daysValue = LibGetDayValue.getDaysBetween(fDate, oDate);
			logger.log("lixi_daysValue is : " + daysValue); // 计利天数

			if (daysValue <= 30 && daysValue >= 0) {
				lixiValue_a = Float.parseFloat(lixi_a) / 30 * daysValue;
				logger.log("应计利息  : " + lixiValue_a);
			} else if (daysValue > 30) {
				logger.log("最近计算日期大于30天，不符合产品" + daysValue);
			} else {
				lixiValue_a = 0.00f;
			}

			String sql_wlixiValue = "SELECT   sum(r.AMOUNTINTEREST) as wlixi   from TB_LOAN_REPAYMENT r WHERE  r.LOAN_ID='"
					+ id + "' and r.status='UNDUE' AND r.CURRENTPERIOD !='1'";
			logger.log("get sql_wlixiValue info --- " + sql_wlixiValue);
			rs = statement.executeQuery(sql_wlixiValue);

			while (rs.next()) {
				wlixiValue = rs.getString("wlixi");
				logger.log("未还利息:" + wlixiValue);
			}
			lixiValue_b = Float.parseFloat(lixi_a) - lixiValue_a;
			logger.log("应计利息后部分[计算罚息使用]:" + lixiValue_b);

			if (loan_qishu <= 6) {

				faixiValue = (Float.parseFloat(wlixiValue) + lixiValue_b)
						* faixiBili6 / 100;
				logger.log("小于等于6个月罚息值是  : " + faixiValue);
			} else if (loan_qishu > 6) {
				faixiValue = (Float.parseFloat(wlixiValue) + lixiValue_b)
						* faixiBiliD6 / 100;
				logger.log("大于6个月罚息值是  : " + faixiValue);

			}
			logger.log("月付息,到期还本 -- 还款本金:" + benjiValue + ",应计利息  : "+ lixiValue_a + ",罚息值是  : " + faixiValue + ",总期数" +
					":" + loan_qishu+ ",开始日期:"+reStr);

			// logger.log("MonthlyInterest");

			// ////////////////////////////////////////////////////
			// ////////////////////////////////////////////////////
			// ////////////////////////////////////////////////////

		} else if (methodKey.equals("EqualInstallment")) {

			String sql_weihaikuanCount = "SELECT count(*) as counts  from TB_LOAN_REPAYMENT r WHERE  r.LOAN_ID='"
					+ id + "' and r.`STATUS`='UNDUE'";
			rs = statement.executeQuery(sql_weihaikuanCount);
			while (rs.next()) {
				weihaikuanCounts = rs.getInt("counts");
				logger.log("未还款期数:" + weihaikuanCounts);
			}

			if (weihaikuanCounts == loan_qishu) {
				logger.log("还款本金:" + loan_amount + "总期数:" + loan_qishu);
			} else if (weihaikuanCounts != loan_qishu) {
				String sql_haikuanValue = "SELECT r.AMOUNTOUTSTANDING as AMOUNTOUTSTANDING  from TB_LOAN_REPAYMENT r WHERE  r.LOAN_ID='"
						+ id
						+ "' and r.`STATUS`='REPAYED' ORDER BY r.CURRENTPERIOD desc LIMIT 1 ";
				rs = statement.executeQuery(sql_haikuanValue);
				while (rs.next()) {
					equalInstallmentValues = rs.getString("AMOUNTOUTSTANDING");
					logger.log("还款本金:" + equalInstallmentValues);
				}
			}

			String sql_lixi = "SELECT r.DUEDATE as dateaction , r.AMOUNTINTEREST as  lixi_a   from TB_LOAN_REPAYMENT r WHERE  r.LOAN_ID='"
					+ id
					+ "'  and r.status='UNDUE' ORDER BY r.CURRENTPERIOD LIMIT 1;";
			logger.log("get sql_lixi info --- " + sql_lixi);
			rs = statement.executeQuery(sql_lixi);
			while (rs.next()) {
				dataIiqianValue = rs.getString("dateaction");
				lixi_a = rs.getString("lixi_a");
				logger.log("lixi_a:" + lixi_a + "最近需要还款日期:" + dataIiqianValue);
			}

			String str = dataIiqianValue;
			Date dt = dateFormat.parse(str);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			// rightNow.add(Calendar.YEAR,+1);//日期加1年
			rightNow.add(Calendar.MONTH, -1);// 日期减1个月
			
			Date dt1 = rightNow.getTime();
			String reStr = dateFormat.format(dt1);

			Date fDate = dateFormat.parse(reStr); // 计息开始日期
			Date oDate = dateFormat.parse(day_now); // 目前日期
//			Date oDate = dateFormat.parse("2015-05-24"); // 目前日期
			
			System.out.println("最近计算开始日期" + reStr);
			System.out.println("最近计算结束日期" + day_now);

			LibGetDayValue LibGetDayValue = new LibGetDayValue();
			daysValue = LibGetDayValue.getDaysBetween(fDate, oDate);
			logger.log("lixi_daysValue is : " + daysValue); // 计利天数
			if (daysValue < 0) {
//				daysValue = -daysValue;
				daysValue = 0;
//				logger.log("lixi_daysValue is  负 : " + daysValue); // 计利天数

			} else {
				daysValue = daysValue;
				logger.log("lixi_daysValue is 正 : " + daysValue); // 计利天数
			}

			if (daysValue <= 32) {
				lixiValue_a = Float.parseFloat(lixi_a) / 30 * daysValue;
				logger.log("天数小于30，应计利息  : " + lixiValue_a);
				String sql_wlixiValue = "SELECT   sum(r.AMOUNTINTEREST) as wlixi   from TB_LOAN_REPAYMENT r WHERE  r.LOAN_ID='"
						+ id
						+ "' and r.status='UNDUE' AND r.CURRENTPERIOD !='1'";
				logger.log("get sql_wlixiValue info --- " + sql_wlixiValue);
				rs = statement.executeQuery(sql_wlixiValue);
				while (rs.next()) {
					wlixiValue = rs.getString("wlixi");
					logger.log("未还利息:" + wlixiValue);
					// logger.log("总期数:" + Integer.parseInt(loan_qishu));
				}
				lixiValue_b = Float.parseFloat(lixi_a) - lixiValue_a;
				logger.log("应计利息后部分[计算罚息使用]:" + lixiValue_b);
				
				if (loan_qishu <= 6) {
					faixiValue = (Float.parseFloat(wlixiValue) + lixiValue_b)* faixiBili6 / 100;
					logger.log("小于等于6个月罚息值是  : " + faixiValue);
				} else if (loan_qishu > 6) {
					faixiValue = (Float.parseFloat(wlixiValue) + lixiValue_b)* faixiBiliD6 / 100;
					logger.log("大于6个月罚息值是  : " + faixiValue);
				}

			} else if (daysValue > 32) {
				String sql_wlixiValue = "SELECT   sum(r.AMOUNTINTEREST) as wlixi   from TB_LOAN_REPAYMENT r WHERE  r.LOAN_ID='"
					+ id
					+ "' and r.status='UNDUE' AND r.CURRENTPERIOD !='1'";
			logger.log("get sql_wlixiValue info --- " + sql_wlixiValue);
			rs = statement.executeQuery(sql_wlixiValue);
			while (rs.next()) {
				wlixiValue = rs.getString("wlixi");
				logger.log("未还利息:" + wlixiValue);
				// logger.log("总期数:" + Integer.parseInt(loan_qishu));
			}
				lixiValue_a = 0.00f;
				lixiValue_b = 0.00f;
				logger.log("lixiValue_a :" + lixiValue_a);
				logger.log("最近计算日期大于30天，不符合产品" + daysValue);

				if (loan_qishu <= 6) {
					faixiValue = (Float.parseFloat(wlixiValue) + lixiValue_b)* faixiBili6 / 100;
					logger.log("小于等于6个月罚息值是  : " + faixiValue);
				} else if (loan_qishu > 6) {
					faixiValue = (Float.parseFloat(wlixiValue) + lixiValue_b)* faixiBiliD6 / 100;
					logger.log("大于6个月罚息值是  : " + faixiValue);
				}
			}

			if (weihaikuanCounts == loan_qishu) {
				// logger.log("还款本金:" + loan_amount + "总期数:" + qishu);
				logger.log("按月等额本息——一期未还 -- 还款本金:" + loan_amount + "应计利息  : "+ String.valueOf(lixiValue_a) + "罚息值是  : " + faixiValue+ "总期数:" + loan_qishu+"开始日期:"+reStr);

			} else if (weihaikuanCounts != loan_qishu) {
				logger.log("按月等额本息  -- 还款本金:" + equalInstallmentValues+ "应计利息  : " + String.valueOf(lixiValue_a) + "罚息值是  : "+ faixiValue + "总期数:" + loan_qishu+"开始日期:"+reStr);
			}
			// logger.log("MonthlyInterest");
		}else {
			 logger.log("其余类型不支持");
		}

		return null;
	}

	public void main() throws Exception {
		Tiqian Tiqian = new Tiqian();
		// String TiqianInfo =
		String TiqianInfo02 = Tiqian.GetTiqianInfo_Total("80442484-075E-479D-A0C8-F1E0A227CEAF"); //<6等额本息
//		String TiqianInfo01 = Tiqian.GetTiqianInfo_Total("9CDDFE85-8063-4AA4-806B-05ED5F134AF3"); //6 等额本息
//		String TiqianInfo02 = Tiqian.GetTiqianInfo_Total("D023C204-4A7B-4DD3-96F8-6BFB1490C728"); //>6等额本息,还款日是假期的
//		String TiqianInfo03 = Tiqian.GetTiqianInfo_Total("9CDDFE85-8063-4AA4-806B-05ED5F134AF3"); //>6等额本息,还款日不是假期的
		
//		String TiqianInfo04 = Tiqian.GetTiqianInfo_Total("E1D89D26-BC77-49E4-8034-E2DA1ED5DE8D"); //<6 先息后本 --这个程序有问题
//		String TiqianInfo05 = Tiqian.GetTiqianInfo_Total("D023C204-4A7B-4DD3-96F8-6BFB1490C728"); //<6先息后本
//		String TiqianInfo04 = Tiqian.GetTiqianInfo_Total("01F0CD9C-6628-465A-846C-664B87F2C773"); //6 先息后本
//		String TiqianInfo05 = Tiqian.GetTiqianInfo_Total("FF5251CC-2A9C-471E-9770-9C079AC7DA62"); //>6先息后本,还款日是假期的
		

	}

}
