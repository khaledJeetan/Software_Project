package projects.software.restaurantns;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    private final ReviewSystem main = new ReviewSystem();

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @FXML
    private Label msg;


    @FXML
    public void signIn() {

        if(!validLogin()){
            return;
        }
        setUser();
        main.changeScene("afterLogin.fxml");
    }

    private void setUser() {
        User user = new User(username.getText(),password.getText());
        UserHolder.getInstance().setUser(user);
    }

    private boolean validLogin() {
        if(username.getText().isEmpty() || password.getText().isEmpty()){
            msg.setText("Please Enter Information");
        }
        else if(username.getText().matches("Software") && password.getText().matches("project123")){
            msg.setText("Success!");
            return true;
        }
        else {
            msg.setText("Invalid username or Password");

        }
        return false;
    }


}