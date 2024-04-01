package Logic;

import TaskList.Task;

import java.util.Scanner;
import java.util.Stack;


public class Parser {

    Stack<String> undo = new Stack<>();

    public Parser(){
    }

    public String tidyUserInput(){

        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        return userInput.trim();
    }

    public int getTaskNumber(String keyword){
            return Integer.parseInt(keyword);
    }

    public boolean taskNumberIsValid(int taskNo, Task tasks) {
        return taskNo <= tasks.getTaskSize() && taskNo > 0;
    }

    /**
     * @param s holds the userInput to enable UNDO
     */
    public void addToUndoStack(String s){
        undo.push(s);
    }

    /**
     * Extracts latest command from Undo Stack
     */
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
//        return ("There's nothing more we can undo. Life must go on");
    }



}
