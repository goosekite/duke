package duke.logic;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {


    /** Creates a default constructor */
    public Parser(){
    }

    /** tidies user input */
    public static String tidyUserInput(){
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        return userInput.trim();
    }

    /** convert task number to string from an integer input */
    public int getTaskNumber(String keyword){
            return Integer.parseInt(keyword);
    }

    /** Checks if task number is a positive number & smaller than task size */
    public boolean taskNumberIsValid(int taskNo, Task tasks) {
        return taskNo <= tasks.getTaskSize() && taskNo > 0;
    }


    public static Deadline keywordBy(String userInput) {
        Pattern pattern = Pattern.compile("(.+) by (.+)");
        Matcher matcher = pattern.matcher(userInput);

        duke.ui.TaskFeedback.searchByDate();
        if (matcher.find()) {
            String eventDescriptionString = matcher.group(1);
            String deadlineString = matcher.group(2);

            if (duke.logic.BotDateTime.stringIsValidDateFormat(deadlineString)){
                deadlineString = duke.logic.BotDateTime.saveDateTime(deadlineString);
            }

            return new Deadline(eventDescriptionString,deadlineString);
        }

        else {
            duke.ui.SuggestFeedback.helpUsingByKeyword();
            return null;
        }
    }

    public static Event keywordFromTo(String userInput) {
        Pattern pattern = Pattern.compile("(.+) from (.+) to (.+)");
        Matcher matcher = pattern.matcher(userInput);

        duke.ui.TaskFeedback.searchFromToDate();

        if (matcher.find()) {

            String eventDescription = matcher.group(1);
            String start = matcher.group(2);
            String end = matcher.group(3);

            if (duke.logic.BotDateTime.stringIsValidDateFormat(start) && duke.logic.BotDateTime.stringIsValidDateFormat(end)) {
                start = duke.logic.BotDateTime.saveDateTime(start);
                end = duke.logic.BotDateTime.saveDateTime(end);
            }

            return new Event(eventDescription, start, end);
        }
            else {
                duke.ui.SuggestFeedback.helpUsingFromToKeyword();
                return null;
            }


//
//            Event event = new Event(eventDescription, start, end);
//            tasks.createTask(event);
//            duke.ui.TaskFeedback.userAddedEvent(userInput);
//            BotUndo.addToStack("delete " + tasks.getTaskSize());
//        }

    }


}
