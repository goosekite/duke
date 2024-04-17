package duke.ui;

import duke.logic.BotName;

public class HelloAndGoodbye {

    public static void chatBotSaysHello() {
        String botName = BotName.getChatBotName();
        duke.ui.ASCII.printLogo();
        System.out.println(botName + ": Hello! you may call me " + botName + ". I remember it, so you don't have to!");
        System.out.println("What can I do for you?");
    }

    public static void chatBotSaysBye() {
        String botName = BotName.getChatBotName();
        System.out.print(botName + ": I'm glad to be of service! I hope to see you again soon!\n");
    }
}
