public class Deadline extends Task {
    public String ddl;
    public Deadline (String x, String d){
        super(x);
        this.type = "[D]";
        this.ddl = d;
    }
    @Override
    public String readyToPrint(){
        return super.readyToPrint() + this.ddl;
    }
    @Override
    public String readyToSave(){
        return super.readyToSave() + " |" + this.ddl.substring(5,this.ddl.length()-1);
    }
}
