package duke.logic;

import duke.task.Task;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Stack;
import java.time.LocalDateTime;

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



}
