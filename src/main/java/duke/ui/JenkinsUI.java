package duke.ui;

import duke.logic.BotName;

public class JenkinsUI {

    //acknowledge user wants to change bot name
    public static void acknowledgeChangeBotNameIntent() {
        System.out.println(BotName.getChatBotName() + ": Sure! Please key in my new name");
    }

    //informs user bot name has successfully changed
    public static void changeBotNameSuccess() {
        System.out.println(BotName.getChatBotName() + ": Right away!");
    }

}