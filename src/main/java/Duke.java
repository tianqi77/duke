import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Duke {
    public static void main(String[] args) throws Exception{
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                java.io.FileDescriptor.out)));
        out.write("Hello from\n" + logo);
        out.write("Hello! I'm Duke\n" + "What can I do for you?\n");
        out.flush();
        StringTokenizer st  = new StringTokenizer(br.readLine());
        String cmd = st.nextToken();
        ArrayList<Task> tasks = new ArrayList<>(100);
        while(!cmd.equals("bye")) {
            if(cmd.equals("list")){
                out.write("Here are the tasks in your list:\n");
                for(int i = 0; i < tasks.size(); i++){
                    out.write(String.valueOf(i+1) + "." +  tasks.get(i).status
                            + tasks.get(i).name + "\n");
                }
            }else if(cmd.equals("done")){
                int idx = Integer.parseInt(st.nextToken())-1;
                tasks.get(idx).done();
                out.write("Nice! I've marked this task as done:\n");
                out.write("  " + tasks.get(idx).status +  tasks.get(idx).name + "\n");
            }else {
                tasks.add(new Task(cmd));
                out.write("added:" + cmd + '\n');
            }
            out.flush();
            st = new StringTokenizer(br.readLine());
            cmd = st.nextToken();
        }
        out.write("Bye. Hope to see you again soon!\n");
        br.close();
        out.close();
    }
}

class Task{
    public String status = "[" + "\u2717" +"] " ;
    public String name;
    public Task(String x){
        this.name = x;
    }
    public void done(){
        this.status ="[" + "\u2713" +"] " ;
    }
}