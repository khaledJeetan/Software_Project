package projects.software.restaurantns;

import javafx.fxml.FXML;

public class OptionsController {

    ReviewSystem main = new ReviewSystem();

    @FXML
    public void breakfast(){
        main.changeScene("breakfast.fxml");
    }

    @FXML
    public  void launch(){
        main.changeScene("lunch.fxml");
    }

    @FXML
    public void dinner(){
        main.changeScene("dinner.fxml");
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
    public void healthyFood(){
        main.changeScene("healthyFood.fxml");
    }

    @FXML
    public void openMap(){}
    @FXML
    public void findRestaurant(){

    }


}
