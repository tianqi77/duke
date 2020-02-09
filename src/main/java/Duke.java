import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import java.io.IOException;

import java.nio.charset.StandardCharsets;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * The main class that runs the duke project.
 */
public class Duke extends Application {
    private Storage storage;
    private Ui ui;
    private TaskList tasks;
    private BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
            java.io.FileDescriptor.out), StandardCharsets.UTF_8));

    /*public Duke() {

    }*/
    /**
     * Constructor of an instance of Duke class.
     * It creates instances of Ui, Storage, and TaskList for processing.
     *
     * @param filePath Location of the file that stores the tasks.
     */
    public Duke(String filePath) {
        ui = new Ui(out);
        try {
            storage = new Storage(filePath);
            tasks = storage.load();
        } catch (IOException e) {
            try {
                ui.showLoadingError();
            } catch (IOException e1) {
                ///
            }
            tasks = new TaskList();
        }
    }

    /**
     * The driver method that runs the whole duke project.
     *
     * @throws IOException .
     */
    public void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ui.start();
        boolean hasNext = true;
        while (hasNext) {
            Parser ps = new Parser(br.readLine(), out);
            hasNext = ps.canContinue(ui, tasks);
            storage.save(tasks);
        }
        br.close();
        out.close();
    }

    @Override
    public void start(Stage stage) {
        Label helloWorld = new Label("Hello World!"); // Creating a new Label control
        Scene scene = new Scene(helloWorld); // Setting the scene to be our Label

        stage.setScene(scene); // Setting the stage to show our screen
        stage.show(); // Render the stage.
    }

    public static void main(String[] args) throws Exception {
        new Duke("data/duke.txt").run();
    }
}
