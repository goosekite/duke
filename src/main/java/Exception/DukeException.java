package Exception;

public class DukeException extends Exception{
    public DukeException(String message) {
        super(message);
    }

    public String getErrorMessage() {
        return ("I'm so sorry, " + getMessage());
    }

    public static void getError(DukeException exception) {
        System.out.println(exception.getErrorMessage());
    }
    public static DukeException invalidTaskNumber() {
        return new DukeException("Task number is wrong");
    }
    public static DukeException expectIntegerButInputIsString() {
        return new DukeException("Expected number ");
    }
    public static DukeException arrayOutOfBounds(){ return new DukeException("Please put a number after your keyword. For example:");}
}



