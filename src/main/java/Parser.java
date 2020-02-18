import java.io.BufferedWriter;
import java.io.IOException;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Arrays;

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
     * @param ui    The user interface.
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

    public void caseDelete(Ui ui, TaskList tasks) throws IOException{
        ui.printLine();
        try {
            String index = st.nextToken();
            int[] deleteList = new int[1000];
            int numDelete = 0;
            while (true) {
                try {
                    int endIdx = index.indexOf(';');
                    if (endIdx == -1) {
                        deleteList[numDelete] = Integer.parseInt(index) - 1;
                        break;
                    } else {
                        deleteList[numDelete] = Integer.parseInt(index.substring(0, endIdx)) - 1;
                    }
                    numDelete++;
                } catch (NoSuchElementException e) {
                    break;
                } catch (NumberFormatException e) {
                    new DukeException("Please enter a valid number.", out);
                }
                index = st.nextToken();
            }
            if (numDelete == 0) {
                ui.exp("Numbers entered are all invalid. No tasks are deleted.");
                return;
            }
            Arrays.sort(deleteList, 0, numDelete);
            if(deleteList[0] >= tasks.list.size()) {
                ui.exp("Numbers entered are all larger than size of task list. No tasks are deleted.");
                return;
            }
            ui.delete();
            for (int i = 0; i < numDelete; i++ ) {
                try {
                    tasks.delete(deleteList[i]-i).print(out);
                } catch (IndexOutOfBoundsException e) {
                    new DukeException(String.valueOf(deleteList[i]) + "is larger than size of task list", out);
                }
            }
            out.flush();
            ui.currTask(tasks.list.size());
            ui.printLine();
        } catch (NoSuchElementException e1) {
            ui.exp("Please enter at least one number");
        }
    }

    public void caseDone(Ui ui, TaskList tasks) throws IOException {
        ui.printLine();
        try {
            String index = st.nextToken();
            int endIdx = index.indexOf(';');
            int idx;
            if (endIdx == -1) {
                idx = Integer.parseInt(index) - 1;
            } else {
                idx = Integer.parseInt(index.substring(0, endIdx)) - 1;
            }
            tasks.done(idx);
            ui.done(tasks.list.get(idx));
            while (true) {
                try {
                    index = st.nextToken();
                    endIdx = index.indexOf(';');
                    if (endIdx == -1) {
                        idx = Integer.parseInt(index) - 1;
                    } else {
                        idx = Integer.parseInt(index.substring(0, endIdx)) - 1;
                    }
                    tasks.done(idx);
                    tasks.list.get(idx).print(out);
                    out.write("\n");
                } catch (NoSuchElementException e) {
                    break;
                }
            }
            out.flush();
            ui.printLine();
        } catch (NoSuchElementException e1) {
            ui.exp("Please enter at least one number");
        } catch (NumberFormatException e2) {
            ui.exp("Please enter a valid number to be done");
        } catch (IndexOutOfBoundsException e3) {
            ui.exp("Number entered is larger than size of task list");
        }
    }

    public void caseTodo(Ui ui, TaskList tasks) throws IOException {
        Task newTask;
        ui.printLine();
        try {
            st.nextToken();
            ui.add();
            curr = curr.substring(4);
            while (true) {
                int endIdx = curr.indexOf("; ");
                if (endIdx == -1) {
                    newTask = new Task(curr);
                    tasks.add(newTask);
                    newTask.print(out);
                    break;
                }
                newTask = new Task(curr.substring(0, endIdx));
                tasks.add(newTask);
                newTask.print(out);
                try {
                    curr = curr.substring(endIdx + 1);
                    st = new StringTokenizer(curr);
                    st.nextToken();
                } catch (IndexOutOfBoundsException | NoSuchElementException e) {
                    break;
                }
            }
            ui.currTask(tasks.list.size());
            out.write("\n");
            ui.printLine();
        } catch (NoSuchElementException e) {
            ui.exp("OOPS!!! The description of a todo cannot be empty.");
        }
    }

    public void caseDeadline(Ui ui, TaskList tasks) throws IOException{
        Task newTask;
        ui.printLine();
        try {
            String test = st.nextToken();
            ui.add();
            curr = curr.substring(8);
            while (true) {
                int timeIdx = curr.indexOf(" /by ");
                int endIdx = curr.indexOf("; ");
                if (test.equals("/by")) {
                    new DukeException("OOPS!!! The task cannot be empty.", out);
                } else if ((timeIdx == -1) || ((timeIdx > endIdx) && (endIdx !=-1))) {
                    new DukeException("OOPS!!! The time cannot be empty.", out);
                } else {
                    if (endIdx == -1) {
                        newTask = new Deadline(curr.substring(0, timeIdx), " (by:" + curr.substring(timeIdx + 4) + ")");
                        tasks.add(newTask);
                        newTask.print(out);
                        break;
                    }
                    newTask = new Deadline(curr.substring(0, timeIdx), " (by:" + curr.substring(timeIdx + 4, endIdx) + ")");
                    tasks.add(newTask);
                    newTask.print(out);
                }
                //prepare for next task
                if (endIdx != -1) {
                    try {
                        curr = curr.substring(endIdx + 1);
                        st = new StringTokenizer(curr);
                        test = st.nextToken();
                    } catch (IndexOutOfBoundsException | NoSuchElementException e) {
                        break;
                    }
                }
            }
            ui.currTask(tasks.list.size());
            out.write("\n");
            ui.printLine();
        } catch (NoSuchElementException e) {
            ui.exp("OOPS!!! The description of a deadline cannot be empty.");
        }
    }

    public void caseEvent(Ui ui, TaskList tasks) throws IOException {
        Task newTask;
        ui.printLine();
        try {
            String test = st.nextToken();
            ui.add();
            curr = curr.substring(5);
            while (true) {
                int venueIdx = curr.indexOf(" /at ");
                int endIdx = curr.indexOf("; ");
                if (test.equals("/at")) {
                    new DukeException("OOPS!!! The task cannot be empty.", out);
                } else if ((venueIdx == -1) || ((venueIdx > endIdx) && (endIdx !=-1))) {
                    new DukeException("OOPS!!! The venue cannot be empty.", out);
                } else {
                    if (endIdx == -1) {
                        newTask = new Event(curr.substring(0, venueIdx), " (at:" + curr.substring(venueIdx + 4) + ")");
                        tasks.add(newTask);
                        newTask.print(out);
                        break;
                    }
                    newTask = new Event(curr.substring(0, venueIdx), " (at:" + curr.substring(venueIdx + 4, endIdx) + ")");
                    tasks.add(newTask);
                    newTask.print(out);
                }
                //prepare for next task
                if (endIdx != -1) {
                    try {
                        curr = curr.substring(endIdx + 1);
                        st = new StringTokenizer(curr);
                        test = st.nextToken();
                    } catch (IndexOutOfBoundsException | NoSuchElementException e) {
                        break;
                    }
                }
            }
            ui.currTask(tasks.list.size());
            out.write("\n");
            ui.printLine();
        } catch (NoSuchElementException e) {
            ui.exp("OOPS!!! The description of an event cannot be empty.");
        }
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
