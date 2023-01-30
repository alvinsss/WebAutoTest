package com.fengjr.function;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.fengjr.util.*;

@Test
public class GetZhaiquanInfo {
	String test;
	Properties testdataconfig = LibDataConfig.getInstance().getProperties();
	private static final LibLogger logger = LibLogger.getLogger(Tiqian.class);


	@Test
	public void getZhaiquanInfoAction( String loanid ,float zheRate , String currentUser ) throws Exception {

		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		ResultSet rs_method = null;
		ResultSet rs_qishu = null;
		ResultSet rs_daishoubenjin = null;
		ResultSet rs_fdate = null;
		ResultSet rs_Currentyingshoulixi = null;
		ResultSet rs_ZhaiquanValue = null;
		ResultSet rs_ZherValue = null;
		ResultSet rs_ZhaijiaoyiValue = null;
		ResultSet rs_ZhaiDaozhangjinE = null;


		int Getqishu = 0;

		String FDATE = null;
		String method = null;

		float Daishoubenjin = 0.00f;
		float Currentyingshoulixi = 0.00f;
		float ZhaiquanValue = 0.00f;
		float ZherValue = 0.00f;
		float ZhaijiaoyiValue = 0.00f;
		float ZhaiDaozhangjinE = 0.00f;
		float ZhaiquanRate  = 0.00f;
		float ZhaiquanShouXuFree = 0.00f;

		// System.out.println(day_now);

		try {
			conn = LibJDBC.getConnection();
			statement = conn.createStatement();
			// logger.log("sql get rs_method  info ");
			String sql_method = "SELECT l.METHOD as DB_method from TB_LOAN l WHERE l.id='"+loanid+"'";
			rs_method = statement.executeQuery(sql_method);
			while (rs_method.next()) {
				method = rs_method.getString("DB_method");
				logger.log("还款方式:" + method);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			// LibJDBC.release(rs, statement, conn);
		}

			
			String sql_qishu = " SELECT count(*) as 'DB_qishu'  from Biz.TB_INVEST_REPAYMENT r,Biz.TB_INVEST i,Biz.TB_USER u  where r.invest_id =i.id and i.userid =u.id  and i.loanid = '"+loanid+"' and r.`STATUS`='REPAYED' and u.LOGINNAME='"+currentUser+"'";
			rs_qishu = statement.executeQuery(sql_qishu);
			while (rs_qishu.next()) {
				Getqishu = rs_qishu.getInt("DB_qishu");
				logger.log("持有期数:" + Getqishu);
			}
			
			String sql_daishoubenjin = "SELECT sum(r.AMOUNTPRINCIPAL) as 'DB_amount'  from Biz.TB_INVEST_REPAYMENT r,Biz.TB_INVEST i,Biz.TB_USER u  where r.invest_id =i.id and i.userid =u.id  and i.loanid = '"+loanid+"' and r.`STATUS`='undue' and u.LOGINNAME='"+currentUser+"' ";
			rs_daishoubenjin = statement.executeQuery(sql_daishoubenjin);
			while (rs_daishoubenjin.next()){
				Daishoubenjin = rs_daishoubenjin.getFloat("DB_amount");
				logger.log("待收本金:" + Daishoubenjin);
			}
			
			String sql_fdate = "SELECT r.DUEDATE as 'fDate' from Biz.TB_INVEST_REPAYMENT r,Biz.TB_INVEST i,Biz.TB_USER u,TB_LOAN l  where r.invest_id =i.id and i.userid =u.id and i.LOANID=l.id  and i.loanid = '"+loanid+"' and r.STATUS!='DEPRECATED' and r.`STATUS`='UNDUE' and u.LOGINNAME='"+currentUser+"' ORDER BY r.CURRENTPERIOD  LIMIT 1;";
			rs_fdate = statement.executeQuery(sql_fdate);
			while (rs_fdate.next()){
				FDATE = rs_fdate.getString("fDate");
				logger.log("上个还款日" +FDATE);
			}
			
			String sql_CurrentYingshoulixi = "SELECT sum((r.AMOUNTPRINCIPAL)*(((l.RATE/10000)/12)/30)*datediff(now(),'"+FDATE+"')) as 'DB_Currentyingshoulixi' from Biz.TB_INVEST_REPAYMENT r,Biz.TB_INVEST i,Biz.TB_USER u,TB_LOAN l  where r.invest_id =i.id and i.userid =u.id and i.LOANID=l.id and i.loanid ='"+loanid+"'  and r.STATUS!='DEPRECATED' and r.`STATUS`='undue' and u.LOGINNAME='"+currentUser+"';";
			rs_Currentyingshoulixi = statement.executeQuery(sql_CurrentYingshoulixi);
			while (rs_Currentyingshoulixi.next()){
				Currentyingshoulixi = rs_Currentyingshoulixi.getFloat("DB_Currentyingshoulixi");
				//与float 0f对比，如果是负数等于0f 否则就是本身
				float comF2 = 0f;
				boolean valueCom = (Float.floatToIntBits(Currentyingshoulixi) < Float.floatToIntBits(comF2));
				if (valueCom){
					Currentyingshoulixi = 0f;
					logger.log("当前应收利息" +Currentyingshoulixi);

				}else{
//					Currentyingshoulixi = Currentyingshoulixi;
					logger.log("当前应收利息" +Currentyingshoulixi);
				}
				
			}
			
			//债权价值
			ZhaiquanValue =  Daishoubenjin +  Currentyingshoulixi;
			logger.log("债权价值" +ZhaiquanValue);
			
			//续费率
			if (Getqishu <= 3 ){
				ZhaiquanRate = 0.007f;
				logger.log("续费率" +ZhaiquanRate);

			}else if(Getqishu > 3 && Getqishu < 12 ){
				ZhaiquanRate = 0.005f;
				logger.log("续费率" +ZhaiquanRate);

			}else{
				ZhaiquanRate = 0.003f;
				logger.log("续费率" +ZhaiquanRate);

			}
			
			//折让金额 
			ZherValue = ZhaiquanValue * zheRate ;
			logger.log("折让金额" +ZherValue);
			
			//债权交易金额
			ZhaijiaoyiValue =  ZhaiquanValue - ZherValue;
			
			//预计手续费
			ZhaiquanShouXuFree = ZhaijiaoyiValue * ZhaiquanRate;

			//预计到账金额
			ZhaiDaozhangjinE = ZhaijiaoyiValue - ZhaiquanShouXuFree;
			/*
			 * */
			
			BigDecimal yh_Daishoubenjin = new BigDecimal(Daishoubenjin);
			BigDecimal yh_Currentyingshoulixi = new BigDecimal(Currentyingshoulixi);
			BigDecimal yh_ZhaiquanValue = new BigDecimal(ZhaiquanValue);
			BigDecimal yh_zheRate = new BigDecimal(zheRate);
			BigDecimal yh_ZherValue = new BigDecimal(ZherValue);
			BigDecimal yh_ZhaijiaoyiValue = new BigDecimal(ZhaijiaoyiValue);
			BigDecimal yh_ZhaiquanShouXuFree = new BigDecimal(ZhaiquanShouXuFree);
			BigDecimal yh_ZhaiDaozhangjinE = new BigDecimal(ZhaiDaozhangjinE);			
			
			//4舍5入
			logger.log("待收本金(元)" +Daishoubenjin + "当期应收收益" +Currentyingshoulixi+"债权价值" +ZhaiquanValue+ "折让率"
					+zheRate+ "折让金额"+ZherValue+
					"债权交易金额" +ZhaijiaoyiValue+ "预计手续费" 
					+ZhaiquanShouXuFree+ "预计到账金额" +ZhaiDaozhangjinE);
			
			
			// 银行家计算法
//			logger.log("待收本金(元)" +yh_Daishoubenjin.setScale(2, RoundingMode.HALF_EVEN) + "当期应收收益" +yh_Currentyingshoulixi.setScale(2, RoundingMode.HALF_EVEN)+"债权价值" +yh_ZhaiquanValue.setScale(2, RoundingMode.HALF_EVEN)+ "折让率"
//					+yh_zheRate.setScale(2, RoundingMode.HALF_EVEN)+ "折让金额"+yh_ZherValue.setScale(2, RoundingMode.HALF_EVEN)+
//					"债权交易金额" +yh_ZhaijiaoyiValue.setScale(2, RoundingMode.HALF_EVEN)+ "预计手续费" 
//					+yh_ZhaiquanShouXuFree.setScale(2, RoundingMode.HALF_EVEN).doubleValue()+ "预计到账金额" +yh_ZhaiDaozhangjinE.setScale(2, RoundingMode.HALF_EVEN));

			

	}
	
//	@Test
//	@Parameters({"loanid","zheRate","currentUser"})
//	public void getInfo() throws Exception {
//		GetZhaiquanInfo ZhaiquanInfo = new GetZhaiquanInfo();
//		ZhaiquanInfo.getZhaiquanInfoAction(String  loanid, float zheRate, String  currentUser);
//
//	}
	
	public void main() throws Exception {
		GetZhaiquanInfo ZhaiquanInfo = new GetZhaiquanInfo();
		String loanid="04280DA5-A8E3-4778-9313-026FABC4D7FF";
		float zheRate=0.02f;
		String currentUser="alvin";
		ZhaiquanInfo.getZhaiquanInfoAction(loanid, zheRate, currentUser);

	}

}
