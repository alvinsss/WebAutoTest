package com.fengjr.function;

import java.math.BigDecimal;
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

import org.joda.time.LocalDate;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.fengjr.util.*;

//@Test
public class TiqianJS {
	
	String test;
	public static String apiIp = null;
	public static String auths = null;
	Properties testdataconfig = LibDataConfig.getInstance().getProperties();
	private static final LibLogger logger = LibLogger.getLogger(TiqianJS.class);

	// 提前还款的金额=当期还款本息+剩余本金+提前还款罚息。
	// 当期还款本息=原还款计划中当天应还款的本金+利息。
	// 剩余本金=还未归还给投资人的本金。从下一次还款日计算。
	// 提前还款罚息=以后每期的应还利息总和，的一定比例。罚息比例为20%，15%或者0。
	// @BeforeClass
	
    /**
     * 计算提前还款
     * @param id 产品ID stroDate 提前还款日期,格式yyyyMMdd
     * @return 
     * @throws Exception 
     */
	public String GetTiqianInfo_Total(String id , String stroDate) throws Exception {

		Connection conn = null;
		Statement statement = null;
		ResultSet rs_method = null;
		ResultSet rs_benji = null;
		ResultSet rs_fDate = null;

		
		// 还款方式
		String db_Method = null;
		String db_Title =  null;
		String db_Amount = null;
		int    db_Qishu  = 0;
		
		// 还款本金
		String db_LoginName = null;
		String db_UserId = null ;
		String db_InvestId = null ;
		BigDecimal db_AmountInterest = null ;
		int    db_CurrentPeriod = 0 ;
		BigDecimal db_Ben = null;
		
		// 应计利息
		int  js_yjlxStartPeriod = 0;
		String db_fDueDate = null ;
		// 一个整月标识
		int jx_flagM = 0;
		// 计息天数相关
		int jx_daysValue = 0;
		
//		Calendar rightNow = Calendar.getInstance();
//		rightNow.setTime(dt);
//		Date dt1 = rightNow.getTime();
//		String reStr = dateFormat.format(dt1);
		

//		Date now = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 可以方便地修改日期格式
//		String day_now = dateFormat.format(now);

		// System.out.println(day_now);

		try {
			conn = LibJDBC.getConnection();
			statement = conn.createStatement();
//			logger.log("sql loan_id is :" +id);
			String sql_method = "SELECT l.METHOD as  METHOD ,l.TITLE as TITLE  ,l.AMOUNT as AMOUNT ,(l.RATE/10000) as rateN,(YEARS*12+MONTHS) as  QISHU from TB_LOAN l WHERE l.ID='"+ id + "'";
			logger.log("get sql_method info sql ---------  " + sql_method);
			rs_method = statement.executeQuery(sql_method);
			while (rs_method.next()) {
				db_Method = rs_method.getString("METHOD");
				db_Amount = rs_method.getString("AMOUNT");
				db_Qishu = rs_method.getInt("QISHU");
				logger.log("还款方式:" + db_Method+ "借款期数" +db_Qishu);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
//			 LibJDBC.release(rs, statement, conn);
		}
		
		if ( db_Method.equals("MonthlyInterest" )) {
			// 还款本金
			String sql_benji="SELECT u.LOGINNAME as LOGINNAME  ,i.USERID as  USERID ,r.INVEST_ID as INVEST_ID ,sum(r.AMOUNTINTEREST) as 'haikuanlix',min(r.CURRENTPERIOD) as tiqianqishu ,sum(r.AMOUNTPRINCIPAL) as 'haikuanbenji' from TB_INVEST_REPAYMENT r,TB_INVEST i,TB_USER u  where r.invest_id =i.id and i.userid =u.id  and i.loanid = '"+ id + "'  and r.STATUS!='DEPRECATED' and r.`STATUS` ='UNDUE' GROUP BY r.INVEST_ID";
			logger.log("还款本金sql --------- " +sql_benji);
			rs_benji = statement.executeQuery(sql_benji);
			while(rs_benji.next()){
				db_LoginName = rs_benji.getString("LOGINNAME");
				db_UserId = rs_benji.getString("USERID");
				db_InvestId = rs_benji.getString("INVEST_ID");
				db_AmountInterest = rs_benji.getBigDecimal("haikuanlix");
				db_CurrentPeriod = rs_benji.getInt("tiqianqishu");
				db_Ben = rs_benji.getBigDecimal("haikuanbenji");
//				logger.log("db_LoginName is : " +db_LoginName+ ",db_UserId is :" +db_UserId+ ",db_InvestId is :" +db_InvestId+ ",db_AmountInterest is :" +db_AmountInterest+ ",db_CurrentPeriod" +db_CurrentPeriod+ ",db_Benji is :" +db_Ben); 
			}
			logger.log("db_LoginName is : " +db_LoginName+ ",db_UserId is :" +db_UserId+ ",db_InvestId is :" +db_InvestId+ ",db_AmountInterest is :" +db_AmountInterest+ ",db_CurrentPeriod is :" +db_CurrentPeriod+ ",db_Benji is :" +db_Ben); 
			
			//应计利息
			
			
			
			//计算计息天数
			if ( db_CurrentPeriod != 1 &&  js_yjlxStartPeriod == 0){
				js_yjlxStartPeriod = db_CurrentPeriod -1;
				String sql_fDate="SELECT r.DUEDATE as DUEDATE from TB_LOAN_REPAYMENT r WHERE r.CURRENTPERIOD="+js_yjlxStartPeriod+" AND r.LOAN_ID='"+id+"'";
				rs_fDate = statement.executeQuery(sql_fDate);
				while(rs_fDate.next()){
					db_fDueDate = rs_fDate.getString("DUEDATE");
				}
			}
				
			}else if (db_Method.equals("EqualInstallment")) {
			logger.log("还款方式等额本息:" + db_Method);

		} else {
			 logger.log("其余类型不支持");
		}

		return null;
	}
	
	@Test
	public void main() throws Exception {
		TiqianJS Tiqian = new TiqianJS();
		String TiqianInfo02 = Tiqian.GetTiqianInfo_Total("70DE1009-115C-45BE-AA96-6227D345FAD1","20150610"); //<6等额本息

	}

}
