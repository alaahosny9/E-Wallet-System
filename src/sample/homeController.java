package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.*;
import java.util.Random;
import java.util.ResourceBundle;

public class homeController implements Initializable{
    @FXML
    Button cancelButton;
    @FXML
    ImageView homeImage;
    @FXML
    ImageView customerLogin;
    @FXML
    ImageView empLogin;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("images/digital--wallet-1024x683.jpg");
        Image image = new Image(file.toURI().toString());
        homeImage.setImage(image);

        File file1 = new File("images/WZ4Dx-removebg-preview-removebg-preview.png");
        Image image1 = new Image(file1.toURI().toString());
        customerLogin.setImage(image1);

        File file2 = new File("images/login-button.png");
        Image image2 = new Image(file2.toURI().toString());
        empLogin.setImage(image2);

    }

    @FXML
    public void cancelButtonAction(javafx.event.ActionEvent actionEvent) { // this method handling cancelButton and it is terminates the program.
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        System.exit(1);
    }

    public void hide(javafx.event.ActionEvent actionEvent) { // this method hides the currently stage.
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void customerAction(ActionEvent actionEvent){ // this method will be implemented when the user hit the "Customer Login" Button and it shows to him the login / signup form
        Stage userStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
            Parent root = loader.load();
            userStage.initStyle(StageStyle.UNDECORATED);
            userStage.setScene(new Scene(root, 689, 398));
            ActionEvent actionEvent1 = new ActionEvent();
            hide(actionEvent1);
            userStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void empAction(ActionEvent actionEvent){ // this method will be implemented when the user hit the "employee Login" Button and it shows to him the login / signup form
        Stage userStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("empLogin.fxml"));
            Parent root = loader.load();
            userStage.initStyle(StageStyle.UNDECORATED);
            userStage.setScene(new Scene(root, 806, 400));
            ActionEvent actionEvent1 = new ActionEvent();
            hide(actionEvent1);
            userStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
