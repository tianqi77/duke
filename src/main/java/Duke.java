import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.nio.charset.StandardCharsets;
public class Duke {
    public static void main(String[] args) throws Exception{
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
        StringTokenizer st  = new StringTokenizer(curr);
        String cmd = st.nextToken();
        ArrayList<Task> tasks = new ArrayList<>(100);
        Task currTask = null;
        while(!cmd.equals("bye")) {
            if(cmd.equals("list")){
                out.write("Here are the tasks in your list:\n");
                for(int i = 0; i < tasks.size(); i++){
                    out.write(String.valueOf(i+1) + ".");
                    tasks.get(i).print(out);
                }
            }else if(cmd.equals("done")){
                int idx = Integer.parseInt(st.nextToken())-1;
                tasks.get(idx).done(out);
            }else {
                if (cmd.equals("todo")) {
                    String sub = curr.substring(4);
                    if(sub.equals("")){
                        DukeException exp = new DukeException(out,"OOPS!!! The description of a todo cannot be empty.");
                    }else {
                        currTask = new Task(sub);
                        tasks.add(currTask);
                        currTask.addTask(out, tasks.size());
                    }
                } else if (cmd.equals("deadline")) {
                    if(curr.substring(8).equals("")){
                        DukeException exp = new DukeException(out,"OOPS!!! The description of a deadline cannot be empty.");
                    }else {
                        int endIdx = curr.indexOf(" /by");
                        if (endIdx == -1) {
                            DukeException exp = new DukeException(out,"OOPS!!! The deadline cannot be empty.");
                        } else {
                            currTask = new Deadline(curr.substring(8, endIdx), " (by:" + curr.substring(endIdx + 4) + ")");
                            tasks.add(currTask);
                            currTask.addTask(out, tasks.size());
                        }
                    }
                } else if (cmd.equals("event")) {
                    if(curr.substring(5).equals("")){
                        DukeException exp = new DukeException(out,"OOPS!!! The description of an event cannot be empty.");
                    }else {
                        int endIdx = curr.indexOf(" /at");
                        if (endIdx == -1) {
                            DukeException exp = new DukeException(out,"OOPS!!! The venue cannot be empty.");
                        } else {
                            currTask = new Event(curr.substring(5, endIdx), " (at:" + curr.substring(endIdx + 4) + ")");
                            tasks.add(currTask);
                            currTask.addTask(out, tasks.size());
                        }
                    }
                }else {
                    DukeException exp = new DukeException(out, "OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            }
            out.flush();
            curr = br.readLine();
            st = new StringTokenizer(curr);
            cmd = st.nextToken();
        }
        out.write("Bye. Hope to see you again soon!\n");
        br.close();
        out.close();
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