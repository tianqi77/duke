import java.io.BufferedWriter;
import java.io.IOException;


/**
 * Stores the content, type and status of a task; The default type is "Todo";
 * The default status is undone.
 */
public class Task {
    public String type = "[T]";
    public String status = "[" + "\u2718" +"]" ;
    public String name;

    /**
     * Constructor of an instance of Task
     * @param x the content of the task
     */
    public Task(String x){
        this.name = x;
    }

    /**
     * Generate the format for the task to be printed
     * @return a string of the format
     */
    public String readyToPrint(){
        return this.type + this.status + this.name;
    }

    /**
     * Generate the format for the task to be saved to the data file
     * @return a string of the format
     */
    public String readyToSave(){
        String done = "0";
        if(status.equals("[" + "\u2713" +"]")){
            done = "1";
        }
        return this.type.charAt(1) + " | " + done +" |" + this.name;
    }

    /**
     * Print out the task
     * @throws IOException
     */
    public void print(BufferedWriter out) throws IOException {
        out.write(readyToPrint() + "\n");
    }
}
