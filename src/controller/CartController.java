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
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.ActivitiesModel;
import model.UserActivitiesModel;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

public class CartController implements Initializable {
	@FXML
	private TableView ActivityTable;
	@FXML
	private TableView UserTable;
	@FXML
	private TableColumn activity_id, activity_name, activity_price, activity_desc;
	@FXML
	private TableColumn user_activity_id, user_activity_name, user_activity_price;

	@FXML
	private Label userid, username, registrationMessage, errorMsg;
	int userId;
	String userName;

	Double p = 0.0, pt = 0.0;

	ObservableList<Map> allData = null;
	ObservableList<Map> cartData = FXCollections.observableArrayList();
	ObservableList<Map> cartDataQty = FXCollections.observableArrayList();
	ObservableList list = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ActivitiesModel fItemDBObj;
		try {
			fItemDBObj = new ActivitiesModel();
			refreshMenu(fItemDBObj.fetchActivityListFromDB());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void addActivity(ActionEvent event) {
		Object selectedItems = ActivityTable.getSelectionModel().getSelectedItem();
		if (selectedItems != null) {
			Map<String, String> cartdatarow = new HashMap<>();
			cartdatarow.put("activity_id", (selectedItems.toString().split(",")[2]
					.substring(selectedItems.toString().split(",")[2].lastIndexOf("=") + 1)));
			cartdatarow.put("activity_name",
					((selectedItems.toString().split(",")[0]
							.substring(selectedItems.toString().split(",")[0].lastIndexOf("=") + 1)).replaceAll("\\s+",
									"")));
			cartdatarow.put("activity_price",
					(selectedItems.toString().split(",")[3].substring(
							selectedItems.toString().split(",")[3].lastIndexOf("=") + 1,
							selectedItems.toString().split(",")[3].indexOf("}"))));
			cartData.add(cartdatarow);

			user_activity_id.setCellValueFactory(new MapValueFactory("activity_id"));
			user_activity_name.setCellValueFactory(new MapValueFactory("activity_name"));
			user_activity_price.setCellValueFactory(new MapValueFactory("activity_price"));
			UserTable.setItems(cartData);
			errorMsg.setText("");
		} else if(selectedItems == null) {
			errorMsg.setText("Please select a menu item");
		}
	}

	@FXML
	public void setUserName(String name) {
		username.setText(name);
		userName = username.getText();
	}

	@FXML
	public void setUserId(int id) {
		userid.setText(id + "");
		userId = Integer.parseInt(userid.getText());
	}

	@FXML
	private void removeFromCart(ActionEvent event) {
		Object selectedRowForDeletion = UserTable.getSelectionModel().getSelectedItem();
		
		if(selectedRowForDeletion != null) {
			UserTable.getItems().removeAll(UserTable.getSelectionModel().getSelectedItem());
			errorMsg.setText("");
		} else {
			errorMsg.setText("No Activity Selected!!");
		}
	}

	public void refreshMenu(ResultSet rs) {
		allData = FXCollections.observableArrayList();

		try {
			while (rs.next()) {

				Map<String, String> dataRow = new HashMap<>();
				dataRow.put("activity_id", rs.getInt(1) + "");
				dataRow.put("activity_name", rs.getString(2));
				dataRow.put("activity_price", rs.getString(3));
				dataRow.put("activity_desc", rs.getString(4));
				allData.add(dataRow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		activity_id.setCellValueFactory(new MapValueFactory("activity_id"));
		activity_name.setCellValueFactory(new MapValueFactory("activity_name"));
		activity_desc.setCellValueFactory(new MapValueFactory("activity_price"));
		activity_price.setCellValueFactory(new MapValueFactory("activity_desc"));
		ActivityTable.setItems(allData);
	}

	@FXML
	public void placeOrder(ActionEvent event) throws NumberFormatException, SQLException {
		UserActivitiesModel userActivity = new UserActivitiesModel();
		Iterator<HashMap<String,String>> itr=UserTable.getItems().iterator();

		while(itr.hasNext())
		{
			HashMap<String,String> hm= itr.next();
			userActivity.addUserActivity(userName,hm.get("activity_name"),Double.parseDouble(hm.get("activity_price")));

		}
		registrationMessage.setText("User Activity Registered");
	}

	@FXML
	public void close(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/UserLoginView.fxml"));
		Scene scene = new Scene(parent, 600, 500);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}

}
