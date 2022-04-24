package projects.software.restaurantns;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.RestaurantService;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ServiceController {
    private final ReviewSystem main = new ReviewSystem();
    private MyListener myListener;

    @FXML
    private URL location;
    @FXML
    private GridPane grid;
    @FXML
    private ImageView restaurantImage;
    @FXML
    private Label servicePrice;
    @FXML
    private Label restaurantStatus;
    @FXML
    private Label restaurantCity;
    @FXML
    private Label restaurantLocation;
    @FXML
    private Label restaurantName;
    @FXML
    private Label restaurantPhone;
    @FXML
    private Label restaurantDelivery;
    @FXML
    private Label restaurantWorkingTime;
    @FXML
    private Label restaurantNotes;

    private List<RestaurantService> restaurantServices = new ArrayList<>();

    @FXML
    private void initialize(){
        //System.out.println(location.getPath());
        String s = location.getPath().substring(87).split("\\.")[0];
        //System.out.println("hello for testing "  );
        restaurantServices.addAll(DbWraper.getrestaurants(s));
        if(restaurantServices != null) {
            showRestaurants(restaurantServices);
        }

    }

    @FXML
    public void addReview(){

    }
    @FXML
    public void addToFavorite(){

    }
    @FXML
    public void seeMenu(){}

    private void setData(RestaurantService restaurantService){
        Image image = restaurantService.getRestaurant().getCoverPhoto();
        if(image != null)
        restaurantImage.setImage(image);
        servicePrice.setText("Price: " + String.valueOf(restaurantService.getPrice()) + "$");
        restaurantName.setText(restaurantService.getRestaurant().getName());
        restaurantCity.setText(restaurantService.getRestaurant().getLocation().getCity());
        restaurantLocation.setText(restaurantService.getRestaurant().getLocation().getLocation());
        if (restaurantService.getRestaurant().getHasDelivery()=="N") {
            restaurantDelivery.setText("Delivery Not Available");
            restaurantDelivery.setTextFill(Color.RED);
        }
        restaurantPhone.setText(String.valueOf(restaurantService.getRestaurant().getPhone()));


    }

    private void showRestaurants(List<RestaurantService> restaurants) {

        myListener = new MyListener() {

            @Override
            public void onClickListener(RestaurantService restaurantService) {
                setData(restaurantService);
            }
        };

        int row = 1;
        try {
            for (RestaurantService rs : restaurants) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("restaurantItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                RestaurantIemController restaurantIemController = fxmlLoader.getController();
                restaurantIemController.setData(rs,myListener);
                grid.add(anchorPane,0,row++);
                GridPane.setMargin(anchorPane, new Insets(10));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        }


    @FXML
    public void back(){
        main.changeScene("options.fxml");
    }
}

