


public class Ui{
    String logo = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";
    public String line = "_____________________________________________________\n";
    public Ui(){
    }

    public void start(){
        System.out.print("Hello from\n" + logo);
        System.out.print("Hello! I'm Duke\n" + "What can I do for you?\n");
        System.out.println();
        printLine();
    }
    public void printLine(){
        System.out.print(line);
    }
    public void done(Task t){
        System.out.print("Nice! I've marked this task as done:\n");
        t.print();
        System.out.println();
    }
    public void delete(Task t, int n){
        System.out.print("Noted. I've removed this task: \n");
        t.print();
        currTask(n);
        System.out.println();
    }
    public void list(){
        System.out.print("Here are the tasks in your list:\n");
    }
    public void add(Task t, int n){
        System.out.print("Got it. I've added this task: \n");
        t.print();
        currTask(n);
        System.out.println();
    }
    public void bye(){
        printLine();
        System.out.print("Bye. Hope to see you again soon!\n");
        System.out.println();
        printLine();;
    }
    public void currTask(int n){
        System.out.print("Now you have " + n + " tasks in the list. \n");
    }
    public void exp(String s){
        new DukeException(s);
    }
    public void showLoadingError(){
        exp("Sorry, there is a loading error:(");
    }
}