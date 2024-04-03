package tasklist;

public class todo extends task {
    public todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    public String convertToCommand(){
        return description;
    }
}