package duke.logic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BotDateTime {
    public static String now(){
        return convertStringToLocalDateTime(LocalDateTime.now());
    }


    public static String convertStringToLocalDateTime(LocalDateTime dateTime){
        try {
            DateTimeFormatter asLongDate = DateTimeFormatter.ofPattern("dd MMMM yyyy, ha");
            return dateTime.format(asLongDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String convertLocalDateToString(LocalDate date){
        try {
            DateTimeFormatter asLongDate = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            return date.format(asLongDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
