package projects.software.restaurantns;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import model.User;

public class HelperController {
    private final ReviewSystem main = new ReviewSystem();
    private final User user = UserHolder.getInstance().getUser();

    @FXML
    private PasswordField confirmNewPass;

    @FXML
    private PasswordField newPass;

    @FXML
    private PasswordField oldPass;

    @FXML
    public void changePassword(){
        if(!oldPass.getText().isEmpty() && oldPass.getText().matches(user.getPassword())){
            if(newPass.getText().matches(confirmNewPass.getText())){
                user.setPassword(newPass.getText());
                if( DbWrapper.updateUser(user)){
                   Alert confirm = new Alert(Alert.AlertType.INFORMATION);
                   confirm.setHeaderText("Successfully Updated");
                   confirm.setContentText(user.getName() + ", Your password has been successfully Updated");
                   confirm.show();
                   backToProfile();
                   return;
               }
            }
        }
        Alert confirm = new Alert(Alert.AlertType.WARNING);
        confirm.setHeaderText("changing Password Failed");
        confirm.setContentText("Failed to update password please check the Information.");
        confirm.show();
    }
    @FXML
    public void backToProfile(){
        main.changeScene("user.fxml");
    }

}
