package UI;

public class JenkinsUI {
    protected static String chatBotName = "Jenkins";
    public static String getChatBotName(){
        return chatBotName;
    }

    public static void printLogo(){
        System.out.println("       _            _    _           ");
        System.out.println("      | |          | |  (_)          ");
        System.out.println("      | | ___ _ __ | | ___ _ __  ___ ");
        System.out.println("  _   | |/ _ \\ '_ \\| |/ / | '_ \\/ __|");
        System.out.println(" | |__| |  __/ | | |   <| | | | \\__ \\");
        System.out.println("  \\____/ \\___|_| |_|_|\\_\\_|_| |_|___/");
    }

    //Extra 2 - just a drawing a line
    public static void drawLine() {
        System.out.println("____________________________________________________________");
    }

    //Level 0-1 Rename
    public void setChatBotName(String userInput){
        chatBotName = userInput;
    }

    //Level 0-2 Greet
    public static void chatBotSaysHello(){
        System.out.println(getChatBotName() + ": Hello! you may call me " + getChatBotName() + ". I remember it, so you don't have to!");
        System.out.println("What can I do for you?");
    }

    public static void chatBotSaysBye(){
        System.out.print(getChatBotName() + ": GoodBye, Stay safe. Hope to see you again soon!\n");
    }

}
