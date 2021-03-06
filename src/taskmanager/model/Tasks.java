package taskmanager.model;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Tasks {

    public  static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {

        if (tasks == null) {
            throw new NullPointerException("Tasks cannot be null!");
        }

        if (start == null || end == null) {
            throw new NullPointerException("Time cannot be null!");
        }

        if (end.compareTo(start) < 0) {
            throw new IllegalArgumentException("Start time  cannot be less then end time!");
        }

        ArrayList<Task> list = new ArrayList<>();

        for (Task task: tasks) {
            if(task.nextTimeAfter(start) != null && task.nextTimeAfter(start).compareTo(end) <= 0) {
                list.add(task);
            }
        }

        //Iterator<Task> itr = tasks.iterator();
        //Stream<Task> stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(itr, 0), false);
        //stream.filter((t) -> t.nextTimeAfter(start) != null && t.nextTimeAfter(start).compareTo(end) <= 0).forEach(list::add);
        return list;
    }

    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {

        if (tasks == null) {
            throw new NullPointerException("Tasks cannot be null!");
        }

        if (start == null || end == null) {
            throw new NullPointerException("Time cannot be null!");
        }

        if (end.compareTo(start) < 0) {
            throw new IllegalArgumentException("Start time  cannot be less then end time!");
        }

        SortedMap<LocalDateTime, Set<Task>> map = new TreeMap<>();

        for (Task task : tasks) {
            for (LocalDateTime i = task.nextTimeAfter(start); i != null && i.compareTo(end) <= 0; i = task.nextTimeAfter(i)) {
                if (map.get(i) == null) {
                    HashSet<Task> set = new HashSet<>();
                    set.add(task);
                    map.put(i,set);
                    //map.put(i, new HashSet<Task>(Collections.singletonList(task)));
                } else {
                    map.get(i).add(task);
                }
            }
        }
        return map;
    }
}
