package projects.software.restaurantns;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.embed.swing.SwingFXUtils;
import model.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ProfileController {

    ReviewSystem main = new ReviewSystem();

    User user = UserHolder.getInstance().getUser();
    @FXML
    private Label name;
    @FXML
    private PieChart pieChart;
    @FXML
    ImageView imageView;
    @FXML
    ImageView personalPhoto;
    @FXML
    private Slider price;
    @FXML
    private Label selectedPrice;


    private byte [] person_image;


    public void initialize(){
        loadData();
        price.valueProperty().addListener((observable, oldValue, newValue) -> {

            selectedPrice.setText(Double.toString(newValue.intValue()));


        });
    }

    @FXML
    public void uploadPhoto(){
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);

        if(file != null)
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageView.setImage(image);
//            imageView.setFitWidth(200);

            FileInputStream fin = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];

            for (int readNum; (readNum = fin.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
            person_image = bos.toByteArray();

        } catch (IOException ex) {
            Logger.getLogger("ss");
        }

    }

    @FXML
    private void addPhoto() {

        try {

            Connection con = main.getConnection();
            String sql = "update user_tb set photo = ? where USERNAME = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setBytes(1,person_image);
            stmt.setString(2,user.getName());

            stmt.executeUpdate();
            System.out.println("image saved successfully");
            stmt.close();
            con.close();
           // imageView.relocate(0.0001,0.0001);

        } catch (SQLException ex) {
            System.out.println("error");

        } catch (NumberFormatException e) {
            System.out.println("number error");

        }
    }

    public void loadData(){
        name.setText(user.getName());
        Image photo = user.getPhoto();
        if(photo != null) {
            personalPhoto.setImage(photo);
        }

        if(pieChart != null) {
            ObservableList<PieChart.Data> chartData =
                    FXCollections.observableArrayList(
                            new PieChart.Data("Users", DbWraper.userCount()),
                            new PieChart.Data("Restaurants", DbWraper.restaurantCount()));
            pieChart.setData(chartData);
            pieChart.setStartAngle(90);
        }
    }

    @FXML
    public void logout(){
        UserHolder.getInstance().setUser(null);
        main.changeScene("login.fxml");
    }

}
