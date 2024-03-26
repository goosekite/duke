import Task.Task;

public class Deadline extends Task {
    protected String by;

    public Deadline() {

    }

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public Deadline(String userInput) {
        this.by = "tomorrow for sure";
    }


    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
