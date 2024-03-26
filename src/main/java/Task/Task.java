import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public Task(String dukeUserInput) {
        this.description = dukeUserInput;
        this.isDone = false;
    }

    public void createTask(Task t){
        this.tasksStorage.add(t);
        taskSize++;
    }

    public void deleteTask(int taskNo) throws DukeException{

        if (isValidTaskNumber(taskNo)) {
            System.out.println("I've deleted " + tasksStorage.get(taskNo - 1));
            tasksStorage.remove(taskNo - 1);
            taskSize--;
        }

        else{
            System.out.println("Sorry, There's no such task number");
        }

    }

    public static boolean isValidTaskNumber(int taskNo) {
        //System.out.println("yes is valid task no");
        return taskNo <= taskSize && taskNo > 0;
    }

    //Methods to filter Information
    public String getStatusIcon() {
        return (getIsDone() ? "X" : " "); // mark done task with X
    }

    public static int getTaskSize(){
        return taskSize;
    }

    public void printWordDiary(){
        if (taskSize == 0){
            System.out.println("Sorry, I found no task :(");
            return;
        }

        //1 way to improve is having an enum to store high medium low amount of task.
        if (getTaskSize() == 1){
            System.out.print(this.tasksStorage.get(0).toString() + "\n");
            System.out.println("Marvelous, Only 1 outstanding task left to complete!");
        }

        else {
            System.out.println("Right away! I found " + getTaskSize() + " tasks!");
            for (int i = 0; i < this.tasksStorage.size(); i++) {
                Task t = this.tasksStorage.get(i);
                System.out.print(i + 1 + " " + t.toString() + "\n");
            }
        }
    }

    //Retrieve String description
    public String getTaskDescription(){
        return description;
    }


    //Retrieve & Update Boolean isDone
    public boolean getIsDone(){
        return isDone;
    }
    public void markAsDone(String s){

        // Define a regular expression pattern to match numbers
        Pattern pattern = Pattern.compile("\\d+");

        // Create a Matcher object to find matches of the pattern in the input string
        Matcher matcher = pattern.matcher(s);

        // Check if any matches are found
        if (matcher.find()) {
            // Extract the matched number
            String numberStr = matcher.group();
            int index = Integer.parseInt(numberStr);
            int counter = index--;

            System.out.println("Searching for task " + counter + "...");

            // Check if the index is valid
            if (index >= 0 && index < tasksStorage.size()) {
                // Toggle the isDone flag of the specified task
                Task task = tasksStorage.get(index);
                task.isDone = !task.isDone;

                System.out.println("Task " + counter + " marked as " + (task.isDone ? "done!" : "undone!"));

                System.out.print(counter + ".");
                System.out.print("[" + task.getStatusIcon() + "] ");
                System.out.println(task.getTaskDescription());

            } else {
                
                System.out.println("We don't have that task yet!");
            }
        } else {
            System.out.println("I found nothing to mark :(");
        }


    }


}
