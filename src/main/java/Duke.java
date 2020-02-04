import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.nio.charset.StandardCharsets;

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
        ArrayList<Task> tasks = new ArrayList<>(100);
        Task currTask;
        load(tasks);
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
                        save(tasks);
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
                        save(tasks);
                        break;
                    case todo:
                        try {
                            st.nextToken();
                            String sub = curr.substring(4);
                            currTask = new Task(sub);
                            tasks.add(currTask);
                            currTask.addTask(out, tasks.size());
                            //save(tasks);
                        } catch (Exception e) {
                            new DukeException(out, "OOPS!!! The description of a todo cannot be empty.");
                        }
                        save(tasks);
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
                        save(tasks);
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
                        save(tasks);
                        break;
                }
            } catch (IllegalArgumentException  e) {
                new DukeException(out, "OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
            out.flush();
            curr = br.readLine();
            st = new StringTokenizer(curr);
        }
    }
    public static void save(ArrayList<Task> tasks) throws Exception{
        BufferedWriter out  = new BufferedWriter(new FileWriter("..\\..\\..\\data\\duke.txt"));
        for(int i = 0; i < tasks.size(); i++){
            if(tasks.get(i) instanceof Deadline){
                out.write("D | ");
            }else if(tasks.get(i) instanceof Event){
                out.write("E | ");
            }else{
                out.write("T | ");
            }
            if(tasks.get(i).status.equals("[" + "\u2713" + "]")){
                out.write("1 |");
            }else{
                out.write("0 |");
            }
            out.write(tasks.get(i).name);
            if(tasks.get(i) instanceof Deadline){
                out.write(" |");
                int len = ((Deadline) tasks.get(i)).ddl.length();
                out.write(((Deadline) tasks.get(i)).ddl.substring(5, len-1));
            }else if(tasks.get(i) instanceof Event){
                out.write(" |");
                int len = ((Event) tasks.get(i)).venue.length();
                out.write(((Event) tasks.get(i)).venue.substring(5, len-1));
            }
            out.write("\n");
            out.flush();
        }
        out.close();
    }
    public static void load(ArrayList<Task> tasks) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader("..\\..\\..\\data\\duke.txt"));
        String curr = br.readLine();
        while(curr!=null){
            StringTokenizer st = new StringTokenizer(curr);
            String cmd = st.nextToken();
            st.nextToken();
            String status = st.nextToken();
            if(cmd.equals("T")) {
                Task newTask = new Task(curr.substring(7));
                if(status.equals("1")) {
                    newTask.status = "[" + "\u2713" + "]";
                }
                tasks.add(newTask);
            }else if(cmd.equals("D")){
                int idx = curr.substring(7).indexOf("|")+7;
                Deadline newTask = new Deadline(curr.substring(7,idx-1), " (by:" + curr.substring(idx+1) + ")");
                if(status.equals("1")) {
                    newTask.status = "[" + "\u2713" + "]";
                }
                tasks.add(newTask);
            }else if(cmd.equals("E")){
                int idx = curr.substring(7).indexOf("|")+7;
                Event newTask = new Event(curr.substring(7,idx-1), " (at:" + curr.substring(idx+1) + ")");
                if(status.equals("1")) {
                    newTask.status = "[" + "\u2713" + "]";
                }
                tasks.add(newTask);
            }
            curr = br.readLine();
        }
        br.close();
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
    public String ddl;
    public Deadline (String x, String d){
        super(x);
        this.type = "[D]";
        this.ddl = d;
    }
    @Override
    public void print(BufferedWriter out) throws Exception{
        out.write(this.type + this.status + this.name + this.ddl + "\n");
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