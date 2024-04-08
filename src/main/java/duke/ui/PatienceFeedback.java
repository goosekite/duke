package duke.ui;

public class PatienceFeedback {
    /**
     * @param botPatience determines if bot should continue to listen for userInput or shut down
     *                    Provides warning accordingly
     */
    public static void soundOffPatienceLevel(int botPatience) {
        if (botPatience > 1) {
            System.out.println("Sorry, I did not receive any commands");
            System.out.println("I will leave if there's no one around. " + botPatience + " more chance");
        } else if (botPatience == 1) {
            System.out.println("Last Chance! Please issue a command or I will leave!");
        } else {
            System.out.println("Looks like no one's here. Good bye");
        }
    }
}
