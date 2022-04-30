package model;

import java.io.InputStream;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Restaurant {

    private int id;
    private String name;
    private  Address address;
    private boolean hasDelivery;
    private int phone;
    private boolean isHealthApproved;
    private ArrayList<Service> services;
    private ArrayList<Review> reviews;
    private Image coverPhoto;
    private  String Openinghours;
    private  String city;
    private  String location;
    private ImageView img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpeningHours() {
        return Openinghours;
    }

    public void setOpeningHours(String OpeningHours) {
        this.Openinghours = Openinghours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setLocation(Address address) {
        this.city = address.getCity();
        this.location = address.getLocation();
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean getHasDelivery() {
        return hasDelivery;
    }

    public void setHasDelivery(boolean hasDelivery) {
        this.hasDelivery = hasDelivery;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public boolean getIsHealthApproved() {
        return isHealthApproved;
    }

    public void setHealthApproved(boolean healthApproved) {
        isHealthApproved = healthApproved;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public Image getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(InputStream coverPhoto) {
        if(coverPhoto != null) {
            Image image = new Image(coverPhoto);
            this.coverPhoto = image;
        }
    }
    public ImageView getImg() {
        img = new ImageView(this.coverPhoto);
        img.setFitWidth(50);
        img.setFitHeight(50);
        return img;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address.toString() +
                '}';
    }
}