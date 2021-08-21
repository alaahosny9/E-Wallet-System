package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;



import java.awt.*;
import javafx.scene.control.TextArea;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class transController implements Initializable{
    @FXML
    ImageView transaction;

    @FXML
    TextArea text;

    String url = "jdbc:mysql://localhost:3306/walletdata";
    String username = "root";
    String password = "80080088";
    String id;

    public void setId(String id) {
        this.id = id;
    }

    public void showData() throws SQLException { // this method can show to the user his transaction history when he press on its button
        Connection connection = DriverManager.getConnection(url, username, password);
        String query = "SELECT * FROM transactions where wallet_id ='" + Integer.parseInt(id) + "'";
        PreparedStatement statement2 = connection.prepareStatement(query);
        ResultSet rs = statement2.executeQuery(query);
        String textt = "";
        int count = 1;
        while (rs.next()){
            int transID = rs.getInt("trans_id");
            String type = rs.getString("transaction_type");
            int balance = rs.getInt("transaction_balance");

//            text.setText("Name: " + cName + " Transaction ID: " + transID + " Wallet ID: " + walletId + " Transaction Type: " + type + " Transaction Balance: " + balance);
            textt = textt + count + " Transaction ID: " + transID +" Transaction Type: " + type + " Transaction Balance: " + balance + "\n";
            count++;
        }

        text.setText(textt);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("images/a633d1e3280cf118c9fa182ec6874a29.png");
        Image image = new Image(file.toURI().toString());
        transaction.setImage(image);
    }
}
