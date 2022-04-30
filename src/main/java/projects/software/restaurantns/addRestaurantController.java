package projects.software.restaurantns;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import model.Address;
import model.Restaurant;
import model.RestaurantService;
import model.Service;


import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class addRestaurantController implements Initializable {
    private Connection con;
    private final  ReviewSystem main = new ReviewSystem();
    private static Restaurant  $restaurant= new Restaurant();
    private static Address $address = new Address();
    private static ArrayList<RestaurantService> resService=new ArrayList<>();
    private MyListener myListener;
    ObservableList <String> list = FXCollections.observableArrayList(
            "Nablus","Jenin","Tulkarem","Al-Quds","Ramallah","Tubas","Biet Lahm","Hebron");
    private byte [] res_photo;
    private char num ='0';
    private Service service;
    private int value;
    String selected = new String();
    @FXML
    private TextField restaurantName;
    @FXML
    private ComboBox <String> city= new ComboBox();
    @FXML
    private TextField address;
    @FXML
    private RadioButton hasDelivery;
    @FXML
    private RadioButton noDelivery;
    @FXML
    private TextField phoneNumber;
    @FXML
    private GridPane grid;
    @FXML
    private TextField OpeningHours;
    @FXML
    private CheckBox isApproved;
    @FXML
    private ToggleGroup Delivery;
    @FXML
    private Label photoFile;
    @FXML
    private Label msg;
    @FXML
    private Label msg0;
    @FXML
    private Label msg1;
    @FXML
    private AnchorPane pane;

    private static AnchorPane mainPane;

    @FXML
    public void citySelected () {
        selected = city.getValue();
    }

    public boolean isNumber(String a) {
        try {
            int value = Integer.parseInt(a);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    @FXML
    public void firstSceneNext () {
        mainPane = pane;
        if (!isNumber(phoneNumber.getText()))
            msg.setText("Please Enter a number in the phone number field");
        else if (restaurantName.getText().isEmpty() || address.getText().isEmpty() ||
                phoneNumber.getText().isEmpty() || city.getValue() == null ||
                Delivery.getSelectedToggle() == null) { msg.setText("Please Enter all Information");}
        else {
            $restaurant.setName(restaurantName.getText());
            $address.setLocation(address.getText());
            $address.setCity(selected);
            if(hasDelivery.isSelected())
                $restaurant.setHasDelivery(true);
            else if (noDelivery.isSelected())
                $restaurant.setHasDelivery(false);
            $restaurant.setPhone(Integer.parseInt(phoneNumber.getText()));

            nextScene("add++.fxml");
        }


    }

    // needs to be checked
    @FXML
    public void SecondSceneNext () {

        if (!resService.isEmpty())
            nextScene("add+++.fxml");

        msg1.setText("Please select at least one service and write its price!");
    }

    private void nextScene(String next) {
        try {
            mainPane.getChildren().clear();
           FXMLLoader loader = new FXMLLoader(getClass().getResource(next));
            AnchorPane nextPane = loader.load();
            mainPane.getChildren().add(nextPane);

            mainPane.setRightAnchor(nextPane, .0);
            mainPane.setTopAnchor(nextPane, .0);
            mainPane.setLeftAnchor(nextPane, .0);
            mainPane.setBottomAnchor(nextPane, .0);

            } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    public void SecondSceneBack () {
        nextScene("add resturant.fxml");
    }

    @FXML
    public void ThirdSceneBack () {
        nextScene("add++.fxml");
    }

    @FXML
    public void addPhoto () {
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
                photoFile.setText(file.getName());
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
        else
            photoFile.setText("");
    }
    @FXML
    public boolean isHealthApproved () {
        return isApproved.isSelected() ? true: false;
    }

    @FXML
    public void AddRestaurant (){
        if(OpeningHours.getText().isEmpty())
            msg0.setText("Please Enter all information");
        else {
            $restaurant.setOpeningHours(OpeningHours.getText());
            $restaurant.setHealthApproved(isHealthApproved());
            try {
                    con = main.getConnection();
                con.setAutoCommit(false);
                String sql = "Insert into ADDRESS (CITY,LOCATION) values (?,?)";
                PreparedStatement stmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, $address.getCity());
                stmt.setString(2, $address.getLocation());
                int addressID = DbWrapper.checkAddress($address);
                if(addressID<1) {
                    stmt.executeUpdate();
                    ResultSet rs = stmt.executeQuery("select ADDRESS_PK_SEQUENCE.currval from DUAL");
                    if(rs != null && rs.next())
                        addressID = rs.getInt(1);
                    System.out.println("Auto generated address Id is : " + addressID);
                }

                String sql1 = "Insert into RESTAURANT (NAME,HAS_DELIVERY,PHONE,ADDRESS," +
                        "IS_HEALTH_APPROVED,OPENING_HOURS,COVER_PHOTO) values (?,?,?,?,?,?,?)";
                stmt = con.prepareStatement(sql1,Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1,$restaurant.getName());
                stmt.setBoolean(2,$restaurant.getHasDelivery());
                stmt.setInt(3,$restaurant.getPhone());
                stmt.setInt(4,addressID);
                stmt.setBoolean(5,$restaurant.getIsHealthApproved());
                stmt.setString(6,OpeningHours.getText());
                stmt.setBytes(7,res_photo);
                stmt.executeUpdate();
                ResultSet rs = stmt.executeQuery("select RESTAURANT_ID_SEQUENCE.currval from DUAL");
                if(rs != null && rs.next())
                    $restaurant.setId(rs.getInt(1));
                for (RestaurantService r : resService)
                {
                    String s = "Insert into RESTAURANT_SERVICE (SERVICE_ID,PRICE,RESTAURANT_ID) values (?,?,?)";
                    PreparedStatement stmt3 = con.prepareStatement(s);
                    stmt3.setInt(1,r.getService().getID());
                    stmt3.setFloat(2,r.getPrice());
                    stmt3.setInt(3,$restaurant.getId());
                    //System.out.println("RES ID : "+$restaurant.getId());
                   // System.out.println("service ID : "+r.getService().getID());
                    //System.out.println("RES ID : "+$restaurant.getId());

                    stmt3.executeUpdate();
                    stmt3.close();
                }
                stmt.close();
                con.setAutoCommit(true);
                con.close();
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setHeaderText("New Restaurant has been added Successfully!!");
                success.setContentText($restaurant.getName() + " has been added.");
                success.show();
                //
                // RESET Static Variables
                $restaurant = new Restaurant();
                $address = new Address();
                resService = new ArrayList<>();
                nextScene("add resturant.fxml");
            }
            catch (Exception e){
                try {
                    con.rollback();
                    con.setAutoCommit(true);
                    con.close();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                System.out.println("Error while adding restaurant");
                Alert warn = new Alert(Alert.AlertType.ERROR);
                warn.setHeaderText("Adding Restaurant Failed!!");
                warn.setContentText(e.toString() + "\n" +e.getMessage());
                warn.show();
                e.printStackTrace();
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        city.setItems(list);
       if(grid != null)
           showAvailableServices(DbWrapper.getServices());
    }

    private void showAvailableServices(List<Service> serviceList) {
        myListener = new MyListener() {

            @Override
            public void onClickListener(RestaurantService restaurantService) {
               addService(restaurantService);
            }
        };

        int row = 1;
        try {
            for (Service s : serviceList) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Service.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ServiceItemController serviceIemController = fxmlLoader.getController();
                serviceIemController.setData(s,myListener);
                grid.add(anchorPane,0,row++);
                //GridPane.setMargin(anchorPane, new Insets(10));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void addService(RestaurantService restaurantService) {
        resService.add(restaurantService);
    }
}
