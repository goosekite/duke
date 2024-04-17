package duke.logic;

/**
 * Determines if bot continues to listen or shut down
 * Bot will be offline if it loses all patience or user switch it off manually
 * Patience is lost when user inputs nothing, but resets when user inputs anything
 * See duke.Duke.duke.Duke -> botIsAlive();
 */
public class BotPatience {

    private final byte BOT_MAX_PATIENCE = 3;
    private byte blankUserInputCount = 0;
    private boolean isOnline = true;

    /**
     * @return number of chance bot will accept a blank input
     */
    public int botPatienceMeter(){
        return BOT_MAX_PATIENCE - blankUserInputCount;
    }

    /**
     * @return true if bot patience is >0 and false if = 0
     */
    public boolean isBotPatient(){
        return (BOT_MAX_PATIENCE - blankUserInputCount) > 0;
    }

    /** inputs with nothing will cause bot to become impatient */
    public void botBecomesImpatient(){
        blankUserInputCount++;
    }

    /** Reset patience meter when user inputs anything */
    public void resetImpatience(){
        blankUserInputCount = 0;
    }

    /** Switch bot to offline so duke.Duke's do while loop will fail */
    public void quitProgram(){
        isOnline = false;
    }

    /**
     * @return status, so program knows to continue or stop listening to userInput
     */
    public Boolean chatBotIsOnline(){
        return isOnline;
    }

}
