package model.business;
import java.awt.Color;
import java.util.Calendar;

/**
* @author Cianc
* @version 创建时间：2019年4月28日 下午3:05:04
* @ClassName 类名称
* @Description 类描述
*/
public class inForm {
	// 数据库用户名与密码
	public static final String USER = "root";
	public static final String PASSWORD = "123456";
	// 主视图层界面大小
	public static int WIDTH = 618;
	public static int HEIGHT = 950;
	// JDBC 驱动名及数据库URL
	public static final String JDBC_DRIVE = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/sctimetable?useSSL=false&allowPublicKeyRetrieval=true";
	public static final String[] WEEK = {"周一","周二","周三","周四","周五","周六","周日"};
	// 基本设置
	public static String imgPath = null;
	public static Calendar courseStart = null;
	// 账户信息
	public static String Name = "";
	public static String Account = "";
	public static String Password = "";
	// 颜色数组
	public static Color[] color = {
			Color.green, Color.MAGENTA, Color.yellow, Color.PINK, Color.LIGHT_GRAY, Color.cyan, Color.orange 
	};
}
