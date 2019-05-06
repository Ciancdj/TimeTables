package model.cao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import model.business.inForm;

/**
* @author Cianc
* @version 创建时间：2019年4月28日 下午7:37:48
* @ClassName 类名称
* @Description 类描述
*/
public class operaCourseTime {
	/**
	 * this function can clear all information for course table
	 */
	public static void clearCourseTime() {
		Connection conn = null;
		PreparedStatement state = null;
		try {
			conn = (Connection) 
					DriverManager.getConnection(inForm.DB_URL, inForm.USER, inForm.PASSWORD);
			state = conn.prepareStatement("truncate table course_time");
			state.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "SQL连接异常  错误为 Clear_CourseTime");
		} finally {
			closeSQL(conn, state, null);
		}
	}
	/*
	 * this func can close the sql connection 、 Statement 、ResultSet
	 */
	public static void closeSQL(Connection conn, PreparedStatement state, ResultSet rs) {
		try {
			if(conn != null)
				conn.close();
			if(state != null) 
				state.close();
			if(rs != null) 
				rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void addCourseTime(int sid, String jc) {
		Pattern p = Pattern.compile("(.+{1,2})-(.+{1,2}).+");
		Matcher m = p.matcher(jc);
		int startIndex = 0, endIndex = 0;
		if(m.find()) {
			startIndex = Integer.parseInt(m.group(1));
			endIndex = Integer.parseInt(m.group(2));
		}
		Connection connCourseTime = null;
		PreparedStatement stateCourseTime = null;
		try {
			connCourseTime =  (Connection) 
					DriverManager.getConnection(inForm.DB_URL, inForm.USER, inForm.PASSWORD);
			stateCourseTime = connCourseTime.prepareStatement("insert into course_time values(?,?,?)");
			stateCourseTime.setInt(1, sid);
			stateCourseTime.setInt(2, startIndex);
			stateCourseTime.setInt(3, endIndex);
			stateCourseTime.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeSQL(connCourseTime, stateCourseTime, null);
		}
	}
}
