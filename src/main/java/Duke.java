import Logic.Parser;
import Storage.Storage;
import TaskList.*;
import UI.JenkinsUI;
import Logic.BotStatus;
import Exception.DukeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Duke{

    private static Task tasks;
    private final JenkinsUI ui;
    private final Parser parser;
    private final Storage storage;
    private final BotStatus botStatus;


    public Duke(){
        tasks = new Task();
        ui = new JenkinsUI();
        parser = new Parser();
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
    public void markUserIndex(String[] keyword){
        try {
            String index = keyword[1];
            tasks.markAsDone(index);
        }
        catch (ArrayIndexOutOfBoundsException e){
            DukeException.getError(DukeException.arrayOutOfBounds("mark"));
            ui.getErrorHelpMark();
        }

    }

    public boolean taskNumberIsValid(int taskNo) {
        return taskNo <= tasks.getTaskSize() && taskNo > 0;
    }

    /** Delete has 3 points of failure:
     * "delete" - User did not follow instructions
     * "delete v" - User did not follow instructions
     * "delete 1" - Correct input, but no such Task Index exists
     */
    public void keywordDelete(String[] keyword) {
        int validTaskNumber = -1;

        try{
            int taskNumber = Integer.parseInt(keyword[1]);
            if (taskNumberIsValid(taskNumber)) {
                validTaskNumber = taskNumber;
            }

            String s = tasks.getTaskBeforeDelete(validTaskNumber); //for undo

            tasks.deleteTask(validTaskNumber);
            ui.taskDeletedSuccessfully(s);
        }
        catch (NumberFormatException e){
            DukeException.getError(DukeException.expectIntegerButInputIsString("delete"));
        }
        catch (ArrayIndexOutOfBoundsException e){
            DukeException.getError(DukeException.arrayOutOfBounds("delete"));
        }
        catch (IndexOutOfBoundsException e){
            DukeException.getError(DukeException.indexOutOfBounds("delete"));
        }
    }

    public void keywordBy(String userInput){
        Pattern pattern = Pattern.compile("(.+) by (.+)");
        Matcher matcher = pattern.matcher(userInput);

        if (matcher.find()) {

            String eventDescription = matcher.group(1);
            String deadline = matcher.group(2);

            Deadline d = new Deadline(eventDescription, deadline);
            tasks.createTask(d);

            ui.userAddedDeadline(userInput);
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
            tasks.createTask(event);


            ui.userAddedEvent(userInput);
        }

        else {
            ui.getErrorHelpFromTo();
        }
    }

    public void keywordTask(String userInput){
        ToDo todo = new ToDo(userInput);
        tasks.createTask(todo);
        ui.userAddedTask(userInput);
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
            tasks.printWordDiary();
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

    /** Responds to [mark] [delete], Deadlines [by ], Events [from]... [to] and Tasks
     * DukeException catches errors here
     */
    public void scanAdvanceKeywords(String userInput)  {
        botStatus.resetImpatience();
        boolean isDeadlineEvent = false, isMarkOrDelete = false;

        String[] keyword = userInput.split(" ", 2);

        //Level 4-0 Mark
        switch (keyword[0]){
            case "mark":
            case "unmark":
//                    markUserIndex(keyword[1]);
                    markUserIndex(keyword);
                    isMarkOrDelete = true;
                break;

            case "delete":
                    keywordDelete(keyword);
                    isMarkOrDelete = true;
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

        /**Level 4-1 Task To do
          @param isMarkOrDelete prevents user from saving keyword "mark" "delete" as Task
          @param isDeadlineEvent prevents bot from saving userInput 2 times: 1st as Deadline/Event. 2nd as Task
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

        String userInput = parser.tidyUserInput();

        //Technically this is more efficient because it stops earlier than looping everything.
        //so I suppose this is ok since we're not looping through 20+ conditional statements,
        if (!isGeneralKeyword(userInput)){
            scanAdvanceKeywords(userInput);
        }
    }

    public static void main(String[] args) {
        new Duke().run();
    }
}