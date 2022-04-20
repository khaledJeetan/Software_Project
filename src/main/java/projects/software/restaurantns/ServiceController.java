package projects.software.restaurantns;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
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

    private void showRestaurants(List<RestaurantService> restaurants) {

        myListener = new MyListener() {

            @Override
            public void onClickListener(RestaurantService restaurantService) {

                main.changeScene("options.fxml");

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

