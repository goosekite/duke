package Logic;

public class BotStatus {

    protected Boolean isOnline = true;
    final int botMaxPatience = 3;
    public byte blankUserInputCount = 0;

    /**
     * For UI action
     * @return 0 to quit, 1 for last chance, 2 for warning
     */
    public int botPatienceMeter(){
        return botMaxPatience - blankUserInputCount;
    }

    public boolean isBotPatient(){
        return (botMaxPatience - blankUserInputCount) > 0;

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
