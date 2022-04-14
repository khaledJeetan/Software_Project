
package projects.software.restaurantns;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.time.LocalDate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class SingupController {
    private  ReviewSystem main = new ReviewSystem();
    private  User user;
    @FXML
    private TextField username;
    @FXML
    private TextField E_mail;
    @FXML
    private TextField newpassword;
    @FXML
    private TextField confirmpassword;
    @FXML
    private Label msg;

    @FXML
    public void signup() {
        if(!validcreateaccount()){
            return;
        }
        setUser();
        main.changeScene("login.fxml");
    }
    private void setUser() {
        UserHolder.getInstance().setUser(user);
    }
    private boolean validcreateaccount() {
        if(username.getText().isEmpty() || newpassword.getText().isEmpty()|| confirmpassword.getText().isEmpty()|| E_mail.getText().isEmpty()){
            msg.setText("Please Enter Information");
        }
        else if(!((confirmpassword.getText()).equals(newpassword.getText()))){
            msg.setText("the Password is not equal");
        }

        else if(isUser()){
            msg.setText("Success!");
            main.changeScene("signup.fxml");
            return true;
        }

        return false;
    }



    private boolean isUser(){
        try {
            LocalDate date = LocalDate.now();
           Connection con = main.getConnection();
            Statement stmt = con.createStatement();
            String sql = "Insert into USER_TB (USERNAME,PASSWORD) values ('"+username.getText()+"',"+newpassword.getText()+") ";
            ResultSet resultSet = stmt.executeQuery(sql);
      //      (USERNAME,PASSWORD,TYPE,CREATION_DATE,LAST_ACCESS_DATE,UPDATED_AT,ENABLED)
            username.setText("");
              newpassword.setText("");
              confirmpassword.setText("");
              E_mail.setText("");
              return true;


        }catch (Exception e){
            System.out.println("Error with getting and checking Users from DB");
            e.printStackTrace();
        }
        return false;
    }
}