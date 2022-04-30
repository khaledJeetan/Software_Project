package projects.software.restaurantns;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public class UserProfileController {

    private final ReviewSystem main = new ReviewSystem();

    private User user = UserHolder.getInstance().getUser();
    @FXML
    private Label name;
    @FXML
    ImageView personalPhoto;
    private byte [] person_image;




    public void initialize(){
        loadData();
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
            personalPhoto.setImage(image);
//            imageView.setFitWidth(200);

            FileInputStream fin = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];

            for (int readNum; (readNum = fin.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
            person_image = bos.toByteArray();

            if(confirm("Confirm Updating Photo", "Do you want to Update and save photo??")) {
                updatePhoto();
            }else{
                personalPhoto.setImage(user.getPhoto());
            }

        } catch (IOException ex) {
            Logger.getLogger("ss");
        }

    }

    private boolean confirm(String title, String msg) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText(title);
        confirm.setContentText(msg);
        return confirm.showAndWait().get().equals(ButtonType.OK);
    }


    private void updatePhoto() {

        try {

            Connection con = main.getConnection();
            String sql = "update user_tb set photo = ?, UPDATED_AT = ? where USERNAME = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setBytes(1,person_image);
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(3,user.getName());

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
    }

    @FXML
    public void back(){
        main.changeScene("viewRestaurants.fxml");
    }

    @FXML
    public void userReview(){
        main.changeScene("ra&co");
    }

    @FXML
    public void favourite(){
        main.changeScene("favorite.fxml");
    }
    @FXML
    public void changePassword(){
        main.changeScene("changepa.fxml");
    }


    @FXML
    public void logout(){
        UserHolder.getInstance().setUser(null);
        main.changeScene("login.fxml");
    }

}
