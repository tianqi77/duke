public class Deadline extends Task {
    public String ddl;
    public Deadline (String x, String d){
        super(x);
        this.type = "[D]";
        this.ddl = d;
    }
    @Override
    public void print(){
        System.out.print(this.type + this.status + this.name + this.ddl + "\n");
    }
}
