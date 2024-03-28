package Exception;

/**
 * Explains why there is error to user
 */
public class DukeException extends Exception{
    public DukeException(String message) {
        super(message);
    }

    /**
     *  Step 1: Find which error was triggered
     */
    public static void getError(DukeException exception) {
        System.out.println(exception.getErrorMessage());
    }

    /**
     * Step 2: Apologise for mishap
     */
    public String getErrorMessage() {
        return ("My apologies. " + getMessage());
    }

    /**
     *  Step 3: Inform user why error happened
     */
    public static DukeException expectIntegerButInputIsString() {
        return new DukeException("Please enter a number instead of a text.");
    }
    public static DukeException arrayOutOfBounds(){
        return new DukeException("No such task number exists\nPlease enter a number after your keyword");
    }
}



