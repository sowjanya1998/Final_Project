package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.*;

public class WelcomePageController implements Initializable{
	
	@FXML
	Hyperlink adminuser  , customercare  , userlogin;
	@FXML
	private ImageView bakery ;
	
	public void AdminLogin(ActionEvent event) throws IOException {
	   Parent parent = FXMLLoader.load(getClass().getResource("/view/AdminLoginView.fxml"));
	   Scene scene = new Scene(parent,600,500);
	   Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
	   stage.setScene(scene);
	   stage.show();

	   Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	   stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
	   stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}

	public void customerCare(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/CustomerCareView.fxml"));
	    Scene scene = new Scene(parent,600,500);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
	    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}

	public void CustomerLogin(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/UserLoginView.fxml"));
	    Scene scene = new Scene(parent,600,500);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
	    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image fest = new Image("/images/Fest.jpg");
		this.bakery.setImage(fest);
	}
}
