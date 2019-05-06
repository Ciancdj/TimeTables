package model.business;

import java.util.Calendar;

/**
* @author Cianc
* @version 创建时间：2019年5月3日 上午8:55:25
* @ClassName operaOther
* @Description the other control for view
*/
public class operaOther {
	
	  public static Calendar getFistDateOfWeek(Calendar cal){
		  //System.out.println(cal.get(Calendar.DAY_OF_MONTH));
		  if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){  
			  cal.add(Calendar.DATE, -1);  
		  }  
		  cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		  //System.out.println(cal.get(Calendar.DAY_OF_MONTH));
		  return cal;
	  }  
	  
	  public static int returnWeek(Calendar cal) {
		  Calendar now = Calendar.getInstance();
		  return (int) ((now.getTimeInMillis() - cal.getTimeInMillis()) / (7*24*60*60*1000)) + 1;
	  }
}
