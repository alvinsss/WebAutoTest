package com.fengjr.util;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import org.joda.time.LocalDate;
import org.testng.annotations.Test;

public class LibGetDayValue {
	private static final LibLogger logger = LibLogger.getLogger(LibGetDayValue.class);
	DateFormat df = new SimpleDateFormat("yyyyMMdd");
	
    /**
     * 计算提前2个日期相差天数
     * @param fDate 开始日期 oDate 结束日期
     * @return 
     */
//	public  int daysOfTwo(LocalDate fDate, LocalDate oDate) {
//	       Calendar aCalendar = Calendar.getInstance();
//	       Calendar localCalendar = Calendar.getInstance();
//
//	       aCalendar.setTime(fDate);
//	       int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
//	       
////	       String stroDate=df.format(oDate);
////			LocalDate newstroDate = oDate;
//
//			boolean isTure =LibGetDayValue.isPublicHoliday(oDate);
//			
//			if (isTure){
//				logger.log("isHoliday");
//			}else{
//				logger.log("is not Holiday");
//			}
//	       localCalendar.setTime(localDate);  
//	       aCalendar.setTime(oDate);
//	       int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
	       
//	       return day2 - day1;
//	    }
	
	  
    public static int getDaysBetween(java.util.Date paramDate1,java.util.Date paramDate2) throws Exception {  
        Calendar localCalendar1 = Calendar.getInstance();  
        Calendar localCalendar2 = Calendar.getInstance();  
        localCalendar1.setTime(paramDate1);  
        localCalendar2.setTime(paramDate2);  
        
        if (localCalendar1.after(localCalendar2))  
            throw new Exception("起始日期小于终止日期!");  
        int i = localCalendar2.get(6) - localCalendar1.get(6);  
        int j = localCalendar2.get(1);  
        if (localCalendar1.get(1) != j) {  
            localCalendar1 = (Calendar) localCalendar1.clone();  
            do {  
                i += localCalendar1.getActualMaximum(6);  
                localCalendar1.add(1, 1);  
            } while (localCalendar1.get(1) != j);  
        }  
        return i;  
    }  
	
	
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
public void main () throws Exception{
	int  days = 0;
	String fDateDB = "2015-03-26";
		
    Date fDate = df.parse(fDateDB.replaceAll("-",""));

    String oDate = "20150328";
//    Date aaoDate = df.parse(oDate);
    Date aaoDate = df.parse(oDate);

	LocalDate oDateL = LocalDate.parse(oDate);
//	LibGetJixiValue a = new LibGetJixiValue();
	boolean isTure =isPublicHoliday(oDateL);
	if (!isTure){
		logger.log("oDateL is Holiday");
		oDateL = nextWorkingDay(oDateL);
		logger.log("nDateL is :" +nextWorkingDay(oDateL));
		}else{
			logger.log("is not Holiday");
	}
	
	LibGetDayValue LibGetDayValue = new LibGetDayValue();
//	days = LibGetDayValue.daysOfTwo(fDate, oDate);
//	System.out.println("days" +days);
	
	logger.log("oDateL.toString().substring(0,8) ： " +oDateL.toString().substring(0,8));
	String aa = oDateL.toString().substring(0,8);
    Date newoDate = df.parse("20150328");

	days = LibGetDayValue.getDaysBetween(fDate,aaoDate);
	System.out.println("days is :" +days);
	}
}