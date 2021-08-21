package sample;

import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.sql.SQLException;

public interface Handling {
    void handle(KeyEvent event) throws SQLException, IOException, InterruptedException; // this method handling when user press "Enter" key
}
