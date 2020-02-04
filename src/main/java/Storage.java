import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.NoSuchFileException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;
public class Storage {
    String location;
    BufferedWriter out;
    BufferedReader br;

    public Storage(String filepath) throws Exception {
        location = filepath;
        br = new BufferedReader(new FileReader(filepath));
    }

    public void save(TaskList tasks) throws Exception{
        out  = new BufferedWriter(new FileWriter(location));
        for(int i = 0; i < tasks.list.size(); i++){
            if(tasks.list.get(i) instanceof Deadline){
                out.write("D | ");
            }else if(tasks.list.get(i) instanceof Event){
                out.write("E | ");
            }else{
                out.write("T | ");
            }
            if(tasks.list.get(i).status.equals("[" + "\u2713" + "]")){
                out.write("1 |");
            }else{
                out.write("0 |");
            }
            out.write(tasks.list.get(i).name);
            if(tasks.list.get(i) instanceof Deadline){
                out.write(" |");
                int len = ((Deadline) tasks.list.get(i)).ddl.length();
                out.write(((Deadline) tasks.list.get(i)).ddl.substring(5, len-1));
            }else if(tasks.list.get(i) instanceof Event){
                out.write(" |");
                int len = ((Event) tasks.list.get(i)).venue.length();
                out.write(((Event) tasks.list.get(i)).venue.substring(5, len-1));
            }
            out.write("\n");
            out.flush();
        }
        out.close();
    }
    public TaskList load() throws Exception{
        TaskList tasks = new TaskList(new ArrayList<Task>());
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
                tasks.list.add(newTask);
            }else if(cmd.equals("D")){
                int idx = curr.substring(7).indexOf("|")+7;
                Deadline newTask = new Deadline(curr.substring(7,idx-1), " (by:" + curr.substring(idx+1) + ")");
                if(status.equals("1")) {
                    newTask.status = "[" + "\u2713" + "]";
                }
                tasks.list.add(newTask);
            }else if(cmd.equals("E")){
                int idx = curr.substring(7).indexOf("|")+7;
                Event newTask = new Event(curr.substring(7,idx-1), " (at:" + curr.substring(idx+1) + ")");
                if(status.equals("1")) {
                    newTask.status = "[" + "\u2713" + "]";
                }
                tasks.list.add(newTask);
            }
            curr = br.readLine();
        }
        br.close();
        return tasks;
    }

}
