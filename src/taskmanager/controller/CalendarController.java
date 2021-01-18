package taskmanager.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import taskmanager.Main;
import taskmanager.model.Task;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.SortedMap;

public class CalendarController {
    public DatePicker calendarStartDateFiled;
    public TextField calendarStartTimeFiled;
    public DatePicker calendarEndDateFiled;
    public TextField calendarEndTimeFiled;
    public ListView calendarList;
    private DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");


    public void onClickShowCalendarButton(ActionEvent actionEvent) {

        LocalDate startLocalDate = calendarStartDateFiled.getValue();
        LocalTime startLocalTime = LocalTime.parse(calendarStartTimeFiled.getText(),formatterTime);
        LocalDateTime startDateTime = startLocalDate.atTime(startLocalTime);

        LocalDate endLocalDate = calendarEndDateFiled.getValue();
        LocalTime endLocalTime = LocalTime.parse(calendarEndTimeFiled.getText(),formatterTime);
        LocalDateTime endDateTime = endLocalDate.atTime(endLocalTime);

        // check interval
        SortedMap<LocalDateTime, Set<Task>> calendar = Main.model.getCalendar(startDateTime, endDateTime);
        ObservableList<String> obsCalendarList = FXCollections.observableArrayList();

        for (SortedMap.Entry<LocalDateTime, Set<Task>> entry: calendar.entrySet()) {
            for (Task task: entry.getValue()) {
                obsCalendarList.add("Time("+ entry.getKey()+") Title("+task.getTitle()+")");
            }
        }
        calendarList.setItems(obsCalendarList);

    }

    public void onClickCancelButton(ActionEvent actionEvent) throws IOException {

        Controller.changeScene("../view/TaskManagerMenuView.fxml",actionEvent);

    }
}
