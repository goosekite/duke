package duke.logic;

public class BotName {
    protected static String chatBotName = "Jenkins"; //Default name is Jenkins everytime you start duke

    /**
     * Get chatbot's name
     */
    public static String getChatBotName(){
        return chatBotName;
    }


    /**
     * Change Bot name based on user input
     * @param userInput is the new bot name
     */
    public static void changeBotName(String userInput){
        chatBotName = userInput;
    }

}
