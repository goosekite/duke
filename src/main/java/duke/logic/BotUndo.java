package duke.logic;

import java.util.Stack;

public class BotUndo {

    static Stack<String> undo = new Stack<>(); //a stack container to hold all user input (as string) to undo actions

    /**
     * @param s holds the userInput to enable UNDO
     */
    public static void addToStack(String s){
        undo.push(s);
    }

    /** Extracts latest command from Undo Stack */
    public static void removeFromStack(){
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
    public static String peekStack(){
        if (!undo.isEmpty()){
            return undo.peek();
        }
        return "";
    }
}
