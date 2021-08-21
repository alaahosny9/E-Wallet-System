package sample;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;





import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
//        primaryStage.setTitle("My Wallet");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 806, 400));
        primaryStage.show();
    }

    public static void main(String[] args) throws InterruptedException, SQLException {
            launch(args);
    }
}