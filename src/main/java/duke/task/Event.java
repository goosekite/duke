package duke.task;

public class Event extends Task {
    protected String start;
    protected String end;

    /** expects a description, start and end date */
    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    /** override ToString method for event format */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + " )";
    }

    /** change event format to user input */
    public String convertToCommand(){
        return description + " from " + start + " to " + end;
    }
}

