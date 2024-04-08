package ui;

import duke.logic.BotName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JenkinsUITest {


    @Test
    public void testChangeBotName(){
        String userInput = "Kiwi";
        BotName.changeBotName(userInput);
        assertEquals("Kiwi", BotName.getChatBotName());

    }
}
