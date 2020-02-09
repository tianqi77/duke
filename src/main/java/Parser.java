import java.io.BufferedWriter;
import java.io.IOException;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Interpret the commands given by user and act accordingly.
 */
public class Parser {
    private String curr;
    private StringTokenizer st;
    private BufferedWriter out;

    /**
     * Constructor of an instance of Parser, convert the user command to Enum Command.
     *
     * @param text The line of text input by user.
     * @param out Writer.
     */
    public Parser(String text, BufferedWriter out) {
        curr = text;
        st = new StringTokenizer(curr);
        this.out = out;
    }

    /**
     * Edit the list of tasks according to the user command.
     * Reflect to the user through user interface.
     * Always return true unless user key in "bye".
     *
     * @param ui The user interface.
     * @param tasks The list of tasks.
     * @return Whether the user wants to continue the program.
     * @throws IOException If an I/O error occurs.
     */
    public boolean canContinue(Ui ui, TaskList tasks) throws IOException {
        try {
            Command cmd = Command.valueOf(st.nextToken());
            switch (cmd) {
            case bye:
                caseBye(ui);
                return false;
            case list:
                caseList(ui, tasks);
                return true;
            case done:
                caseDone(ui, tasks);
                return true;
            case delete:
                caseDelete(ui, tasks);
                return true;
            case todo:
                caseTodo(ui, tasks);
                return true;
            case deadline:
                caseDeadline(ui, tasks);
                return true;
            case event:
                caseEvent(ui, tasks);
                return true;
            case find:
                caseFind(ui, tasks);
                return true;
            }
        } catch (IllegalArgumentException e) {
            caseException(ui);
        }
        return true;
    }

    /**
     * Prints the closing message.
     *
     * @param ui User interface.
     * @throws IOException if there is an I/O error.
     */
    public void caseBye(Ui ui) throws IOException {
        ui.bye();
    }

    /**
     * List down all existing tasks and show to the user.
     *
     * @param ui User interface.
     * @param tasks Stores the list of tasks.
     * @throws IOException if there is an I/O error.
     */
    public void caseList(Ui ui, TaskList tasks) throws IOException {
        ui.printLine();
        ui.list();
        tasks.print(out);
        out.write("\n");
        ui.printLine();
    }

    /**
     * Mark the specified task as done and inform the user.
     *
     * @param ui User interface.
     * @param tasks  Stores the list of tasks.
     * @throws IOException if there is an I/O error.
     */
    public void caseDone(Ui ui, TaskList tasks) throws IOException {
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
    }

    /**
     * Delete the specified task and inform user.
     *
     * @param ui User interface.
     * @param tasks Stores the list of tasks.
     * @throws IOException if there is an I/O error.
     */
    public void caseDelete(Ui ui, TaskList tasks) throws IOException {
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
    }

    /**
     * Add a todo task and inform user.
     *
     * @param ui User interface.
     * @param tasks Stores the list of tasks.
     * @throws IOException if there is an I/O error.
     */
    public void caseTodo(Ui ui, TaskList tasks) throws IOException {
        ui.printLine();
        try {
            st.nextToken();
            Task newTask = new Task(curr.substring(4));
            tasks.add(newTask);
            ui.add(newTask, tasks.list.size());
            ui.printLine();
        } catch (NoSuchElementException e1) {
            ui.exp("OOPS!!! The description of a todo cannot be empty.");
        }
    }

    /**
     * Add a deadline task and inform user.
     *
     * @param ui User interface.
     * @param tasks Stores the list of tasks.
     * @throws IOException if there is an I/O error.
     */
    public void caseDeadline(Ui ui, TaskList tasks) throws IOException {
        try {
            st.nextToken();
            int endIdx = curr.indexOf(" /by ");
            if (endIdx == -1) {
                ui.exp("OOPS!!! The time cannot be empty.");
            } else {
                Task newTask = new Deadline(curr.substring(8, endIdx),
                        " (by:" + curr.substring(endIdx + 4) + ")");
                tasks.add(newTask);
                ui.add(newTask, tasks.list.size());
            }
        } catch (NoSuchElementException e1) {
            ui.exp("OOPS!!! The description of a deadline cannot be empty.");
        }
    }

    /**
     * Add an event task and inform user.
     *
     * @param ui User interface.
     * @param tasks Stores the list of tasks.
     * @throws IOException if there is an I/O error.
     */
    public void caseEvent(Ui ui, TaskList tasks) throws IOException {
        try {
            st.nextToken();
            int endIdx = curr.indexOf(" /at ");
            if (endIdx == -1) {
                ui.exp("OOPS!!! The venue cannot be empty.");
            } else {
                Task newTask = new Event(curr.substring(5, endIdx),
                        " (by:" + curr.substring(endIdx + 4) + ")");
                tasks.add(newTask);
                ui.add(newTask, tasks.list.size());
            }
        } catch (NoSuchElementException e1) {
            ui.exp("OOPS!!! The description of an event cannot be empty.");
        }
    }

    /**
     * Search for tasks that contains the keyword and inform user.
     *
     * @param ui User interface.
     * @param tasks Stores the list of tasks.
     * @throws IOException if there is an I/O error.
     */
    public void caseFind(Ui ui, TaskList tasks) throws IOException {
        try {
            String keyword = st.nextToken();
            ui.search();
            tasks.find(keyword, out);
            out.write("\n");
            ui.printLine();
        } catch (NoSuchElementException e1) {
            ui.exp("OOPS!!! The description of find cannot be empty.");
        }
    }

    /**
     * Call DukeException.
     *
     * @param ui User interface.
     * @throws IOException if there is an I/O error.
     */
    public void caseException(Ui ui)throws IOException {
        ui.exp("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }
}
