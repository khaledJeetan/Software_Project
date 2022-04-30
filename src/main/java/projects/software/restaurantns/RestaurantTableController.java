package projects.software.restaurantns;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import model.Address;
import model.Restaurant;
import model.User;

import java.util.Collection;
import java.util.List;

public class RestaurantTableController {

    @FXML
    private TableColumn<Restaurant, Integer> ID;

    @FXML
    private TableColumn<Restaurant, Address> address;
    @FXML
    private TableColumn<Address,String> city;
    @FXML
    private TableColumn<Address,String> Located;

    @FXML
    private TableColumn<Restaurant, Boolean> hasDelivery;

    @FXML
    private TableColumn<Restaurant,Boolean> healthApproved;

    @FXML
    private TableColumn<Restaurant,String> name;

    @FXML
    private TableColumn<Restaurant  , Integer> phone;

    @FXML
    private TableColumn<Restaurant, Image> photo;

    @FXML
    private TableView<Restaurant> restaurantTable;

    @FXML
    private TableColumn<Restaurant,String> action;


    private ObservableList<Restaurant> restaurantList = FXCollections.observableArrayList();

    private Restaurant selectedRestaurant;


    @FXML
    public void initialize(){
        setTableColumn();

    }

    private void setTableColumn() {
        addButtonToTable();
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
         //password.setCellValueFactory(new PropertyValueFactory<>("password"));
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        Located.setCellValueFactory(new PropertyValueFactory<>("location"));
        hasDelivery.setCellValueFactory(new PropertyValueFactory<>("hasDelivery"));
        healthApproved.setCellValueFactory(new PropertyValueFactory<>("isHealthApproved"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        photo.setCellValueFactory(new PropertyValueFactory<>("img"));
        restaurantList.addAll(DbWrapper.getAllRestaurants());
        restaurantTable.setItems(restaurantList);
    }


    private void addButtonToTable() {
        Callback<TableColumn<Restaurant, String>, TableCell<Restaurant, String>> cellFoctory = (TableColumn<Restaurant, String> param) -> {
            // make cell containing buttons
            final TableCell<Restaurant, String> cell = new TableCell<Restaurant, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {


                        ImageView deleteIcon = new ImageView(
                                new Image("icons8-delete-64.png"));
                        ImageView editIcon = new ImageView(
                                new Image("icons8-edit-64.png"));
                        ImageView viewIcon = new ImageView(
                                new Image("icons8-view-64.png"));
                        deleteIcon.setPickOnBounds(true);
                        viewIcon.setPickOnBounds(true);
                        editIcon.setPickOnBounds(true);
                        deleteIcon.setFitWidth(25);
                        deleteIcon.setFitHeight(25);
                        editIcon.setFitWidth(25);
                        editIcon.setFitHeight(25);
                        viewIcon.setFitWidth(25);
                        viewIcon.setFitHeight(25);


                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            selectedRestaurant = restaurantTable.getSelectionModel().getSelectedItem();
                            if(Confirm("Delete User") && DbWrapper.deleteRestaurant(selectedRestaurant)){
                                Alert success = new Alert(Alert.AlertType.INFORMATION);
                                success.setTitle("Successfully");
                                success.setHeaderText(selectedRestaurant.getName() + "Deleted Successfully");
                                success.show();
                                refreshTable();
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            selectedRestaurant = restaurantTable.getSelectionModel().getSelectedItem();
                            Alert Clicked = new Alert(Alert.AlertType.INFORMATION);
                            Clicked.setHeaderText("Did you Clicked Me ._.");
                            Clicked.setContentText("YOu have Clicked me So you waked me up. For Testing Purposes");
                            Clicked.show();
                            refreshTable();
                        });
                        viewIcon.setOnMouseClicked((MouseEvent event) -> {
                            selectedRestaurant = restaurantTable.getSelectionModel().getSelectedItem();
                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon,viewIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        HBox.setMargin(viewIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        action.setCellFactory(cellFoctory);
        restaurantTable.setItems(restaurantList);

    }

    private boolean Confirm(String delete_user) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText("Confirm Action");
        confirm.setContentText("Are you sure you want to " + delete_user);
        return confirm.showAndWait().get().equals(ButtonType.OK);
    }

    private void refreshTable() {
        restaurantList.clear();
        restaurantList.addAll(DbWrapper.getAllRestaurants());
        restaurantTable.refresh();
    }

}
