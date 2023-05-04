package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.UserActivitiesModel;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminUserActivityController implements Initializable {

	@FXML
	private TableView ActivitiesTable;

	@FXML
	private TableColumn userId, userName, activityName, price;

	@FXML
	private TextField userNewId, userNewName, userNewActivityName, userNewPrice;

	@FXML
	private Label dbOperationsMsg;

	ObservableList<Map> allData = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		UserActivitiesModel userActivityDb = new UserActivitiesModel();
		try {
			userActivityDb.createUserActivitiesTable();
			activityOperation(userActivityDb.fetchUserActivities());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ActivitiesTable.setOnMouseClicked((MouseEvent event) -> {
			if (event.getClickCount() == 1) {
				setCellValueFromTableToTextField();
			}
		});
	}

	@FXML
	public void addUserActivity(ActionEvent event) throws Exception {
		UserActivitiesModel userActivityobj = new UserActivitiesModel();

		if (!(userNewName.getText().equalsIgnoreCase("") && userNewActivityName.getText().equalsIgnoreCase("")
				&& userNewPrice.getText().equalsIgnoreCase(""))) {
			userActivityobj.addUserActivity(userNewName.getText(), userNewActivityName.getText(), Double.parseDouble(userNewPrice.getText()));
			activityOperation(userActivityobj.fetchUserActivities());

			dbOperationsMsg.setText("New User Activity  added Successfully!");
			refreshTextFields();
		} else {
			dbOperationsMsg.setText("OOPS!! Data Missing!!");
		}
	}

	@FXML
	public void updateUserActivity(ActionEvent event) throws SQLException {
		String name = userNewName.getText();
		String activity = userNewActivityName.getText();
		String price = userNewPrice.getText();

		try {
			// updating in database
			if (!(name.equalsIgnoreCase("") && activity.equalsIgnoreCase("") && price.equalsIgnoreCase(""))) {
				int id = Integer.parseInt(userNewId.getText());
				UserActivitiesModel userActivityobj = new UserActivitiesModel();
				userActivityobj.updateUserActivity(id, name, activity, price);

				// refresh FX tableview
				activityOperation(userActivityobj.fetchUserActivities());
				dbOperationsMsg.setText("User Activity details have been updated successfully!");
				refreshTextFields();
			} else {
				dbOperationsMsg.setText("OOPS!! No Record Selected.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void back(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/AdminFunctionsView.fxml"));
		Scene scene = new Scene(parent, 600, 500);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}

	@FXML
	public void deleteUserActivity(ActionEvent event) {
		UserActivitiesModel userActivityobj;
		
		try {
			// deleting from database
			if (!(userNewId.getText().equalsIgnoreCase(""))){
				userActivityobj = new UserActivitiesModel();
				userActivityobj.deleteData(Integer.parseInt(userNewId.getText()));
			
				// deleting from FX tableview
				activityOperation(userActivityobj.fetchUserActivities());
				dbOperationsMsg.setText("Deleted Successfully!");
				refreshTextFields();
			} else {
				dbOperationsMsg.setText("OOPS!! No Record Selected.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void logoutBtnAction(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/AdminLoginView.fxml"));
		Scene scene = new Scene(parent, 600, 500);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2); 
	    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}

	public void activityOperation(ResultSet rs) {
		allData = FXCollections.observableArrayList();

		try {
			while (rs.next()) {

				Map<String, String> dataRow = new HashMap<>();
				dataRow.put("user_id", rs.getInt(1) + "");
				dataRow.put("user_name", rs.getString(2));
				dataRow.put("activity_name", rs.getString(3));
				dataRow.put("price", rs.getString(4));

				allData.add(dataRow);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		userId.setCellValueFactory(new MapValueFactory("user_id"));
		userName.setCellValueFactory(new MapValueFactory("user_name"));
		activityName.setCellValueFactory(new MapValueFactory("activity_name"));
		price.setCellValueFactory(new MapValueFactory("price"));
		ActivitiesTable.setItems(allData);
	}

	private void setCellValueFromTableToTextField() {
		if (ActivitiesTable.getSelectionModel().getSelectedItem() != null) {
			Object selectedItems = ActivitiesTable.getSelectionModel().getSelectedItem();
			System.out.println(selectedItems.toString());

			userNewActivityName.setText((selectedItems.toString().split(",")[0]
					.substring(selectedItems.toString().split(",")[0].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));
			userNewPrice.setText((selectedItems.toString().split(",")[3].substring(
					selectedItems.toString().split(",")[3].lastIndexOf("=") + 1,
					selectedItems.toString().split(",")[3].indexOf("}"))).replaceFirst("\\s+", ""));
			userNewId.setText((selectedItems.toString().split(",")[1]
					.substring(selectedItems.toString().split(",")[1].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));
			userNewName.setText((selectedItems.toString().split(",")[2]
					.substring(selectedItems.toString().split(",")[2].lastIndexOf("=") + 1)).replaceFirst("\\s+", ""));
		}
	}

	private void refreshTextFields() {
		userNewId.setText("");
		userNewName.setText("");
		userNewActivityName.setText("");
		userNewPrice.setText("");
	}
}
