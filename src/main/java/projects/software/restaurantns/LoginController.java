package projects.software.restaurantns;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.*;

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
        else if(isUser()){
            msg.setText("Success!");
            return true;
        }
        else {
            msg.setText("Invalid username or Password");
        }
        return false;
    }

    private boolean isUser(){
        try {
            Connection con = main.getConnection();
            Statement stmt = con.createStatement();
            String sql = "select username, password from user_tb";
            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                String user = resultSet.getString("username");
                String pass = resultSet.getString("password");
                if (username.getText().matches(user) && password.getText().matches(pass)) {
                    return true;
                }
            }
        }catch (Exception e){
            System.out.println("Error with getting and checking Users from DB");
            e.printStackTrace();
        }
        return false;
    }


}