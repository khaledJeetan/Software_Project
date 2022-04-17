package projects.software.restaurantns;

import javafx.fxml.FXML;

public class serviceController {
    private final ReviewSystem main = new ReviewSystem();

    @FXML
    public void back(){
        main.changeScene("options.fxml");
    }
}
