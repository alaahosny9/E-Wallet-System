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

import javax.imageio.IIOParam;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.*;
import java.util.Random;
import java.util.ResourceBundle;

public class empController implements Initializable {
    Customer C[] = new Customer[0];
    Wallet wallet[] = new Wallet[0];
    FXMLLoader loader;
    @FXML
    Button cancelButton;
    @FXML
    TextField removeId;
    @FXML
    Label wIdReq;
    @FXML
    Label mobReq;
    @FXML
    Label idReq;
    @FXML
    Label removeMobReq;
    @FXML
    TextField removeMob;
    @FXML
    TextField Mobile;
    @FXML
    TextField customerId;
    @FXML
    Label welcome;
    @FXML
    Label idLabel;
    @FXML
    Label login;
    @FXML
    ImageView shieldImage;
    String url = "jdbc:mysql://localhost:3306/walletdata";
    String username = "root";
    String password = "80080088";
    String id , mobile;
    double balance;
    Employee emp = new Employee();
    int size;
    Connection connection = DriverManager.getConnection(url, username, password);
    Employee employees[] = new Employee[size];
    private String passwordd;

    public empController() throws SQLException {
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public void setEmployees(Employee[] employees) {
        this.employees = employees;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Employee getEmp() {
        return emp;
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
    }

    public void show(){
        idLabel.setText(id);
        welcome.setText("welcome ".toUpperCase() + emp.getName().toUpperCase() + " " + emp.getLastName().toUpperCase());
    }

    @FXML
    public void cancelButtonAction(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        System.exit(1);
    }

    public void hide(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

//    EmployeeServices services = new EmployeeServices();

    public void AccessUser(ActionEvent actionEvent) throws IOException, SQLException {
//        services.AccessCustomer();

        if (customerId.getText().isBlank() == false && Mobile.getText().isBlank() == false){
            wIdReq.setText("");
            mobReq.setText("");
            String query = "SELECT * FROM user_account  where wallet_id ='" + Integer.parseInt(customerId.getText()) + "'" + " AND mobile_no = '" + Mobile.getText() + "'";
            PreparedStatement statement2 = connection.prepareStatement(query);
            ResultSet rs = statement2.executeQuery(query);
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
                        int walletId = resultSet2.getInt("wallet_id");
                        System.out.println();
                        String mobileNo = resultSet2.getString("mobile_no");
                        String cPassword = resultSet2.getString("password");
                        int cBalance = resultSet2.getInt("balance");
                        C[counter] = new Customer();
                        C[counter].setName(cName);
                        C[counter].setMobile(mobileNo);
                        C[counter].setPin(cPassword);
                        wallet[counter] = new Wallet();
                        wallet[counter].setId(walletId);
                        wallet[counter].setBalance(cBalance);

                        counter++;
                    }
                    Wallet obj = new Wallet();
                    Wallet obj2 = obj.search(wallet,  Integer.parseInt(customerId.getText()));
                    Customer cObj = obj.search(C , Mobile.getText());

                    if (cObj.getMobile().equals(Mobile.getText()) && obj2.getId() == Integer.parseInt(customerId.getText())){
                        createAccountForm();
                        Socket socket = new Socket("localhost" , 4444);
                        ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
                        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                        outputStream.writeUTF(cObj.getName());

                        userController userController = loader.getController();
                        userController.setId(customerId.getText());
                        userController.setMobile(Mobile.getText());
                        userController.setBalance(obj2.getBalance());
                        userController.setWallet(wallet);
                        userController.setPasswordd(cObj.getPin());
                        userController.setCustomer(C);
                        userController.setObj(obj2);
                        userController.setcObj(cObj);
                        userController.setSize(count);

                        userController.show();
                    }
                    else
                        login.setText("wallet data is incorrect!..".toUpperCase());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else
                login.setText("SORRY THIS USER DOESN'T EXIST");
        }
        else {
            if (customerId.getText().isBlank())
                wIdReq.setText("* required");
            else
                wIdReq.setText("");
            if (Mobile.getText().isBlank())
                mobReq.setText("* required");
            else
                mobReq.setText("");
        }

    }

    public void createAccountForm() throws IOException {
        Stage userStage = new Stage();
        try {
            loader = new FXMLLoader(getClass().getResource("user.fxml"));
            Parent root = loader.load();
            userStage.setTitle("Wallet Page");
            userStage.setScene(new Scene(root, 750, 500));
            ActionEvent actionEvent = new ActionEvent();
            userStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createRegisterForm(){ // this method shows to the
        Stage userStage = new Stage();
        try {
            loader = new FXMLLoader(getClass().getResource("signup.fxml"));
            Parent root = loader.load();
//            userStage.setTitle("Wallet Registration");
            userStage.initStyle(StageStyle.UNDECORATED);
            userStage.setScene(new Scene(root, 650, 465));

            userStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(ActionEvent actionEvent) throws SQLException { // this method doing the deletion for the employee
//        services.deleteCustomer();

        if (removeId.getText().isBlank() == false && removeMob.getText().isBlank() == false){
            idReq.setText("");
            removeMobReq.setText("");
            Statement stmt = connection.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
            stmt.close();

            String sql = "DELETE FROM user_account WHERE wallet_id = " + Integer.parseInt(removeId.getText()) + " AND mobile_no = " + removeMob.getText();
            Statement statement2 = connection.createStatement();
            int rs = statement2.executeUpdate(sql);
            if (rs > 0){
                login.setText("successful deleting user!..".toUpperCase());
            }
            else
                login.setText("this user doesn't exist!..".toUpperCase());
        }
        else {
            if (removeId.getText().isBlank())
                idReq.setText("* required");
            else{
                idReq.setText("");
            }
            if (removeMob.getText().isBlank())
                removeMobReq.setText("* required");
            else
                removeMobReq.setText("");
        }
    }

}
