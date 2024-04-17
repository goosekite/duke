package duke.ui;

public class SuggestFeedback {
    public static void helpUsingByKeyword(){
        System.out.println("I noticed your intent to create a deadline with \"by\"");
        System.out.println("Please input as follows: [Task] by [timing]");
    }

    public static void helpUsingFromToKeyword(){
        System.out.println("Seems like you want to create an event with \"from\" & \"to\"");
        System.out.println("Please input as follows: [Task] from [time] to [time]");
    }

    /** Prints all commands user can use */
    public static void getHelp() {
        String lineSeparator = System.lineSeparator();

        System.out.println(duke.logic.BotName.getChatBotName() + ": Certainly! Here are all commands that I can understand:");
        System.out.println("help - prints this help list to help recall");
        System.out.println("bye - exits program --- tap {ENTER} 3 times)");
        System.out.println("tap {ENTER} 3 times to exit program quickly" + lineSeparator);

        System.out.println("[Task] - records Tasks");
        System.out.println("[Task] by [timing] - records Deadlines");
        System.out.println("[Task] from [time] to [time] - records Events" + lineSeparator);

        System.out.println("mark [Task number] - Marks/Unmarks Task number");
        System.out.println("find [Task] - Find tasks by Keyword");
        System.out.println("list - prints all recorded events");
        System.out.println("delete [Task number] - Delete Task" + lineSeparator);

        System.out.println("undo - Undo latest command if possible");
        System.out.println("delete all - Deletes all task. WARNING, irreversible!");
    }
}
