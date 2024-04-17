package duke.ui;

import java.io.File;

public class StorageFeedback {

    public static void savedTimeStampSuccessfully(String dateTime) {
        System.out.println("Saved data successfully to memory on " + dateTime);
    }

    public static void loadDateTimeSuccessfully(String dateTime) {
        System.out.println("We've last reviewed our tasks on " + dateTime);
        System.out.println("loading...");
    }

    public static void foundFileToLoad(String context) {
        System.out.println("I've found " + context + " to load! Wonderful!");
    }

    public static void noFileFound(File f, String context){
        System.out.println("I've created " + f.getName() + " because " + context + " doesn't exist in our database");
    }

    public static void noFolderFound(File f){
        System.out.println("I've created " + f.getName() + " because " + "the folder" + " doesn't exist in our database");
    }


}
