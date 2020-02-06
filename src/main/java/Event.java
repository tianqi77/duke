public class Event extends Task{
    public String venue;
    public Event(String x, String v) {
        super(x);
        this.type = "[E]";
        this.venue = v;
    }
    @Override
    public void print() {
        System.out.print(this.type + this.status + this.name + this.venue + "\n");
    }
}
