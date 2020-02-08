import java.io.BufferedWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Interpret the commands given by user and act accordingly
 */
public class Parser {
    public Command cmd;
    public String curr;
    public StringTokenizer st;
    public BufferedWriter out;

    /**
     * Constructor of an instance of Parser, convert the user command to Enum Command
     * @param text the line of text input by user
     */
    public Parser(String text, BufferedWriter out) {
        curr = text;
        st = new StringTokenizer(curr);
        this.out = out;
    }

    /**
     * Edit the list of tasks according to the user command, and reflect to the user
     * through user interface
     * @param ui the user interface
     * @param tasks the list of tasks
     * @return whether the user wants to continue the program
     * @throws IOException
     */
    public boolean process(Ui ui, TaskList tasks) throws IOException{
        Task newTask;
        try {
            cmd = Command.valueOf(st.nextToken());
            switch (cmd) {
                case bye:
                    ui.bye();
                    return false;
                case list:
                    ui.printLine();
                    ui.list();
                    tasks.print(out);
                    System.out.println();
                    ui.printLine();
                    return true;
                case done:
                    ui.printLine();
                    try {
                        int idx = Integer.parseInt(st.nextToken()) - 1;
                        tasks.done(idx);
                        ui.done(tasks.list.get(idx));
                        ui.printLine();
                    } catch (NoSuchElementException e1) {
                        ui.exp("Please enter a number");
                    } catch (NumberFormatException e2) {
                        ui.exp("Please enter a valid number to be done");
                    } catch (IndexOutOfBoundsException e3) {
                        ui.exp("Number entered is larger than size of task list");
                    }
                    return true;
                case delete:
                    ui.printLine();
                    try {
                        int idx = Integer.parseInt(st.nextToken()) - 1;
                        ui.delete(tasks.delete(idx), tasks.list.size());
                        ui.printLine();
                    } catch (NoSuchElementException e1) {
                        ui.exp("Please enter a number");
                    } catch (NumberFormatException e2) {
                        ui.exp("Please enter a valid number to be done");
                    } catch (IndexOutOfBoundsException e3) {
                        ui.exp("Number entered is larger than size of task list");
                    }
                    return true;
                case todo:
                    ui.printLine();
                    try {
                        st.nextToken();
                        newTask = new Task(curr.substring(4));
                        tasks.add(newTask);
                        ui.add(newTask, tasks.list.size());
                        ui.printLine();
                    } catch (NoSuchElementException e1) {
                        ui.exp("OOPS!!! The description of a todo cannot be empty.");
                    }
                    return true;
                case deadline:
                    try {
                        st.nextToken();
                        int endIdx = curr.indexOf(" /by ");
                        if (endIdx == -1) {
                            ui.exp("OOPS!!! The time cannot be empty.");
                        } else {
                            newTask = new Deadline(curr.substring(8, endIdx), " (by:" + curr.substring(endIdx + 4) + ")");
                            tasks.add(newTask);
                            ui.add(newTask, tasks.list.size());
                        }
                    } catch (NoSuchElementException e1) {
                        ui.exp("OOPS!!! The description of a deadline cannot be empty.");
                    }
                    return true;
                case event:
                    try {
                        st.nextToken();
                        int endIdx = curr.indexOf(" /at ");
                        if (endIdx == -1) {
                            ui.exp("OOPS!!! The venue cannot be empty.");
                        } else {
                            newTask = new Event(curr.substring(5, endIdx), " (by:" + curr.substring(endIdx + 4) + ")");
                            tasks.add(newTask);
                            ui.add(newTask, tasks.list.size());
                        }
                    } catch (NoSuchElementException e1) {
                        ui.exp("OOPS!!! The description of an event cannot be empty.");
                    }
                    return true;
            }
        } catch (IllegalArgumentException e) {
            ui.exp("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
        return true;
    }
}
