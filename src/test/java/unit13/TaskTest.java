package unit13;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import unit13.assignment2.Task;

public class TaskTest {

    @Test
    public void findMaxSubsetTest() {
        //setup
        Task t1 = new Task("Mop", 2);
        Task t2 = new Task("Sweep", 1);
        Task t3 = new Task("Clean Office", 6);
        Task t4 = new Task("Laundry", 3);
        Task t5 = new Task("Landscaping", 4);
        Task t6 = new Task("Clean Basement", 5);
        List<Task> tasks = new ArrayList<>();
        tasks.add(t1); tasks.add(t2); tasks.add(t3); tasks.add(t4); tasks.add(t5); tasks.add(t6);

        //invoke
        List<Task> actualSubset = Task.findMaxSubset(tasks, 6);

        //analyze
        List<Task> expectedSubset = new ArrayList<>();
        expectedSubset.add(t2); expectedSubset.add(t1); expectedSubset.add(t4);

        assertEquals(expectedSubset, actualSubset);
    }
}
