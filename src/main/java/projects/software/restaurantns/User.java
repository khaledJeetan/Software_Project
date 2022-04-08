package projects.software.restaurantns;

public class User {

    private String name;
    private String Password;

    public User(String name, String password){
        this.name =  name;
        this.Password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
