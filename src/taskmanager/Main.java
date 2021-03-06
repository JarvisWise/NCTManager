package taskmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import taskmanager.controller.Controller;
import taskmanager.model.TaskManagerModel;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        if(Controller.isInitialize) {
            Parent root = FXMLLoader.load(getClass().getResource("view/TaskManagerMenuView.fxml"));
            primaryStage.setTitle("TaskManager");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            TaskManagerModel.log.info("TaskManager started");
        } else {
            TaskManagerModel.log.info("TaskManager did not start");
        }
    }

    @Override
    public void stop() throws Exception {
        TaskManagerModel.log.info("TaskManager stopped");
        super.stop();
    }

    public static void main(String[] args) {
            launch(args);
    }
}