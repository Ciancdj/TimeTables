package control.main;

import java.sql.Date;
import java.util.Calendar;
import java.util.Queue;

import model.business.*;
import model.cao.*;
import view.main.*;

/**
* @author Cianc
* @version 创建时间：2019年5月1日 下午3:45:38
* @ClassName operaControl
* @Description 类描述
*/
public class operaControl {
	public static void init() {
		Calendar now = Calendar.getInstance();
		if(!operaInstall.haveElem()) {
			//System.out.println("acaca");
			operaInstall.addInstall();
		}
		Object []obj = operaInstall.getInstall();
		Date tem = null;
		if(obj[0] != null) {
			inForm.imgPath = (String)obj[0];
		}
		if(obj[1] != null) {
			if(inForm.courseStart == null) {
				inForm.courseStart = Calendar.getInstance();
			}
			inForm.courseStart.setTimeInMillis(((Date)obj[1]).getTime());
			if(((now.getTimeInMillis() - inForm.courseStart.getTimeInMillis()) / (1000*24*60*60)) > 180) {
				inForm.courseStart = operaOther.getFistDateOfWeek(Calendar.getInstance());
			}
		} else {
			inForm.courseStart = operaOther.getFistDateOfWeek(Calendar.getInstance());
		}
		obj = operaUser.getUser();
		if(obj[0] == null || obj[1] == null || obj[2] == null) {
			inForm.Account = inForm.Password = inForm.Name = "";
		} else {
			inForm.Name = (String)obj[0];
			inForm.Account = (String)obj[1];
			inForm.Password = (String)obj[2];
		}
	}
	
	public static void upTable() throws NullPointerException{
		if(inForm.Account == null || inForm.Password == null) {
			new NullPointerException();
			return;
		}
		webObtain web = new webObtain(inForm.Account, inForm.Password, "http://jw.jluzh.com");
		web.login();
		web.getCourse("2018","12", inForm.Name);
		operaInstall.addStartTime(new Date(inForm.courseStart.getTimeInMillis()));
	}
	
	public static int bingLogin(String account, String password){
		webObtain tem = new webObtain(account, password, "http://jw.jluzh.com");
		return tem.checkLogin();
	}
	
	public static void holdLogin(String account, String password) {
		// restart to create the user table
		operaUser.clearUser();
		operaUser.addUser(account, password);
		inForm.Account = account;
		inForm.Password = password;
		inForm.Name = "";
	}
	
	public static void holdImg(String imgPath) {
		inForm.imgPath = imgPath;
		operaInstall.addImgPath(imgPath);
	}	
	
	public static void holdTime(int Time) {
		Calendar now = Calendar.getInstance();
		int week = now.get(Calendar.DAY_OF_WEEK) - 1;
		if(week == 0) {
			week = 7;
		}
		long reduce = (Time-1)*7+week - 1;
		//System.out.println(reduce);
		now.set(Calendar.DATE, (int) (now.get(Calendar.DATE) - reduce));
		//System.out.println((now.get(Calendar.MONTH)+1) + "  " + now.get(Calendar.DAY_OF_MONTH));
		operaInstall.addStartTime(new Date(now.getTimeInMillis()));
		inForm.courseStart = now;
	}
	
	public static void reduction() {
		operaUser.clearUser();
		operaInstall.clearInstall();
		operaCourse.clearCourse();
		operaCourseTime.clearCourseTime();
		operaCourseWeek.clearCourseWeek();
		System.exit(0);
	}
	
	public static Queue<Object[]> buildCourse(int week, int regu) {
		if(regu == 0) {
			return operaCourse.search(week, 0);
		} else {
			if(week % 2 == 0) {
				return operaCourse.search(week, 2);
			} else {
				return operaCourse.search(week, 1);
			}
		}
	}
}
