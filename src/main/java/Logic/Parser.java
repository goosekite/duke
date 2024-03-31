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

    public void addToStack(){
        String s = "ASD";
        undo.push(s);
    }
    public void addToStack(String s){
        undo.push(s);
    }

    public void removeFromStack(){
        if (!undo.empty()) {

            undo.pop();
        }

        else{
            System.out.println("Stack is empty!");
        }
    }



    public String peekStack(){
        if (!undo.isEmpty()){
            String peek = undo.peek();
            return peek;
        }

        return "";
//        return ("There's nothing more we can undo. Life must go on");
    }



}
