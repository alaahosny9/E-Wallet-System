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


import javax.imageio.IIOParam;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class userController implements Initializable , Handling{
    String url = "jdbc:mysql://localhost:3306/walletdata";
    String username = "root";
    String password = "80080088";
    FXMLLoader loader;
    @FXML
    public ImageView shieldImage;
    @FXML
    public Label idLabel;
    @FXML
    public Label mobileLabel;
    @FXML
    public PasswordField Pin;
    @FXML
    public TextField mobileReceive;
    @FXML
    public TextField amountSend;
    @FXML
    public TextField withdraw;
    @FXML
    public Label idreq;
    @FXML
    public Label welcome;
    @FXML
    public TextField deposit;
    @FXML
    public ImageView transferImage;
    @FXML
    public Label withdrawReq;
    @FXML
    public Label depositReq;
    @FXML
    public Label sendReq;
    @FXML
    public Label mobileReq;
    @FXML
    public Label passReq;
    @FXML
    public Label error;
    @FXML
    public TextField ReceiverId;
    @FXML
    Button cancelButton;
    @FXML
    public Label balanceLabel;
    String id , mobile;
    double balance;

    Wallet obj = new Wallet();
    Customer cObj = new Customer();
    int size;
    Wallet wallet[] = new Wallet[size];
    Customer customer[] = new Customer[size];
    private String passwordd;

    public void setcObj(Customer cObj) {
        this.cObj = cObj;
    }

    public void setWallet(Wallet[] wallet) {
        this.wallet = wallet;
    }

    public void setCustomer(Customer[] customer) {
        this.customer = customer;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setObj(Wallet obj) {
        this.obj = obj;
    }

    public Wallet getObj() {
        return obj;
    }

    public Customer getcObj() {
        return cObj;
    }

    public void setPasswordd(String passwordd) {
        this.passwordd = passwordd;
    }

    public String getPasswordd() {
        return passwordd;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("images/b6175b2af100007788fe697d3f52400e.jpg");
        Image image = new Image(file.toURI().toString());
        shieldImage.setImage(image);

        File file1 = new File("images/two-color-money-transfer-icon-from-payment-vector-25746767-removebg-preview.png");
        Image image1 = new Image(file1.toURI().toString());
        transferImage.setImage(image1);
    }

    @FXML
    public void cancelButtonAction(javafx.event.ActionEvent actionEvent) { // this method handling cancelButton and it is terminates the program.
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        System.exit(1);
    }

    public void hide(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void withdrawHandle(KeyEvent event) throws SQLException, IOException, InterruptedException { // method to handle when user enter a key and checks if he entered "enter" key
        ActionEvent actionEvent = new ActionEvent();
        if (event.getCode().equals(KeyCode.ENTER))
            withdraw(actionEvent);
    }

    public void depositHandle(KeyEvent event) throws SQLException, IOException, InterruptedException { // method to handle when user enter a key and checks if he entered "enter" key
        ActionEvent actionEvent = new ActionEvent();
        if (event.getCode().equals(KeyCode.ENTER))
            deposit(actionEvent);

    }


    @Override
    public void handle(KeyEvent event) throws SQLException, IOException, InterruptedException { // handling transferring money
        ActionEvent actionEvent = new ActionEvent();
        if (event.getCode().equals(KeyCode.ENTER))
            transferHandle(actionEvent);
    }

    public void transferHandle(ActionEvent actionEvent) throws SQLException, IOException, InterruptedException { // handling transferring money
        error.setText("");
        withdrawReq.setText("");
        depositReq.setText("");
        if (amountSend.getText().isBlank() == false && ReceiverId.getText().isBlank() == false && mobileReceive.getText().isBlank() == false && Pin.getText().isBlank() == false) {
            sendReq.setText("");
            idreq.setText("");
            mobileReq.setText("");
            passReq.setText("");
            sendMoney(actionEvent);
        }
        else {
            if (amountSend.getText().isBlank())
                sendReq.setText("* required");
            else
                sendReq.setText("");

            if (ReceiverId.getText().isBlank())
                idreq.setText("* required");
            else
                idreq.setText("");

            if (mobileReceive.getText().isBlank())
                mobileReq.setText("* required");
            else
                mobileReq.setText("");

            if (Pin.getText().isBlank())
                passReq.setText("* required");
            else
                passReq.setText("");
        }
    }

    public void showInfo(ActionEvent actionEvent) throws SQLException { // method to show for user his info on his screen
        Connection connection = DriverManager.getConnection(url, username, password);
        String query2 = "SELECT * FROM user_account where mobile_no = '" + mobileLabel.getText() + "'";
        PreparedStatement stmt2 = connection.prepareStatement(query2);
        //Executing the query
        ResultSet resultSet2 = stmt2.executeQuery(query2);
        //Retrieving the result
        int counter = 0;
        while (resultSet2.next()) {
            String cName = resultSet2.getString("user_name");
            String lName = resultSet2.getString("lastName");
            int walletId = resultSet2.getInt("wallet_id");
            String mobileNo = resultSet2.getString("mobile_no");
            String cPassword = resultSet2.getString("password");
            int cBalance = resultSet2.getInt("balance");
            idLabel.setText(String.valueOf(walletId));
            mobileLabel.setText(mobileNo);
            balanceLabel.setText(String.valueOf(cBalance));
        welcome.setText("welcome ".toUpperCase() + cObj.getName().toUpperCase() + " " + cObj.getLastName().toUpperCase());

        }

    }

    public void show(){
        idLabel.setText(id);
        mobileLabel.setText(mobile);
        balanceLabel.setText(String.valueOf(obj.getBalance()));
        welcome.setText("welcome ".toUpperCase() + cObj.getName().toUpperCase() + " " + cObj.getLastName().toUpperCase());
    }
    public void sendMoney(ActionEvent actionEvent) throws SQLException { // when the user hit the button "send money" this method can do transfer the money
        error.setText("");
        Service service = new Service();
        Connection connection = DriverManager.getConnection(url, username, password);
        String sql2 = "INSERT INTO transactions (user_name , lastName ,trans_id , wallet_id , transaction_type , transaction_balance) values ( ? , ? , ? , ? , ? , ?)";
        PreparedStatement statement4 = connection.prepareStatement(sql2);

        if (amountSend.getText().isBlank() == false && ReceiverId.getText().isBlank() == false && mobileReceive.getText().isBlank() == false && Pin.getText().isBlank() == false){
            sendReq.setText("");
            idreq.setText("");
            mobileReq.setText("");
            passReq.setText("");
            int amount = Integer.parseInt(amountSend.getText());
            if (amount > obj.getBalance()){
                error.setText("balance is insufficient!..".toUpperCase());
            }
            else {
                if (Pin.getText().equals(passwordd)) {
                    Wallet s = service.search(wallet , customer , Integer.parseInt(ReceiverId.getText()) , mobileReceive.getText() , amount);
                    Wallet a = new Wallet();

                    if (s == null) {
                        error.setText("this user isn't exist".toUpperCase());
                    }
                    else {
                        Customer c = a.searchReceiver(customer , mobileReceive.getText());
                        try {
                            double neww = obj.sendMoney(amount , cObj);
                            int newB = s.sendMoney(amount);

                            String query = "UPDATE user_account SET balance = " + newB + " WHERE wallet_id = " + s.getId();
                            Statement statement1 = connection.createStatement();
                            int rows = statement1.executeUpdate(query);
                            if (rows > 0) {
                                System.out.println("updated");
                            }

                            Random random = new Random();
                            int transId = random.nextInt(2000);
                            try {
                                statement4.setString(1, c.getName());
                                statement4.setString(2, c.getLastName());
                                statement4.setInt(3, transId);
                                statement4.setInt(4, s.getId());
                                statement4.setString(5, "Received");
                                statement4.setDouble(6, amount);
                                int row2 = statement4.executeUpdate();
                                if (row2 > 0) {
                                    error.setText("successful sending".toUpperCase());
                                    System.out.println("A transaction row has been inserted");
                                }
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }

                        } catch (NumberFormatException e){
                            error.setText("please type valid inputs!..".toUpperCase());
                        }
                    }
                    }
                else {
                    error.setText("your password is incorrect".toUpperCase());
                }
                }
            }
        else {
            if (amountSend.getText().isBlank()){
                sendReq.setText("* required");
            }
            else
                sendReq.setText("");
            if (ReceiverId.getText().isBlank()){
                idreq.setText("* required");
            }
            else
                idreq.setText("");

            if (mobileReceive.getText().isBlank()){
                mobileReq.setText("* required");
            }
            else
                mobileReq.setText("");

            if (Pin.getText().isBlank()){
                passReq.setText("* required");
            }
            else
                passReq.setText("");
        }
    }

    public void withdraw(ActionEvent actionEvent) throws SQLException { // when the user hit the button "withdraw" this method can do withdrawal
         Connection connection = DriverManager.getConnection(url, username, password);
        if (withdraw.getText().isBlank()){
            withdrawReq.setText("* required");
            error.setText("please enter an amount to withdraw".toUpperCase());
        }
         else {
            withdrawReq.setText("");
            error.setText("");
            int amount =  Integer.parseInt(withdraw.getText());
             if (amount > obj.getBalance()){
                 error.setText("BALANCE IS INSUFFICIENT!..");
             }
             else{
                 int newB = obj.withdraw(amount , cObj);
                 balanceLabel.setText(String.valueOf(newB));
                 error.setText("Successful withdrawal".toUpperCase());
                 try {
                     String query = "UPDATE user_account SET balance = " + newB + " WHERE wallet_id = " + id;
                     Statement statement1 = connection.createStatement();
                     int rows = statement1.executeUpdate(query);
                     if (rows > 0) {
                         System.out.println("updated");
                     }
                 } catch (NumberFormatException e){
                     error.setText("please type valid inputs!..".toUpperCase());
                 }
             }
         }

        }

    public void deposit(ActionEvent actionEvent) throws SQLException { // this method can do the deposit transaction fro the customer
        withdrawReq.setText("");
        sendReq.setText("");
        idreq.setText("");
        mobileReq.setText("");
        passReq.setText("");
        Connection connection = DriverManager.getConnection(url, username, password);
        if (deposit.getText().isBlank()) {
            depositReq.setText("* required");
            error.setText("please enter an amount to deposit".toUpperCase());
        }
        else {
            depositReq.setText("");
            error.setText("");
            int amount = Integer.parseInt(deposit.getText());
            if (amount <= 50) {
                error.setText("PLEASE ENTER AN AMOUNT ABOVE OR EQUALS 50");
            } else {
                int newB = obj.deposit(amount, cObj);
                balanceLabel.setText(String.valueOf(newB));
                error.setText("Successful deposit".toUpperCase());
                try {
                    String query = "UPDATE user_account SET balance = " + newB + " WHERE wallet_id = " + id;
                    Statement statement1 = connection.createStatement();
                    int rows = statement1.executeUpdate(query);

                    if (rows > 0) {
                        System.out.println("updated");
                    }
                } catch (NumberFormatException e){
                    error.setText("please type valid inputs!..".toUpperCase());
                }
            }
        }
    }

    public void transactions(ActionEvent actionEvent) throws SQLException, IOException { // this method can create the transaction form and to show his transactions history
        createTransactionForm();
        transController trans = loader.getController();
        trans.setId(idLabel.getText());
        trans.showData();

    }

    public void createTransactionForm() throws IOException { // this method can create the transaction form and to show his transactions history
        Stage userStage = new Stage();
        try {
            loader = new FXMLLoader(getClass().getResource("trans.fxml"));
            Parent root = loader.load();
            userStage.initStyle(StageStyle.UNDECORATED);
            userStage.setScene(new Scene(root, 950, 400));
            ActionEvent actionEvent = new ActionEvent();
//            hide(actionEvent);
            userStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }
