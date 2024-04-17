package duke.task;

public class ToDo extends Task {

    /** constructor expects a description for ToDo Task */
    public ToDo(String description) {
        super(description);
    }

    /** override ToString method for ToDo Task format */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /** change ToDo format to user input */
    public String convertToCommand(){
        return description;
    }
}