public class Event extends Task{
    public String venue;
    public Event(String x, String v) {
        super(x);
        this.type = "[E]";
        this.venue = v;
    }
    @Override
    public String readyToPrint() {
        return super.readyToPrint() + this.venue;
    }
    @Override
    public String readyToSave(){
        return super.readyToSave() + " |" + this.venue.substring(5,this.venue.length()-1);
    }
}
