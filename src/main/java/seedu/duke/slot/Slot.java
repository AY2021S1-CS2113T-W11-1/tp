package seedu.duke.slot;

import java.util.ArrayList;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class Slot {

    public static final String MON = "mon";
    public static final String TUE = "tue";
    public static final String WED = "wed";
    public static final String THU = "thu";
    public static final String FRI = "fri";
    public static final String SAT = "sat";
    public static final String SUN = "sun";

    public static final String[] days = { MON, TUE, WED, THU, FRI, SAT, SUN };

    private static final String SEPARATOR = " | ";
    private LocalTime startTime;
    private LocalTime endTime;
    private String day;
    private String title;

    public Slot(LocalTime startTimeInput, LocalTime endTimeInput, String dayInput, String titleInput) {
        startTime = startTimeInput;
        endTime = endTimeInput;
        day = dayInput;
        title = titleInput;
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

    public void setDay(String dayInput) {
        day = dayInput;
    }

    public String getDay() {
        return day;
    }

    public void setTitle(String titleInput) {
        title = titleInput;
    }

    public String getTitle() {
        return title;
    }

    public String getExport() {
        return startTime.toString() + SEPARATOR + endTime.toString() + SEPARATOR + day + SEPARATOR + title;
    }

    public static Slot initSlot(String data) {
        List<String> details = Arrays.asList(data.split("\\|"));

        LocalTime startTime = LocalTime.parse(details.get(0).trim());
        LocalTime endTime = LocalTime.parse(details.get(1).trim());
        String day = details.get(2).trim();
        String title = details.get(3).trim();

        return new Slot(startTime, endTime, day, title);
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
    public String toString() {
        return String.format(startTime.toString() + "-" + endTime.toString() + " " + title);
    }
}
