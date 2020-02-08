import java.io.BufferedWriter;
import java.io.IOException;

import java.util.ArrayList;

/**
 * Stores all existing tasks and manage them.
 */
public class TaskList {
    public ArrayList<Task> list = new ArrayList<>();

    /**
     * Add a new task to the end of the ArrayList list.
     *
     * @param task The task to be added to list.
     */
    public void add(Task task) {
        list.add(task);
    }

    /**
     * Delete a task from the list.
     *
     * @param idx The index of the (to be deleted) task in the list.
     * @return The deleted task.
     */
    public Task delete(int idx) {
        return list.remove(idx);
    }

    /**
     * Mark one of the tasks as done.
     *
     * @param idx The index of the task in the list.
     */
    public void done(int idx) {
        list.get(idx).status = "[" + "\u2713" + "]";
    }

    /**
     * Print out all existing tasks according to their order in the list.
     *
     * @throws IOException .
     */
    public void print(BufferedWriter out) throws IOException {
        for (int i = 0; i < list.size(); i++) {
            out.write(String.valueOf(i + 1) + ".");
            list.get(i).print(out);
            out.flush();
        }
    }

    public int find(String keyword, BufferedWriter out) throws IOException {
        int number = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).name.contains(keyword)) {
                number++;
                out.write(String.valueOf(i) + ".");
                list.get(i).print(out);
                out.flush();
            }
        }
        return number;
    }
}