import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;

public class Duke {
    public static void main(String[] args) throws Exception{
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.print("Hello! I'm Duke\n" + "What can I do for you?\n");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                java.io.FileDescriptor.out)));
        String cmd = br.readLine();
        while(!cmd.equals("bye")) {
            out.write(cmd + '\n');
            out.flush();
            cmd = br.readLine();
        }
        out.write("Bye. Hope to see you again soon!\n");
        br.close();
        out.close();
    }
}

