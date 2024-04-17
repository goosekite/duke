package duke.logic;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    /** Default constructor */
    public Parser(){
    }

    /** Listen and tidies user input */
    public static String tidyUserInput(){
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        return userInput.trim();
    }

    /** Convert task number to string from an integer input */
    public int getTaskNumber(String keyword){
            return Integer.parseInt(keyword);
    }

    /** Checks if task number is a positive number & smaller than task size */
    public static boolean taskNumberIsValid(int taskNo, Task tasks) {
        return taskNo <= tasks.getTaskSize() && taskNo > 0;
    }

    /**
     * Filters validated userInput into 2 parameters to create deadline object
     * @param userInput is made of eventDescription and dueDate
     * @return deadline task object
     */
    public static Deadline keywordBy(String userInput) {
        Pattern pattern = Pattern.compile("(.+) by (.+)");
        Matcher matcher = pattern.matcher(userInput);

        duke.ui.TaskFeedback.searchByDate();
        if (matcher.find()) {
            String eventDescription = matcher.group(1);
            String dueDate = matcher.group(2);

            if (duke.logic.BotDateTime.stringIsValidDateFormat(dueDate)){
                dueDate = duke.logic.BotDateTime.saveDateTime(dueDate);
            }

            return new Deadline(eventDescription,dueDate);
        }

        else {
            duke.ui.SuggestFeedback.helpUsingByKeyword();
            return null;
        }
    }

    /**
     * Filters validated userInput into 3 parameters to create event object
     * @param userInput is made of eventDescription, start and end
     * @return event task object
     */
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
    }

}
