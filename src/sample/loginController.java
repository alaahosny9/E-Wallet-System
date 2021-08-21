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
import javafx.stage.StageStyle;


import java.awt.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class loginController implements Initializable , Login , Handling{
    Customer C[] = new Customer[0];
    Wallet wallet[] = new Wallet[0];
    String url = "jdbc:mysql://localhost:3306/walletdata";
    String username = "root";
    String password = "80080088";
    Stage stage;
    FXMLLoader loader;
    ResultSet rs;
    @FXML
    Button cancelButton;
    @FXML
    Label login;
    @FXML
    ImageView myImage;
    @FXML
    TextField walletID;
    @FXML
    TextField Mobile;
    @FXML
    Label idrequired;
    @FXML
    Label mobrequired;
    @FXML
    Label passrequired;
    @FXML
    PasswordField Password;
    @FXML
    ImageView loginB;
    @FXML
    ImageView regB;
    String cPassword;
    private String Id;
    Connection connection;
    @Override
    public void loginButtonAction(ActionEvent actionEvent) throws SQLException, InterruptedException, IOException { // this method will be implemented when the user choose to login and it checks if he entered anything or not

        login.setText("");
        if (walletID.getText().isBlank() == false && Mobile.getText().isBlank() == false && Password.getText().isBlank() == false) {
            idrequired.setText("");
            mobrequired.setText("");
            passrequired.setText("");
            validateLogin();
        } else
            if (walletID.getText().isBlank()){
                idrequired.setText("* required");
            }
            else
                idrequired.setText("");

            if (Mobile.getText().isBlank()){
                mobrequired.setText("* required");
            }
            else
                mobrequired.setText("");

            if (Password.getText().isBlank()){
                passrequired.setText("* required");
            }
            else
                passrequired.setText("");

    }

    @Override
    public void handle(KeyEvent event) throws SQLException, IOException, InterruptedException {
        ActionEvent actionEvent = new ActionEvent();
        if (event.getCode().equals(KeyCode.ENTER))
            loginButtonAction(actionEvent);
    }

    @FXML
    public void cancelButtonAction(javafx.event.ActionEvent actionEvent) {
        stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        System.exit(1);
    }

    public void hide(javafx.event.ActionEvent actionEvent) {
        stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("images/bank.jpg");
        Image image = new Image(file.toURI().toString());
        myImage.setImage(image);

        File file1 = new File("images/login-button.png");
        Image image1 = new Image(file1.toURI().toString());
        loginB.setImage(image1);

        File file2 = new File("images/0PVOvb-register-button-free-png.png");
        Image image2 = new Image(file2.toURI().toString());
        regB.setImage(image2);


    }

    @Override
    public void validateLogin() throws SQLException { // method to check if this user exists already or not
        try {
            connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM user_account  where wallet_id ='" + Integer.parseInt(walletID.getText()) + "'" + " AND mobile_no = '" + Mobile.getText() + "'" /*+ " AND password = '" + Password.getText() + "'"*/;
            PreparedStatement statement2 = connection.prepareStatement(query);
            rs = statement2.executeQuery(query);

            if (rs.next()) {
                try {
                    String sqll = "SELECT count(*) FROM user_account";
                    PreparedStatement stmt = connection.prepareStatement(sqll);
                    //Executing the query
                    ResultSet resultSet = stmt.executeQuery(sqll);
                    //Retrieving the result
                    resultSet.next();
                    int count = resultSet.getInt(1);
//                System.out.println(count);

                    String query2 = "SELECT * FROM user_account";
                    PreparedStatement stmt2 = connection.prepareStatement(query2);
                    //Executing the query
                    ResultSet resultSet2 = stmt2.executeQuery(query2);
                    //Retrieving the resultv
                    C = new Customer[count];
                    wallet = new Wallet[count];

                    int counter = 0;
                    while (resultSet2.next()) {
                        String cName = resultSet2.getString("user_name");
                        String lName = resultSet2.getString("lastName");
                        int walletId = resultSet2.getInt("wallet_id");
                        System.out.println();
                        String mobileNo = resultSet2.getString("mobile_no");
                        cPassword = resultSet2.getString("password");
                        int cBalance = resultSet2.getInt("balance");
                        C[counter] = new Customer(count);
                        C[counter].setName(cName);
                        C[counter].setLastName(lName);
                        C[counter].setMobile(mobileNo);
                        C[counter].setPin(cPassword);
//                    account = C[counter].createAccount(walletId , cBalance);
                        wallet[counter] = new Wallet();
                        wallet[counter].setId(walletId);
                        wallet[counter].setBalance(cBalance);

                        counter++;
                    }
                    Wallet obj = new Wallet();
                    Wallet obj2 = obj.search(wallet,  Integer.parseInt(walletID.getText()));
                    Customer cObj = obj.search(C, Mobile.getText());

                    if (cObj.getPin().equals(Password.getText())) {
                        Socket socket = new Socket("localhost", 4444);
                        createAccountForm();
                        ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
                        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                        outputStream.writeUTF("Customer: " + cObj.getName() + " " + cObj.getLastName());


                        userController userController = loader.getController();
                        userController.setId(walletID.getText());
                        userController.setMobile(Mobile.getText());
                        userController.setBalance(obj2.getBalance());
                        userController.setWallet(wallet);
                        userController.setPasswordd(cObj.getPin());
                        userController.setCustomer(C);
                        userController.setObj(obj2);
                        userController.setcObj(cObj);
                        userController.setSize(count);

                        ActionEvent actionEvent = new ActionEvent();
                        userController.show();
//                    System.out.println(obj2.getId());
                    } else
                        login.setText("your password is incorrect!..".toUpperCase());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else
                login.setText("SORRY THIS USER DOESN'T EXIST");
        } catch (NumberFormatException ex) {
            login.setText("please type a valid inputs".toUpperCase());
        } catch (NullPointerException e) {
            login.setText("please type a valid inputs".toUpperCase());
        }
    }

    public void createAccountForm() throws IOException { // this method creates the user screen for customers
        Stage userStage = new Stage();
        try {
            loader = new FXMLLoader(getClass().getResource("user.fxml"));
            Parent root = loader.load();
            userStage.setTitle("Wallet Page");
            userStage.initStyle(StageStyle.UNDECORATED);
            userStage.setScene(new Scene(root, 750, 500));
            ActionEvent actionEvent = new ActionEvent();
            hide(actionEvent);
            userStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createRegisterForm(){ // this method creates the registration page for the customer
        Stage userStage = new Stage();
        try {
            loader = new FXMLLoader(getClass().getResource("signup.fxml"));
            Parent root = loader.load();
//            userStage.setTitle("Wallet Registration");
            userStage.setScene(new Scene(root, 650, 465));

            userStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

