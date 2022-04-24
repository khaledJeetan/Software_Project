package projects.software.restaurantns;

import javafx.fxml.FXML;

public class OptionsController {

    ReviewSystem main = new ReviewSystem();

    @FXML
    public void breakfast(){
        main.changeScene("mainscreen.fxml");
    }





    @FXML
    public void coffeeTea(){
        main.changeScene("coffeeTea.fxml");
    }
    @FXML
    public void sweet(){
        main.changeScene("sweets.fxml");
    }


    @FXML
    public void openMap(){}
    @FXML
    public void findRestaurant(){

    }


}
