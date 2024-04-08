package duke.logic;

import duke.task.Task;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Stack;
import java.time.LocalDateTime;

public class Parser {

    Stack<String> undo = new Stack<>();

    /** Creates a default constructor */
    public Parser(){
    }

    public static String getDateTimeNow(){
        LocalDateTime dateTimeNow = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTimeNow.format(formatter);
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

    /**
     * @param s holds the userInput to enable UNDO
     */
    public void addToUndoStack(String s){
        undo.push(s);
    }

    /** Extracts latest command from Undo Stack */
    public void removeFromUndoStack(){
        if (undo.isEmpty()){
            System.out.println("Stack is empty!");
        }
        else {
            undo.pop();
        }
    }

    /**
     * @return if there is a value from Undo Stack
     */
    public String peekUndoStack(){
        if (!undo.isEmpty()){
            return undo.peek();
        }
        return "";
    }

}