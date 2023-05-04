 package controller;

 import javafx.event.ActionEvent;
 import javafx.fxml.FXML;
 import javafx.fxml.FXMLLoader;
 import javafx.geometry.Rectangle2D;
 import javafx.scene.Node;
 import javafx.scene.Parent;
 import javafx.scene.Scene;
 import javafx.scene.control.Button;
 import javafx.scene.control.Hyperlink;
 import javafx.scene.control.Label;
 import javafx.scene.control.TextField;
 import javafx.stage.Screen;
 import javafx.stage.Stage;
 import model.ActivitiesModel;
 import model.UserDetailModel;

 import java.io.IOException;
 import java.security.NoSuchAlgorithmException;
 import java.sql.SQLException;

 public class UserLoginController{
     @FXML
     private TextField username, password;

     @FXML
     private Label loginStatus;

     @FXML
     private Button loginBtn;

     @FXML
     private Hyperlink signup;

     int user_id;

     public void userSignUp(ActionEvent event) throws IOException, SQLException {
         Parent parent = FXMLLoader.load(getClass().getResource("/view/UserSignUp.fxml"));
         Scene scene = new Scene(parent, 600, 500);
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         stage.setScene(scene);
         stage.show();

         Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
         stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
         stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
     }

     @FXML
     public void UserActions(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException {
// When User click login button
         UserDetailModel UserInfo = new UserDetailModel();

         boolean flag = UserInfo.searchUserDetailDB(username.getText(), PasswordEncoder.getInstance().encode(password.getText()));

         if(flag) {
             user_id = UserInfo.getIdFromUserDetailDB(username.getText(), password.getText());
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegistrationView.fxml"));
             Parent parent =(Parent) loader.load();
             CartController userAction = loader.getController();
             userAction.setUserName(username.getText());
             userAction.setUserId(user_id);
             Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
             stage.setScene(new Scene(parent));
             stage.show();

             Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
             stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
             stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
         }
         else {
             loginStatus.setText("OOPS!! Invalid Credentials. Try Login Again.......");
         }
     }

     public void Welcome(ActionEvent event) throws IOException {
         Parent parent = FXMLLoader.load(getClass().getResource("/view/WelcomePageView.fxml"));
         Scene scene = new Scene(parent, 600, 500);
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         stage.setScene(scene);
         stage.show();

         Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
         stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
         stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
     }


 }
