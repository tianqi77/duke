import java.io.BufferedWriter;

public class Task {
    public String type = "[T]";
    public String status = "[" + "\u2718" +"]" ;
    public String name;
    public Task(String x){
        this.name = x;
    }
    public void print(){
        System.out.print(this.type + this.status + this.name + "\n");
    }
    public void done(){
        this.status ="[" + "\u2713" +"]" ;
        System.out.print("Nice! I've marked this task as done:\n");
        this.print();
    }
    public void addTask(BufferedWriter out, int number) throws Exception {
        out.write("Got it. I've added this task:\n");
        this.print();
        if(number > 1) {
            out.write("Now you have " + String.valueOf(number) + " tasks in the list.\n");
        }else{
            out.write("Now you have 1 task in the list.\n");
        }
    }
}
