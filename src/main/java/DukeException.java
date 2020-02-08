import java.io.BufferedWriter;
import java.io.IOException;
/**
 * Handles invalid input by user and prints error message
 */
public class DukeException{

    /**
     * Prints error message
     * @param msg error message
     * @throws IOException
     */
    public DukeException(String msg, BufferedWriter out) throws IOException{
        String errMsg = "\u2639"+ " " + msg+ "\n";
        out.write(errMsg);
    }
}
