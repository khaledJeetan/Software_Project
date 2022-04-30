package projects.software.restaurantns;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Restaurant;
import model.RestaurantService;

public class RestaurantIemController {


    @FXML
    private Label city;

    @FXML
    private Label Rlocation;

    @FXML
    private ImageView restaurantImage;

    @FXML
    private Label restaurantName;

    @FXML
    private Label reviewNumber;

    private MyListener myListener;

    private RestaurantService restaurantService;

    @FXML
    public void viewRestaurant(){

        myListener.onClickListener(restaurantService);
    }

    public void setData(RestaurantService restaurantService,MyListener myListener){

        this.myListener = myListener;
        this.restaurantService = restaurantService;
        Restaurant restaurant = restaurantService.getRestaurant();
        restaurantName.setText(restaurant.getName());
        city.setText(restaurant.getAddress().getCity());
        Rlocation.setText(restaurant.getAddress().getLocation());

        Image image = restaurant.getCoverPhoto();
        if(image != null){
            restaurantImage.setImage(image);
        }

        String sql = "select count(*) from review where restaurant_id = " + restaurant.getId();
        int reviewCount = DbWrapper.count(sql);
        reviewNumber.setText(String.valueOf(reviewCount));

    }


}
