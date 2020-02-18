import java.io.BufferedWriter;
import java.io.IOException;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Interpret the commands given by user and act accordingly.
 */
public class Parser {
    private Command cmd;
    private String curr;
    private StringTokenizer st;

    /**
     * Constructor of an instance of Parser, convert the user command to Enum Command.
     *
     * @param text The line of text input by user.
     */
    public Parser(String text) {
        curr = text;
        st = new StringTokenizer(curr);
    }

    /**
     * Edit the list of tasks according to the user command.
     * Reflect to the user through user interface.
     * Always return true unless user key in "bye".
     *
     * @param ui The user interface.
     * @param tasks The list of tasks.
     * @return Whether the user wants to continue the program.
     * @throws IOException .
     */
    public boolean canContinue(Ui ui, TaskList tasks) {
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
                out.write("\n");
                ui.printLine();
                return true;
            case done:

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
                        newTask = new Deadline(curr.substring(8, endIdx),
                                " (by:" + curr.substring(endIdx + 4) + ")");
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
                        newTask = new Event(curr.substring(5, endIdx),
                                " (by:" + curr.substring(endIdx + 4) + ")");
                        tasks.add(newTask);
                        ui.add(newTask, tasks.list.size());
                    }
                } catch (NoSuchElementException e1) {
                    ui.exp("OOPS!!! The description of an event cannot be empty.");
                }
                return true;
            case find:
                try{
                    String keyword = st.nextToken();
                    ui.search();
                    tasks.find(keyword, out);
                    out.write("\n");
                    ui.printLine();
                } catch (NoSuchElementException e1){
                    ui.exp("OOPS!!! The description of find cannot be empty.");
                }
            default:
                return true;
            }
        } catch (IllegalArgumentException e) {
            ui.exp("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
        return true;
    }
    public String caseBye(Ui ui) {
        return ui.bye();
    }
    public  String caseList(Ui ui, TaskList tasks) {
        return ui.line + ui.list() + tasks.print() + "\n" + ui.line;
    }
    public String caseDone(Ui ui, TaskList tasks) {
        String message = ui.line;
        try {
            int idx = Integer.parseInt(st.nextToken()) - 1;
            tasks.done(idx);
            message += ui.done(tasks.list.get(idx));
            message += ui.line;
            return message;
        } catch (NoSuchElementException e1) {
            return ui.exp("Please enter a number");
        } catch (NumberFormatException e2) {
            return ui.exp("Please enter a valid number to be done");
        } catch (IndexOutOfBoundsException e3) {
            return ui.exp("Number entered is larger than size of task list");
        }
    }
    public String caseDelete() {
        
    }

}
