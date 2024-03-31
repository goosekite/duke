package Storage;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

import Exception.DukeException;

public class Storage {
    protected String filePath;

    public Storage() {
        this.filePath = "src/main/data/duke.txt"; // Default file path
    }

    public void storageDooDad(){
//        if(filePath == null) {
//            System.err.println("File path is not initialized.");
//            return;
//        }

        // Create a File object representing the file
        File file = new File(filePath);
        saveDataToStorage(file);
    }

    public void saveDataToStorage(File file) {
        // Create the file if it doesn't exist
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");

                // Create a FileWriter object with the specified file path
                FileWriter writer = new FileWriter(filePath);

                // Write "a" to the file

                //Part where I need get data
                writer.write("a");

                // Close the writer
                writer.close();

                System.out.println("Successfully wrote to the file.");
            }
        } catch (IOException e) {
            DukeException.getError(DukeException.filePathDoesNotExist());
        }
    }

    public void saveDataToStorage(){
        //does nothing
    }


}
