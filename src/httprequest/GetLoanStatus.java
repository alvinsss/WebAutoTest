package httprequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import net.sf.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.fengjr.util.*;

/**
 * 查看到时间没有变成开标状态，获取serverdate接口值与loan详情接口timeopen值，定位问题原因
 * @param String env 指定测试环境，TEST是测试环境，BETA是beta环境 。 String loanId 等待开标id.
 * @author alvin
 * @version 1.0
 * @since JDK 1.7
 * 需要的Http请求工具 HttpUtils.java
 */

@Test
public class GetLoanStatus{

    Properties testdataconfig = LibDataConfig.getInstance().getProperties();
    private static final LibLogger logger = LibLogger.getLogger(GetLoanStatus.class);

    @BeforeClass
    public void setUp() throws Exception {
        logger.log(" get Loan status start ! ");
    }
    
    
    public void  getLoanIdstatus( String env , String loanId) {
        
        String interFace ="/api/v2/loan/";
        String cookies ="";
        String serverDateUrl="";
        String apiIp ="";

        if (env.equals("TEST")){
            apiIp="http://test.fengjr.inc";
            serverDateUrl ="http://test.fengjr.inc/api/v2/server/date";
        }else if(env.equals("BETA")){
            apiIp="http://beta.fengjr.com";
            serverDateUrl ="http://beta.fengjr.com/api/v2/server/date";
        }else{
            logger.log("您指定环境不存在，程序退出");
            System.exit(0);
        }
        
    
        Date d = new Date();
        long longtime = d.getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        logger.log("date is:" +sdf.format(longtime));
        
        long totalMilliSeconds = System.currentTimeMillis();
        StringBuffer sbs = new StringBuffer();
        sbs.append(apiIp).append(interFace).append(loanId);
        
        //做get请求
        String results = HttpUtils.doGetBody(sbs.toString(),cookies);

        // 通过JSONObject 获取接口josn返回信息  timeopen  status
        String actualResults = results.replace('\"', '\'');
        JSONObject dataJson = JSONObject.fromObject(actualResults);
        String resultInfo = dataJson.getString("data");
        JSONObject resultInfoStatus = JSONObject.fromObject(resultInfo);
        long timeopen = resultInfoStatus.getLong("timeopen");
        String status = resultInfoStatus.getString("status");
        
        // 通过返回json截取serverdate值
        String resultsServerDate = HttpUtils.doGetBody(serverDateUrl, cookies);
        String actualresultsServerDateResults = resultsServerDate.replace('\"', '\'');
        actualresultsServerDateResults=actualresultsServerDateResults.substring(14,27);
        logger.log("ServerDate is :" +actualresultsServerDateResults);

//        logger.log("resultServerDateInfo is :" +resultServerDateInfo);
        logger.log("currentdate is :"+sdf.format(longtime)+",currentlMilliSeconds is :"+totalMilliSeconds);
        logger.log("ServerDate is :"+actualresultsServerDateResults+",timeopen is :"+timeopen+ ",status is :" +status);

        //本机时间
        int currentDataTimeMillis= new Long(totalMilliSeconds).intValue();  
        
        // josn的时间戳
        int jsonInttimeopen= new Long(totalMilliSeconds).intValue();  

        if( status.equals("OPENED") ) {
            
            for(int i=0;i<1000;i++){
                logger.log(" Current Status is OPENED , ServerDate is :"+actualresultsServerDateResults+",timeopen is :"+timeopen+ ",status is :" +status);
            }
            System.exit(0);
            
        }
                    
    }
    
    @Test
    public void getStatus() {
        
        //等待开标ID
        String loanId ="E7C365B1-8C51-42B8-BE23-B9B3F48B149A"; 
        String env = "TEST";


        while(true){
            
            GetLoanStatus status = new GetLoanStatus();
            status.getLoanIdstatus(env,loanId);
            
        }
        
    }
}
