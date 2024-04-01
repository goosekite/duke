import Logic.Parser;
import Storage.Storage;
import TaskList.*;
import UI.JenkinsUI;
import Logic.BotStatus;
import Exception.DukeException;

import java.util.Queue;
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

        Queue<String> queue = storage.loadData();

        while (!queue.isEmpty()){
            String s = queue.peek();
            scanAdvanceKeywords(s);
            queue.poll();
        }
    }

    /**
     * Lifeblood of the system are: run() and botIsAlive()
     */
    public void run() {
        ui.chatBotSaysHello();
        tasks.printTaskList();

        do{
            botListensForInput();
            //Save undo

        }
        while (botIsAlive());

        String s = tasks.printTaskListForRecording();

        storage.saveDataToStorage(s);
        botStatus.quitProgram();
        ui.chatBotSaysBye();
    }

    public boolean botIsAlive(){
        return (botStatus.chatBotIsOnline() && botStatus.isBotPatient());
    }

    /** Mark has 3 points of failure:
     * "mark" - User did not follow instructions: NumberFormatException e
     * "mark v" - User did not follow instructions: ArrayIndexOutOfBoundsException
     * "mark 1" - Correct input, but no such Task Index exists: IndexOutOfBoundsException
     */
    public void markUserIndex(String[] keyword){
        int validTaskNumber = -1;

        try {
            int taskNumber = parser.getTaskNumber(keyword[1]);
            ui.searchTaskToMark(taskNumber);

            if (parser.taskNumberIsValid(taskNumber, tasks)) {
                validTaskNumber = taskNumber;
            }
                int index = validTaskNumber - 1;
                Task t = tasks.retrieveTaskDetails(index);

                tasks.markTaskIndex(index);
                ui.displayMarkedTask(taskNumber, t.taskIsDone(t), t.getStatusIcon(), t.getTaskDescription());

//                parser.addToStack("a");
                parser.addToUndoStack("mark " + tasks.getTaskSize());


        }
        catch (NumberFormatException e){
            DukeException.getError(DukeException.expectIntegerButInputIsString("mark"));
        }
        catch (ArrayIndexOutOfBoundsException e){
            DukeException.getError(DukeException.arrayOutOfBounds("mark"));
        }
        catch (IndexOutOfBoundsException e){ //taskNumber is -1
            DukeException.getError(DukeException.indexOutOfBounds("mark"));
        }
    }

    /** Delete has 3 points of failure:
     * "delete" - User did not follow instructions: NumberFormatException e
     * "delete v" - User did not follow instructions: ArrayIndexOutOfBoundsException
     * "delete 1" - Correct input, but no such Task Index exists: IndexOutOfBoundsException
     */
    public void keywordDelete(String[] keyword) {
        int validTaskNumber = -1;

        try{
            int taskNumber = Integer.parseInt(keyword[1]);
            if (parser.taskNumberIsValid(taskNumber, tasks)) {
                validTaskNumber = taskNumber;
            }

            String s = tasks.getTaskBeforeDelete(validTaskNumber); //for undo

            parser.addToUndoStack(s);

            tasks.deleteTask(validTaskNumber);
            ui.displayDeletedTask(s);
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

            parser.addToUndoStack("delete " + tasks.getTaskSize());

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

            parser.addToUndoStack("delete " + tasks.getTaskSize());

        }

        else {
            ui.getErrorHelpFromTo();
        }
    }

    public void keywordTask(String userInput){

        ToDo todo = new ToDo(userInput);
        tasks.createTask(todo);
        ui.userAddedTask(userInput);

        parser.addToUndoStack("delete " + tasks.getTaskSize());
        //I cannot
        //parser.addToStack("delete " + todo.convertToCommand()); because my delete is not by task name, is by index
    }

    /**
     * These safe keywords will never trigger DukeExceptions
     */
    public boolean isGeneralKeyword(String trimmedUserInput){

        if (trimmedUserInput.isBlank() || trimmedUserInput.isEmpty()) {
            botStatus.botBecomesImpatient();
            ui.patienceFeedback(botStatus.botPatienceMeter());
            return true;
        }

        else if (trimmedUserInput.equalsIgnoreCase("bye") || trimmedUserInput.equalsIgnoreCase("quit")){
            botStatus.quitProgram();
            return true;
        }

        else if (trimmedUserInput.equalsIgnoreCase("list")){
            int taskSize = tasks.getTaskSize();

            ui.preTaskSizeFeedback(taskSize);
            tasks.printTaskList();
            ui.postTaskSizeFeedback(taskSize);

            botStatus.resetImpatience();
            return true;
        }

        else if (trimmedUserInput.equalsIgnoreCase("d")){
            System.out.println(tasks.printTaskListForRecording());
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

        else if (trimmedUserInput.equalsIgnoreCase("undo")){

            String s = parser.peekUndoStack();
            parser.removeFromUndoStack();

            String[] keyword = s.split(" ", 2);
            ui.undoSuccess();
            keywordDelete(keyword);

        }

        return false;
    }

    /** Responds to [mark] [delete], Deadlines [by ], Events [from]... [to] and Tasks
     * DukeException catches errors here
     * Undo records userInput in Stack
     */
    public void scanAdvanceKeywords(String userInput)  {
        botStatus.resetImpatience();
        boolean isDeadlineEvent = false, isMarkOrDelete = false;

        String[] keyword = userInput.split(" ", 2);

        //Level 4-0 Mark
        switch (keyword[0]){
            case "mark":
            case "unmark":
                markUserIndex(keyword);
                isMarkOrDelete = true;
                break;

            case "delete":
                keywordDelete(keyword);
                isMarkOrDelete = true;
                break;
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

        /* Level 4-1 Task To do
          @param isMarkOrDelete prevents user from saving keyword "mark" "delete" as Task
          @param isDeadlineEvent prevents bot from saving userInput 2 times: 1st as Deadline/Event. 2nd as Task
         */
        if (!isMarkOrDelete && !isDeadlineEvent && !userInput.equals("undo") && !userInput.contains("delete")){
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

        if (!isGeneralKeyword(userInput)){
            scanAdvanceKeywords(userInput);
        }
    }

    public static void main(String[] args) {
        new Duke().run();
    }
}