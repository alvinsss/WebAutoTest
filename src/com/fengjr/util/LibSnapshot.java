package com.fengjr.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/**
 * @author qa
 * @func   SnapshotDemo 
 * @throws IOException 
 */
public class LibSnapshot {
	private static String dir_name = null ;
    
	public static void snapshot(TakesScreenshot wdname, String filename){
		File scrFile = wdname.getScreenshotAs(OutputType.FILE);	
//			String dir_name = "F:\\workspace\\AutoTest_UI\\AutoTestResult\\snapshot";  
		Properties testdataconfig = LibDataConfig.getInstance().getProperties();
		if (dir_name == null) {
			dir_name = testdataconfig.getProperty("SnapshotDir");
			System.out.println(dir_name);
		}

		if (!(new File(dir_name).isDirectory())) { 
	        new File(dir_name).mkdir();
	 	}
		 
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
	    String time = sdf.format(new Date());  // 20120406-165210    
		
		try {
			System.out.println("save snapshot path is:"+dir_name +time +filename);
			FileUtils.copyFile(scrFile, new File( dir_name + File.separator +time +filename));						
	    } catch (IOException e) {
			System.out.println("Can't save screenshot");
			e.printStackTrace();
		} 
		finally{
			System.out.println("screen shot finished");
		}
	}
}
