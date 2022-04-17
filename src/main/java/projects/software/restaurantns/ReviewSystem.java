package projects.software.restaurantns;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ReviewSystem extends Application {

    private static Stage stage;
    private static Connection con;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(ReviewSystem.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Restaurant Rating and Review System");
        stage.getIcons().add(new Image("icon.png"));
        stage.setScene(scene);
        stage.show();
    }

    public void changeScene(String fxml) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            this.stage.getScene().setRoot(root);
            this.stage.sizeToScene();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public Connection getConnection() throws SQLException {
        con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "c##software", "123456");
        if(con != null){
            System.out.println("connected successfully!");
        }
        return con;
    }

    public static void main(String[] args) throws SQLException {
        launch();
        con.close();
    }
}