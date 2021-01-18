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


public class EditTaskController {
    public TextField titleField;
    public TextField startTimeField;
    public DatePicker startDateField;
    public CheckBox checkActive;
    public CheckBox checkRepeated;
    public DatePicker endDateField;
    public TextField endTimeField;
    public TextField intervalField;
    public VBox forRepeatedTask;

    private DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
    private DateTimeFormatter formatterInterval = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static Task selectedTask;

    public static void setSelectedTask(Task st) {
        selectedTask = st;
    }

    public void initialize(){
        initTaskView();
    }

    private void initTaskView() {
        if (selectedTask != null ) {
            titleField.setText(selectedTask.getTitle());

            startDateField.setValue(selectedTask.getStartTime().toLocalDate());
            startTimeField.setText(formatterTime.format(selectedTask.getStartTime().toLocalTime()));
            checkActive.setSelected(selectedTask.isActive());
            checkRepeated.setSelected(selectedTask.isRepeated());

            if (selectedTask.isRepeated()) {
                endDateField.setValue(selectedTask.getEndTime().toLocalDate());
                endTimeField.setText(formatterTime.format(selectedTask.getEndTime().toLocalTime()));
                intervalField.setText(formatterInterval.format(LocalTime.ofSecondOfDay(selectedTask.getRepeatInterval())));
            } else {
                forRepeatedTask.setVisible(false);
            }
        }
    }

    public void onClickEditButton(ActionEvent actionEvent) throws IOException {
        Task newTask;
        String title = titleField.getText();
        LocalDate startLocalDate = startDateField.getValue();
        LocalTime startLocalTime = LocalTime.parse(startTimeField.getText(),formatterTime);
        LocalDateTime startDateTime = startLocalDate.atTime(startLocalTime);
        boolean isActive = checkActive.isSelected();

        if (checkRepeated.isSelected()) {
            newTask = new Task(title, startDateTime);
            newTask.setActive(isActive);
        } else {
            LocalDate endLocalDate = endDateField.getValue();
            LocalTime endLocalTime = LocalTime.parse(endTimeField.getText(),formatterTime);
            LocalDateTime endDateTime = endLocalDate.atTime(endLocalTime);
            int interval = (LocalTime.parse(intervalField.getText(),formatterInterval)).toSecondOfDay();

            newTask = new Task(title, startDateTime, endDateTime, interval);
            newTask.setActive(isActive);
        }

        Main.model.changeTask(selectedTask, newTask);
        onClickCancelButton(actionEvent);
    }

    public void onClickDeleteButton(ActionEvent actionEvent) throws IOException {
        Main.model.removeTask(selectedTask);
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
