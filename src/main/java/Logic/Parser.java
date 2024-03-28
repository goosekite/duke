package Logic;

import java.util.Scanner;

public class Parser {




    public String tidyUserInput(){

        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        return userInput.trim();
    }

}
