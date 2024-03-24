import java.lang.System;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Duke{
    private static Task task;

    private String chatBotName = "Jenkins";
    public static String userInput = "";
    public static Boolean chatbotIsOnline = false;
    public static byte blankUserInputCount = 0;

    public Duke(){
        chatbotIsOnline = false;
        System.out.println("       _            _    _           ");
        System.out.println("      | |          | |  (_)          ");
        System.out.println("      | | ___ _ __ | | ___ _ __  ___ ");
        System.out.println("  _   | |/ _ \\ '_ \\| |/ / | '_ \\/ __|");
        System.out.println(" | |__| |  __/ | | |   <| | | | \\__ \\");
        System.out.println("  \\____/ \\___|_| |_|_|\\_\\_|_| |_|___/");
    }

    public String getChatBotName(){
        return this.chatBotName;
    }

    public void changeChatBotName(){
        System.out.println(getChatBotName() + ": Sure! Please key in my new name");
        Scanner sc = new Scanner(System.in);
        userInput = sc.nextLine();

        String name = userInput.trim();
        setChatBotName(name);

        System.out.println(getChatBotName() + ": Right away!");
        sc.close();
    }

    public void powerOn(){
        chatbotIsOnline = true;
        chatBotGreetings();
        task = new Task();
        listenForInput();
    }

    //Extra 1 - Impatience Meter
    public void botGetsImpatient(int blankUserInput){
        final int botMaxPatience = 2;
        int botPatience = botMaxPatience - blankUserInput;

        if (botPatience > 1) {
            System.out.println("Sorry, I did not receive any commands");
            System.out.println("I will leave if there's no one around. " + botPatience + " more chance");
            listenForInput();
        }

        else if (botPatience == 1) {
            System.out.println("Last Chance! Please issue a command or I will leave!");
            listenForInput();
        }

        else {
            System.out.println("Looks like no one's here. Good bye");
            quitProgram();
        }
    }

    //Level 1 Echo
    public void echoUserInputAdded(String s){
        System.out.println("added: " + s);
        listenForInput();
    }

    //Extra 2 - just a drawing a line
    public void drawLine() {
        System.out.println("____________________________________________________________");
    }

    //Level 0-1 Rename
    public void setChatBotName(String userInput){
        this.chatBotName = userInput;
    }

    //Level 0-2 Greet
    public void chatBotGreetings(){
        System.out.println(getChatBotName() + ": Hello! you may call me " + getChatBotName() + ". I remember it, so you don't have to!");
        System.out.println("What can I do for you?");
    }

    public void help(){
        System.out.println(getChatBotName() + ": Certainly! Here are all commands that I can understand:");
        System.out.println("help or {.} - prints this help list to help recall");
        System.out.println("bye - exits program --- tap {ENTER} 3 times)");
        System.out.println("tap {ENTER} 3 times to exit program quickly");
        drawLine();

        System.out.println("[Task] - records Tasks");
        System.out.println("[Task] by [timing] - records Deadlines");
        System.out.println("[Task] from [time] to [time] - records Events");

        System.out.println("mark OR unmark [Task number] - Marks/Unmarks Task number");
        System.out.println("list - prints all recorded events");
        System.out.println("Delete [Task number] - Delete Task");
    }

    public void quitProgram(){
        chatbotIsOnline = false;
        System.out.print(getChatBotName() + ": Bye. Hope to see you again soon!\n");
    }

    public void markUserIndex(String index){
        task.markAsDone(index);
    }

    public void keywordDelete(String[] keyword){
        try {
            int taskNumber = Integer.parseInt(keyword[1]); //problems comes from converting string to number
            task.deleteTask(taskNumber);
        } catch (DukeException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            DukeException.getError(DukeException.invalidTaskNumber());
        } catch (IllegalArgumentException e) {
            DukeException.getError(DukeException.expectIntegerButInputIsString());
        }
    }

    public void keywordBy(){
        Pattern pattern = Pattern.compile("(.+) by (.+)");
        Matcher matcher = pattern.matcher(userInput);

        if (matcher.find()) {

            String eventDescription = matcher.group(1);
            String deadline = matcher.group(2);

            Deadline d = new Deadline(eventDescription, deadline);
            task.createTask(d);
            System.out.print("Deadline ");
            echoUserInputAdded(userInput);
        }

        else {
            System.out.println("I noticed your intent to create a deadline with \"by\"");
            System.out.println("Please input as follows: [Task] by [timing]");

        }
    }

    public void keywordFromTo(){
        Pattern pattern = Pattern.compile("(.+) from (.+) to (.+)");
        Matcher matcher = pattern.matcher(userInput);

        if (matcher.find()) {

            String eventDescription = matcher.group(1);
            String start = matcher.group(2);
            String end = matcher.group(3);

            Event event = new Event(eventDescription, start, end);
            task.createTask(event);

            System.out.print("Event ");
            echoUserInputAdded(userInput);
        }

        else {
            System.out.println("Seems like you want to create an event with \"from\" & \"to\"");
            System.out.println("Please input as follows: [Task] from [time] to [time]");
        }
    }

    public void keywordTask(){
        ToDo todo = new ToDo(userInput);
        task.createTask(todo);
        System.out.print("Task to do ");
        echoUserInputAdded(userInput);
    }

    public void scanKeyword(String userInput)  {
        blankUserInputCount = 0; // resets inpatient meter
        boolean isMarkScenario = false, isDeadlineEvent = false; //Both flags must be false to confirm keyword [Task]

        String[] keyword = userInput.split(" ", 2);

        //Level 4-0 Mark
        switch (keyword[0]){
            case "mark":
            case "unmark":
                markUserIndex(keyword[1]);
                isMarkScenario = true;
                break;
            case "delete":
                keywordDelete(keyword);
                isMarkScenario = true;
        }

        // Level 4-2 Deadlines
        if (userInput.contains("by ")){
            keywordBy();
            isDeadlineEvent = true;
        }

        // Level 4-3 Events
        if (userInput.contains("from ") && userInput.contains("to ")){
            keywordFromTo();
            isDeadlineEvent = true; //corner case from from to to
        }

        // Level 4-1 Task To do
        if (!isMarkScenario && !isDeadlineEvent){
            keywordTask();
        }
    }

    public void listenForInput() {

        drawLine();
        Scanner sc = new Scanner(System.in);
        userInput = sc.nextLine();
        String trimmedUserInput = userInput.trim();

            if (userInput.isBlank()) {
                botGetsImpatient(blankUserInputCount++);
            }

            else if (userInput.equalsIgnoreCase("bye")){
                quitProgram();
            }

            else if (userInput.equalsIgnoreCase("list")){
                task.printWordDiary();
            }

            else if (userInput.equalsIgnoreCase("help")){
                help();
            }

            else if (userInput.equalsIgnoreCase("change bot name")){
                changeChatBotName();
            }

            else{
                scanKeyword(trimmedUserInput);
            }

            sc.close();

            if (chatbotIsOnline){ //quitProgram() when input "bye" or empty input 3 times
                listenForInput();
            }
    }

    public static void main(String[] args) {
        Duke Jenkins = new Duke();
        Jenkins.powerOn();
    }
}