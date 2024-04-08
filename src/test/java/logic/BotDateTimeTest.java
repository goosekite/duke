package logic;

import duke.logic.BotDateTime;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BotDateTimeTest {
    LocalDateTime dateTime = LocalDateTime.of(1994,12, 6,18,0);
//    LocalDate dateOnly = LocalDate.parse("1994-12-01");
    LocalDate[] dates = {
//            LocalDate.parse("07 12 1994"),
            LocalDate.parse("1994-12-02"),
    };
//    public void TestConvertLocalDateTimeToString(){
//        assertEquals("6 Dec 1994", BotDateTime.convertStringToLocalDateTime());
//    }

    @Test
    public void TestDateOnly() {
        assertEquals("02 December 1994", BotDateTime.convertLocalDateToString(dates[0]));
//        assertEquals("01 December 1994", BotDateTime.convertLocalDateToString(dates[1]));
    }

    @Test
    public void TestConvertStringToLocalDateTime() {
        assertEquals("06 December 1994, 06:00PM", BotDateTime.convertStringToLocalDateTime(dateTime));
    }
}
