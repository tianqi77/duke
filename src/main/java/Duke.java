import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;
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
        String cmd = br.readLine();
        ArrayList<String> strings = new ArrayList<>(100);
        while(!cmd.equals("bye")) {
            if(cmd.equals("list")){
                for(int i = 0; i < strings.size(); i++){
                    out.write(String.valueOf(i+1) + ". " + strings.get(i) + "\n");
                }
            }else {
                strings.add(cmd);
                out.write("added:" + cmd + '\n');
            }
            out.flush();
            cmd = br.readLine();
        }
        out.write("Bye. Hope to see you again soon!\n");
        br.close();
        out.close();
    }
}

