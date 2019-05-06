package model.cao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import javax.swing.JOptionPane;

import model.business.inForm;

/**
* @author Cianc
* @version 创建时间：2019年4月28日 上午9:48:22
* @ClassName operaInstall
* @Description merely to operate install table
*/
public class operaInstall {
	/**
	 * this function can clear all information for course table
	 */
	public static void clearInstall() {
		Connection conn = null;
		PreparedStatement state = null;
		try {
			conn = (Connection) 
					DriverManager.getConnection(inForm.DB_URL, inForm.USER, inForm.PASSWORD);
			state = conn.prepareStatement("truncate table install");
			state.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "SQL连接异常  错误为 Clear_install");
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
	
	public static boolean haveElem() {
		Connection connInstall = null;
		PreparedStatement stateInstall = null;
		ResultSet rsInstall = null;
		boolean bool = false;
		try {
			connInstall = (Connection) 
					DriverManager.getConnection(inForm.DB_URL, inForm.USER, inForm.PASSWORD);
			stateInstall = connInstall.prepareStatement("select ID from sctimetable.install where ID = 1");
			rsInstall = stateInstall.executeQuery();
			if(rsInstall.next()) {
				//System.out.println(rsInstall.getInt("ID"));
				bool = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeSQL(connInstall, stateInstall, rsInstall);
		}
		return bool;
	}
	
	public static Object[] getInstall() {
		Object[] ret = new Object[2];
		Connection connInstall = null;
		PreparedStatement stateInstall = null;
		ResultSet rsInstall = null;
		try {
			connInstall = (Connection) 
					DriverManager.getConnection(inForm.DB_URL, inForm.USER, inForm.PASSWORD);
			stateInstall = connInstall.prepareStatement("select * from install");
			rsInstall = stateInstall.executeQuery();
			if(rsInstall.next()) {
				ret[0] = rsInstall.getString("imgPath");
				ret[1] = rsInstall.getDate("courseStart");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeSQL(connInstall, stateInstall, rsInstall);
		}
		return ret;
	}
	
	public static void addInstall() {
		Connection connInstall = null;
		PreparedStatement stateInstall = null;
		try {
			connInstall = (Connection) 
					DriverManager.getConnection(inForm.DB_URL, inForm.USER, inForm.PASSWORD);
			stateInstall = connInstall.prepareStatement("insert into install values(null,null,null)");
			stateInstall.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeSQL(connInstall, stateInstall, null);
		}
	}
	
	public static void addImgPath(String path) {
		Connection connInstall = null;
		PreparedStatement stateInstall = null;
		try {
			connInstall = (Connection) 
					DriverManager.getConnection(inForm.DB_URL, inForm.USER, inForm.PASSWORD);
			stateInstall = connInstall.prepareStatement("update install set imgPath = ? where ID = 1");
			stateInstall.setString(1, path);
			stateInstall.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeSQL(connInstall, stateInstall, null);
		}
	}
	
	public static void addStartTime(Date date) {
		Connection connInstall = null;
		PreparedStatement stateInstall = null;
		try {
			connInstall = (Connection) 
					DriverManager.getConnection(inForm.DB_URL, inForm.USER, inForm.PASSWORD);
			stateInstall = connInstall.prepareStatement("update install set courseStart = ? where ID = 1");
			stateInstall.setDate(1, date);
			stateInstall.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeSQL(connInstall, stateInstall, null);
		}
	}
}
