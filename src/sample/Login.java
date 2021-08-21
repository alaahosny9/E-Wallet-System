package sample;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.sql.SQLException;

public interface Login {
    void loginButtonAction(ActionEvent actionEvent) throws SQLException, InterruptedException, IOException; // this method will be implemented when the user choose to login and it checks if he entered anything or not
    void validateLogin() throws SQLException; // method to check if this user exists already or not
}
