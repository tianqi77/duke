import java.io.BufferedWriter;

public class Task {
    public String type = "[T]";
    public String status = "[" + "\u2718" +"]" ;
    public String name;
    public Task(String x){
        this.name = x;
    }
    public String readyToPrint(){
        return this.type + this.status + this.name;
    }
    public String readyToSave(){
        String done = "0";
        if(status.equals("[" + "\u2713" +"]")){
            done = "1";
        }
        return this.type.charAt(1) + " | " + done +" |" + this.name;
    }
    public void print(){
        System.out.print(readyToPrint() + "\n");
    }
}
