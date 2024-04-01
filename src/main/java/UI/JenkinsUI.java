package UI;

import java.util.Scanner;

public class JenkinsUI {
    protected String chatBotName = "Jenkins";

    protected String getChatBotName(){
        return chatBotName;
    }

    //Source: https://patorjk.com/software/taag/#p=display&f=Ogre&t=Jenkins
    public void printLogo(){
        System.out.println("       _            _    _           ");
        System.out.println("      | |          | |  (_)          ");
        System.out.println("      | | ___ _ __ | | ___ _ __  ___ ");
        System.out.println("  _   | |/ _ \\ '_ \\| |/ / | '_ \\/ __|");
        System.out.println(" | |__| |  __/ | | |   <| | | | \\__ \\");
        System.out.println("  \\____/ \\___|_| |_|_|\\_\\_|_| |_|___/");
    }

    //Extra 2 - just a drawing a line
    public void drawLine() {
        System.out.println("____________________________________________________________");
    }

    //Level 0-1 Rename
    public void setChatBotName(String userInput){
        chatBotName = userInput;
    }

    public void chatBotSaysHello(){
        printLogo();
        System.out.println(getChatBotName() + ": Hello! you may call me " + getChatBotName() + ". I remember it, so you don't have to!");
        System.out.println("What can I do for you?");
    }

    public void chatBotSaysBye(){
        System.out.print(getChatBotName() + ": GoodBye, Stay Safe. I hope to see you again soon!\n");
    }

    public void getHelp(){
        System.out.println(getChatBotName() + ": Certainly! Here are all commands that I can understand:");
        System.out.println("help or {.} - prints this help list to help recall");
        System.out.println("bye - exits program --- tap {ENTER} 3 times)");
        System.out.println("tap {ENTER} 3 times to exit program quickly");

        drawLine();

        System.out.println("[Task] - records Tasks");
        System.out.println("[Task] by [timing] - records Deadlines");
        System.out.println("[Task] from [time] to [time] - records Events");

        System.out.println("mark [Task number] - Marks/Unmarks Task number");
        System.out.println("list - prints all recorded events");
        System.out.println("delete [Task number] - Delete Task.Task");
    }

    public void searchTaskToMark(int taskNumber){
        System.out.println("Searching for task " + taskNumber + "...");
    }

    public void displayMarkedTask(int index, String doneStatus, String taskIcon, String taskDescription){
        System.out.println("Task " + index + " marked as " + doneStatus);
        System.out.print(index + ".");
        System.out.print("[" + taskIcon + "] ");
        System.out.println(taskDescription);
    }

    public void getErrorHelpBy(){
        System.out.println("I noticed your intent to create a deadline with \"by\"");
        System.out.println("Please input as follows: [Task] by [timing]");
    }

    public void getErrorHelpFromTo(){
        System.out.println("Seems like you want to create an event with \"from\" & \"to\"");
        System.out.println("Please input as follows: [Task] from [time] to [time]");
    }

    /**
     * @param botPatience determines if bot should continue to listen for userInput or shut down
     * Provides warning accordingly
     */
    public void patienceFeedback(int botPatience){
        if (botPatience > 1) {
            System.out.println("Sorry, I did not receive any commands");
            System.out.println("I will leave if there's no one around. " + botPatience + " more chance");
        }

        else if (botPatience == 1) {
            System.out.println("Last Chance! Please issue a command or I will leave!");
        }

        else{
            System.out.println("Looks like no one's here. Good bye");
        }
    }

    public void changeChatBotName(){
        System.out.println(getChatBotName() + ": Sure! Please key in my new name");
        Scanner sc = new Scanner(System.in); //open scanner!
        String userInput = sc.nextLine();

        String name = userInput.trim();
        setChatBotName(name);

        System.out.println(getChatBotName() + ": Right away!");
    }

    public void userAddedTask(String taskDescription){
        System.out.println("Task to do added: " + taskDescription);
    }

    public void userAddedEvent(String taskDescription){
        System.out.println("Event added: " + taskDescription);
    }

    public void userAddedDeadline(String taskDescription){
        System.out.println("Deadline added: " + taskDescription);
    }

    public void displayDeletedTask(String taskDescription){
        System.out.println("Certainly, I've deleted " + taskDescription);
    }

    public void preTaskSizeFeedback(int taskSize){
        if (taskSize > 1) {
            System.out.println("Right away! I found " + taskSize + " tasks!");
        }

        if (taskSize >= 5){
            System.out.println("Oh my! you have " + taskSize + " tasks!");
        }
    }

    public void postTaskSizeFeedback(int taskSize){
        if (taskSize == 0){
            System.out.println("Sorry, I found no task :(");
        }

        else if (taskSize == 1){
            System.out.println("Marvelous, Only 1 outstanding task left to complete!");
        }
    }

    public void undoSuccess(){
        System.out.println("As you wish! Undo command!");
    }

}
