package duke.task;


public class Deadline extends Task {
    protected String by;

    public Deadline() {
    }

    /** expects a description and dueDate "by" */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /** override ToString method for deadline format */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    /** change deadline format to user input */
    public String convertToCommand(){
        return description + " by " + by;
    }

}
