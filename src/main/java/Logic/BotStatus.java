package Logic;

/**
 * Determines if bot is patient or online
 * Bot will be offline if it loses all patience or user switch it off manually
 */
public class BotStatus {
    final int BOT_MAX_PATIENCE = 3;
    public byte blankUserInputCount = 0;
    protected Boolean isOnline = true;

    public int botPatienceMeter(){
        return BOT_MAX_PATIENCE - blankUserInputCount;
    }

    public boolean isBotPatient(){
        return (BOT_MAX_PATIENCE - blankUserInputCount) > 0;
    }

    public void botBecomesImpatient(){
        blankUserInputCount++;
    }

    public void resetImpatience(){
        blankUserInputCount = 0;
    }

    public void quitProgram(){
        isOnline = false;
    }

    public Boolean chatBotIsOnline(){
        return isOnline;
    }



}
