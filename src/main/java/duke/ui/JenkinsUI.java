package duke.ui;

import duke.logic.BotName;

public class JenkinsUI {

    protected static String botName = BotName.getChatBotName();

    public void acknowledgeChangeBotNameIntent() {
        System.out.println(BotName.getChatBotName() + ": Sure! Please key in my new name");
    }

    public void changeBotNameSuccess() {
        botName = BotName.getChatBotName();
        System.out.println(botName + ": Right away!");
    }

    //Source: https://patorjk.com/software/taag/#p=display&f=Ogre&t=Jenkins
    public static void printLogo() {
        System.out.println("       _            _    _           ");
        System.out.println("      | |          | |  (_)          ");
        System.out.println("      | | ___ _ __ | | ___ _ __  ___ ");
        System.out.println("  _   | |/ _ \\ '_ \\| |/ / | '_ \\/ __|");
        System.out.println(" | |__| |  __/ | | |   <| | | | \\__ \\");
        System.out.println("  \\____/ \\___|_| |_|_|\\_\\_|_| |_|___/");
    }

    /**
     * Draws a line
     */
    public static void drawLine() {
        System.out.println("____________________________________________________________");
    }

    public void getHelp() {
        botName = BotName.getChatBotName();
        System.out.println(botName + ": Certainly! Here are all commands that I can understand:");
        System.out.println("help or {.} - prints this help list to help recall");
        System.out.println("bye - exits program --- tap {ENTER} 3 times)");
        System.out.println("tap {ENTER} 3 times to exit program quickly");

        drawLine();

        System.out.println("[Task] - records Tasks");
        System.out.println("[Task] by [timing] - records Deadlines");
        System.out.println("[Task] from [time] to [time] - records Events");

        System.out.println("mark [Task number] - Marks/Unmarks Task number");
        System.out.println("list - prints all recorded events");
        System.out.println("delete [Task number] - Delete Task");

        System.out.println("undo - Undo latest command if possible");
    }

}