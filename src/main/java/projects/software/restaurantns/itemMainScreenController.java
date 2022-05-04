package projects.software.restaurantns;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import model.RestaurantService;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class itemMainScreenController {
        RestaurantService RestaurantService;
        private User user = UserHolder.getInstance().getUser();
        private Connection con;
        private final ReviewSystem main = new ReviewSystem();
        @FXML
        private Label name;
        @FXML
        private Label delivery;
        @FXML
        private Label isopen;
        @FXML
        private Label type;
        @FXML
        private Label address;
        @FXML
        private Label number;
        @FXML
        private Label openinghours;
        @FXML
        private RadioButton onestar;
        @FXML
        private RadioButton twostars;
        @FXML
        private RadioButton threestars;
        @FXML
        private RadioButton fourstars;
        @FXML
        private RadioButton fivestars;
        @FXML
        private ToggleGroup star;
        @FXML
        private TextField comment;

        private void initialize(){
                setData();
        }
        public void setData() {
                name.setText(RestaurantService.getRestaurant().getName());
                delivery.setText(RestaurantService.getRestaurant().getHasDelivery()?"Yes":"No");
                openinghours.setText(RestaurantService.getRestaurant().getOpeningHours());
                type.setText(RestaurantService.getRestaurant().getServices()+"");
                number.setText(RestaurantService.getRestaurant().getPhone()+"");
                address.setText(RestaurantService.getRestaurant().getAddress().getLocation()+" "+RestaurantService.getRestaurant().getAddress().getLocation());
                //if(LocalTime.now().isAfter())

        }
        @FXML
        public void save() {
                if(star.getSelectedToggle()==null||comment.getText().isEmpty())
                {
                        ///show message
                }
                else
                {
                        try {
                                con = main.getConnection();
                                String sql = "Insert into REVIEW (username, user_comment, star_count, created_at, restaurant_id) values (?,?,?,?,?)";
                                PreparedStatement stmt = con.prepareStatement(sql);
                                stmt.setString(1,user.getName());  //for now cuz i don't wanna see errors
                                stmt.setString(2,comment.getText());
                                if(onestar.isSelected())
                                        stmt.setInt(3,1);
                                else if (twostars.isSelected())
                                        stmt.setInt(3,2);
                                else if (threestars.isSelected())
                                        stmt.setInt(3,3);
                                else if (fourstars.isSelected())
                                        stmt.setInt(3,4);
                                else if (fivestars.isSelected())
                                        stmt.setInt(3,5);
                                stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                                stmt.executeUpdate();
                                System.out.println("Query Executed successfully");
                                stmt.close();
                                con.close();


                        }catch (Exception e){
                                System.out.println("Error with Creating new User.");
                                e.printStackTrace();
                        }
                }

        }
//        public void loadData(){
//                name.setText(user.getName());
//                Image photo = user.getPhoto();
//                if(photo != null) {
//                        personalPhoto.setImage(photo);
//                }
//
//                if(pieChart != null) {
//                        ObservableList<PieChart.Data> chartData =
//                                FXCollections.observableArrayList(
//                                        new PieChart.Data("Users", DbWraper.userCount()),
//                                        new PieChart.Data("Restaurants", DbWraper.restaurantCount()));
//                        pieChart.setData(chartData);
//                        pieChart.setStartAngle(90);
//                }
//        }




    }


