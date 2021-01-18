package taskmanager.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.stream.Stream;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import taskmanager.Main;
import taskmanager.model.Task;

public class TaskManagerMenuController {

    public ListView taskList;

    public void initialize(){
        initListView();
    }

    public void onClickedAddTask(ActionEvent actionEvent) throws IOException {

        Controller.changeScene("../view/AddTaskView.fxml",actionEvent);
    }

    public void onClickedCalendar(ActionEvent actionEvent) throws IOException {

        Controller.changeScene("../view/CalendarView.fxml",actionEvent);
    }

    private void initListView() {
        ObservableList<Task> obsTaskList = FXCollections.observableArrayList();
        Stream<Task> stream = Main.model.getTaskList().getStream();
        stream.forEach(obsTaskList::add);
        taskList.setItems(obsTaskList);

    }

    public void onClickEditTask(MouseEvent mouseEvent) throws IOException {
        if (Main.model.getTaskList().size()==0) {
            return;
        }

        EditTaskController.setSelectedTask((Task)taskList.getSelectionModel().getSelectedItem());

        Controller.changeScene("../view/EditTaskView.fxml",mouseEvent);
    }
}
