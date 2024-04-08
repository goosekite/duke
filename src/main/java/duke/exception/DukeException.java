package exception;


/**
 * Explains to user why error happened and how to resolve it
 */
public class DukeException extends Exception{

    /** Detects an exception has been raised and override it */
    public DukeException(String message) {
        super(message);
    }

    public static void getError(DukeException exception) {
        System.out.println(exception.getErrorMessage());
    }

    /** Apologises for mishap first */
    public String getErrorMessage() {
        return ("My apologies. " + getMessage());
    }

    /** Expects a number instead of text */
    public static DukeException expectIntegerButInputIsString(String keyword) {
        return new DukeException("Please enter a number instead of a text.\nFor example: " + keyword + " 1");
    }

    /**
     *  Expects a number after the [mark] or [delete] keyword
     *  @param keyword [mark] or [delete]
     */
    public static DukeException arrayOutOfBounds(String keyword){
        return new DukeException("No such task number exists, Please enter a number after your keyword.\n" +
                "For example: " + keyword + " 1");
    }

    /**
     * respects command syntax but no such file found in database
     * @param keyword [mark] or [delete]
     */
    public static DukeException indexOutOfBounds(String keyword){
        return new DukeException("There is no such task to " + keyword);
    }

    /** Activates when Duke fails to find path for accessing database */
    public static DukeException filePathDoesNotExist(){
        return new DukeException("Sorry, the file path is not initialised. Storage loading failed. Creating empty list.. ");
    }
}



