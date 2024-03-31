package Exception;

/**
 * Explains why there is error to user
 */
public class DukeException extends Exception{

    /**
     *  Step 1: Find which error was triggered
     */
    public DukeException(String message) {
        super(message);
    }

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
     *  expect
     */
    public static DukeException expectIntegerButInputIsString(String keyword) {
        return new DukeException("Please enter a number instead of a text.\nFor example: " + keyword + " 1");
    }
    public static DukeException arrayOutOfBounds(String keyword){
        return new DukeException("No such task number exists, Please enter a number after your keyword.\n" +
                "For example: " + keyword + " 1");
    }

    public static DukeException indexOutOfBounds(String keyword){
        return new DukeException("There is no such task to " + keyword);
    }

    public static DukeException filePathDoesNotExist(){
        return new DukeException("Sorry, the file path is not initialised. Storage loading failed. Creating empty list.. ");
    }
}



