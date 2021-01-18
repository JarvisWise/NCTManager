package taskmanager.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import taskmanager.Main;
import taskmanager.model.Task;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AddTaskController {
    public DatePicker startDateField;
    public TextField startTimeField;
    public TextField titleField;
    public CheckBox checkActive;
    public CheckBox checkRepeated;
    public DatePicker endDateField;
    public TextField endTimeField;
    public TextField intervalField;
    public VBox forRepeatedTask;
    private DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm"); // not MM
    private DateTimeFormatter formatterInterval = DateTimeFormatter.ofPattern("HH:mm:ss");


    public void onClickAddButton(ActionEvent actionEvent) throws IOException {

        // check and add to list
        Task newTask;
        String title = titleField.getText();
        LocalDate startLocalDate = startDateField.getValue();
        LocalTime startLocalTime = LocalTime.parse(startTimeField.getText(),formatterTime);
        LocalDateTime startDateTime = startLocalDate.atTime(startLocalTime);
        boolean isActive = checkActive.isSelected();


        if (!checkRepeated.isSelected()) {

            //check
            newTask = new Task(title, startDateTime);
            newTask.setActive(isActive);

        } else {
            LocalDate endLocalDate = endDateField.getValue();
            LocalTime endLocalTime = LocalTime.parse(endTimeField.getText(),formatterTime);
            LocalDateTime endDateTime = endLocalDate.atTime(endLocalTime);
            int interval = (LocalTime.parse(intervalField.getText(),formatterInterval)).toSecondOfDay();

            //check
            newTask = new Task(title, startDateTime, endDateTime, interval);
            newTask.setActive(isActive);

        }
        Main.model.addToTaskList(newTask);
        onClickCancelButton(actionEvent);
    }

    public void onClickCancelButton(ActionEvent actionEvent) throws IOException {

        Controller.changeScene("../view/TaskManagerMenuView.fxml",actionEvent);
    }

    public void onChangedRepeated(ActionEvent actionEvent) {
        if(checkRepeated.isSelected()) {
            forRepeatedTask.setVisible(true);
        } else {
            forRepeatedTask.setVisible(false);
        }
    }
}
