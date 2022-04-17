package projects.software.restaurantns;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class DbWraper {

    private static ReviewSystem main = new ReviewSystem();

    public static int userCount(){

        String sql  = "select count(*) from user_tb";
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

    public static User wrapUser(ResultSet resultSet) {

        try {

            String username = resultSet.getString("username");
            String pass = resultSet.getString("password");
            Boolean isAmdin = resultSet.getBoolean("type");
            Timestamp creation_date = resultSet.getTimestamp("creation_date");
            Timestamp updated_at = resultSet.getTimestamp("updated_at");
            Timestamp last_access_date = resultSet.getTimestamp("last_access_date");
            Boolean isEnable = resultSet.getBoolean("enabled");
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

    public static int restaurantCount() {

        String sql  = "select count(*) from RESTAURANT";
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
}
