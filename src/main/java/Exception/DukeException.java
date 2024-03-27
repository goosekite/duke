package Exception;

/**
 * A list of exceptions for Duke.
 * Errors tend to happen when dealing with accessing index in array
 */
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
        return new DukeException("Please put a number instead. For example:");
    }
    public static DukeException arrayOutOfBounds(){ return new DukeException("Please put a number after your keyword. For example:");}
}



