import Storage.Storage;
import Task.*;
import UI.JenkinsUI;
import Logic.BotStatus;
import Exception.DukeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Duke{

    private static Task task;
    private final JenkinsUI ui;
    private final Storage storage;
    private final BotStatus botStatus;

    public Duke(){
        ui = new JenkinsUI();
        task = new Task();
        botStatus = new BotStatus();

        storage = new Storage();
        storage.tryStorage();
    }

    /**
     * Lifeblood of the system are: run() and botIsAlive()
     */
    public void run() {
        ui.chatBotSaysHello();

        do{
            botListensForInput();
        }
        while (botIsAlive());

        botStatus.quitProgram();
        ui.chatBotSaysBye();
    }

    public boolean botIsAlive(){
        return (botStatus.chatBotIsOnline() && botStatus.isBotPatient());
    }

    /**
     * These 5 methods are logic for scanAdvanceKeywords()
     */
    public void markUserIndex(String index){
        task.markAsDone(index);
    }

    public void keywordDelete(String[] keyword) {
            int taskNumber = Integer.parseInt(keyword[1]); //problems comes from converting string to number
            task.deleteTask(taskNumber);
    }

    public void keywordBy(String userInput){
        Pattern pattern = Pattern.compile("(.+) by (.+)");
        Matcher matcher = pattern.matcher(userInput);

        if (matcher.find()) {

            String eventDescription = matcher.group(1);
            String deadline = matcher.group(2);

            Deadline d = new Deadline(eventDescription, deadline);
            task.createTask(d);
            System.out.print("Deadline ");
            ui.echoUserInputAdded(userInput);
        }

        else {
            ui.getErrorHelpBy();
        }
    }

    public void keywordFromTo(String userInput){
        Pattern pattern = Pattern.compile("(.+) from (.+) to (.+)");
        Matcher matcher = pattern.matcher(userInput);

        if (matcher.find()) {

            String eventDescription = matcher.group(1);
            String start = matcher.group(2);
            String end = matcher.group(3);

            Event event = new Event(eventDescription, start, end);
            task.createTask(event);

            System.out.print("Event ");
            ui.echoUserInputAdded(userInput);
        }

        else {
            ui.getErrorHelpFromTo();
        }
    }

    public void keywordTask(String userInput){
        ToDo todo = new ToDo(userInput);
        task.createTask(todo);
        System.out.print("Task to do ");
        ui.echoUserInputAdded(userInput);
    }

    /**
     * Inputs which are not related to mark, delete, deadlines, events nor tasks
     */
    public boolean isGeneralKeyword(String trimmedUserInput){

        if (trimmedUserInput.isBlank()) {
            botStatus.botBecomesImpatient();
            ui.patienceFeedback(botStatus.botPatienceMeter());
            return true;
        }

        else if (trimmedUserInput.equalsIgnoreCase("bye") || trimmedUserInput.equalsIgnoreCase("quit")){
            botStatus.quitProgram();
            return true;
        }

        else if (trimmedUserInput.equalsIgnoreCase("list")){
            task.printWordDiary();
            botStatus.resetImpatience();
            return true;
        }

        else if (trimmedUserInput.equalsIgnoreCase("help") || trimmedUserInput.equalsIgnoreCase("faq")){
            ui.getHelp();
            botStatus.resetImpatience();
            return true;
        }

        else if (trimmedUserInput.equalsIgnoreCase("change bot name")){
            ui.changeChatBotName();
            botStatus.resetImpatience();
            return true;
        }

        return false;
    }

    /** Response to [mark] [delete], Deadlines [by ], Events [from]... [to] and Tasks
     * DukeException prevents array out of bounds exception caused by input "mark", "unmark" and "delete"
     */
    public void scanAdvanceKeywords(String userInput)  {
        botStatus.resetImpatience();
        boolean isDeadlineEvent = false, isMarkOrDelete = false;

        String[] keyword = userInput.split(" ", 2);

        //Level 4-0 Mark
        switch (keyword[0]){
            case "mark":
            case "unmark":
                try{
                    markUserIndex(keyword[1]);
                }
                catch (ArrayIndexOutOfBoundsException e){
                    DukeException.getError(DukeException.arrayOutOfBounds());
                    ui.getErrorHelpMark();
                }
                finally {
                    isMarkOrDelete = true;
                }
                break;

            case "delete":
                try{
                    keywordDelete(keyword);
                }
                catch (ArrayIndexOutOfBoundsException e){
                    DukeException.getError(DukeException.arrayOutOfBounds());
                    ui.getErrorHelpDelete();
                }
                catch (NumberFormatException e){
                    DukeException.getError(DukeException.expectIntegerButInputIsString());
                    ui.getErrorHelpDelete();
                }
                finally {
                    isMarkOrDelete = true;
                }
        }

        // Level 4-2 Deadlines
        if (userInput.contains("by ")){
            keywordBy(userInput);
            isDeadlineEvent = true;
        }

        // Level 4-3 Events
        if (userInput.contains("from ") && userInput.contains("to ")){
            keywordFromTo(userInput);
            isDeadlineEvent = true; //corner case from from to to
        }

        /*Level 4-1 Task To do
          isDeadlineEvent prevents bot from saving userInput 2 times: 1st as Deadline/Event. 2nd as Task
          isMarkOrDelete prevents user from saving keyword "mark" "delete" as Task
         */
        if (!isMarkOrDelete && !isDeadlineEvent){
            keywordTask(userInput);
        }
    }

    /** Purpose of the system is to listen to user
     * General: Quality of life functions
     * Advance: Bot functions as intended
     */
    public void botListensForInput() {
        ui.drawLine();

        String userInput = ui.cleanUserCommand();
        
        if (!isGeneralKeyword(userInput)){
            scanAdvanceKeywords(userInput);
        }
    }

    public static void main(String[] args) {
        new Duke().run();
    }
}