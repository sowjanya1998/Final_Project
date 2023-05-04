package model;

import controller.FOS_Constants_Controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserActivitiesModel {
	DBConnect dbc = null;
	Connection conn;
	Statement stmt = null;
	ResultSet result = null;

	// Create table for storing Menu Details
	public void createUserActivitiesTable() {
		try {

			// Open a connection
			System.out.println("Connecting to database to add users registered in Activities Table...");
			System.out.println("Connected database successfully...");

			// Execute create query
			System.out.println("Creating userActivity table in given database...");
			dbc = new DBConnect();
			stmt = dbc.connect().createStatement();

			String sql = "CREATE TABLE  IF NOT EXISTS "  + FOS_Constants_Controller.getUserActivities() +  "(user_id INTEGER NOT NULL AUTO_INCREMENT, "
					+ " user_name VARCHAR(20), " + " activity_name VARCHAR(20), " + " price numeric(8,2), "
					+ " PRIMARY KEY(user_id))";

			stmt.executeUpdate(sql);
			System.out.println("Created userActivity table in given database...");
			dbc.connect().close(); // close db connection
		} catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}

	public void addUserActivity(String username, String activityname, Double price) throws SQLException {
		dbc = new DBConnect();
		stmt = dbc.connect().createStatement();

		String sql = "INSERT INTO " + FOS_Constants_Controller.getUserActivities() + "(user_name , activity_name ,price) "
				+ "VALUES (' " + username + " ', ' " + activityname + " ', ' " + price + " ')";
		stmt.execute(sql);
		dbc.connect().close();
	}

	public void updateUserActivity(int userid, String username, String activityname, String price) throws SQLException {
		Connection conn = null;
		dbc = new DBConnect();

		stmt = dbc.connect().createStatement();
		String query = "Update " + FOS_Constants_Controller.getUserActivities() + " SET user_name = ' " + username + "' , activity_name = ' " + activityname
				+ " ' , price = ' " + price + " ' WHERE user_id = ' " + userid + "'";
		stmt.executeUpdate(query);
		dbc.connect().close();
	}

	public void deleteData(int id) {
		Connection conn = null;
		dbc = new DBConnect();

		try {
			stmt = dbc.connect().createStatement();
			String sql = "DELETE FROM " + FOS_Constants_Controller.getUserActivities() + " where user_id = '" + id + "'";
			stmt.executeUpdate(sql);
			dbc.connect().close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public ResultSet fetchUserActivities() throws SQLException {
		dbc = new DBConnect();
		stmt = dbc.connect().createStatement();

		String query = "SELECT * FROM " + FOS_Constants_Controller.getUserActivities();

		ResultSet rs = stmt.executeQuery(query);
		dbc.connect().close();

		return rs;
	}
}
