package com.fengjr.api;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Properties;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import  com.fengjr.util.*;

/**
 * 获取投资的北京用户数据
 * @param 
 * @author alvin
 * @version 1.0   
 * @since JDK 1.7
 */
@Test
public class CheckBJUserInfo {
	
    Properties testdataconfig = LibDataConfig.getInstance().getProperties();
    private static final LibLogger logger = LibLogger.getLogger(CheckBJUserInfo.class);
    String userid = "";
    
	public void getUserAccountInfo() throws IOException{
		File testdataFile;
//		testdataFile = new File("F://UserData//Workspaces//QA//testdata//userid.txt");
		testdataFile = new File("D://autotestenv//testdata//userid.txt");

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(testdataFile)));
	    String apiIp ="http://10.255.72.158:19902/pay/api/v2/ceres/user/bjbBalance/";
	    String userid=null;
	    String cookieStr ="";
	    
		while ((userid = in.readLine()) != null) {
			StringBuffer sbs = new StringBuffer();
			sbs.append(apiIp).append(userid);
			String results = HttpUtils.doGetBody(sbs.toString(), cookieStr);
			
			// 取数据段
			results = results.replace('\"', '\'');
			JSONObject dataJson = JSONObject.fromObject(results);
			String resultInfoAll = dataJson.getString("data");
	//		logger.log("resultInfoAll is:"+resultInfoAll);
			
			// 取电子帐号数据
			JSONObject resultInfoBody = JSONObject.fromObject(resultInfoAll);
			String resultInfoBody_DZ = resultInfoBody.getString("电子账户");
	//		logger.log("resultInfoBody_DZ is:"+resultInfoBody_DZ);
			
			// 取电子帐号数据_在途
			JSONObject resultInfoBody_DZ_Body = JSONObject.fromObject(resultInfoBody_DZ);
			String resultInfoBody_DZ_Body_ZAITU = resultInfoBody_DZ_Body.getString("在途");
			String resultInfoBody_DZ_Body_XIANJING = resultInfoBody_DZ_Body.getString("现金");
	
	//		logger.log("resultInfoBody_DZ_Body_ZAITU is:"+resultInfoBody_DZ_Body_ZAITU);
			
			// 取电子帐号数据_在途 f_balance n_balance t_balance
			JSONObject resultInfoBody_DZ_Body_ZAITU_Data = JSONObject.fromObject(resultInfoBody_DZ_Body_ZAITU);
			String ResBody_DZ_Body_ZAITU_f_balance = resultInfoBody_DZ_Body_ZAITU_Data.getString("f_balance");
			String ResBody_DZ_Body_ZAITU_n_balance = resultInfoBody_DZ_Body_ZAITU_Data.getString("n_balance");
			String ResBody_DZ_Body_ZAITU_t_balance = resultInfoBody_DZ_Body_ZAITU_Data.getString("t_balance");
	
			// 取电子帐号数据_现金 f_balance n_balance t_balance t_balance
			JSONObject resultInfoBody_DZ_Body_XIANJING_Data = JSONObject.fromObject(resultInfoBody_DZ_Body_XIANJING);
			String ResInfoBody_DZ_Body_XIANJING_f_balance = resultInfoBody_DZ_Body_ZAITU_Data.getString("f_balance");
			String ResInfoBody_DZ_Body_XIANJING_n_balance = resultInfoBody_DZ_Body_ZAITU_Data.getString("n_balance");
			String ResInfoBody_DZ_Body_XIANJING_t_balance = resultInfoBody_DZ_Body_ZAITU_Data.getString("t_balance");
	
			//返回的数据操作 		保留小数位4
			double compareValues = 0.0000;
			DecimalFormat  df   = new DecimalFormat("######0.0000");
			double ResBody_DZ_Body_ZAITU_f_balance_d=Double.valueOf(ResBody_DZ_Body_ZAITU_f_balance).doubleValue();
			double ResBody_DZ_Body_ZAITU_n_balance_d=Double.valueOf(ResBody_DZ_Body_ZAITU_n_balance).doubleValue();
			double ResBody_DZ_Body_ZAITU_t_balance_d=Double.valueOf(ResBody_DZ_Body_ZAITU_t_balance).doubleValue();
	
			int compareResF_balance = (df.format(ResBody_DZ_Body_ZAITU_f_balance_d)).compareTo((df.format(compareValues)));
			int compareResN_balance = (df.format(ResBody_DZ_Body_ZAITU_n_balance_d)).compareTo((df.format(compareValues)));
			int compareResT_balance = (df.format(ResBody_DZ_Body_ZAITU_t_balance_d)).compareTo((df.format(compareValues)));
			
//			logger.log("compareValues is:"+compareValues);
//			logger.log("userid is:"+userid +"(电子)在途->f_balance:"+ResBody_DZ_Body_ZAITU_f_balance+"->n_balance:"+ResBody_DZ_Body_ZAITU_n_balance+"->t_balance:"+ResBody_DZ_Body_ZAITU_t_balance);
			if(  compareResF_balance > 0 || compareResN_balance >0 || compareResT_balance > 0 ){
				logger.log("userid is:"+userid +"现金在途其中任意不是0，->f_balance:"+ResBody_DZ_Body_ZAITU_f_balance+"->n_balance:"+ResBody_DZ_Body_ZAITU_n_balance+"->t_balance:"+ResBody_DZ_Body_ZAITU_t_balance);
			}else{
				logger.log("userid is:"+userid +"现金在途全部是0，->f_balance:"+ResBody_DZ_Body_ZAITU_f_balance+"->n_balance:"+ResBody_DZ_Body_ZAITU_n_balance+"->t_balance:"+ResBody_DZ_Body_ZAITU_t_balance);
			
		    }
			
//			if(compareResF_balance > 0 ){
//				logger.log("userid is:"+userid +"现金在途其中任意不是0，->f_balance:"+ResBody_DZ_Body_ZAITU_f_balance+"->n_balance:"+ResBody_DZ_Body_ZAITU_n_balance+"->t_balance:"+ResBody_DZ_Body_ZAITU_t_balance);
//			}else{
//				if(compareResN_balance >0){
//					logger.log("userid is:"+userid +"现金在途其中任意不是0，->f_balance:"+ResBody_DZ_Body_ZAITU_f_balance+"->n_balance:"+ResBody_DZ_Body_ZAITU_n_balance+"->t_balance:"+ResBody_DZ_Body_ZAITU_t_balance);
//				}else{
//					if(compareResT_balance > 0){
//						logger.log("userid is:"+userid +"现金在途其中任意不是0，->f_balance:"+ResBody_DZ_Body_ZAITU_f_balance+"->n_balance:"+ResBody_DZ_Body_ZAITU_n_balance+"->t_balance:"+ResBody_DZ_Body_ZAITU_t_balance);
//
//					}else{
//						logger.log("userid is:"+userid +"现金在途全部是0，->f_balance:"+ResBody_DZ_Body_ZAITU_f_balance+"->n_balance:"+ResBody_DZ_Body_ZAITU_n_balance+"->t_balance:"+ResBody_DZ_Body_ZAITU_t_balance);
//					}
//				}
//			}

		 
		}
		
	}
		
}
