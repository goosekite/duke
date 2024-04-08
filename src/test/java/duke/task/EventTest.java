package duke.task;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {
    @Test
    public void testCreateDeadline(){
        Deadline deadline = new Deadline("eat", "noon");
        assertEquals("[D][ ] eat (by: noon)", deadline.toString());
    }


}
