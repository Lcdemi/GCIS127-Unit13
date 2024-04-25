package unit13.assignment2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Task implements Comparable<Task> {
    private String name;
    private int time;

    public Task(String name, int time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    @Override
    public int compareTo(Task other) {
        int timeComparison = this.getTime() - other.getTime();
        if(timeComparison != 0) {
            return timeComparison;
        } else {
            return this.getName().compareTo(other.getName());
        }
    }

    public static List<Task> findMaxSubset(List<Task> tasks, int maxTime) {
        List<Task> addedTasks = new ArrayList<>();
        Collections.sort(tasks);
        for(Task t : tasks) {
            if(maxTime - t.getTime() >= 0) {
                addedTasks.add(t);
                maxTime -= t.getTime();
            } else {
                break;
            }
        }
        return addedTasks;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", timeToComplete=" + time +
                '}';
    }
}
