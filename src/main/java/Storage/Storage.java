package Storage;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import Exception.DukeException;

public class Storage {

    protected String filePath;


    public Storage() {
        this.filePath = "src/main/data/duke.txt"; // Default file path
    }

    public Queue<String> loadData(){
        Queue<String> queue = new LinkedList<>();


        if(filePath == null) {
            System.err.println("File path is not initialized.");
        }

        try {

            // Create a File object representing the file
            File file = new File(filePath);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                queue.offer(line);
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return queue;

    }

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
            DukeException.getError(DukeException.filePathDoesNotExist());
        }
    }




}
