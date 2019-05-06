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
* @version 创建时间：2019年4月28日 下午7:38:09
* @ClassName 类名称
* @Description 类描述
*/
public class operaCourseWeek {
	/**
	 * this function can clear all information for course table
	 */
	public static void clearCourseWeek() {
		Connection conn = null;
		PreparedStatement state = null;
		try {
			conn = (Connection) 
					DriverManager.getConnection(inForm.DB_URL, inForm.USER, inForm.PASSWORD);
			state = conn.prepareStatement("truncate table course_week");
			state.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "SQL连接异常  错误为 Clear_CourseWeek");
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

	public static void addCourseWeek(int sid, String zcd) {
		String []splitWord = zcd.split(",");
		for(int index = 0; index < splitWord.length; index++) {
			addWeek(sid,splitWord[index]);
		}
	}
	
	public static void addWeek(int sid, String zcd) {
		Pattern p = Pattern.compile("((.{1,2})-(.{1,2}).(\\((.)\\)|)|(\\d+))");
		Matcher m = p.matcher(zcd);
		int startIndex = 0, endIndex = 0;
		String regu = "";
		if(m.find()) {
			if(m.group(2) != null && m.group(3) != null) {
				startIndex = Integer.parseInt(m.group(2));
				endIndex = Integer.parseInt(m.group(3));
			} else {
				startIndex = Integer.parseInt(m.group(6));
				endIndex = Integer.parseInt(m.group(6));
			}
			regu = m.group(5);
//			System.out.println("m.group(0)" + m.group(0) + "\n"
//							+ "m.group(1)" + m.group(1) + "\n"
//							+ "m.group(2)" + m.group(2) + "\n"
//							+ "m.group(3)" + m.group(3) + "\n"
//							+ "m.group(4)" + m.group(4) + "\n"
//							+ "m.group(5)" + m.group(5) + "\n"
//							+ "m.group(6)" + m.group(6) + "\n"
//							+ "--------------------------------"
//					);
		}
		Connection connCourseWeek = null;
		PreparedStatement stateCourseWeek = null;
		try {
			connCourseWeek =  (Connection) 
					DriverManager.getConnection(inForm.DB_URL, inForm.USER, inForm.PASSWORD);
			stateCourseWeek = connCourseWeek.prepareStatement("insert into course_week values(null,?,?,?,?)");
			stateCourseWeek.setInt(1, sid);
			stateCourseWeek.setInt(2, startIndex);
			stateCourseWeek.setInt(3, endIndex);
			if(regu == null) {
				stateCourseWeek.setInt(4, 0);
			} else if(regu.equals("单")) {
				stateCourseWeek.setInt(4, 1);
			} else {
				stateCourseWeek.setInt(4, 2);
			}
			stateCourseWeek.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeSQL(connCourseWeek, stateCourseWeek, null);
		}
	}
}
