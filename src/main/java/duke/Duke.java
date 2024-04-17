package duke;

import duke.logic.BotName;
import duke.logic.BotUndo;
import duke.logic.Parser;
import duke.storage.Storage;
import duke.task.*;
import duke.ui.HelloAndGoodbye;
import duke.logic.BotPatience;
import duke.exception.DukeException;
import duke.ui.PatienceFeedback;

import java.util.Queue;

public class Duke {

    private static Task tasks;
    private final Storage STORAGE;
    private final BotPatience BOT_PATIENCE;
    private final int INVALID_TASK_NUMBER = -1;


    public Duke(){
        tasks = new Task();
        BOT_PATIENCE = new BotPatience();
        STORAGE = new Storage();
    }

    public static void main(String[] args) {
        new Duke().run();
    }

    /** Creates task list by using user commands from text file */
    public void createTaskListFromStorage(){
        Queue<String> queue = STORAGE.loadData();
        STORAGE.loadDataTimeStamp();

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
        duke.ui.HelloAndGoodbye.chatBotSaysHello();
        tasks.printTaskList();

        do{
            duke.ui.ASCII.drawLine();
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
        STORAGE.saveDataToStorage(s); //Saves tasks to Storage
        STORAGE.saveDateTimeStamp(); //Saves time stamp;

        BOT_PATIENCE.quitProgram(); //Change bot status to offline
        HelloAndGoodbye.chatBotSaysBye(); //Bot says bye
    }

    public boolean botIsAlive(){
        return (BOT_PATIENCE.chatBotIsOnline() && BOT_PATIENCE.isBotPatient());
    }

    private int assertIndex(String[] keyword){
        Parser parser = new Parser();
        int taskNumber = parser.getTaskNumber(keyword[1]);

        assert taskNumber > 0;
        return taskNumber;
    }


    /**
     * marks or unmarks task based on given index
     * 3 points of failure:
     * "mark" - User did not follow instructions: NumberFormatException e
     * "mark v" - User did not follow instructions: ArrayIndexOutOfBoundsException
     * "mark 1" - Correct input, but no such Task Index exists: IndexOutOfBoundsException
     */
    public void markUserIndex(String[] keyword){
        int validTaskNumber = INVALID_TASK_NUMBER; //indicates failure condition

        try {
            int taskNumber = assertIndex(keyword);

            duke.ui.TaskFeedback.searchTaskToMark(taskNumber);

            if (duke.logic.Parser.taskNumberIsValid(taskNumber, tasks)) {
                validTaskNumber = taskNumber;
            }
                int index = validTaskNumber - 1;
                Task t = tasks.retrieveTaskDetails(index);
                tasks.markTaskIndex(index);
                duke.ui.TaskFeedback.displayMarkedTask(taskNumber, t.taskIsDone(t), t.getStatusIcon(), t.getTaskDescription());

            BotUndo.addToStack("mark " + keyword[1]); //Undo remembers how to remark task

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
        catch (AssertionError e){
            System.out.println("assert failed. Task number is negative");
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
        int validTaskNumber = INVALID_TASK_NUMBER; //indicates failure condition

        try{
            int taskNumber = assertIndex(keyword);

            if (duke.logic.Parser.taskNumberIsValid(taskNumber, tasks)) {
                validTaskNumber = taskNumber; // Changes value to valid index
            }

            String s = tasks.getTaskBeforeDelete(validTaskNumber); //get String for undo
            BotUndo.addToStack(s); //Undo remembers this

            tasks.deleteTask(validTaskNumber);
            duke.ui.TaskFeedback.displayDeletedTask(s);
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
        catch (AssertionError e){
            System.out.println("assert failed. Task number is negative");
        }

    }

    /**
     * Search for all tasks containing userInput keyword
     * */
    public void keywordFind(String[] keyword){

        String s = keyword[1]; //get task description from 2nd word
        int taskSize = tasks.printTaskList(s);
        duke.ui.TaskFeedback.postTaskSizeFeedback(taskSize);

        BOT_PATIENCE.resetImpatience();
    }

    /**
     * Create and store a "Deadline" task object
     * Stores Inverted command to Undo Stack. Create -> Delete
     * @param userInput is an event when keywords "from " and "to " detected
     */
    public void keywordBy(String userInput){

        Deadline deadline = duke.logic.Parser.keywordBy(userInput);

        tasks.createTask(deadline);
        duke.ui.TaskFeedback.userAddedDeadline(userInput);
        BotUndo.addToStack("delete " + tasks.getTaskSize());
    }

    /**
     * Create and store an "Event" task object
     * Stores Inverted command to Undo Stack. Create -> Delete
     * @param userInput is an event when keywords "from " and "to " detected
     */
    public void keywordFromTo(String userInput){

        Event event = duke.logic.Parser.keywordFromTo(userInput);

        tasks.createTask(event);
        duke.ui.TaskFeedback.userAddedEvent(userInput);
        BotUndo.addToStack("delete " + tasks.getTaskSize());
    }

    /**
     * Create and store a "To do" task object.
     * Stores Inverted command to Undo Stack. Create -> Delete.
     * @param userInput is considered a todo Task because it survived all guard clauses.
     */
    public void keywordTask(String userInput){
        ToDo todo = new ToDo(userInput);
        tasks.createTask(todo);
        duke.ui.TaskFeedback.userAddedTask(userInput);
        BotUndo.addToStack("delete " + tasks.getTaskSize());
    }

    /**
     * Informs and provides feedback user based on the number of tasks they have
     */
    public void handleList(){
        int taskSize = tasks.getTaskSize();

        duke.ui.TaskFeedback.preTaskSizeFeedback(taskSize);
        tasks.printTaskList();
        duke.ui.TaskFeedback.postTaskSizeFeedback(taskSize);

        BOT_PATIENCE.resetImpatience();
    }

    /**
     * Peek Undo Stack to retrieve latest user input
     * Call the scanAdvanceKeywords() to activate command instead of botListensForInput()
     * Pop command from Stack after use
     */
    public void handleUndo(){
        duke.ui.UndoFeedback.acknowledgeUndoCommand();
        String s = BotUndo.peekStack();
        scanAdvanceKeywords(s); //Skips isGeneralKeyword() for efficiency
        BotUndo.removeFromStack();
    }

    public void handleDeleteAll(){
        tasks.deleteAllTask();
        duke.ui.TaskFeedback.cleanedState();
        duke.ui.HelloAndGoodbye.chatBotSaysHello();
    }

    /**
     * Parse keywords to decide which command to run
     * These safe keywords will never trigger DukeExceptions
     * @return false to run advanceKeyword() method, true to skip
     * This optimises the program slightly
     */
    public boolean isGeneralKeyword(String trimmedUserInput){

        if (trimmedUserInput.isBlank() || trimmedUserInput.isEmpty()) {
            BOT_PATIENCE.botBecomesImpatient();
            PatienceFeedback.soundOffPatienceLevel(BOT_PATIENCE.botPatienceMeter());
            return true;
        }

        else if (trimmedUserInput.equalsIgnoreCase("bye") || trimmedUserInput.equalsIgnoreCase("quit")){
            BOT_PATIENCE.quitProgram();
            return true;
        }

        else if (trimmedUserInput.equalsIgnoreCase("list")){
            handleList();
            BOT_PATIENCE.resetImpatience();
            return true;
        }

        else if (trimmedUserInput.equalsIgnoreCase("help") || trimmedUserInput.equalsIgnoreCase("faq")){
            duke.ui.SuggestFeedback.getHelp();
            BOT_PATIENCE.resetImpatience();
            return true;
        }

        else if (trimmedUserInput.equalsIgnoreCase("change bot name")){
            duke.ui.JenkinsUI.acknowledgeChangeBotNameIntent();
            BotName.changeBotName(duke.logic.Parser.tidyUserInput());
            duke.ui.JenkinsUI.changeBotNameSuccess();
            BOT_PATIENCE.resetImpatience();
            return true;
        }

        else if (trimmedUserInput.equalsIgnoreCase("undo")){
            handleUndo();
            BOT_PATIENCE.resetImpatience();
            return true;
        }

        else if (trimmedUserInput.equalsIgnoreCase("delete all")){
            handleDeleteAll();
            BOT_PATIENCE.resetImpatience();
            return true;
        }

        return false; //Remembers user command is not found in general stack
    }

    /**
     * Responds to [mark] [delete], Deadlines [by ], Events [from]... [to] and [todo] Tasks
     * Labelled as advance keywords because every method here can trigger DukeExceptions
     * Undo records userInput in Stack
     */
    public void scanAdvanceKeywords(String trimmedUserInput)  {
        BOT_PATIENCE.resetImpatience();
        boolean isDeadlineEvent = false, isMarkDeleteOrFind = false;

        String[] keyword = trimmedUserInput.split(" ", 2);

        //Level 4-0 Mark
        switch (keyword[0]){
            case "mark":
            case "unmark":
                markUserIndex(keyword);
                isMarkDeleteOrFind = true;
                break;

            case "delete":
                keywordDelete(keyword);
                isMarkDeleteOrFind = true;
                break;

            case "find":
                keywordFind(keyword);
                isMarkDeleteOrFind = true;
                break;
        }

        // Level 4-2 Deadlines
        if (trimmedUserInput.contains("by ")){
            keywordBy(trimmedUserInput);
            isDeadlineEvent = true;
        }

        // Level 4-3 Events
        if (trimmedUserInput.contains("from ") && trimmedUserInput.contains("to ")){
            keywordFromTo(trimmedUserInput);
            isDeadlineEvent = true; //corner case from from to to
        }

        /* Level 4-1 Task To do
          @param isMarkDeleteOrFind prevents user from saving keyword "mark" "delete" as Task
          @param isDeadlineEvent prevents bot from saving userInput 2 times: 1st as Deadline/Event. 2nd as Task
         */
        if (!isMarkDeleteOrFind && !isDeadlineEvent && !trimmedUserInput.equals("undo") && !trimmedUserInput.contains("delete")){
            keywordTask(trimmedUserInput);
        }
    }

    /**
     * listen to user input, and parse keyword
     * General: Quality of life functions
     * Advance: Bot functions as intended
     */
    public void botListensForInput() {
        String userInput = Parser.tidyUserInput();

        if (!isGeneralKeyword(userInput)){
            scanAdvanceKeywords(userInput);
        }
    }
}