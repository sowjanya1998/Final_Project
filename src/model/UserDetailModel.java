package model;

import controller.FOS_Constants_Controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDetailModel {
	DBConnect dbc;
	Connection conn;
	Statement stmt = null;
	ResultSet result = null;

	public UserDetailModel() throws SQLException {
		dbc = new DBConnect();
	}

	// Create table for storing User Details
	public void createUserDetailDBTable() {
		try {
			// Open a connection
			System.out.println("Connecting to database to create UserDetailDB Table...");
			System.out.println("Connected database successfully...");

			// Execute create query
			System.out.println("Creating UserDetailDB table in given database...");

			stmt = dbc.connect().createStatement();
			String sql = "CREATE TABLE " + FOS_Constants_Controller.getUserDetails()
					+ "(user_id INTEGER NOT NULL AUTO_INCREMENT, " + " username VARCHAR(20), " + " email VARCHAR(30), "
					+ " address VARCHAR(50), " + " phone VARCHAR(10), " + " password VARCHAR(100), "
					+ " PRIMARY KEY(user_id))";
			stmt.executeUpdate(sql);
			System.out.println("Created UserDetailDB table in given database...");
			dbc.connect().close(); // close db connection

		} catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}

	public boolean insertUserDetailRecords(String username, String email, String address, String phone,
			String password) {
		Boolean flag = false;
		try {
			stmt = dbc.connect().createStatement();
			String sql = null;

			sql = "INSERT INTO " + FOS_Constants_Controller.getUserDetails() + "(username,email,address,phone,password) "
					+ "VALUES ('" + username + "', '" + email + "', '" + address + "', '" + phone + "', '"
					+ password + "' )";

			stmt.executeUpdate(sql);

			System.out.println("Records Inserted into UserDetailDB.");
			flag = true;
			dbc.connect().close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		System.out.println("flag value after insert: " + flag);
		return flag;
	}

	public boolean searchUserDetailDB(String username, String password) {
		boolean flag = false;
		try {
			stmt = dbc.connect().createStatement();
			String sql = null;
			sql = "SELECT username, password FROM " + FOS_Constants_Controller.getUserDetails() + " WHERE username = '"
					+ username + "' AND password = '" + password + "' ";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("sql: "+sql);
			System.out.println("rs: "+rs);
			if (rs.next())
				flag = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return flag;
	}
	
	public int getIdFromUserDetailDB(String username, String password) {
		boolean flag = false;
		int userId = 0;
		try {
			stmt = dbc.connect().createStatement();
			String sql = null;
			sql = "SELECT user_id FROM " + FOS_Constants_Controller.getUserDetails() + " WHERE username = '"
					+ username + "' AND password = '" + password + "' ";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				flag = true;
				userId = rs.getInt(1);
				System.out.println("userId: "+userId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userId;
	}

}
