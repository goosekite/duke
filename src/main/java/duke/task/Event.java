package duke.task;

public class Event extends Task {
    protected String start;
    protected String end;

    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + " )";
    }

    public String convertToCommand(){
        return description + " from " + start + " to " + end;
    }
}

