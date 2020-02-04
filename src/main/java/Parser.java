
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.nio.charset.StandardCharsets;

public class Parser{
    public Command cmd;
    public String curr;
    public StringTokenizer st;
    public Parser(String s){
        curr = s;
        st = new StringTokenizer(curr);
        cmd = Command.valueOf(st.nextToken());
    }
    public boolean process(Ui ui, TaskList tasks) {
        Task newTask;
        try {
            switch (cmd) {
                case bye:
                    ui.bye();
                    return false;
                case list:
                    ui.printLine();
                    ui.list();
                    tasks.print();
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
                            ui.exp("OOPS!!! The deadline cannot be empty.");
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
        } catch (Exception E) {
            ui.exp("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
        return true;
    }
}