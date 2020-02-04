import java.io.BufferedWriter;
import java.util.ArrayList;
public class TaskList{
    public ArrayList<Task> list;
    public TaskList(ArrayList<Task> l){
        this.list = l;
    }
    public void add(Task t){
        list.add(t);
    }
    public Task delete(int idx){
        return list.remove(idx);
    }
    public void done(int idx){
        list.get(idx).status = "[" + "\u2713" +"]" ;
    }
    public void print(){
        for(int i = 0; i < list.size(); i++){
            System.out.print(String.valueOf(i + 1) + ".");
            list.get(i).print();
        }
    }
}