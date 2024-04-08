import logic.Parser;
import storage.Storage;
import tasklist.*;
import UI.JenkinsUI;
import logic.BotStatus;
import exception.DukeException;

import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Duke {

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

    }

    /** Creates task list by using user commands from text file */
    public void createTaskListFromStorage(){
        Queue<String> queue = storage.loadData();
        storage.loadDataTimeStamp();

        while (!queue.isEmpty()){ //Access queue, process stored user input & dequeue until empty
            String s = queue.peek();
            scanAdvanceKeywords(s); //We skip general commands because we know what we want
            queue.poll();
        }
    }

    /**
     * Greets user and prints tasks from text file
     * Listens for userInput until it shuts down properly
     */
    public void run() {

        createTaskListFromStorage();
        ui.chatBotSaysHello();
        tasks.printTaskList();

        do{
            botListensForInput();
        }
        while (botIsAlive());

        shutDownProperly();
    }

    /**
     * Retrieve all tasks and store it in a text file before it turns itself
     * to offline status and says goodbye
     */
    public void shutDownProperly(){
        String s = tasks.printTaskListForRecording(); //Converts all tasks to string
        storage.saveDataToStorage(s); //Saves tasks to Storage
        storage.saveDateTimeStamp(); //Saves time stamp;

        botStatus.quitProgram(); //Change bot status to offline
        ui.chatBotSaysBye(); //Bot says bye
    }

    public boolean botIsAlive(){
        return (botStatus.chatBotIsOnline() && botStatus.isBotPatient());
    }

    /**
     * marks or unmarks task based on given index
     * 3 points of failure:
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

                parser.addToUndoStack("mark " + keyword[1]); //Undo remembers how to remark task
            System.out.println("mark " + keyword[1]);
        }
        catch (NumberFormatException e){
            DukeException.getError(DukeException.expectIntegerButInputIsString("mark"));
        }
        catch (ArrayIndexOutOfBoundsException e){
            DukeException.getError(DukeException.arrayOutOfBounds("mark"));
        }
        catch (IndexOutOfBoundsException e){
            DukeException.getError(DukeException.indexOutOfBounds("mark"));
        }
    }

    /**
     * Deletes task based on given index
     * Delete has 3 points of failure:
     * "delete" - User did not follow instructions: NumberFormatException e
     * "delete v" - User did not follow instructions: ArrayIndexOutOfBoundsException
     * "delete 1" - Correct input, but no such Task Index exists: IndexOutOfBoundsException
     */
    public void keywordDelete(String[] keyword) {
        int validTaskNumber = -1; //indicates failure condition

        try{
            int taskNumber = parser.getTaskNumber(keyword[1]); //get task number from 2nd word
            if (parser.taskNumberIsValid(taskNumber, tasks)) {
                validTaskNumber = taskNumber; // Changes value from -1 to valid index
            }

            String s = tasks.getTaskBeforeDelete(validTaskNumber); //get String for undo
            parser.addToUndoStack(s); //Undo remembers this

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

    /**
     * Create and store a "Deadline" task object
     * UI Informs user accordingly
     * Stores Inverted command to Undo Stack. Create -> Delete
     * @param userInput is an event when keywords "from " and "to " detected
     */
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

    /**
     * Create and store an "Event" task object
     * UI Informs user accordingly
     * Stores Inverted command to Undo Stack. Create -> Delete
     * @param userInput is an event when keywords "from " and "to " detected
     */
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

    /**
     * Create and store a "To do" task object.
     * UI Informs user accordingly.
     * Stores Inverted command to Undo Stack. Create -> Delete.
     * @param userInput is considered a todo Task because it survived all guard clauses.
     */
    public void keywordTask(String userInput){
        ToDo todo = new ToDo(userInput);
        tasks.createTask(todo);
        ui.userAddedTask(userInput);
        parser.addToUndoStack("delete " + tasks.getTaskSize());
    }

    /**
     * Informs and provides feedback user based on the number of tasks they have
     */
    public void handleList(){
        int taskSize = tasks.getTaskSize();

        ui.preTaskSizeFeedback(taskSize);
        tasks.printTaskList();
        ui.postTaskSizeFeedback(taskSize);

        botStatus.resetImpatience();
    }

    /**
     * Peek Undo Stack to retrieve latest user input
     * Call the scanAdvanceKeywords() to activate command instead of botListensForInput()
     * Pop command from Stack after use
     */
    public void handleUndo(){
        ui.acknowledgeUndoCommand();
        String s = parser.peekUndoStack();
        scanAdvanceKeywords(s); //Skips isGeneralKeyword() for efficiency
        parser.removeFromUndoStack();
    }

    /**
     * Parse keywords to decide which command to run
     * These safe keywords will never trigger DukeExceptions
     * @return false to run advanceKeyword() method, true to skip
     * This optimises the program slightly
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
            handleList();
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

        else if (trimmedUserInput.equalsIgnoreCase("undo")){
            handleUndo();
            botStatus.resetImpatience();
            return true;
        }
        return false; //Remembers user command is not found in general stack
    }

    /**
     * Responds to [mark] [delete], Deadlines [by ], Events [from]... [to] and [todo] Tasks
     * Labelled as advance keywords because every method here can trigger DukeExceptions
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

    /**
     * listen to user input, and parse keyword
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