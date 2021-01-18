package taskmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import taskmanager.model.TaskManagerModel;

import java.io.IOException;

public class Main extends Application {

    public static TaskManagerModel  model;

    static {
        try {
            model = new TaskManagerModel();
        } catch (IOException e) {
            e.printStackTrace(); //
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/TaskManagerMenuView.fxml"));
        primaryStage.setTitle("TaskManager");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        TaskManagerModel.log.info("TaskManager is started");
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        TaskManagerModel.log.info("TaskManager is stopped");
    }

    public static void main(String[] args) {
        launch(args);
    }
}