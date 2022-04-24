package projects.software.restaurantns;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import model.Address;
import model.Restaurant;
import model.RestaurantService;


import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class addRestaurantController implements Initializable {
    private final  ReviewSystem main = new ReviewSystem();
    static Restaurant  $restaurant= new Restaurant();
    static Address $address = new Address();
    private static ArrayList<RestaurantService> resService=new ArrayList<RestaurantService>();
    @FXML
    private TextField restaurantName;
    @FXML
    private ComboBox <String> city= new ComboBox();
    ObservableList <String> list = FXCollections.observableArrayList("Nablus","Jenin","Tulkarem","Al-Quds","Ramallah","Tubas","Biet Lahm","Hebron");
    @FXML
    private TextField address;
    @FXML
    private RadioButton hasDelivery;
    @FXML
    private RadioButton noDelivery;
    @FXML
    private TextField phoneNumber;
    @FXML
    private CheckBox Breakfast;
    @FXML
    private CheckBox Lunch;
    @FXML
    private CheckBox Dinner;
    @FXML
    private CheckBox Sweets;
    @FXML
    private CheckBox HealthyFood;
    @FXML
    private CheckBox CoffeeTea;
    @FXML
    private TextField BreakfastPrice;
    @FXML
    private TextField LunchPrice;
    @FXML
    private TextField DinnerPrice;
    @FXML
    private TextField SweetsPrice;
    @FXML
    private TextField HealthyFoodPrice;
    @FXML
    private TextField CoffeeTeaPrice;
    @FXML
    private TextField OpeningHours;
    @FXML
    private CheckBox isApproved;
    @FXML
    private ToggleGroup Delivery;
    @FXML
    private Label msg;
    @FXML
    private Label msg0;
    @FXML
    private Label msg1;
    private byte [] res_photo;
    private char num ='0';
    private int value;
    String selected = new String();
    @FXML
    public void citySelected () {
        selected = city.getValue();
    }

    public boolean isNumber(String a)
    {
        try {
            int value = Integer.parseInt(a);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    @FXML
    public void firstSceneNext () {

        if (!isNumber(phoneNumber.getText()))
            msg.setText("Please Enter a number in the phone number field");
        else if (restaurantName.getText().isEmpty() || address.getText().isEmpty() || phoneNumber.getText().isEmpty() || city.getValue() == null || Delivery.getSelectedToggle() == null) {
            msg.setText("Please Enter all Information");}
        else {
            $restaurant.setName(restaurantName.getText());
            $address.setLocation(address.getText());
            $address.setCity(selected);
            if(hasDelivery.isSelected())
                $restaurant.setHasDelivery("Y");
            else if (noDelivery.isSelected())
                $restaurant.setHasDelivery("N");
            $restaurant.setPhone(Integer.parseInt(phoneNumber.getText()));
            main.changeScene("add++.fxml");
        }


    }

    public void ServiceOptions(){
        if (Breakfast.isSelected())
        {
            num='1';
            RestaurantService $breakfast = new RestaurantService();
            $breakfast.setPrice(Integer.parseInt(BreakfastPrice.getText()));
            $breakfast.setService(10);
            resService.add($breakfast);
        }
        if (Lunch.isSelected())
        {
            num='1';
            RestaurantService $lunch = new RestaurantService();
            $lunch.setPrice(Integer.parseInt(LunchPrice.getText()));
            $lunch.setService(11);
            resService.add($lunch);

        }
        if (Dinner.isSelected())
        {
            num='1';
            RestaurantService $dinner = new RestaurantService();
            $dinner.setPrice(Integer.parseInt(DinnerPrice.getText()));
            $dinner.setService(12);
            resService.add($dinner);

        }
        if (CoffeeTea.isSelected())
        {
            num='1';
            RestaurantService $coffeetea = new RestaurantService();
            $coffeetea.setPrice(Integer.parseInt(CoffeeTeaPrice.getText()));
            $coffeetea.setService(13);
            resService.add($coffeetea);


        }
        if (Sweets.isSelected())
        {
            num='1';
            RestaurantService $sweets = new RestaurantService();
            $sweets.setPrice(Integer.parseInt(SweetsPrice.getText()));
            $sweets.setService(14);
            resService.add($sweets);

        }
        if (HealthyFood.isSelected())
        {
            num='1';
            RestaurantService $healthy = new RestaurantService();
            $healthy.setPrice(Integer.parseInt(HealthyFoodPrice.getText()));
            $healthy.setService(15);
            resService.add($healthy);

        }
    }
    @FXML
    public void SecondSceneNext () {

        if(num==0)
            msg1.setText("Please select at least one service and write its price!");
        else if (Breakfast.isSelected()&&!isNumber(BreakfastPrice.getText())||Lunch.isSelected()&&!isNumber(LunchPrice.getText())||Dinner.isSelected()&&!isNumber(DinnerPrice.getText())||CoffeeTea.isSelected()&&!isNumber(CoffeeTeaPrice.getText())||Sweets.isSelected()&&!isNumber(SweetsPrice.getText())||HealthyFood.isSelected()&&!isNumber(HealthyFoodPrice.getText()))
            msg1.setText("Please write the price of the service you selected");
        else {
            ServiceOptions();
            main.changeScene("add+++.fxml");}

    }
    @FXML
    public void SecondSceneBack () {
        main.changeScene("add resturant.fxml");
    }
    @FXML
    public void ThirdSceneBack () {
        main.changeScene("add++.fxml");
    }

    @FXML
    public void addPhoto ()
    {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);

        if(file != null)
            try {

                InputStream fin = new FileInputStream(file);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];

                for (int readNum; (readNum = fin.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum);
                }
                res_photo = bos.toByteArray();

            } catch (IOException ex) {
                Logger.getLogger("ss");
            }
    }
    @FXML
    public String isHealthApproved () {
        if (isApproved.isSelected())
            return "Y";
        else return "N";
    }
    @FXML
    public void AddRestaurant () {
        if(OpeningHours.getText().isEmpty())
            msg0.setText("Please Enter all information");
        else {
            $restaurant.setOpeningHours(OpeningHours.getText());
            $restaurant.setHealthApproved(isHealthApproved());
            try {
                Connection con = main.getConnection();
                String locations = "Insert into ADDRESS (ID,CITY,LOCATION) values (ADDRESS_PK_SEQUENCE.nextval,?,?)";
                PreparedStatement stmt1 = con.prepareStatement(locations);
                stmt1.setString(1, $address.getCity());
                stmt1.setString(2, $address.getLocation());
                String sql = "Insert into RESTAURANT (ID,NAME,HAS_DELIVERY,PHONE,ADDRESS,IS_HEALTH_APPROVED,OPENING_HOURS,COVER_PHOTO) values (RESTAURANT_ID_SEQUENCE.nextval,?,?,?,ADDRESS_PK_SEQUENCE.currval,?,?,?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1,$restaurant.getName());
                stmt.setString(2,$restaurant.getHasDelivery());
                stmt.setInt(3,$restaurant.getPhone());
                stmt.setString(4,$restaurant.getHealthApproved());
                stmt.setString(5,OpeningHours.getText());
                stmt.setBytes(6,res_photo);
                stmt1.executeUpdate();
                stmt.executeUpdate();
                for (RestaurantService r : resService)
                {
                    String s = "Insert into RESTAURANT_SERVICE (SERVICE_ID,PRICE,RESTAURANT_ID) values (?,?,RESTAURANT_ID_SEQUENCE.currval)";
                    PreparedStatement stmt3 = con.prepareStatement(s);
                    stmt3.setInt(1,r.getService());
                    stmt3.setInt(2,r.getPrice());
                    stmt3.executeUpdate();
                }
                stmt1.close();
                stmt.close();
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            catch (Exception e){
                System.out.println("Error while adding restaurant");
                e.printStackTrace();
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        city.setItems(list);
    }
}
