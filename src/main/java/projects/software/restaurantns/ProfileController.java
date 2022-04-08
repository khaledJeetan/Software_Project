package projects.software.restaurantns;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ProfileController {


    @FXML
    private Label name;
    @FXML
    private Label pass;
    @FXML
    private AnchorPane afterLogin;

    public void initialize(){
        loadData();
    }

    public void loadData(){
        name.setText(UserHolder.getInstance().getUser().getName());
        pass.setText(UserHolder.getInstance().getUser().getPassword());
    }

}
