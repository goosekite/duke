package duke.task;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    ToDo task = new ToDo("eat");

    @Test
    public void testCreateEvent(){
        assertEquals("[T][ ] eat", task.toString());
    }

    @Test
    public void testEventToUserInput(){
        assertEquals("eat", task.convertToCommand());
    }

}
