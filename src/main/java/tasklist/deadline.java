package tasklist;


public class deadline extends task {
    protected String by;

    public deadline() {
    }

    public deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    public String convertToCommand(){
        return description + " by " + by;
    }

}
