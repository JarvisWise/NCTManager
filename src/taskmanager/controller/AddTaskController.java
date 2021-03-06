package taskmanager.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import taskmanager.model.Task;
import taskmanager.model.TaskManagerModel;

public class AddTaskController {
    public TextField titleField;
    public DatePicker startDateField;
    public TextField startTimeField;
    public CheckBox checkActive;
    public CheckBox checkRepeated;
    public DatePicker endDateField;
    public TextField endTimeField;
    public TextField intervalField;
    public VBox forRepeatedTask;
    private static final DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter formatterInterval = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * method react on Add button clicked, check all fields and add new task to task list
     * @param actionEvent current event
     */

    public void onClickAddButton(ActionEvent actionEvent) {
        Task newTask;
        LocalDateTime startDateTime;

        String title = titleField.getText();
        if (titleField.getText().equals("")) {
            Controller.showWarningAlert("Wrong input",
                                 "Title field",
                                 "Please enter the title");
            return;
        }

        try {
            LocalDate startLocalDate = startDateField.getValue();
            LocalTime startLocalTime = LocalTime.parse(startTimeField.getText(), formatterTime);
            startDateTime = startLocalDate.atTime(startLocalTime);
        } catch (Exception e) {
            Controller.showWarningAlert("Wrong input",
                                 "Time Field",
                                 "Please enter the start time correct");
            return;
        }

        boolean isActive = checkActive.isSelected();
        if (!checkRepeated.isSelected()) {
            newTask = new Task(title, startDateTime);
            newTask.setActive(isActive);

        } else {
            LocalDateTime endDateTime;
            int interval;
            try {
                LocalDate endLocalDate = endDateField.getValue();
                LocalTime endLocalTime = LocalTime.parse(endTimeField.getText(), formatterTime);
                endDateTime = endLocalDate.atTime(endLocalTime);
                interval = (LocalTime.parse(intervalField.getText(), formatterInterval)).toSecondOfDay();
            } catch (Exception e) {
                Controller.showWarningAlert("Wrong input",
                                     "Time Field",
                                     "Please enter the end time or interval correct");
                return;
            }

            if (endDateTime.compareTo(startDateTime) < 0) {
                Controller.showWarningAlert("Wrong input",
                        "Start and end time",
                        "Start time cannot be after end time");
                return;
            }

            newTask = new Task(title, startDateTime, endDateTime, interval);
            newTask.setActive(isActive);
        }

        try {
            Controller.model.addToTaskList(newTask);
        } catch (IOException e) {
            Controller.showWarningAlert("File problem",
                                 "Add new task",
                                 "Problems with file system, task was not added");
            TaskManagerModel.log.warn("Task was not added", e);
            return;
        }
        onClickCancelButton(actionEvent);
    }

    /**
     * This method change this scene on TaskManagerMenu
     * @param actionEvent current event
     */

    public void onClickCancelButton(ActionEvent actionEvent) {
        Controller.changeScene("../view/TaskManagerMenuView.fxml", actionEvent);
    }

    /**
     * This method using for show and hide fields,
     * that can be used for create repeated task
     */

    public void onChangedRepeated() {
        if (checkRepeated.isSelected()) {
            forRepeatedTask.setVisible(true);
        } else {
            forRepeatedTask.setVisible(false);
        }
    }
}
