package taskmanager.model;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

import static taskmanager.model.Tasks.calendar;

public class TaskManagerModel {
    public static final Logger log = Logger.getLogger(TaskManagerModel.class);
    private AbstractTaskList taskList;
    private File dataFile;

    public TaskManagerModel() throws IOException {
        dataFile = new File(System.getProperty("user.dir") + "\\data.txt");
        taskList = new ArrayTaskList();
        try {
            if (!dataFile.exists()) {
                if(dataFile.createNewFile()) {
                    log.info("File data.txt was created");
                } else {
                    log.error("File data.txt  was not created");
                    throw new IOException("File for saving data was not created!");
                }
            } else {
                readTaskListFromRes();
            }
        } catch (IOException e) {
            throw new IOException("Exception at model part",e);
        }
    }

    public AbstractTaskList getTaskList() {
        return taskList;
    }

    public SortedMap<LocalDateTime, Set<Task>> getCalendar(LocalDateTime start, LocalDateTime end) {

        // add check
        return calendar(taskList, start, end);
    }

    public void readTaskListFromRes() throws IOException {
        try {
            TaskIO.readText(taskList, dataFile);
        } catch (IOException e) {
            log.warn("Reading from file was failed", e);
            throw new IOException(e);
        }
    }

    public void writeTaskListFromRes() throws IOException {
        try {
            TaskIO.writeText(taskList, dataFile);
        } catch (IOException e) {
            log.warn("Writing to file was failed", e);
            throw new IOException(e);
        }
    }

    public void addToTaskList(Task task) throws IOException {
        if(task == null)
            return;

        taskList.add(task);
        writeTaskListFromRes();
    }

    public void changeTask(Task oldValue, Task newValue) throws IOException {
        taskList.setTask(oldValue, newValue);
        writeTaskListFromRes();
    }

    public void removeTask(Task task) throws IOException {
        taskList.remove(task);
        writeTaskListFromRes();
    }

}
