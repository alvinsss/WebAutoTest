package com.fengjr.util;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Reporter;

public class LibLogger {
    
    private static Logger logger = null;
    private static LibLogger logg = null;
    
    public static LibLogger getLogger(Class<?> T) {
        if (logger == null) {
            Properties props = new Properties();
            
            try {
                InputStream is = new FileInputStream("src//com//fengjr//config//log4j.properties");
                props.load(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            PropertyConfigurator.configure(props);
            logger = Logger.getLogger(T);
            logg = new LibLogger();
        }
        return logg;
    }
    
   
    public void log(String msg) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar ca = Calendar.getInstance();
        logger.info(msg);
//        try {
//		msg = new String(msg.getBytes("ISO-8859-1"),"UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}finally{
//			 logger.info(msg);
//		     Reporter.log("Reporter:" + sdf.format(ca.getTime()) + "===>" + msg);
//		}       
//        Reporter.log("Reporter:" + sdf.format(ca.getTime()) + "===>" + msg);
    }
    
    public void log(boolean msg) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar ca = Calendar.getInstance();
        logger.info(msg);
        Reporter.log("Reporter:" + sdf.format(ca.getTime()) + "===>" + msg);
    }
    
    public void debug(String msg) {
        logger.debug(msg);
    }
    
    public void warn(String msg) {
        logger.warn(msg);
        Reporter.log("Reporter:" + msg);
    }
    
    public void error(String msg) {
        logger.error(msg);
        Reporter.log("Reporter:" + msg);
    }
}
