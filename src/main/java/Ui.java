import java.io.IOException;
import java.io.BufferedWriter;
/**
 * Handles the User Interface; Print out the actions taken following each user command
 */
public class Ui {
    String logo = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";
    public String line = "_____________________________________________________\n";
    public BufferedWriter out;
    public Ui(BufferedWriter out){
        this.out = out;
    }
    /**
     * Prints the greeting message
     * @throws IOException
     */
    public void start() throws IOException{
        out.write("Hello from\n" + logo);
        out.write("Hello! I'm Duke\n" + "What can I do for you?\n");
        out.write("\n");
        printLine();
        out.flush();
    }

    /**
     * Prints a single line of underline
     * @throws IOException
     */
    public void printLine() throws IOException{
        out.write(line);
        out.flush();
    }

    /**
     * Prints a message to inform user that a task has been marked done
     * @param task the task that has been marked done
     * @throws IOException
     */
    public void done(Task task) throws IOException {
        out.write("Nice! I've marked this task as done:\n");
        task.print(out);
        out.write("\n");
        out.flush();
    }

    /**
     * Prints a message to inform user that a task has been deleted, and current number
     * of remaining tasks
     * @param task the deleted task
     * @param n current number of tasks
     * @throws IOException
     */
    public void delete(Task task, int n) throws IOException{
        out.write("Noted. I've removed this task: \n");
        task.print(out);
        currTask(n);
        out.write("\n");
        out.flush();
    }

    /**
     * Inform users that tasks in the list are to be printed
     * @throws IOException
     */
    public void list() throws IOException{
        out.write("Here are the tasks in your list:\n");
        out.flush();
    }

    /**
     * Prints a message to inform user that a task has been added, and current number
     * of remaining tasks
     * @param task the added task
     * @param n current number of tasks in the list
     * @throws IOException
     */
    public void add(Task task, int n) throws IOException{
        out.write("Got it. I've added this task: \n");
        task.print(out);
        currTask(n);
        out.write("\n");
        out.flush();
    }

    /**
     * Prints the closing message before the program ends
     * @throws IOException
     */
    public void bye() throws IOException{
        printLine();
        out.write("Bye. Hope to see you again soon!\n");
        out.write("\n");
        printLine();;
        out.flush();
    }

    /**
     * Prints the current number of tasks in the list
     * @param n number of tasks in the list
     * @throws IOException
     */
    public void currTask(int n) throws IOException{
        out.write("Now you have " + n + " tasks in the list. \n");
        out.flush();
    }

    /**
     * Prints the error message
     * @param err error essage
     * @throws IOException
     */
    public void exp(String err) throws IOException{
        printLine();
        new DukeException(err, out);
        out.write("\n");
        printLine();
        out.flush();
    }

    /**
     * Inform user that there is a loading error when accessing the data file
     * @throws IOException
     */
    public void showLoadingError()throws IOException{
        exp("Sorry, there is a loading error:(");
    }
}