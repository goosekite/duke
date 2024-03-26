package UI;

import java.util.Scanner;

public class JenkinsUI {
    protected String chatBotName = "Jenkins";
    protected String userInput = "";
    public String getChatBotName(){
        return chatBotName;
    }

    public String readCommand(){
        Scanner sc = new Scanner(System.in);
        userInput = sc.nextLine();
        return userInput.trim();
    }

    public void printLogo(){
        System.out.println("       _            _    _           ");
        System.out.println("      | |          | |  (_)          ");
        System.out.println("      | | ___ _ __ | | ___ _ __  ___ ");
        System.out.println("  _   | |/ _ \\ '_ \\| |/ / | '_ \\/ __|");
        System.out.println(" | |__| |  __/ | | |   <| | | | \\__ \\");
        System.out.println("  \\____/ \\___|_| |_|_|\\_\\_|_| |_|___/");
    }

    //Extra 2 - just a drawing a line
    public void drawLine() {
        System.out.println("____________________________________________________________");
    }

    //Level 0-1 Rename
    public void setChatBotName(String userInput){
        chatBotName = userInput;
    }

    public void chatBotSaysHello(){
        System.out.println(getChatBotName() + ": Hello! you may call me " + getChatBotName() + ". I remember it, so you don't have to!");
        System.out.println("What can I do for you?");
    }

    public void chatBotSaysBye(){
        System.out.print(getChatBotName() + ": GoodBye, Stay Safe. I hope to see you again soon!\n");
    }

    public void getHelp(){
        System.out.println(getChatBotName() + ": Certainly! Here are all commands that I can understand:");
        System.out.println("help or {.} - prints this help list to help recall");
        System.out.println("bye - exits program --- tap {ENTER} 3 times)");
        System.out.println("tap {ENTER} 3 times to exit program quickly");

        drawLine();

        System.out.println("[Task] - records Tasks");
        System.out.println("[Task] by [timing] - records Deadlines");
        System.out.println("[Task] from [time] to [time] - records Events");

        System.out.println("mark OR unmark [Task number] - Marks/Unmarks Task number");
        System.out.println("list - prints all recorded events");
        System.out.println("Delete [Task number] - Delete Task");
    }

    /**
     * Test if bot should continue to listen for userInput
     * @param botPatience bot patience meter
     * feedback in text accordingly
     */
    public void patienceFeedback(int botPatience){
        if (botPatience > 1) {
            System.out.println("Sorry, I did not receive any commands");
            System.out.println("I will leave if there's no one around. " + botPatience + " more chance");
        }

        else if (botPatience == 1) {
            System.out.println("Last Chance! Please issue a command or I will leave!");
        }

        else{
            System.out.println("Looks like no one's here. Good bye");
        }
    }

    public void changeChatBotName(){
        System.out.println(getChatBotName() + ": Sure! Please key in my new name");
        Scanner sc = new Scanner(System.in); //open scanner!
        userInput = sc.nextLine();

        String name = userInput.trim();
        setChatBotName(name);

        System.out.println(getChatBotName() + ": Right away!");
    }


    public void echoUserInput(String s){
        System.out.println("added: " + s);
    }


}
