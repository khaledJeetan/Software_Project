
package projects.software.restaurantns;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class SingupController {
    private  ReviewSystem main = new ReviewSystem();
    @FXML
    private TextField username;
    @FXML
    private TextField newpassword;
    @FXML
    private TextField confirmpassword;
    @FXML
    private Label msg;

    @FXML
    public void signup() {
        if(!validCreateAccount()){
            return;
        }

        main.changeScene("login.fxml");
    }

    private boolean validCreateAccount() {
        if(username.getText().isEmpty() || newpassword.getText().isEmpty()|| confirmpassword.getText().isEmpty()){
            msg.setText("Please Enter Information");
        }
        else if(!((confirmpassword.getText()).equals(newpassword.getText()))){
            msg.setText("the Password is not equal");
        }

        else if(!isUser()){
            if(createUser()) {
                msg.setText("Account created Successfully");
                msg.setStyle("-fx-background-color:#d1e6dd");
                msg.setTextFill(Color.rgb(48, 92, 69));
                msg.setVisible(true);
                return true;
            }
        }
        else {
            msg.setText(username.getText() + " is Already a User!!");
        }
        msg.setVisible(true);
        return false;
    }

    private boolean isUser(){
        try {
            Connection con = main.getConnection();
            Statement stmt = con.createStatement();
            String sql = "select USERNAME from user_tb where USERNAME = '" +
                    this.username.getText() + "'";

             ResultSet resultSet = stmt.executeQuery(sql);

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
            Timestamp creationDate = Timestamp.valueOf(LocalDateTime.now());

            Connection con = main.getConnection();
            String sql = "Insert into USER_TB (USERNAME,PASSWORD,CREATION_DATE) values (?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,username.getText());
            stmt.setString(2,confirmpassword.getText());
            stmt.setTimestamp(3,creationDate);
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