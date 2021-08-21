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

public class empLoginController implements Initializable , Login , Handling{
    FXMLLoader loader;
    @FXML
    Button cancelButton;
    @FXML
    Label login;
    @FXML
    ImageView empLogin;
    @FXML
    ImageView homeImage;
    @FXML
    TextField empId;
    @FXML
    PasswordField Password;
    @FXML
    Label idrequired;
    @FXML
    Label passrequired;
    String url = "jdbc:mysql://localhost:3306/walletdata";
    String username = "root";
    String password = "80080088";

    Employee employee[] = new Employee[0];
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("images/digital--wallet-1024x683.jpg");
        Image image = new Image(file.toURI().toString());
        homeImage.setImage(image);

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

    public void hide(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    @Override
    public void loginButtonAction(ActionEvent actionEvent) throws SQLException, InterruptedException, IOException { // this method will be implemented when the user choose to login and it checks if he entered anything or not

        if (empId.getText().isBlank() == false && Password.getText().isBlank() == false) {
            idrequired.setText("");
            passrequired.setText("");
            validateLogin();
        } else
        if (empId.getText().isBlank()){
            idrequired.setText("* required");
        }
        else
            idrequired.setText("");

        if (Password.getText().isBlank()){
            passrequired.setText("* required");
        }
        else
            passrequired.setText("");
    }

    @Override
    public void handle(KeyEvent event) throws SQLException, IOException, InterruptedException { // this method checks if the user press on the "enter" key
        ActionEvent actionEvent = new ActionEvent();
        if (event.getCode().equals(KeyCode.ENTER))
            loginButtonAction(actionEvent);
    }

    @Override
    public void validateLogin() throws SQLException { // method to check if this user exists already or not
        Connection connection = DriverManager.getConnection(url, username, password);
        String query = "SELECT * FROM employee  where Employee_id ='" + empId.getText() + "'" + " AND Employee_pass = '" + Password.getText() + "'";
        PreparedStatement statement2 = connection.prepareStatement(query);
        ResultSet rs = statement2.executeQuery(query);
        if (rs.next()) {

            try {
                String sqll = "SELECT count(*) FROM employee";
                PreparedStatement stmt = connection.prepareStatement(sqll);
                //Executing the query
                ResultSet resultSet = stmt.executeQuery(sqll);
                //Retrieving the result
                resultSet.next();
                int count = resultSet.getInt(1);
//                System.out.println(count);

                String query2 = "SELECT * FROM employee";
                PreparedStatement stmt2 = connection.prepareStatement(query2);
                //Executing the query
                ResultSet resultSet2 = stmt2.executeQuery(query2);
                //Retrieving the resultv
                employee = new Employee[count];

                int counter = 0;
                while (resultSet2.next()) {
                    String first_name = resultSet2.getString("First Name");
                    String last_name = resultSet2.getString("Last Name");
                    String employee_id = resultSet2.getString("Employee_id");

                    String employee_pass = resultSet2.getString("Employee_pass");
                    employee[counter] = new Employee();
                    employee[counter].setName(first_name);
                    employee[counter].setLastName(last_name);
                    employee[counter].setId(employee_id);

                    employee[counter].setPassword(employee_pass);
                    counter++;
                }
                Employee emp = new Employee();
                Employee empp = emp.search(employee , empId.getText());

                if (empp.getPassword().equals(Password.getText())){
                    createAccountForm();
                    Socket socket = new Socket("localhost" , 4444);
                    ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                    outputStream.writeUTF("Emp. : " + empp.getName() + " " + empp.getLastName());


                    empController empController = loader.getController();
                    empController.setId(empId.getText());
                    empController.setEmp(empp);
                    empController.setPasswordd(empp.getPassword());
                    empController.setEmployees(employee);
                    empController.setSize(count);

                    ActionEvent actionEvent = new ActionEvent();
                    empController.show();
//                    System.out.println(obj2.getId());
                }
                else
                    login.setText("your password is incorrect!..".toUpperCase());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else
            login.setText("SORRY THIS USER DOESN'T EXIST");
    }

    public void createAccountForm() throws IOException { // this method creates the user screen for employee
        Stage userStage = new Stage();
        try {
            loader = new FXMLLoader(getClass().getResource("emp.fxml"));
            Parent root = loader.load();
            userStage.setTitle("Employee Page");
            userStage.setScene(new Scene(root, 750, 500));
//            ActionEvent actionEvent = new ActionEvent();
            userStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
