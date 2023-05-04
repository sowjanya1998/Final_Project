package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLoginController extends AdminInterfaceController{

	@FXML
	private TextField user;

	@FXML
	private PasswordField password;

	@FXML
	private Label label;

	@FXML
	private Button button;

	@FXML
	private Button home;


	@Override
	@FXML
	public void Authenticate(ActionEvent event) {
			try {
			if (("root".equals(user.getText()) && ("root2023".equals(password.getText())))) {
				Parent parent = FXMLLoader.load(getClass().getResource("/view/AdminFunctionsView.fxml"));
				Scene scene = new Scene(parent);
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.setScene(scene);
	
				Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
				stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
				stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	
			} else {
				label.setText("OOPS!! Wrong Credentials");
				user.setText("");
				password.setText("");
			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	@Override
	@FXML
	public void Welcome(ActionEvent event) {
		Parent parent;
		try {
			parent = FXMLLoader.load(getClass().getResource("/view/WelcomePageView.fxml"));
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
			stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
