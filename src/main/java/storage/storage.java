package storage;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import exception.dukeexception;

public class storage {

    protected String filePath;

    /** Creates a constructor and sets a default file path */
    public storage() {
        this.filePath = "src/main/data/duke.txt"; // Default file path
    }

    /**
     * Creates a queue to and search for storage location
     * Reads file using scanner and inserts every line into Queue
     * @return Queue with userInput so Duke can reconstruct task list
     */
    public Queue<String> loadData(){
        Queue<String> queue = new LinkedList<>();

        if(filePath == null) {
            System.err.println("File path is not initialized.");
        }

        try {
            File file = new File(filePath); // Create a File object representing the file
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

        File file = new File(filePath);
        // Create the file if it doesn't exist
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");

                // Create a FileWriter object with the specified file path
                FileWriter writer = new FileWriter(filePath);

                //Part where I need get data
                writer.write(content);

                // Close the writer
                writer.close();

                System.out.println("Successfully wrote to the file.");
            }
        } catch (IOException e) {
            dukeexception.getError(dukeexception.filePathDoesNotExist());
        }
    }

}
