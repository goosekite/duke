package duke.ui;

public class SuggestFeedback {
    public static void getErrorHelpBy(){
        System.out.println("I noticed your intent to create a deadline with \"by\"");
        System.out.println("Please input as follows: [Task] by [timing]");
    }

    public static void getErrorHelpFromTo(){
        System.out.println("Seems like you want to create an event with \"from\" & \"to\"");
        System.out.println("Please input as follows: [Task] from [time] to [time]");
    }
}
