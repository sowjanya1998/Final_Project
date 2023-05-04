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
import model.ActivitiesModel;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminActivityListController implements Initializable {

	@FXML
	private TableView foodtable;

	@FXML
	private TableColumn activity_id, activity_name, activity_desc, activity_price;

	@FXML
	private TextField new_activity_id, new_activity_name, new_activity_price, new_activity_desc;

	@FXML
	private Label dbOperationsMsg;

	ObservableList<Map> allData = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ActivitiesModel fItemDBObj;

		try {
			fItemDBObj = new ActivitiesModel();
			fItemDBObj.createUserDetailDBTable();
			refreshMenuFxTable(fItemDBObj.fetchActivityListFromDB());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		foodtable.setOnMouseClicked((MouseEvent event) -> {
			if (event.getClickCount() == 1) {
				setCellValueFromTableToTextField();
			}
		});
	}

	@FXML
	public void addNewActivity(ActionEvent event) throws Exception {
		ActivitiesModel fItemDBObj = new ActivitiesModel();

		if (!(new_activity_name.getText().equalsIgnoreCase("") && new_activity_desc.getText().equalsIgnoreCase("")
				&& new_activity_price.getText().equalsIgnoreCase(""))) {
			fItemDBObj.insertUserDetailRecords(new_activity_name.getText(),  Double.parseDouble(new_activity_price.getText()),new_activity_desc.getText());
			refreshMenuFxTable(fItemDBObj.fetchActivityListFromDB());

			dbOperationsMsg.setText("New Activity added Successfully!");
			refreshTextFields();
		} else {
			dbOperationsMsg.setText("No Data Entered");
		}
	}

	@FXML
	public void updateActivity(ActionEvent event) throws SQLException {
		String name = new_activity_name.getText();
		String desc = new_activity_desc.getText();
		String price = new_activity_price.getText();

		try {
			// updating in database
			if (!(name.equalsIgnoreCase("") && desc.equalsIgnoreCase("") && price.equalsIgnoreCase(""))) {
				int id = Integer.parseInt(new_activity_id.getText());
				ActivitiesModel fItemDBObj = new ActivitiesModel();
				fItemDBObj.updateUserActivity(id, name, price, desc);

				// refresh FX tableview
				refreshMenuFxTable(fItemDBObj.fetchActivityListFromDB());
				dbOperationsMsg.setText("User Activity have been updated successfully!");
				refreshTextFields();
			} else {
				dbOperationsMsg.setText("No Record Selected");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void deleteActivity(ActionEvent event) {
		ActivitiesModel fItemDBObj;
		
		try {
			// deleting from database
			if (!(new_activity_id.getText().equalsIgnoreCase(""))){
				fItemDBObj = new ActivitiesModel();
				fItemDBObj.deleteActivity(Integer.parseInt(new_activity_id.getText()));
			
				// deleting from FX tableview
				refreshMenuFxTable(fItemDBObj.fetchActivityListFromDB());
				dbOperationsMsg.setText("Activity Deleted Successfully!");
				refreshTextFields();
			} else {
				dbOperationsMsg.setText("No Record Selected");
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

	public void refreshMenuFxTable(ResultSet rs) {
		allData = FXCollections.observableArrayList();

		try {
			while (rs.next()) {

				Map<String, String> dataRow = new HashMap<>();
				dataRow.put("activity_id", rs.getInt(1) + "");
				dataRow.put("activity_name", rs.getString(2));
				dataRow.put("price", rs.getString(3));
				dataRow.put("decription", rs.getString(4));

				allData.add(dataRow);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		activity_id.setCellValueFactory(new MapValueFactory("activity_id"));
		activity_name.setCellValueFactory(new MapValueFactory("activity_name"));
		activity_desc.setCellValueFactory(new MapValueFactory("decription"));
		activity_price.setCellValueFactory(new MapValueFactory("price"));
		foodtable.setItems(allData);
	}

	private void setCellValueFromTableToTextField() {
		if (foodtable.getSelectionModel().getSelectedItem() != null) {
			Object selectedItems = foodtable.getSelectionModel().getSelectedItem();

			new_activity_name.setText((selectedItems.toString().split(",")[0]
					.substring(selectedItems.toString().split(",")[0].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));
			new_activity_id.setText((selectedItems.toString().split(",")[3].substring(
					selectedItems.toString().split(",")[3].lastIndexOf("=") + 1,
					selectedItems.toString().split(",")[3].indexOf("}"))).replaceFirst("\\s+", ""));
			new_activity_desc.setText((selectedItems.toString().split(",")[1]
					.substring(selectedItems.toString().split(",")[1].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));
			new_activity_price.setText((selectedItems.toString().split(",")[2]
					.substring(selectedItems.toString().split(",")[2].lastIndexOf("=") + 1)).replaceFirst("\\s+", ""));
		}
	}

	private void refreshTextFields() {
		new_activity_id.setText("");
		new_activity_name.setText("");
		new_activity_price.setText("");
		new_activity_desc.setText("");
	}
}
