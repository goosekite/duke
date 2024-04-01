package TaskList;

import java.util.ArrayList;


/**
 * Parent Class for Deadline, Event and To do
 */
public class Task {
    private ArrayList<Task> tasksStorage;

    protected String description;
    protected boolean isDone;

    private static int taskSize = 0;

    //Constructors
    public Task(){
        this.tasksStorage = new ArrayList<>();
    }

    public String toString(){
        return "[" + getStatusIcon() + "] " + this.description;
    }

    public String convertToCommand(){
        return "";
    }

    public Task(String dukeUserInput) {
        this.description = dukeUserInput;
        this.isDone = false;
    }

    public void createTask(Task t){
        this.tasksStorage.add(t);
        taskSize++;
    }


    public void deleteTask(int taskNo) {
        tasksStorage.remove(taskNo - 1);
        taskSize--;
    }

    public String getStatusIcon() {
        return (getIsDone() ? "X" : " "); // mark done task with X
    }

    public int getTaskSize(){
        return taskSize;
    }

    public void printTaskList(){
        if (taskSize == 0){
            return;
        }

        //1 way to improve is having an enum to store high medium low amount of task.
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

    public String printTaskListforRecording(){
        StringBuilder sb = new StringBuilder();

        if (taskSize == 0){
            return "";
        }

        //https://stackoverflow.com/questions/14534767/how-to-append-a-newline-to-stringbuilder
        for (int i = 0; i < tasksStorage.size(); i++) {
            Task t = tasksStorage.get(i);
            sb.append(t.toString());
            sb.append(System.lineSeparator());
        }

        return sb.toString();

    }


    //Retrieve String description
    public String getTaskDescription(){
        return description;
    }

    //for undo method
    public String getTaskBeforeDelete(int taskNumber){
        return String.valueOf((tasksStorage.get(taskNumber-1)));
    }

    //Retrieve & Update Boolean isDone
    public boolean getIsDone(){
        return isDone;
    }


    public String taskIsDone(Task task){
        return task.isDone ? "done!" : "undone!";
    }

    public void markTaskIndex(int taskIndex){
            Task task = tasksStorage.get(taskIndex);
            task.isDone = !task.isDone;
    }

    public Task retrieveTaskDetails(int taskIndex){
        return tasksStorage.get(taskIndex);
    }


}
