package duke.ui;

import duke.logic.BotName;

import java.io.File;

public class JenkinsUI {

    protected static String botName = BotName.getChatBotName();

    //Source: https://patorjk.com/software/taag/#p=display&f=Ogre&t=Jenkins
    public void printLogo(){
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
    public void drawLine() {
        System.out.println("____________________________________________________________");
    }

    public void chatBotSaysHello(){
        botName = BotName.getChatBotName();
        printLogo();
        System.out.println(botName + ": Hello! you may call me " + botName + ". I remember it, so you don't have to!");
        System.out.println("What can I do for you?");
    }

    public void chatBotSaysBye(){
        botName = BotName.getChatBotName();
        System.out.print(botName + ": GoodBye, Stay Safe. I hope to see you again soon!\n");
    }

    public void getHelp(){
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

    public void changingBotName(){
        botName = BotName.getChatBotName();
        System.out.println(botName + ": Sure! Please key in my new name");

    }

    public void changeBotNameSuccess(){
        botName = BotName.getChatBotName();
        System.out.println(botName + ": Right away!");
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

    public void acknowledgeUndoCommand(){
        System.out.println("As you wish! Command Undo!~");
    }

    public static void noFileFound(File f, String context){
        System.out.println("I've created " + f.getName() + " because " + context + " doesn't exist in our database");
    }

    public static void foundFileToLoad(String context) {
        System.out.println("I've found " + context + " to load! Wonderful!");
    }

    public static void loadDateTimeSuccessfully(String dateTime) {
        System.out.println("We've last reviewed our tasks on " + dateTime);
        System.out.println("loading...");
    }

    public static void savedTimeStampSuccessfully(String dateTime) {
        botName = BotName.getChatBotName();
        System.out.println(botName + ": I've committed everything to memory on " + dateTime);
    }

    public static void creatingTimeStamp() {
        System.out.println("Creating timestamp.. ");
    }

    public static void cantFindTimeStampSoIWillRecreate(String dateTime) {
        System.out.println("Oh dear, I can't find when we've last saved our file");
        System.out.println("No worries, I'll mark it as now on " + dateTime);
    }


}
