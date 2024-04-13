package duke.task;

import java.util.ArrayList;


/** Parent Class for Deadline, Event and To do */
public class Task {
    private ArrayList<Task> tasksStorage;

    protected String description;
    protected boolean isDone;

    private static int taskSize = 0;

    /** Creates an array list by calling this default constructor */
    public Task(){
        this.tasksStorage = new ArrayList<>();
    }

    /**
     * Creates a task object when this constructor is called
     * @param dukeUserInput Stores task description and sets task to be undone by default
     */
    public Task(String dukeUserInput) {
        this.description = dukeUserInput;
        this.isDone = false;
    }

    /**
     * Created to be overridden by child classes [deadline], [event] & [todo]
     * @return appropriate [task type][mark status][task description]
     */
    public String toString(){
        return "[" + getStatusIcon() + "] " + this.description;
    }

    /**
     * Created to be overridden by child classes [deadline], [event] & [todo]
     * Translates task list syntax back to user command
     * @return reconstructed userInput for Storage class
     */
    public String convertToCommand(){
        return "";
    }

    /**
     * Adds a task to existing arraylist and increase task size by 1
     * @param t Task details given by user
     */
    public void createTask(Task t){
        this.tasksStorage.add(t);
        taskSize++;
    }

    /**
     * Deletes a task from our arraylist and decrease task size by 1
     * @param taskNo Task index given by user
     */
    public void deleteTask(int taskNo) {
        tasksStorage.remove(taskNo - 1); //When user says delete 1, means delete index 0
        taskSize--;
    }

    /**
     * Access the boolean value of isDone variable
     * @return marked icon or a space
     */
    public String getStatusIcon() {
        return (getIsDone() ? "X" : " "); // mark done task with X
    }

    public int getTaskSize(){
        return taskSize;
    }

    /** prints task list exhaustively based on keyword "list" */
    public void printTaskList(){
        if (taskSize == 0){ //guard clause
            return;
        }

        if (getTaskSize() == 1){
            System.out.print(tasksStorage.get(0).toString() + System.lineSeparator());
        }

         else if (getTaskSize() < 5){
            for (int i = 0; i < tasksStorage.size(); i++) {
                Task t = tasksStorage.get(i);
                System.out.print(i + 1 + " " + t.toString() + System.lineSeparator());
            }
        }

         else{
            for (int i = 0; i < tasksStorage.size(); i++) {
                Task t = tasksStorage.get(i);
                System.out.print(i + 1 + " " + t.toString() + System.lineSeparator());
            }
        }
    }

    /** prints task list exhaustively based on keyword "find x"*/
    public int printTaskList(String keywordToFind){
        int taskCount = -1;

        if (taskSize == 0){ //guard clause
            System.out.println("nothing found");
            return taskCount;
        }

        taskCount = 0; //start counting from 0 after guard clause

        for (int i = 0; i < tasksStorage.size(); i++) {
            Task t = tasksStorage.get(i); //get task from taskStorage

            if (t.description.contains(keywordToFind)) {
                System.out.print(i + 1 + " " + t + System.lineSeparator());
                taskCount++;
            }
        }
        return taskCount;
    }

    /**
     * Use a string builder to translate all tasks to user input command used to create said list
     * @return empty string OR exhaustive list of user commands to create current list
     */
    public String printTaskListForRecording(){
        StringBuilder sb = new StringBuilder();
        if (taskSize == 0){ //guard clause
            return "";
        }

        //https://stackoverflow.com/questions/14534767/how-to-append-a-newline-to-stringbuilder
        for (Task t : tasksStorage) {
            sb.append(t.convertToCommand());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    //Retrieve Task description
    public String getTaskDescription(){
        return description;
    }

    //Get temporary string for undo method
    public String getTaskBeforeDelete(int taskNumber){
        return String.valueOf((tasksStorage.get(taskNumber-1)));
    }

    //Retrieve & Update Boolean isDone
    public boolean getIsDone(){
        return isDone;
    }

    /**
     * @param task checks if selected task is marked or not
     * @return String as feedback if selected task isDone value is true/false
     */
    public String taskIsDone(Task task){
        return task.isDone ? "done!" : "undone!";
    }

    /**
     * Inverts selected task's boolean value [isDone]
     * @param taskIndex Index used to locate the correct task before flipping its boolean value
     */
    public void markTaskIndex(int taskIndex){
            Task task = tasksStorage.get(taskIndex);
            task.isDone = !task.isDone;
    }

    /**
     * Only mark keyword uses this
     * @param taskIndex Index used to locate the correct task
     * @return task object based on user specified index
     */
    public Task retrieveTaskDetails(int taskIndex){
        return tasksStorage.get(taskIndex);
    }


}
