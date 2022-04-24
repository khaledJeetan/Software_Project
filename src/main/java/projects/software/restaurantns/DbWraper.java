package projects.software.restaurantns;

import javafx.scene.control.Alert;
import model.*;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbWraper {

    private static Connection con;

    private static PreparedStatement stmt;

    private static String sql;

    private static final ReviewSystem main = new ReviewSystem();

    public static int count(String sql){

        try {
            Connection con = main.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            int rowCount = 0;
            while(resultSet.next()){
                rowCount = resultSet.getInt(1);
            }
            return rowCount;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }

    }

    public static int userCount(){

        String sql  = "select count(*) from user_tb";
        return count(sql);

    }



    public static int restaurantCount() {

        String sql  = "select count(*) from RESTAURANT";
        return count(sql);
    }

    public static List<User> getUsers(){
        List<User> users = new ArrayList<>();
        sql  = "Select * from user_tb";
        try {
            con = main.getConnection();
            stmt = con.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()){
                User user = new User();
                user.setName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setPhoto(resultSet.getBinaryStream("photo"));
                user.setAdmin(resultSet.getBoolean("type"));
                user.setCreation_date(resultSet.getTimestamp("creation_date"));
                user.setLast_access_date(resultSet.getTimestamp("last_access_date"));
                user.setUpdated_at(resultSet.getTimestamp("updated_at"));
                user.setEnabled(resultSet.getBoolean("enabled"));
                users.add(user);
            }
            return users;

        }catch (Exception e){
            e.printStackTrace();
        }
        return users;

    }

    // this methode gets restaurants that have specified Service
    public static List<RestaurantService> getrestaurants(String serv){

        List<RestaurantService> restaurantServiceArrayList = new ArrayList<>();
        String sql = "select r.id,r.name,Address.city,Address.location,r.has_Delivery,r.phone," +
                "r.is_health_approved,r.cover_photo,service.name service," +
                "restaurant_service.price from restaurant r\n" +
                "inner join restaurant_service on r.id = restaurant_service.restaurant_id\n" +
                "inner join address on address.id = r.address\n" +
                "inner join service on service.id = restaurant_service.service_id\n" +
                "where service.name like ?";
        System.out.println("search Word is:- " + serv + " SQL satement is: \n"+sql );
        try {
            Connection con = main.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,"%" + serv + "%");
            ResultSet resultSet = stmt.executeQuery();

            while(resultSet.next()){
                Restaurant restaurant = new Restaurant();
                Address address = new Address();
                Service service = new Service();
                RestaurantService restaurantService = new RestaurantService();
                restaurant.setId(resultSet.getInt("id"));
                restaurant.setName(resultSet.getString("name"));
                restaurant.setHasDelivery(resultSet.getString("has_delivery"));
                restaurant.setPhone(resultSet.getInt("phone"));
                restaurant.setHealthApproved(resultSet.getString("is_health_approved"));
                restaurant.setCoverPhoto(resultSet.getBinaryStream("cover_photo"));
                service.setName(resultSet.getString("service"));
                address.setCity(resultSet.getString("city"));
                address.setLocation(resultSet.getString("location"));
                restaurant.setLocation(address);
                //restaurantService.setService(service);
                restaurantService.setRestaurant(restaurant);
                restaurantService.setPrice(resultSet.getInt("price"));
                restaurantServiceArrayList.add(restaurantService);
            }
            return restaurantServiceArrayList;

        }catch (Exception e){
            e.printStackTrace();
        }


        return null;

    }

    public static boolean deleteUser(User user){
        try {

            sql = "DELETE FROM USER_TB WHERE username  = ?";
            con = main.getConnection();
            stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getName());
            stmt.execute();
            return true;

        } catch (Exception ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Deleting Record Failed");
            error.setHeaderText("Error While Deleting From Database!!");
            error.setContentText(ex.getMessage());
            return false;
        }
    }

    public static User getUser(ResultSet resultSet) {

        try {

            String username = resultSet.getString("username");
            String pass = resultSet.getString("password");
            boolean isAmdin = resultSet.getBoolean("type");
            Timestamp creation_date = resultSet.getTimestamp("creation_date");
            Timestamp updated_at = resultSet.getTimestamp("updated_at");
            Timestamp last_access_date = resultSet.getTimestamp("last_access_date");
            boolean isEnable = resultSet.getBoolean("enabled");
            InputStream photo = resultSet.getBinaryStream("photo");
            User user = new User(username, pass);
            user.setAdmin(isAmdin);
            user.setEnabled(isEnable);
            user.setCreation_date(creation_date);
            user.setLast_access_date(last_access_date);
            user.setUpdated_at(updated_at);
            user.setPhoto(photo);
            return user;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}