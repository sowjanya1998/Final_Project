package model;

import controller.FOS_Constants_Controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ActivitiesModel {
	DBConnect dbc;
	Connection conn;
	Statement stmt = null;
	ResultSet result = null;

	public ActivitiesModel() throws SQLException {
		dbc = new DBConnect();
	}

	public void createUserDetailDBTable() {
		try {
			dbc = new DBConnect();
			stmt = dbc.connect().createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS " + FOS_Constants_Controller.getACTIVITIES()
					+ "(activity_id INTEGER NOT NULL AUTO_INCREMENT, " + " activity_name VARCHAR(20), " + " price numeric(8,2), "
					+ " Description VARCHAR(50),"
					+ " PRIMARY KEY(activity_id))";
			stmt.executeUpdate(sql);
			dbc.connect().close(); // close db connection

		} catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}

	public boolean insertUserDetailRecords(String activityName, Double price, String description) {
		Boolean flag = false;
		try {
			stmt = dbc.connect().createStatement();
			String sql = null;
			sql = "INSERT INTO " + FOS_Constants_Controller.getACTIVITIES() + "(activity_name,price,Description) "
					+ "VALUES ('" + activityName + "', '" + price + "', '" + description+ "')";

			stmt.executeUpdate(sql);
			flag = true;
			dbc.connect().close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return flag;
	}


	public ResultSet fetchActivityListFromDB() throws SQLException {
		dbc = new DBConnect();
		stmt = dbc.connect().createStatement();

		String query = "SELECT * FROM " + FOS_Constants_Controller.getACTIVITIES();

		ResultSet rs = stmt.executeQuery(query);
		dbc.connect().close();

		return rs;
	}

	public void updateUserActivity(int activity_id, String activity_name, String Price, String description)
			throws SQLException {
		Connection conn = null;
		dbc = new DBConnect();
		stmt = dbc.connect().createStatement();
		String query = "Update " + FOS_Constants_Controller.getACTIVITIES() + " SET activity_name = '" + activity_name
				+ "' , price = '" + Price + "' , Description = '" + description
				+ "' WHERE activity_id = '" + activity_id + "'";
		stmt.executeUpdate(query);
		dbc.connect().close();
	}

	public void deleteActivity(int id) {
		Connection conn = null;
		dbc = new DBConnect();

		try {
			stmt = dbc.connect().createStatement();
			String sql = "DELETE FROM " + FOS_Constants_Controller.getACTIVITIES() + " where activity_id = '" + id + "'";
			stmt.executeUpdate(sql);
			dbc.connect().close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}


}
