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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.*;
import java.util.Random;
import java.util.ResourceBundle;

public class signupController implements Initializable , Handling{
    @FXML
    TextField lastName;
    @FXML
    Label lastRequired;
    @FXML
    TextField nameLabel;
    @FXML
    ImageView registerImage;
    @FXML
    TextField mobileLabel;
    @FXML
    Label error;
    @FXML
    PasswordField passwordLabel;
    @FXML
    Label namerequired;
    @FXML
    Label mobilerequired;
    @FXML
    Label balancerequired;
    @FXML
    Label passwordrequired;
    @FXML
    TextField balanceText;
    @FXML
    Button cancelButton;
    ResultSet resultSet2 = null;
    String url = "jdbc:mysql://localhost:3306/walletdata";
    String username = "root";
    String password = "80080088";
    Customer C[] = new Customer[0];
    Wallet wallet[] = new Wallet[0];

    PreparedStatement statement = null;

    int id;
    FXMLLoader loader;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("images/register-red-removebg-preview.png");
        Image image = new Image(file.toURI().toString());
        registerImage.setImage(image);

//        File file1 = new File("images/64-641659_an-error-occurred-sign-up-logo-png-removebg-preview.png");
//        Image image1 = new Image(file1.toURI().toString());
//        signImage.setImage(image1);
    }

    public void signUpButtonAction(javafx.event.ActionEvent actionEvent) throws SQLException, InterruptedException, IOException { // this method will be implemented when the user choose to signup and it checks if he entered anything or not
        error.setText("");
        if (nameLabel.getText().isBlank() == false && lastName.getText().isBlank() == false && mobileLabel.getText().isBlank() == false && balanceText.getText().isBlank() == false && passwordLabel.getText().isBlank() == false) {
            namerequired.setText("");
            lastRequired.setText("");
            mobilerequired.setText("");
            balancerequired.setText("");
            passwordrequired.setText("");
            validateSignup();
        } else
        if (nameLabel.getText().isBlank()){
            namerequired.setText("* required");
        }
        else
            namerequired.setText("");

        if (lastName.getText().isBlank()){
            lastRequired.setText("* required");
        }
        else
            lastRequired.setText("");

        if (mobileLabel.getText().isBlank()){
            mobilerequired.setText("* required");
        }
        else
            mobilerequired.setText("");
        if (balanceText.getText().isBlank()){
            balancerequired.setText("* required");
        }
        else
            balancerequired.setText("");

        if (passwordLabel.getText().isBlank()){
            passwordrequired.setText("* required");
        }
        else
            passwordrequired.setText("");

    }

    public void validateSignup() throws SQLException, IOException { // method to check if this user exists already or not
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM user_account  where mobile_no = '" + mobileLabel.getText() + "'" /*+ " AND password = '" + Password.getText() + "'"*/;
            PreparedStatement statement2 = connection.prepareStatement(query);
            ResultSet rs = statement2.executeQuery(query);

            if (rs.next()) {
                error.setText("THIS USER IS EXIST");
            } else {
//                createAccountForm();
                String sql2 = "INSERT INTO user_account(user_name , lastName , wallet_id , mobile_no , password , balance) values ( ? , ? , ? , ? , ? , ?)";
                statement = connection.prepareStatement(sql2);
                statement.setString(1, nameLabel.getText());
                Random randomm = new Random();
                id = randomm.nextInt(2500);
                statement.setString(2, lastName.getText());
                statement.setInt(3, id);
                statement.setString(4, mobileLabel.getText());
                statement.setString(5, passwordLabel.getText());
                statement.setInt(6, Integer.parseInt(balanceText.getText()));
                int rows = statement.executeUpdate();
                if (rows > 0) {
                    System.out.println("A row has been inserted");
                }
                error.setText("you have registered!..".toUpperCase());
                createAccountForm();

                String sqll = "SELECT count(*) FROM user_account";
                PreparedStatement stmt = connection.prepareStatement(sqll);
                //Executing the query
                ResultSet resultSet = stmt.executeQuery(sqll);
                //Retrieving the result
                resultSet.next();
                int count = resultSet.getInt(1);
                System.out.println(count);

                String query2 = "SELECT * FROM user_account";
                PreparedStatement stmt2 = connection.prepareStatement(query2);
                //Executing the query
                resultSet2 = stmt2.executeQuery(query2);
                //Retrieving the resultv
                C = new Customer[count];
                wallet = new Wallet[count];

                int counter = 0;
                while (resultSet2.next()) {
                    String cName = resultSet2.getString("user_name");
                    String lName = resultSet2.getString("lastName");
                    int walletId = resultSet2.getInt("wallet_id");
                    String mobileNo = resultSet2.getString("mobile_no");
                    String cPassword = resultSet2.getString("password");
                    int cBalance = resultSet2.getInt("balance");
                    C[counter] = new Customer(count);
                    C[counter].setName(cName);
                    C[counter].setLastName(lName);
                    C[counter].setMobile(mobileNo);
                    C[counter].setPin(cPassword);
                    wallet[counter] = new Wallet();
                    wallet[counter].setId(walletId);
                    wallet[counter].setBalance(cBalance);

                    counter++;
                }


                Socket socket = new Socket("localhost", 4444);
                ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());


                Wallet obj = new Wallet();
                Wallet obj2 = obj.search(wallet, id);
                Customer cObj = obj.search(C, mobileLabel.getText());
                outputStream.writeUTF(cObj.getName());

                userController userController = loader.getController();
                userController.setId(String.valueOf(id));
                userController.setMobile(mobileLabel.getText());
                userController.setBalance(obj2.getBalance());
                userController.setWallet(wallet);
                userController.setCustomer(C);
                userController.setObj(obj2);
                userController.setcObj(cObj);
                userController.setPasswordd(cObj.getPin());
                userController.setSize(count);

                ActionEvent actionEvent = new ActionEvent();
                userController.show();

            }

            } catch(SQLIntegrityConstraintViolationException e){
                error.setText("THIS MOBILE NUMBER IS EXIST");
                //            e.printStackTrace();
            } catch(SQLException throwables){
                error.setText("THIS USER IS EXIST");
            } catch(IOException e){
                e.printStackTrace();
            }
        catch (NumberFormatException e){
            error.setText("please enter valid inputs".toUpperCase());
        }
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


    public void createAccountForm() throws IOException { // this method creates the user screen
        Stage userStage = new Stage();
        try {
            loader = new FXMLLoader(getClass().getResource("user.fxml"));
            Parent root = loader.load();
            userStage.setTitle("Wallet Page");
            userStage.setScene(new Scene(root, 750, 500));

            userStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(KeyEvent event) throws SQLException, IOException, InterruptedException {
        ActionEvent actionEvent = new ActionEvent();
        if (event.getCode().equals(KeyCode.ENTER))
            signUpButtonAction(actionEvent);
    }
}
