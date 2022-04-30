package projects.software.restaurantns;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public class AdminProfileController {
    private final ReviewSystem main = new ReviewSystem();

    private User user = UserHolder.getInstance().getUser();
    @FXML
    AnchorPane dashboard;
    @FXML
    private Label name;
    @FXML
    private PieChart pieChart;
    @FXML
    private ImageView photo;
    @FXML
    private AnchorPane paneMainContent;

    public void initialize(){
        loadData();
    }

    private void loadData() {
        
        viewActiveUser();
        
        if(pieChart != null) {
            ObservableList<PieChart.Data> chartData =
                    FXCollections.observableArrayList(
                            new PieChart.Data("Users", DbWrapper.userCount()),
                            new PieChart.Data("Restaurants", DbWrapper.restaurantCount()));
            pieChart.setData(chartData);
            pieChart.setStartAngle(90);
        }
        name.setText(user.getName());
        if(user.getPhoto() != null)
            photo.setImage(user.getPhoto());
    }

    private void viewActiveUser() {
    }

    @FXML
    public void addRestaurants() {
        loadPage("add resturant");
    }

    @FXML
    public void chosen() {
        System.out.println("Chosen is Selected");
        paneMainContent.getChildren().clear();
    }

    @FXML
    public void ratingComment() {
        loadPage("rating&comment");
    }

    @FXML
    public void viewRestaurants() {
        loadPage("viewResturants");
    }

    @FXML
    public void viewUser() {
        loadPage("Viewuser");
    }

    @FXML
    public void logout(){
        UserHolder.getInstance().setUser(null);
        main.changeScene("login.fxml");
    }

    private void loadPage(String name) {

        try {
            paneMainContent.getChildren().clear();
//            grid.getChildren().clear();
//            grid.setAlignment(Pos.BASELINE_CENTER);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(name + ".fxml"));
            AnchorPane newPane = loader.load();
            paneMainContent.getChildren().add(newPane);

            // Set the loaded FXML file as the content of our main right-side pane

            paneMainContent.setRightAnchor(newPane, .0);
            paneMainContent.setTopAnchor(newPane, .0);
            paneMainContent.setLeftAnchor(newPane, .0);
            paneMainContent.setBottomAnchor(newPane, .0);

            Stage s = (Stage) dashboard.getScene().getWindow();
            s.sizeToScene();
            s.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}