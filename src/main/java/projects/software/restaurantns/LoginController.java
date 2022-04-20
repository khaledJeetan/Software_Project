package projects.software.restaurantns;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;

import java.sql.*;

public class LoginController {

    private final ReviewSystem main = new ReviewSystem();
    private User user;

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

        if(user.isAdmin()){
            main.changeScene("admin.fxml");
        }
        else{
            main.changeScene("user.fxml");
        }

    }



    @FXML
    public void signUp() {

        main.changeScene("signup.fxml");
    }

    public void close() {

        main.changeScene("options.fxml");
    }




    private void setUser() {
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
            String sql = "select * from user_tb";
            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                String user = resultSet.getString("username");
                String pass = resultSet.getString("password");
                if (username.getText().matches(user) && password.getText().matches(pass)) {
                    this.user = DbWraper.wrapUser(resultSet);
                    stmt.close();
                    con.close();
                    return true;
                }
            }
            stmt.close();
            con.close();
        }catch (Exception e){
            System.out.println("Error with getting and checking Users from DB");
            e.printStackTrace();
        }
        return false;
    }


}