package seedu.zoomaster.command.timetable;

import seedu.zoomaster.Ui;
import seedu.zoomaster.bookmark.BookmarkList;
import seedu.zoomaster.command.Command;
import seedu.zoomaster.exception.ZoomasterException;
import seedu.zoomaster.exception.ZoomasterExceptionType;
import seedu.zoomaster.slot.Slot;
import seedu.zoomaster.slot.Timetable;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditSlotCommand extends Command {
    public static final String EDIT_KW = "edit";

    public static final String FIELD_KW_MODULE = "module";
    public static final String FIELD_KW_TITLE = "title";
    public static final String FIELD_KW_TIME = "time";

    private String fieldToEdit;
    private String details;

    public EditSlotCommand(String command) throws ZoomasterException {
        assert command.startsWith(EDIT_KW);

        // details: "NEW_MODULE_CODE" or "NEW_TITLE" or "NEW_DAY START_TIME END_TIME"
        details = command.substring(EDIT_KW.length()).trim();

        if (details.isBlank()) {
            throw new ZoomasterException(ZoomasterExceptionType.EMPTY_COMMAND, EDIT_KW);
        }

        Pattern parser = Pattern.compile(
                "(?<fieldToEdit>[a-zA-Z]+)\\s+(?<details>.+)"
        );
        Matcher matcher = parser.matcher(details);

        if (!matcher.matches()) {
            throw new ZoomasterException(ZoomasterExceptionType.INVALID_EDIT_INPUT);
        }

        fieldToEdit = matcher.group("fieldToEdit");
        details = matcher.group("details");
    }

    @Override
    public void execute(BookmarkList bookmarks, Timetable timetable, Ui ui) throws ZoomasterException {
        Pattern parser = Pattern.compile(
                "(?<day>[a-zA-Z]+)\\s+(?<indexInDay>\\d+)\\s+(?<firstArgument>\\w+)"
                + "(\\s+(?<startTime>[\\w:]+)\\s+(?<endTime>[\\w:]+))?"
        );
        Matcher matcher = parser.matcher(details);

        if (!matcher.matches()) {
            throw new ZoomasterException(ZoomasterExceptionType.INVALID_EDIT_INPUT);
        }

        String day = matcher.group("day");
        int indexInDay = Integer.parseInt(matcher.group("indexInDay"));
        Slot slot;

        String message = "";

        switch (fieldToEdit) {
        case FIELD_KW_MODULE:
            timetable.moveSlotToAnotherModule(day, indexInDay, matcher.group("firstArgument"));
            message = "Slot moved to " + matcher.group("firstArgument").toUpperCase();
            break;
        case FIELD_KW_TITLE:
            slot = timetable.getSlotByIndexInDay(day, indexInDay);
            slot.setTitle(matcher.group("firstArgument"));
            message = "Slot title changed to " + matcher.group("firstArgument");
            break;
        case FIELD_KW_TIME:
            slot = timetable.getSlotByIndexInDay(day, indexInDay);
            message = changeSlotTime(
                    slot,
                    matcher.group("firstArgument"),
                    matcher.group("startTime"),
                    matcher.group("endTime"),
                    timetable);
            break;
        default:
            message = "Unrecognized field!";
            break;
        }

        ui.print(message + System.lineSeparator());
    }

    /**
     * Change a slot's start and end time with the given user input.
     *
     * @param slot The slot object with the day we want to change.
     * @param startTimeInput The new start time as string.
     * @param endTimeInput The new end time as string.
     */
    private String changeSlotTime(
            Slot slot, String dayInput, String startTimeInput, String endTimeInput, Timetable timetable)
            throws ZoomasterException {
        LocalTime startTime;
        LocalTime endTime;

        try {
            startTime = LocalTime.parse(startTimeInput);
            endTime = LocalTime.parse(endTimeInput);
        } catch (DateTimeParseException e) {
            throw new ZoomasterException(ZoomasterExceptionType.INVALID_TIME_FORMAT);
        }

        if (timetable.isOverlapTimeSlot(dayInput, startTime, endTime)) {
            return "Timing clashes with another slot!";
        }

        slot.setDay(dayInput);
        slot.setStartTime(startTime);
        slot.setEndTime(endTime);

        return "Slot time changed to " + dayInput + " " + startTimeInput + " " + endTimeInput;
    }
}
