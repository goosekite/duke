package duke.ui;

public class TaskFeedback {

    public static void preTaskSizeFeedback(int taskSize){
        if (taskSize > 1) {
            System.out.println("Right away! I found " + taskSize + " tasks!");
        }

        if (taskSize >= 5){
            System.out.println("Oh my! you have " + taskSize + " tasks!");
        }
    }

    public static void postTaskSizeFeedback(int taskSize){
        if (taskSize == 0){
            System.out.println("Sorry, I found no task :(");
        }

        else if (taskSize == 1){
            System.out.println("Marvelous, Only 1 outstanding task left to complete!");
        }
    }

    public static void searchTaskToMark(int taskNumber){
        System.out.println("Searching for task " + taskNumber + "...");
    }

    public static void searchByDate(){
        System.out.print("Keyword by found! ");
    }

    public static void searchFromToDate(){
        System.out.print("Keyword Event..From..To.. found! ");
    }

    public static void displayMarkedTask(int index, String doneStatus, String taskIcon, String taskDescription){
        System.out.println("Task " + index + " marked as " + doneStatus);
        System.out.print(index + ".");
        System.out.print("[" + taskIcon + "] ");
        System.out.println(taskDescription);
    }

    public static void userAddedTask(String taskDescription){
        System.out.println("Task to do added: " + taskDescription);
    }

    public static void userAddedEvent(String taskDescription){
        System.out.println("Event added: " + taskDescription);
    }

    public static void userAddedDeadline(String taskDescription){
        System.out.println("Deadline added: " + taskDescription);
    }

    public static void displayDeletedTask(String taskDescription){
        System.out.println("Certainly, I've deleted " + taskDescription);
    }

    public static void cleanedState(){
        System.out.println("As you wish! Let's start over in a clean state!");
    }

}
