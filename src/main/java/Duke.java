import Storage.Storage;
import Task.*;
import UI.JenkinsUI;
import Logic.*;
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

    public boolean botIsAlive(){
        return (botStatus.chatBotIsOnline() && botStatus.isBotPatient());
    }

    /**
     * Bot listens for input until it loses patience user shuts it off
     */
    public void run() throws DukeException {
        ui.chatBotSaysHello();

        do{
            listenForInput();
        }
        while (botIsAlive());

        botStatus.quitProgram();
        ui.chatBotSaysBye();
    }

    public void markUserIndex(String index){
        task.markAsDone(index);
    }

    public void keywordDelete(String[] keyword) throws DukeException {
        try {
            int taskNumber = Integer.parseInt(keyword[1]); //problems comes from converting string to number
            task.deleteTask(taskNumber);
        } catch (NumberFormatException e) {
            DukeException.getError(DukeException.invalidTaskNumber());
        } catch (IllegalArgumentException e) {
            DukeException.getError(DukeException.expectIntegerButInputIsString());
        } catch (ArrayIndexOutOfBoundsException e){
            DukeException.getError(DukeException.arrayOutOfBounds());
        }
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
     * Respond to inputs which are not related to task, events nor deadline
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
            return true;
        }

        else if (trimmedUserInput.equalsIgnoreCase("help") || trimmedUserInput.equalsIgnoreCase("faq")){
            ui.getHelp();
            return true;
        }

        else if (trimmedUserInput.equalsIgnoreCase("change bot name")){
            ui.changeChatBotName();
            return true;
        }

        return false;
    }

    /** Response to [mark] [delete], Deadlines [by ], Events [from].. [to] and Tasks
     * @throws DukeException array out of bounds exception is caused by lone input "mark", "unmark" and "delete"
     */
    public void scanAdvanceKeywords(String userInput) throws DukeException {
        botStatus.resetImpatience();
        boolean isMarkScenario = false, isDeadlineEvent = false, loopHasNoError = true; //Both flags must be false to confirm keyword [Task]

        String[] keyword = userInput.split(" ", 2);

        //Level 4-0 Mark
        switch (keyword[0]){
            case "mark":
            case "unmark":
                try{
                    markUserIndex(keyword[1]);
                    isMarkScenario = true;
                }
                catch (ArrayIndexOutOfBoundsException e){
                    DukeException.getError(DukeException.arrayOutOfBounds());
                    loopHasNoError = false;
                    ui.getErrorHelpMark();
                }
                break;

            case "delete":
                try{
                    keywordDelete(keyword);
                    isMarkScenario = true;
                }
                catch (ArrayIndexOutOfBoundsException e){
                    DukeException.getError(DukeException.arrayOutOfBounds());
                    loopHasNoError = false;
                    ui.getErrorHelpDelete();
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

        // Level 4-1 Task To do
        if (!isMarkScenario && !isDeadlineEvent && loopHasNoError){
            keywordTask(userInput);
        }
    }

    public void listenForInput() throws DukeException {
        ui.drawLine();

        String userInput = ui.cleanUserCommand();
        
        if (!isGeneralKeyword(userInput)){
            scanAdvanceKeywords(userInput);
        }


    }

    public static void main(String[] args) throws DukeException {
        new Duke().run();
    }
}