package duke.storage;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import duke.logic.BotDateTime;

import duke.exception.DukeException;


public class Storage {

    protected final String FILE_PATH_FOR_TASK_LIST = "./dukeData/duke.txt";
    protected final String FILE_PATH_FOR_DATETIME = "./dukeData/lastAccessed.txt";

    /** Default constructor ensures Task list and time stamp file exists */
    public Storage() {
        ensureTaskListFileExists();
        ensureTimeStampFileExists();
    }

    /**
     * Ensures task list file exists so the other methods do not need to protect it
     * The text file will be locked during run time
     */
    public void ensureTaskListFileExists() {
        // Use getResourceAsStream to load resource from classpath
        try (InputStream taskListStream = getClass().getResourceAsStream(FILE_PATH_FOR_TASK_LIST)) {


            File dataFolder = new File("dukeData");
            dataFolder.mkdirs(); // Create the "data" folder if it doesn't exist
            File newFile = new File(dataFolder, "duke.txt");

            if (newFile.createNewFile()) {
                duke.ui.StorageFeedback.noFolderFound(dataFolder);
            }

            else
            {
                duke.ui.StorageFeedback.foundFileToLoad("task list folder");
            }

            File taskListPathFile = new File(FILE_PATH_FOR_TASK_LIST);

            if (taskListStream == null && taskListPathFile.createNewFile()) {
                duke.ui.StorageFeedback.noFileFound(taskListPathFile, "task list");
            }
            else {
                duke.ui.StorageFeedback.foundFileToLoad("task list file");
            }
        } catch (IOException e) {
            // Error handling
            duke.ui.StorageFeedback.foundFileToLoad("task list file");
        }
    }

    /**
     * Ensures Time Stamp file exists so the other methods do not need to protect it
     * The text file will be locked during run time
     */
    public void ensureTimeStampFileExists() {
        // Similar logic as ensureTaskListFileExists
        try (InputStream dateTimeStream = getClass().getResourceAsStream(FILE_PATH_FOR_DATETIME)) {
            File timeStampPathFile = new File(FILE_PATH_FOR_DATETIME);
            if (dateTimeStream == null && timeStampPathFile.createNewFile()) {
                System.out.println("Time Stamp File created: " + timeStampPathFile.getName());
            }
            else{
                duke.ui.StorageFeedback.foundFileToLoad("time stamp file");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Read file data and records it in queue
     * @return Queue so Duke can reconstruct task list sequentially
     */
    public Queue<String> loadData(){
        Queue<String> queue = new LinkedList<>();

        try {
            File file = new File(FILE_PATH_FOR_TASK_LIST); // Create a File object representing the file
            Scanner sc = new Scanner(file); // Create a Scanner Object to read text file

            while (sc.hasNextLine()) {
                String line = sc.nextLine(); // Read a line and store it in a string
                queue.offer(line); //Store string in the queue
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e); //Should not trigger because default file path is set
        }

        return queue;
    }

    /**
     * Saves the string of commands to be retrieved when user restarts program to a specified path
     * @param content commands to be recorded to recreate current task list
     */
    public void saveDataToStorage(String content) {

        // Create the file if it doesn't exist
        try {
            File file = new File(FILE_PATH_FOR_TASK_LIST);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            }

            // Create a FileWriter object with the specified file path
            FileWriter writer = new FileWriter(FILE_PATH_FOR_TASK_LIST);

            //Part where I need get data
            writer.write(content);

            // Close the writer
            writer.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e); //Should not trigger because default file path is set
        } catch (IOException e) {
            DukeException.getError(DukeException.filePathDoesNotExist());
        }

    }

    /**
     * Reads Time stamp from file
     */
    public void loadDataTimeStamp() {
        File file = new File(FILE_PATH_FOR_DATETIME); // Create a File object representing the file

        // Get time now
        String formattedDateTime = BotDateTime.now();

        //Read & inform user last access date
        try {
            if (file.createNewFile()) {
                duke.ui.DateTimeFeedback.cantFindTimeStampSoIWillRecreate(formattedDateTime);
                saveDateTimeStamp();
            }

            Scanner sc = new Scanner(file); // Create a Scanner Object to read text file

            while (sc.hasNextLine()) {
                String loadedTimeStamp = sc.nextLine(); // Read a line and store it in a string
                duke.ui.StorageFeedback.loadDateTimeSuccessfully(loadedTimeStamp); // Informs user load Date Time was successful
            }

        } catch (IOException e) {
            throw new RuntimeException(e); //Should not trigger because default file path is set
        }
    }

    /**
     * Reads Time stamp from file
     */
    public void saveDateTimeStamp() {
        File file = new File(FILE_PATH_FOR_DATETIME);

        // Get time now
        String formattedDateTime = BotDateTime.now();

        try {
            if (file.createNewFile()) {
                duke.ui.DateTimeFeedback.creatingTimeStamp();
            }

            // let writer know file location
            FileWriter writer = new FileWriter(FILE_PATH_FOR_DATETIME);

            //Overwrite file with DateTime
            writer.write(formattedDateTime);

            writer.close();

            duke.ui.StorageFeedback.savedTimeStampSuccessfully(formattedDateTime);

        } catch (IOException e) {
            DukeException.getError(DukeException.filePathDoesNotExist());
        }
    }
}
