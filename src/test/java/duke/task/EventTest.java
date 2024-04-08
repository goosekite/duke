package duke.task;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    Event event = new Event("eat", "noon", "1pm");

    @Test
    public void testCreateEvent(){
        assertEquals("[E][ ] eat (from: noon to: 1pm )", event.toString());
    }

    @Test
    public void testEventToUserInput(){
        assertEquals("eat from noon to 1pm", event.convertToCommand());
    }

}
