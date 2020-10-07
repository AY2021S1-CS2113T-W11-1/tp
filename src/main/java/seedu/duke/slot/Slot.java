package seedu.slot;

import java.util.ArrayList;
import java.time.LocalTime;

public class Slot {
    
    public static final String MON = "Monday";
    public static final String TUE = "Tuesday";
    public static final String WED = "Wednesday";
    public static final String THU = "Thursday";
    public static final String FRI = "Friday";
    public static final String SAT = "Saturday";
    public static final String SUN = "Sunday";

    public static final String[] days = { MON, TUE, WED, THU, FRI, SAT, SUN };

    private LocalTime startTime;
    private LocalTime endTime;
    private String title;
    private String day;

    public Slot(LocalTime startTimeInput, LocalTime endTimeInput, String dayInput, String titleInput) {
        startTime = startTimeInput;
        endTime = endTimeInput;
        title = titleInput;
        day = dayInput;
    }

    public void setStartTime(LocalTime timeInput) {
        startTime = timeInput;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setEndTime(LocalTime timeInput) {
        endTime = timeInput;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setTitle(String titleInput) {
        title = titleInput;
    }

    public String getTitle() {
        return title;
    }

    public void setDay(String dayInput) {
        day = dayInput;
    }

    public String getDay() {
        return day;
    }

    public static void printSlotsInADay(ArrayList<Slot> slots, String day) {
        for (Slot s: slots) {
            if (s.getDay().equals(day)) {
                System.out.println(s);
            }
        }
    }

    public static void printTimetable(ArrayList<Slot> slots) {
        for (String d: days) {
            System.out.println(d);
            printSlotsInADay(slots, d);
            System.out.println("");
        }
    }

    @Override
    public String toString(){
        return String.format(startTime.toString() + "-" + endTime.toString() + " " + title);
    }
}
