package duke.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class BotDateTime {

    static final String EXPECTED_DATE_TIME_FORMAT  = "yyyy-MM-dd HHmm";
    public static String now(){
        return getStringFromLocalDateTime(LocalDateTime.now());
    }

    /**
     * create datetime for lastAccessed.txt
     * @param dateTime dateTime.now();
     * @return formatted date
     */
    public static String getStringFromLocalDateTime(LocalDateTime dateTime){
        try {
            DateTimeFormatter asLongDate = DateTimeFormatter.ofPattern(EXPECTED_DATE_TIME_FORMAT);
            return dateTime.format(asLongDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * checks if string can convert to date.
     * @param dateTime
     * @return
     */
    public static boolean stringIsValidDateFormat(String dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(EXPECTED_DATE_TIME_FORMAT);

        try {
            // Parse the user input into a Date object
            Date date = dateFormat.parse(dateTime);
            return true;
        } catch (Exception e) {
            System.out.println("Invalid date format entered.");
            return false;
        }

    }

    /**
     * This string becomes date, convert it, become string again
     */
    public static String saveDateTime(String dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HHmm");


        try {
            Date date = dateFormat.parse(dateTime);
            System.out.println(dateFormat.format(date) + " YESSS");
            return dateFormat.format(date);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    //Only used in test case
    public static String convertLocalDateToString(LocalDate date){
        try {
            DateTimeFormatter asLongDate = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            return date.format(asLongDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
