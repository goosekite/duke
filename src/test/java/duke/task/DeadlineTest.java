package duke.task;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {
    Deadline deadline = new Deadline("", "noon");

    @Test
    public void testCreateDeadline(){
        assertEquals("[D][ ] eat (by: noon)", deadline.toString());
    }

    @Test
    public void testDeadlineToUserInput(){
        assertEquals("eat by noon", deadline.convertToCommand());
    }

}
