package duke.ui;

public class DateTimeFeedback {
    public static void creatingTimeStamp() {
        System.out.println("Creating timestamp.. ");
    }

    public static void cantFindTimeStampSoIWillRecreate(String dateTime) {
        System.out.println("Oh dear, I can't find when we've last saved our file");
        System.out.println("No worries, I'll mark it as now on " + dateTime);
    }
}
