import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
public class Duke {
    public static void main(String[] args) throws Exception {
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
                                try {
                                    currTask = new Deadline(curr.substring(8, endIdx), " (by:" + curr.substring(endIdx + 4) + ")");
                                    tasks.add(currTask);
                                    currTask.addTask(out, tasks.size());
                                }catch(DateTimeParseException e){
                                    new DukeException(out, "OOPS!!! Try to type date in the format of yyyy-mm-dd.");
                                }
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
                                currTask = new Event(curr.substring(5, endIdx), " (at:" + curr.substring(endIdx + 4) + ")");
                                tasks.add(currTask);
                                currTask.addTask(out, tasks.size());
                            }
                        } catch (NoSuchElementException e1) {
                            new DukeException(out, "OOPS!!! The description of an event cannot be empty.");
                        }
                        break;
                }
            } catch (IllegalArgumentException e) {
                new DukeException(out, "OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
            out.flush();
            curr = br.readLine();
            st = new StringTokenizer(curr);
        }
    }
}

class Task{
    public String type = "[T]";
    public String status = "[" + "\u2718" +"]" ;
    public String name;
    public Task(String x){
        this.name = x;
    }
    public void print(BufferedWriter out) throws Exception{
        out.write(this.type + this.status + this.name + "\n");
    }
    public void done(BufferedWriter out) throws Exception{
        this.status ="[" + "\u2713" +"]" ;
        out.write("Nice! I've marked this task as done:\n");
        this.print(out);
    }
    public void addTask(BufferedWriter out, int number) throws Exception {
        out.write("Got it. I've added this task:\n");
        this.print(out);
        if(number > 1) {
            out.write("Now you have " + String.valueOf(number) + " tasks in the list.\n");
        }else{
            out.write("Now you have 1 task in the list.\n");
        }
    }
}
class Deadline extends Task{
    public LocalDate ddl;
    public Deadline (String x, String d){
        super(x);
        this.type = "[D]";
        int len = d.length();
        this.ddl = LocalDate.parse(d.substring(6, len-1));
    }
    public String convert(LocalDate date){
        int year = date.getYear();
        int day = date.getDayOfMonth();
        Month mon = date.getMonth();
        return mon.toString().substring(0,3) + " " + String.valueOf(day) + " " + String.valueOf(year);
    }
    @Override
    public void print(BufferedWriter out) throws Exception{
        out.write(this.type + this.status + this.name + " (by: " + convert(this.ddl)+ ")\n");
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
    public void print(BufferedWriter out) throws Exception{
        out.write(this.type + this.status + this.name + this.venue + "\n");
    }
}
class DukeException{
    public DukeException(BufferedWriter out, String msg) throws Exception{
        String errMsg = "\u2639"+ " " + msg+ "\n";
        out.write(errMsg);
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
