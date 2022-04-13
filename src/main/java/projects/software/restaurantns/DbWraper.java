package projects.software.restaurantns;

import java.sql.ResultSet;
import java.sql.Timestamp;

public class DbWraper {

    public static User wrapUser(ResultSet resultSet) {

        try {

            String username = resultSet.getString("username");
            String pass = resultSet.getString("password");
            Boolean isAmdin = resultSet.getBoolean("type");
            Timestamp creation_date = resultSet.getTimestamp("creation_date");
            Timestamp updated_at = resultSet.getTimestamp("updated_at");
            Timestamp last_access_date = resultSet.getTimestamp("last_access_date");
            Boolean isEnable = resultSet.getBoolean("enabled");
            User user = new User(username, pass);
            user.setAdmin(isAmdin);
            user.setEnabled(isEnable);
            user.setCreation_date(creation_date);
            user.setLast_access_date(last_access_date);
            user.setUpdated_at(updated_at);
            return user;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
