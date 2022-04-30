package projects.software.restaurantns;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import model.User;
import java.sql.Timestamp;


public class UsersController {

    @FXML
    private TableColumn<User, Timestamp> creation_date;

    @FXML
    private TableColumn<User, Boolean> enabled;

    @FXML
    private TableColumn<User, Timestamp> last_access;

    @FXML
    private TableColumn<User, String> password;

    @FXML
    private TableColumn<User, Image> photo;

    @FXML
    private TableColumn<User, Boolean> type;

    @FXML
    private TableColumn<User, Timestamp> updated_at;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> username;
    @FXML
    private TableColumn<User,String> action;

    ObservableList<User> usersList = FXCollections.observableArrayList();

    private User selectedUser;


    @FXML
    public void initialize(){
        setTableColumn();

    }

    private void setTableColumn() {
       // addButtonToTable();
        username.setCellValueFactory(new PropertyValueFactory<>("name"));
       // password.setCellValueFactory(new PropertyValueFactory<>("password"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        creation_date.setCellValueFactory(new PropertyValueFactory<>("creation_date"));
        last_access.setCellValueFactory(new PropertyValueFactory<>("last_access_date"));
        updated_at.setCellValueFactory(new PropertyValueFactory<>("updated_at"));
        enabled.setCellValueFactory(new PropertyValueFactory<>("enabled"));
        photo.setCellValueFactory(new PropertyValueFactory<>("img"));
        usersList.addAll(DbWrapper.getUsers());
        userTable.setItems(usersList);
    }

    private void addButtonToTable() {
        Callback<TableColumn<User, String>, TableCell<User, String>> cellFoctory = (TableColumn<User, String> param) -> {
            // make cell containing buttons
            final TableCell<User, String> cell = new TableCell<User, String>() {
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
                            selectedUser = userTable.getSelectionModel().getSelectedItem();
                            if(Confirm("Delete User") && DbWrapper.deleteUser(selectedUser)){
                                Alert success = new Alert(Alert.AlertType.INFORMATION);
                                success.setTitle("Successfully");
                                success.setHeaderText(selectedUser.getName() + "Deleted Successfully");
                                success.show();
                                refreshTable();
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            selectedUser = userTable.getSelectionModel().getSelectedItem();
                            Alert Clicked = new Alert(Alert.AlertType.INFORMATION);
                            Clicked.setHeaderText("Did you Clicked Me ._.");
                            Clicked.setContentText("YOu have Clicked me So you waked me up. For Testing Purposes");
                            Clicked.show();
                            refreshTable();
                        });
                        viewIcon.setOnMouseClicked((MouseEvent event) -> {
                            selectedUser = userTable.getSelectionModel().getSelectedItem();
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
        userTable.setItems(usersList);

    }

    private boolean Confirm(String delete_user) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText("Confirm Action");
        confirm.setContentText("Are you sure you want to " + delete_user);
        return confirm.showAndWait().get().equals(ButtonType.OK);
    }

    private void refreshTable() {
        usersList.clear();
        usersList.addAll(DbWrapper.getUsers());
        userTable.refresh();
    }

}
