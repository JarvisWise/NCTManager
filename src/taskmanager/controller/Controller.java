package taskmanager.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    public static void changeScene(String path, Event event) throws IOException {
        Parent TaskManagerMenuParent  = FXMLLoader.load(Controller.class.getResource(path));
        Scene TaskManagerMenuScene = new Scene(TaskManagerMenuParent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(TaskManagerMenuScene);
    }

}
