package model.cao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.business.inForm;

/**
* @author Cianc
* @version 创建时间：2019年4月28日 上午9:48:08
* @ClassName operaUser
* @Description merely operate user table
*/
public class operaUser {
	/**
	 * this function can clear all information for course table
	 */
	public static void clearUser() {
		Connection conn = null;
		PreparedStatement state = null;
		try {
			conn = (Connection) 
					DriverManager.getConnection(inForm.DB_URL, inForm.USER, inForm.PASSWORD);
			state = conn.prepareStatement("truncate table user");
			state.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "SQL连接异常  错误为 Clear_user");
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
	
	public static Object[] getUser() {
		Object[] obj = new Object[3];
		Connection connUser = null;
		PreparedStatement stateUser = null;
		ResultSet rsUser = null;
		try {
			connUser = (Connection) 
					DriverManager.getConnection(inForm.DB_URL, inForm.USER, inForm.PASSWORD);
			stateUser = connUser.prepareStatement("select * from user");
			rsUser =  stateUser.executeQuery();
			if(rsUser.next()) {
				obj[0] = rsUser.getString("userName");
				obj[1] = rsUser.getString("userAccount");
				obj[2] = rsUser.getString("userPassword");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeSQL(connUser, stateUser, rsUser);
		}
		return obj;
	}
	
	/*
	 * first to add the account information
	 */
	public static void addUser(String account, String password) {
		Connection connUser = null;
		PreparedStatement stateUser = null;
		try {
			connUser = (Connection) 
					DriverManager.getConnection(inForm.DB_URL, inForm.USER, inForm.PASSWORD);
			stateUser = connUser.prepareStatement("insert into user values(?,?,null)");
			stateUser.setString(1, account);
			stateUser.setString(2, password);
			stateUser.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeSQL(connUser, stateUser, null);
		}
	}
	
	/*
	 * add the last information
	 */
	public static void addUserInfor(String account, String name) {
		Connection connUser = null;
		PreparedStatement stateUser = null;
		try {
			connUser = (Connection) 
					DriverManager.getConnection(inForm.DB_URL, inForm.USER, inForm.PASSWORD);
			stateUser = connUser.prepareStatement("update user set userName = ? where userAccount = ?");
			stateUser.setString(1, name);
			stateUser.setString(2, account);
			stateUser.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeSQL(connUser, stateUser, null);
		}
	}
}
