package com.fengjr.util;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.joda.time.LocalDate;
import org.junit.Test;


public class LibGetJixiValue {
	
	private static final LibLogger logger = LibLogger.getLogger(LibGetJixiValue.class);

    /**
     * 判断当前日期是否是周末或节假日
     * @param currentDate
     * @return 
     */
    public static boolean isPublicHoliday(LocalDate currentDate) {
        boolean isHoliday = false;
        //读取假日文件，判断假日文件中是否有当天
        
        
        File holidayFile = new File("src//com//fengjr//config//holiday");
        
        if (holidayFile.canRead()) {
            try  {
            	Scanner scanner = new Scanner(holidayFile);
                while (scanner.hasNextLine()) {
                    if (scanner.nextLine().contains(currentDate.toString("yyyyMMdd"))) {
                        isHoliday = true;
                        break;
                    }
                }
            }catch (Exception ex) {
                logger.error("Can't use scanner on holiday file.");
            }
        }
        return isHoliday;
    }
    
    /**
     * 查找下一个工作日
     * @param currentDate 周末或者节假日
     * @return 
     */
    public static LocalDate nextWorkingDay(LocalDate currentDate) {
        boolean isPublicHoliday = isPublicHoliday(currentDate);
        //周末或者节假日的下一个工作日
        if (isPublicHoliday) {
            currentDate = currentDate.plusDays(1);
            return nextWorkingDay(currentDate);
        }
        return currentDate;
    }
    
    @Test
	public void main()  {
//		Date now = new Date();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		String day_now = dateFormat.format(now);
		LocalDate today = LocalDate.now();
		LibGetJixiValue a = new LibGetJixiValue();
		boolean isTure =a.isPublicHoliday(today);
		
		if (isTure){
			logger.log("isHoliday");
		}else{
			logger.log("is not Holiday");
		}
		
	}

}
