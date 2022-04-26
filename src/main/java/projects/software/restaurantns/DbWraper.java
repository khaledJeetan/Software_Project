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
            con.close();
            stmt.close();
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

    public static List<Service> getServices(){
        sql = "select * from service";
        List<Service> serviceList = new ArrayList<>();
        try{
            con = main.getConnection();
            stmt = con.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()){
                Service s = new Service();
                s.setID(resultSet.getInt("ID"));
                s.setName(resultSet.getString("name"));
                serviceList.add(s);
            }
            return serviceList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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
            con.close();
            stmt.close();
            return users;

        }catch (Exception e){
            e.printStackTrace();
        }
        return users;

    }

    // this methode gets restaurants that have specified Service
    public static List<RestaurantService> getRestaurants(String serv){

        List<RestaurantService> restaurantServiceArrayList = new ArrayList<>();
        String sql = """
                select r.id,r.name,Address.city,Address.location,r.has_Delivery,r.phone,r.is_health_approved,
                r.cover_photo,service.name service,restaurant_service.price from restaurant r
                inner join restaurant_service on r.id = restaurant_service.restaurant_id
                inner join address on address.id = r.address
                inner join service on service.id = restaurant_service.service_id
                where service.name like ?""";
        System.out.println("search Word is:- " + serv + " SQL statement is: \n"+sql );
        try {
            Connection con = main.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,"%" + serv + "%");
            ResultSet resultSet = stmt.executeQuery();
            Address address;
            Service service;
            while(resultSet.next()){
                Restaurant restaurant = new Restaurant();
                address = new Address();
                service = new Service();
                RestaurantService restaurantService = new RestaurantService();
                restaurant.setId(resultSet.getInt("id"));
                restaurant.setName(resultSet.getString("name"));
                restaurant.setHasDelivery(resultSet.getBoolean("has_delivery"));
                restaurant.setPhone(resultSet.getInt("phone"));
                restaurant.setHealthApproved(resultSet.getBoolean("is_health_approved"));
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
            con.close();
            stmt.close();
            return restaurantServiceArrayList;

        }catch (Exception e){
            e.printStackTrace();
        }


        return null;

    }

    public static boolean deleteUser(User user){
        try {

            System.out.println("Program Is Here before Query Deleting");
            sql = "DELETE FROM USER_TB WHERE username  = ?";
            con = main.getConnection();
            stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getName());
            stmt.executeUpdate();
            System.out.println("Program Is Here after Query Deleting");

            con.close();
            stmt.close();
            return true;

        } catch (Exception ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Deleting Record Failed");
            error.setHeaderText("Error While Deleting From Database!!");
            error.setContentText(ex.getMessage());
            return false;
        }
    }

    public static int checkAddress(Address address){
        try {
            sql = "Select id from Address where city = ? and location = ?";
            con = main.getConnection();
            stmt = con.prepareStatement(sql);
            stmt.setString(1,address.getCity());
            stmt.setString(2,address.getLocation());
            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next())
                return resultSet.getInt("id");
            return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static User getUser(ResultSet resultSet) {

        try {

            String username = resultSet.getString("username");
            String pass = resultSet.getString("password");
            boolean isAdmin = resultSet.getBoolean("type");
            Timestamp creation_date = resultSet.getTimestamp("creation_date");
            Timestamp updated_at = resultSet.getTimestamp("updated_at");
            Timestamp last_access_date = resultSet.getTimestamp("last_access_date");
            boolean isEnable = resultSet.getBoolean("enabled");
            InputStream photo = resultSet.getBinaryStream("photo");
            User user = new User(username, pass);
            user.setAdmin(isAdmin);
            user.setEnabled(isEnable);
            user.setCreation_date(creation_date);
            user.setLast_access_date(last_access_date);
            user.setUpdated_at(updated_at);
            user.setPhoto(photo);
            con.close();
            stmt.close();
            return user;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}