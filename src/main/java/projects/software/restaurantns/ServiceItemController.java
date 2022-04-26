package projects.software.restaurantns;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import model.RestaurantService;
import model.Service;


public class ServiceItemController {


        @FXML
        private TextField value;
        @FXML
        private CheckBox service;
        private final RestaurantService restaurantService = new RestaurantService();
    private MyListener myListener;

    @FXML
    public void addService(){

        if (service.isSelected() && !value.getText().isEmpty() && isValueInt()){
            System.out.println("service " + service.getText() + " Is Selected.");
            restaurantService.setPrice(Float.parseFloat(value.getText()));
            myListener.onClickListener(restaurantService);
        }
        else if(service.isSelected() && value.getText().isEmpty()){
            Alert warn = new Alert(Alert.AlertType.WARNING);
            warn.setHeaderText("Price NOt Specified");
            warn.setContentText("PLease specify Price for this Service First!!");
            warn.show();
            service.setSelected(false);
        }
    }

    private boolean isValueInt() {
        try{
            Float.parseFloat(value.getText());
            return true;
        }catch (Exception e){
            Alert warn = new Alert(Alert.AlertType.ERROR);
            warn.setHeaderText("Price is not a number!!");
            warn.setContentText("String is not Accepted. Please enter number value for Price!!");
            warn.show();
            service.setSelected(false);
            return false;
        }
    }

    public void setData(Service s, MyListener myListener){
        this.myListener = myListener;
        service.setText(s.getName());
        this.restaurantService.setService(s);

    }

}
