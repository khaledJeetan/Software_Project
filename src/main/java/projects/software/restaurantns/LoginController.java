package projects.software.restaurantns;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.User;

import java.sql.*;

public class LoginController{

    private final ReviewSystem main = new ReviewSystem();
    private User user;

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label msg;
    @FXML
    private ProgressBar progressBar;


    @FXML
    public void signIn() {

        if(username.getText().isEmpty() || password.getText().isEmpty()){
            msg.textProperty().unbind();
            msg.setText("Please Enter Information");
            return;
        }
        isUser();
    }



    @FXML
    public void signUp() {

        main.changeScene("signup.fxml");
    }

    @FXML
    public void close() {

        main.changeScene("Viewuser.fxml");
    }


    private void setUser() {
        UserHolder.getInstance().setUser(user);
    }


    private void isUser(){

        Task<Boolean> checkUser = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                try {
                    Connection con = main.getConnection();
                    Statement stmt = con.createStatement();
                    String sql = "select * from user_tb";
                    ResultSet resultSet = stmt.executeQuery(sql);
                    while (resultSet.next()) {
                        msg.setTextFill(Color.valueOf("#698ee4"));
                        String name = resultSet.getString("username");
                        String pass = resultSet.getString("password");
                        if (username.getText().matches(name) && password.getText().matches(pass)) {
                            user = DbWrapper.getUser(resultSet);
                            stmt.close();
                            con.close();
                            updateMessage("Success");
                            return true;
                        }
                    }
                    stmt.close();
                    con.close();
                    msg.setTextFill(Color.RED);
                    System.out.println();
                    updateMessage("Invalid Username or Password");
                    return false;
                }catch (Exception e){
                    System.out.println("Error with getting and checking Users from DB");
                    e.printStackTrace();
                    msg.textProperty().unbind();
                    return false;
                }
            }
        };
        checkUser.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                progressBar.setVisible(false);
                if(checkUser.getValue()) {
                    setUser();
                    if (user.isAdmin()) {
                        System.out.println("User IS Admin : " + user.getName());
                        main.changeScene("admin.fxml");
                    } else {
                        System.out.println("Hello User:  " + user.getName());
                        main.changeScene("user.fxml");
                    }
                }
            }
        });
        checkUser.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                progressBar.setVisible(false);
               System.out.println("Failed!!");
               msg.setText("Error: Checking user from database failed!!");
            }
        });
        progressBar.setVisible(true);
        progressBar.progressProperty().bind(checkUser.progressProperty());
        msg.textProperty().bind(checkUser.messageProperty());
        new Thread(checkUser).start();
    }

    }