import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.nio.charset.StandardCharsets;
public class Duke {
    private Storage storage;
    private Ui ui;
    private TaskList tasks;

    public Duke(String filePath) throws Exception{
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = storage.load();
        } catch (FileNotFoundException e) {
            ui.showLoadingError();
            tasks = new TaskList(new ArrayList<Task>());
        }
    }

    public void run() throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ui.start();
        boolean a = true;
        while (a) {
            Parser ps = new Parser(br.readLine());
            a = ps.process(ui, tasks);
            storage.save(tasks);
        }
    }

    public static void main(String[] args) throws Exception{
        new Duke("..\\..\\..\\data\\duke.txt").run();
    }
}






 /*   public static void main(String[] args) throws Exception {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                java.io.FileDescriptor.out), StandardCharsets.UTF_8));
        out.write("Hello from\n" + logo);
        out.write("Hello! I'm Duke\n" + "What can I do for you?\n");
        out.flush();
        String curr = br.readLine();
        StringTokenizer st = new StringTokenizer(curr);
        //String cmd = st.nextToken();
        ArrayList<Task> tasks = new ArrayList<>(100);
        Task currTask = null;
        //Command cmd = Command.valueOf(st.nextToken());
        while (true) {
            try {
                Command cmd = Command.valueOf(st.nextToken());
                switch (cmd) {
                    case bye:
                        out.write("Bye. Hope to see you again soon!\n");
                        br.close();
                        out.close();
                        return;
                    case list:
                        out.write("Here are the tasks in your list:\n");
                        for (int i = 0; i < tasks.size(); i++) {
                            out.write(String.valueOf(i + 1) + ".");
                            tasks.get(i).print(out);
                        }
                        break;
                    case done:
                        int idx = Integer.parseInt(st.nextToken()) - 1;
                        tasks.get(idx).done(out);
                        break;
                    case delete:
                        int index = Integer.parseInt(st.nextToken()) - 1;
                        out.write("Noted. I've removed this task:\n");
                        tasks.remove(index).print(out);
                        int numTask = tasks.size();
                        if (numTask == 1) {
                            out.write("Now you have 1 task in the list.\n");
                        } else {
                            out.write("Now you have" + String.valueOf(numTask) + "tasks in the list.\n");
                        }
                        break;
                    case todo:
                        try {
                            st.nextToken();
                            String sub = curr.substring(4);
                            currTask = new Task(sub);
                            tasks.add(currTask);
                            currTask.addTask(out, tasks.size());
                        } catch (Exception e) {
                            new DukeException(out, "OOPS!!! The description of a todo cannot be empty.");
                        }
                        break;
                    case deadline:
                        try {
                            st.nextToken();
                            int endIdx = curr.indexOf(" /by ");
                            if (endIdx == -1) {
                                new DukeException(out, "OOPS!!! The deadline cannot be empty.");
                            } else {
                                currTask = new Deadline(curr.substring(8, endIdx), " (by:" + curr.substring(endIdx + 4) + ")");
                                tasks.add(currTask);
                                currTask.addTask(out, tasks.size());
                            }
                        } catch (NoSuchElementException e1) {
                            new DukeException(out, "OOPS!!! The description of a deadline cannot be empty.");
                        }
                        break;
                    case event:
                        try {
                            st.nextToken();
                            int endIdx = curr.indexOf(" /at ");
                            if (endIdx == -1) {
                                new DukeException(out, "OOPS!!! The venue cannot be empty.");
                            } else {
                                currTask = new Deadline(curr.substring(8, endIdx), " (by:" + curr.substring(endIdx + 4) + ")");
                                tasks.add(currTask);
                                currTask.addTask(out, tasks.size());
                            }
                        } catch (NoSuchElementException e1) {
                            new DukeException(out, "OOPS!!! The description of an event cannot be empty.");
                        }
                        break;
                }
            } catch (Exception e) {
                new DukeException(out, "OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
            out.flush();
            curr = br.readLine();
            st = new StringTokenizer(curr);
        }
    }
}*/

class Task{
    public String type = "[T]";
    public String status = "[" + "\u2718" +"]" ;
    public String name;
    public Task(String x){
        this.name = x;
    }
    public void print(){
        System.out.print(this.type + this.status + this.name + "\n");
    }
    public void done(){
        this.status ="[" + "\u2713" +"]" ;
        System.out.print("Nice! I've marked this task as done:\n");
        this.print();
    }
    public void addTask(BufferedWriter out, int number) throws Exception {
        out.write("Got it. I've added this task:\n");
        this.print();
        if(number > 1) {
            out.write("Now you have " + String.valueOf(number) + " tasks in the list.\n");
        }else{
            out.write("Now you have 1 task in the list.\n");
        }
    }
}
class Deadline extends Task{
    public String ddl;
    public Deadline (String x, String d){
        super(x);
        this.type = "[D]";
        this.ddl = d;
    }
    @Override
    public void print(){
        System.out.print(this.type + this.status + this.name + this.ddl + "\n");
    }

}
class Event extends Task{
    public String venue;
    public Event(String x, String v) {
        super(x);
        this.type = "[E]";
        this.venue = v;
    }
    @Override
    public void print() {
        System.out.print(this.type + this.status + this.name + this.venue + "\n");
    }
}
class DukeException{
    public DukeException(String msg){
        String errMsg = "\u2639"+ " " + msg+ "\n";
        System.out.print(errMsg);
    }
}
enum Command{
    bye,
    list,
    done,
    delete,
    todo,
    deadline,
    event,
}