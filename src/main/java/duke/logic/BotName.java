package duke.logic;

public class BotName {
    protected static String chatBotName = "Jenkins";

    public static String getChatBotName(){
        return chatBotName;
    }

    //Level 0-1 Rename
    public static void changeBotName(String userInput){
        chatBotName = userInput;
    }

}
