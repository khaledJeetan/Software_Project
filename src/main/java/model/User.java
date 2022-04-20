package model;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.sql.Timestamp;

public class User {

    private String name;
    private String Password;
    private boolean isAdmin;
    private Timestamp creation_date;
    private Timestamp updated_at;
    private Timestamp last_access_date;
    private boolean enabled;
    private Image photo;

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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Timestamp getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Timestamp creation_date) {
        this.creation_date = creation_date;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Timestamp getLast_access_date() {
        return last_access_date;
    }

    public void setLast_access_date(Timestamp last_access_date) {
        this.last_access_date = last_access_date;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto( InputStream photo) {
        if(photo != null) {
            Image image = new Image(photo);
            this.photo = image;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
