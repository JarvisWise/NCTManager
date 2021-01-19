package taskmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import taskmanager.model.TaskManagerModel;

public class Main extends Application {

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
        TaskManagerModel.log.info("TaskManager is stopped");
        super.stop();
    }

    public static void main(String[] args) {
            launch(args);
    }
}