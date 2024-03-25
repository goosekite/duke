import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class Storage {
    protected String filePath = "src/main/data/duke.txt";

    public Storage() {
        this.filePath = "src/main/data/duke.txt"; // Default file path
    }

    public void tryStorage(){
        if(filePath == null) {
            System.err.println("File path is not initialized.");
            return;
        }
        try {
            // Create a File object representing the file
            File file = new File(filePath);

            // Create the file if it doesn't exist
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }

            // Create a FileWriter object with the specified file path
            FileWriter writer = new FileWriter(filePath);

            // Write "a" to the file
            writer.write("a");

            // Close the writer
            writer.close();

            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
