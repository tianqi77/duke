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
        String root = System.getProperty("user.dir");
        //new Duke(root + "/../../../data/duke.txt").run();
        new Duke(root + "/data/duke.txt").run();
    }
}
