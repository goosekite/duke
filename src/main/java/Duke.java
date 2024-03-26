import Storage.Storage;
import UI.JenkinsUI;
import Logic.*;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Duke{

    private static Task task;
    private final JenkinsUI ui;
    private final Storage storage;
    private final BotStatus botStatus;
//    private final Command command;
    private final Parser parser;


    public static String userInput = "";




    public Duke(){
        ui = new JenkinsUI();
        task = new Task();
        botStatus = new BotStatus();
//        command = new Command();
        parser = new Parser();

        storage = new Storage();
        storage.tryStorage();
    }


    public void run(){
        ui.printLogo();
        ui.chatBotSaysHello();

        listenForInput(); //next time is command


    }

    public void changeChatBotName(){
        System.out.println(ui.getChatBotName() + ": Sure! Please key in my new name");
        Scanner sc = new Scanner(System.in);
        userInput = sc.nextLine();

        String name = userInput.trim();
        ui.setChatBotName(name);

        System.out.println(ui.getChatBotName() + ": Right away!");
        sc.close();
    }

    //Level 1 Echo
    public void echoUserInputAdded(String s){
        System.out.println("added: " + s);
        listenForInput();
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
        botStatus.resetImpatience();
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

    public void botDecidesBasedOn(String trimmedUserInput){



        if (userInput.equalsIgnoreCase("bye") || userInput.equalsIgnoreCase("quit")){
            botStatus.quitProgram();
            ui.chatBotSaysBye();
        }

        else if (userInput.equalsIgnoreCase("list")){
            task.printWordDiary();
        }

        else if (userInput.equalsIgnoreCase("help")){
            ui.getHelp();
        }

        else if (userInput.equalsIgnoreCase("change bot name")){
            changeChatBotName();
        }

        else{
            scanKeyword(trimmedUserInput);
        }

        if (botStatus.chatBotIsOnline()){ //quitProgram() when input "bye" or empty input 3 times
            listenForInput();
        }
    }

    public void listenForInput() {
        ui.drawLine();

        String s = ui.readCommand();

        if (parser.isBlank(s)) {
            botStatus.botBecomesImpatient();
            ui.patienceFeedback(botStatus.botPatienceMeter());
        }

        else if (s.equalsIgnoreCase("bye") || s.equalsIgnoreCase("quit")){
            botStatus.quitProgram();
        }

        else if (s.equalsIgnoreCase("list")){
            task.printWordDiary();
        }

        else if (s.equalsIgnoreCase("help") || s.equalsIgnoreCase("faq")){
            ui.getHelp();
        }

        else if (s.equalsIgnoreCase("change bot name")){
            ui.changeChatBotName();
        }


//        command.botDecidesBasedOn(trimmedUserInput);

//        botDecidesBasedOn(trimmedUserInput);



        if (botStatus.chatBotIsOnline() && botStatus.isBotPatient()){

            listenForInput();
        }

        else{
            botStatus.quitProgram();
            ui.chatBotSaysBye();
        }
    }

    public static void main(String[] args) {
        new Duke().run();
    }
}