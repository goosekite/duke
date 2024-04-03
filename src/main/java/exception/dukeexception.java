package exception;


/**
 * Explains to user why error happened and how to resolve it
 */
public class dukeexception extends Exception{

    /** Detects an exception has been raised and override it */
    public dukeexception(String message) {
        super(message);
    }

    public static void getError(dukeexception exception) {
        System.out.println(exception.getErrorMessage());
    }

    /** Apologises for mishap first */
    public String getErrorMessage() {
        return ("My apologies. " + getMessage());
    }

    /** Expects a number instead of text */
    public static dukeexception expectIntegerButInputIsString(String keyword) {
        return new dukeexception("Please enter a number instead of a text.\nFor example: " + keyword + " 1");
    }

    /**
     *  Expects a number after the [mark] or [delete] keyword
     *  @param keyword [mark] or [delete]
     */
    public static dukeexception arrayOutOfBounds(String keyword){
        return new dukeexception("No such task number exists, Please enter a number after your keyword.\n" +
                "For example: " + keyword + " 1");
    }

    /**
     * respects command syntax but no such file found in database
     * @param keyword [mark] or [delete]
     */
    public static dukeexception indexOutOfBounds(String keyword){
        return new dukeexception("There is no such task to " + keyword);
    }

    /** Activates when Duke fails to find path for accessing database */
    public static dukeexception filePathDoesNotExist(){
        return new dukeexception("Sorry, the file path is not initialised. Storage loading failed. Creating empty list.. ");
    }
}



