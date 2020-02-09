import java.io.IOException;
import java.io.BufferedWriter;

/**
 * Handles the User Interface; Print out the actions taken following each user command.
 */
public class Ui {
    private String logo = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";
    private String line = "_____________________________________________________\n";
    private BufferedWriter out;

    public Ui(BufferedWriter out) {
        this.out = out;
    }

    /**
     * Prints the greeting message.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void start() throws IOException {
        out.write("Hello from\n" + logo);
        out.write("Hello! I'm Duke\n" + "What can I do for you?\n");
        out.write("\n");
        printLine();
        out.flush();
    }

    /**
     * Prints a single line of underline.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void printLine() throws IOException {
        out.write(line);
        out.flush();
    }

    /**
     * Prints a message to inform user that a task has been marked done.
     * @param task The task that has been marked done.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void done(Task task) throws IOException {
        out.write("Nice! I've marked this task as done:\n");
        task.print(out);
        out.write("\n");
        out.flush();
    }

    /**
     * Prints a message to inform user that a task has been deleted, and current number
     * of remaining tasks.
     *
     * @param task The deleted task.
     * @param n Current number of tasks.
     * @throws IOException if an I/O error occurs.
     */
    public void delete(Task task, int n) throws IOException {
        out.write("Noted. I've removed this task: \n");
        task.print(out);
        currTask(n);
        out.write("\n");
        out.flush();
    }

    /**
     * Inform users that tasks in the list are to be printed.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void list() throws IOException {
        out.write("Here are the tasks in your list:\n");
        out.flush();
    }

    /**
     * Prints a message to inform user that a task has been added, and current number
     * of remaining tasks.
     *
     * @param task The added task.
     * @param n Current number of tasks in the list.
     * @throws IOException if an I/O error occurs.
     */
    public void add(Task task, int n) throws IOException {
        out.write("Got it. I've added this task: \n");
        task.print(out);
        currTask(n);
        out.write("\n");
        out.flush();
    }

    /**
     * Prints the closing message before the program ends.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void bye() throws IOException {
        printLine();
        out.write("Bye. Hope to see you again soon!\n");
        out.write("\n");
        printLine();;
        out.flush();
    }

    /**
     * Prints the current number of tasks in the list.
     *
     * @param n Number of tasks in the list.
     * @throws IOException if an I/O error occurs.
     */
    public void currTask(int n) throws IOException {
        out.write("Now you have " + n + " tasks in the list. \n");
        out.flush();
    }

    /**
     * Inform users that matched tasks are to be printed
     *
     * @throws IOException if an I/O error occurs.
     */
    public void search() throws IOException {
        printLine();
        out.write("Here are the matching tasks in your list:\n");
    }

    /**
     * Prints the error message.
     *
     * @param err Error message.
     * @throws IOException if an I/O error occurs.
     */
    public void exp(String err) throws IOException {
        printLine();
        new DukeException(err, out);
        out.write("\n");
        printLine();
        out.flush();
    }

    /**
     * Inform user that there is a loading error when accessing the data file.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void showLoadingError() throws IOException {
        exp("Sorry, there is a loading error:(");
    }
}