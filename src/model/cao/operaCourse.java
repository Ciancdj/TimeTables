package model.cao;

import model.business.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JOptionPane;


/**
* @author Cianc
* @version 创建时间：2019年4月28日 上午9:45:08
* @ClassName operaCourse
* @Description merely to operate the course table
*/
public class operaCourse {
	/**
	 * this function can clear all information for course table
	 */
	public static void clearCourse() {
		Connection conn = null;
		PreparedStatement state = null;
		try {
			conn = (Connection) 
					DriverManager.getConnection(inForm.DB_URL, inForm.USER, inForm.PASSWORD);
			state = conn.prepareStatement("truncate table course");
			state.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "SQL连接异常  错误为 Clear_Course");
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
	
	public static void clearCourseTable() {
		clearCourse();
		operaCourseTime.clearCourseTime();
		operaCourseWeek.clearCourseWeek();
	}
	
	public static void addCourse(String classID, String classRoom, String courseName,
			String teacherName, int dayIndex, String jc, String zcd) {
		Connection connCourse = null;
		PreparedStatement stateCourse = null;
		PreparedStatement stateSid = null;
		ResultSet rsSid = null;
		int SID = 0;
		try {
			connCourse = (Connection) 
					DriverManager.getConnection(inForm.DB_URL, inForm.USER, inForm.PASSWORD);
			stateCourse = connCourse.prepareStatement("insert into course values(null,?,?,?,?,?)");
			stateSid = connCourse.prepareStatement("SELECT LAST_INSERT_ID()");
			stateCourse.setString(1, classID);
			stateCourse.setString(2, classRoom);
			stateCourse.setString(3, courseName);
			stateCourse.setString(4, teacherName);
			stateCourse.setInt(5, dayIndex);
			stateCourse.executeUpdate();
			rsSid = stateSid.executeQuery();
			while(rsSid.next()) {
				SID = Integer.parseInt(rsSid.getString("LAST_INSERT_ID()"));
			}
			operaCourseTime.addCourseTime(SID, jc);
			operaCourseWeek.addCourseWeek(SID, zcd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeSQL(null,stateCourse,null);
			closeSQL(connCourse, stateSid, rsSid);
		}
	}
	
	public static Queue<Object[]> search(int week, int regu) {
		Connection connCourse = null;
		PreparedStatement stateCourse = null;
		ResultSet rsCourse = null;
		Queue<Object[]> que = new LinkedList<Object[]>();
		try {
			connCourse = (Connection) 
					DriverManager.getConnection(inForm.DB_URL, inForm.USER, inForm.PASSWORD);
			stateCourse = connCourse.prepareStatement("select course.classID, course.classRoom, course.courseName, course.teacherName, course.dayIndex," + 
					"course_time.startIndex, course_time.endIndex from course, course_time, course_week" + 
					"	where course.SID = course_time.SID and" + 
					"		course.SID = course_week.SID and course_week.startIndex <= ?" + 
					"			and course_week.endIndex >= ? and reguWeek = ?");
			stateCourse.setInt(1, week);
			stateCourse.setInt(2, week);
			stateCourse.setInt(3, regu);
			rsCourse = stateCourse.executeQuery();
			Object []obj = null;
			// classID \ classRoom \ courseName \ teacherName \ dayIndex \ startIndex \ endIndex
			while(rsCourse.next()) {
				obj = new Object[7];
				obj[0] = rsCourse.getString("classID");
				obj[1] = rsCourse.getString("classRoom");
				obj[2] = rsCourse.getString("courseName");
				obj[3] = rsCourse.getString("teacherName");
				obj[4] = rsCourse.getInt("dayIndex");
				obj[5] = rsCourse.getInt("startIndex");
				obj[6] = rsCourse.getInt("endIndex");
				que.offer(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return que;
	}
	
}
