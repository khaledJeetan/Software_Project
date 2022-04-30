
package projects.software.restaurantns;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.sql.*;
import java.time.LocalDateTime;


public class SingupController {
    private final ReviewSystem main = new ReviewSystem();
    @FXML
    private TextField username;
    @FXML
    private PasswordField newpassword;
    @FXML
    private PasswordField confirmpassword;
    @FXML
    private Label msg;

    private Connection con;
    private PreparedStatement stmt;

    @FXML
    public void signup() {
        if(!validCreateAccount()){
            return;
        }

        main.changeScene("login.fxml");
    }

    private boolean validCreateAccount() {
        if(username.getText().isEmpty() || newpassword.getText().isEmpty()|| confirmpassword.getText().isEmpty()){
          //  msg.setText("Please Enter Information");
        }
        else if(!((confirmpassword.getText()).equals(newpassword.getText()))){
          //  msg.setText("the Password is not equal");
        }

        else if(!isUser()){
            if(createUser()) {
             //   msg.setText("Account created Successfully");
              //  msg.setStyle("-fx-background-color:#d1e6dd");
             ////   msg.setTextFill(Color.rgb(48, 92, 69));
              //  msg.setVisible(true);
                return true;
            }
        }
        else {
         //   msg.setText(username.getText() + " is Already a User!!");
        }
       // msg.setVisible(true);
        return false;
    }

    private boolean isUser(){
        try {
            String sql = "select USERNAME from user_tb where USERNAME = ?";
            con = main.getConnection();
            stmt = con.prepareStatement(sql);
            stmt.setString(1, this.username.getText());
             ResultSet resultSet = stmt.executeQuery();
             if(resultSet.next()) {
                 stmt.close();
                 con.close();
                    return true;
                }
            stmt.close();
            con.close();
            } catch (SQLException ex) {
            ex.printStackTrace();
        }
        catch (Exception e){
            System.out.println("Error with getting and checking User from DB");
            e.printStackTrace();
        }
        return false;
    }

    private boolean createUser(){
        try {
            con = main.getConnection();
            String sql = "Insert into USER_TB (USERNAME,PASSWORD) values (?,?)";
            stmt = con.prepareStatement(sql);
            stmt.setString(1,username.getText());
            stmt.setString(2,confirmpassword.getText());
            stmt.executeUpdate();
            System.out.println("Query Executed successfully");
            stmt.close();
            con.close();
            username.setText("");
            newpassword.setText("");
            confirmpassword.setText("");
              return true;


        }catch (Exception e){
            System.out.println("Error with Creating new User.");
            e.printStackTrace();
        }
        return false;
    }
}