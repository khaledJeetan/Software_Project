package model;

import java.io.InputStream;
import java.util.ArrayList;
import javafx.scene.image.Image;

public class Restaurant {

    private int id;
    private String name;
    private  Address address;
    private String hasDelivery;
    private int phone;
    private String isHealthApproved;
    private ArrayList<Service> services;
    private ArrayList<Review> reviews;
    private Image coverPhoto;
    private static String Openinghours;

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

    public Address getLocation() {
        return address;
    }

    public void setLocation(Address address) {
        this.address = address;
    }

    public String getHasDelivery() {
        return hasDelivery;
    }

    public void setHasDelivery(String hasDelivery) {
        this.hasDelivery = hasDelivery;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getHealthApproved() {
        return isHealthApproved;
    }

    public void setHealthApproved(String healthApproved) {
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

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address.toString() +
                '}';
    }
}