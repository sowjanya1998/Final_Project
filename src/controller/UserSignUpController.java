package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import model.UserDetailModel;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserSignUpController implements Initializable {

	@FXML
	private TextField name, email, address, phone;

	@FXML
	private PasswordField password, password2;

	@FXML
	private Button signup;

	@FXML
	private Label nameerror, emailerror, addresserror, phoneerror, passworderror, cpassworderror;

	// Name Validation
	boolean whetherNameIsNull = false;

	public void CheckNameIsNull(TextField name, Label nameerror, String message) {
		if (name.getText().isEmpty()) {
			whetherNameIsNull = true;
			nameerror.setText(message);
		}else {
			whetherNameIsNull = false;
			nameerror.setText("");
		}

	}

	// Email Validation
	boolean WhetherEmailIsNull = false;

	public void CheckEmailIsNull(TextField email, Label emailerror, String message) {

		if (email.getText().isEmpty()) {
			WhetherEmailIsNull = true;
			emailerror.setText(message);
		} else {
			WhetherEmailIsNull = false;
			CheckEmailPattern(email, emailerror);
		}
	}

	boolean EmailFlag = false;
	public void CheckEmailPattern(TextField email, Label emailerror) {
		if (email.getText().endsWith("@gmail.com") || email.getText().endsWith("@yahoo.com")
				|| email.getText().endsWith("@hawk.iit.edu")) {
			emailerror.setText("");
			EmailFlag = true;
		} else {
			EmailFlag = false;
			emailerror.setText("Please enter a valid e-mail address");
		}
	}

	// Address Validation
	boolean WhetherAddressIsNull = false;

	public void CheckAddressIsNull(TextField address2, Label addresserror2, String string) {
		// TODO Auto-generated method stub
		if (address2.getText().isEmpty()) {
			WhetherAddressIsNull = true;
			addresserror.setText(string);
		} else {
			WhetherAddressIsNull = false;
			addresserror.setText("");
		}
	}

	// Phone Validation
	boolean WhetherphoneIsNull = false;

	public void CheckPhoneIsNull(TextField phone, Label phoneerror, String message) throws SQLException {
		if (phone.getText().isEmpty()) {
			WhetherphoneIsNull = true;
			phoneerror.setText(message);
		} else {
			WhetherphoneIsNull = false;
			CheckPhoneFlag(phone, phoneerror);
		}
	}

	boolean PhoneFlag = true;
	
	public void CheckPhoneFlag(TextField phone2, Label phoneerror2) throws SQLException {
		if(!phone.getText().matches("^[0-9]{10}$")) {
			PhoneFlag = false;
			phoneerror2.setText("Please enter a valid phone number");
		}
		else if(phone.getText().matches("^[0-9]{10}$")) {
			PhoneFlag = true;
			phoneerror2.setText("");
		}
	}

	boolean WhetherPasswordIsNull = false;

	public void CheckPassword(PasswordField password, Label passworderror, String string) throws Exception {
		if (password.getText().isEmpty()) {
			WhetherPasswordIsNull = true;
			passworderror.setText(string);
		} else {
			WhetherPasswordIsNull = false;
			CheckPasswordMatch(password2, cpassworderror);
			passworderror.setText("");
		}
	}

	boolean PasswordMatch = true;
	public void CheckPasswordMatch(PasswordField password2, Label cpassworderror) throws Exception {
		if(!(new SHAHashingController().passwordMatch(password.getText(),password2.getText()))){
			PasswordMatch = false;
			cpassworderror.setText("Please re-enter your password");
		} else {
			password.setText(new SHAHashingController().encode(password.getText()));
			password2.setText(new SHAHashingController().encode(password2.getText()));
			PasswordMatch = true;
			cpassworderror.setText("");
		}
	}

	public void newuser(ActionEvent event) throws Exception {
		UserDetailModel userDb = new UserDetailModel();

		CheckNameIsNull(name, nameerror, "Name Missing");
		CheckEmailIsNull(email, emailerror, "Email Missing");
		CheckAddressIsNull(address, addresserror, "Address Missing");
		CheckPhoneIsNull(phone, phoneerror, "Phone No Missing");
		CheckPassword(password, passworderror, "Password Missing");

		boolean flag=true;
		if (whetherNameIsNull == false && WhetherEmailIsNull == false && WhetherAddressIsNull == false
				&& WhetherphoneIsNull == false && WhetherPasswordIsNull == false && PhoneFlag == true && EmailFlag == true && PasswordMatch == true) {

			flag = userDb.insertUserDetailRecords(name.getText(), email.getText(), address.getText(), phone.getText(),
					password.getText());
			
			if(flag) {
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
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		int maxLength = 10;
		phone.textProperty().addListener(new ContactValidationController(phone, 10));
	}
	
	@FXML
	private void WelcomeLinkAction(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/WelcomePageView.fxml"));
		Scene scene = new Scene(parent);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2); 
	    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}

}
