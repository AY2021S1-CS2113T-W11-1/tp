package seedu.zoomaster.command.timetable;

import seedu.zoomaster.Ui;
import seedu.zoomaster.bookmark.BookmarkList;
import seedu.zoomaster.command.Command;
import seedu.zoomaster.exception.ZoomasterExceptionType;
import seedu.zoomaster.slot.Module;
import seedu.zoomaster.slot.Slot;
import seedu.zoomaster.exception.ZoomasterException;
import seedu.zoomaster.slot.SlotContainer;
import seedu.zoomaster.slot.Timetable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ShowTimetableCommand extends Command {
    public static final String SHOW_KW = "show";
    private String day;
    private boolean showBookmarks = false;
    private String module = null;

    /**
     * Constructs a new ShowTimetableCommand instance.
     */
    public ShowTimetableCommand(String command) throws ZoomasterException {
        assert command.startsWith(SHOW_KW) : "command should start with show keyword";
        if (command.compareTo(SHOW_KW) == 0) {
            day = "ALL";
        } else {
            if (command.charAt(SHOW_KW.length()) != ' ') {
                throw new ZoomasterException(ZoomasterExceptionType.INVALID_COMMAND_FORMAT);
            }
            String details = command.substring(SHOW_KW.length() + 1).trim();
            if (details.toLowerCase().equals("today")) {
                day = getDayToday();
            }
            if (isDay(details)) {
                day = getDayFromCommand(details);
            } else {
                String[] something = details.split(" ", 2);
                module = something[0];
                if (something.length == 2) {
                    if (something[1].compareTo("bookmarks") == 0) {
                        showBookmarks = true;
                    } else {
                        throw new ZoomasterException(ZoomasterExceptionType.INVALID_COMMAND_FORMAT);
                    }
                }
            }
        }
    }

    @Override
    public void execute(BookmarkList bookmarks, Timetable timetable, Ui ui) throws ZoomasterException {
        String message = "";
        List<Module> modules = timetable.getFullModuleList();
        if (day != null) { // "show" and "show day" and "show today"
            List<Slot> list = new ArrayList<>(timetable.getFullSlotList());
            message += getMessageLessonAtTime(modules, list, day);
        } else if (module != null && !showBookmarks) {
            if (!timetable.moduleExists(module)) {
                throw new ZoomasterException(ZoomasterExceptionType.INVALID_MODULE);
            }
            Module matchedModule = timetable.getModule(module);
            message += getMessageForModule(matchedModule);
        } else if (module != null && showBookmarks) {
            if (!timetable.moduleExists(module)) {
                throw new ZoomasterException(ZoomasterExceptionType.INVALID_MODULE);
            }
            Module matchedModule = timetable.getModule(module);
            message += matchedModule.getBookmarks();
        }
        ui.print(message);
    }

    private boolean isDay(String input) {
        boolean isDay = false;
        if (input.compareToIgnoreCase(Slot.MON) == 0) {
            isDay = true;
        } else if (input.compareToIgnoreCase(Slot.TUE) == 0) {
            isDay = true;
        } else if (input.compareToIgnoreCase(Slot.WED) == 0) {
            isDay = true;
        } else if (input.compareToIgnoreCase(Slot.THU) == 0) {
            isDay = true;
        } else if (input.compareToIgnoreCase(Slot.FRI) == 0) {
            isDay = true;
        } else if (input.compareToIgnoreCase(Slot.SAT) == 0) {
            isDay = true;
        } else if (input.compareToIgnoreCase(Slot.SUN) == 0) {
            isDay = true;
        }
        return isDay;
    }

    private String getDayFromCommand(String input) {
        String outputData;
        if (input.compareToIgnoreCase(Slot.MON) == 0) {
            outputData = Slot.MON;
        } else if (input.compareToIgnoreCase(Slot.TUE) == 0) {
            outputData = Slot.TUE;
        } else if (input.compareToIgnoreCase(Slot.WED) == 0) {
            outputData = Slot.WED;
        } else if (input.compareToIgnoreCase(Slot.THU) == 0) {
            outputData = Slot.THU;
        } else if (input.compareToIgnoreCase(Slot.FRI) == 0) {
            outputData = Slot.FRI;
        } else if (input.compareToIgnoreCase(Slot.SAT) == 0) {
            outputData = Slot.SAT;
        } else if (input.compareToIgnoreCase(Slot.SUN) == 0) {
            outputData = Slot.SUN;
        } else {
            outputData = null;
        }
        return outputData;
    }

    private String getMessageSlotsInADay(List<Module> modules, List<Slot> slots, String day) {
        StringBuilder message = new StringBuilder();
        boolean hasSlotOnDay = false;
        boolean hasIndicatorOnDay = false;
        if (day.equals(getDayToday())) {
            hasIndicatorOnDay = true;
        }

        ArrayList<Slot> thisDaySlots = new ArrayList<>();
        ArrayList<String> thisDayModuleCodesList = new ArrayList<>();
        for (Slot s: slots) {
            for (Module module : modules) {
                if (module.slotExists(s) && s.getDay().equals(day)) {
                    thisDaySlots.add(s);
                    thisDayModuleCodesList.add(module.getModuleCode());
                }
            }
        }
        SlotContainer slotContainer = new SlotContainer(thisDaySlots, thisDayModuleCodesList);
        SlotContainer sortedSlotContainer = sortSlotsByTime(slotContainer);
        ArrayList<Slot> sortedSlots = sortedSlotContainer.getSlotList();
        ArrayList<String> sortedModuleCodes = sortedSlotContainer.getModuleCodesList();

        for (int i = 0; i < sortedSlots.size(); i++) {
            if (hasLessonNow(sortedSlots.get(i))) {
                message.append(getHighlighBoxUpperMessage());
                message.append(sortedSlots.get(i).toString()).append(" ")
                        .append(sortedModuleCodes.get(i)).append("\n");
                message.append(getHighlighBoxLowerMessage());
                hasIndicatorOnDay = false;
            } else {
                if (sortedSlots.get(i).getStartTime().isAfter(LocalTime.now())
                        && hasIndicatorOnDay == true) {
                    message.append(getIndicatorMessage());
                    hasIndicatorOnDay = false;
                }
                message.append(sortedSlots.get(i).toString()).append(" ")
                        .append(sortedModuleCodes.get(i)).append("\n");
            }
            hasSlotOnDay = true;
        }

        if (!hasSlotOnDay) {
            message.append("No lessons" + "\n");
        }

        if (hasIndicatorOnDay == true) {
            message.append(getIndicatorMessage());
            hasIndicatorOnDay = false;
        }

        message.append("\n");
        return message.toString();
    }

    private SlotContainer sortSlotsByTime(SlotContainer slotContainer) {
        ArrayList<Slot> thisDaySlots = slotContainer.getSlotList();
        ArrayList<String> thisDayModuleCodesList = slotContainer.getModuleCodesList();
        ArrayList<Slot> sortedThisDaySlots = new ArrayList<>();
        ArrayList<String> sortedThisDayModuleCodesList = new ArrayList<>();
        int indextEarlistTimeSlot;
        Slot earlistTimeSlot;

        while (thisDaySlots.size() != 0) {
            earlistTimeSlot = thisDaySlots.get(0);
            indextEarlistTimeSlot = 0;
            for (int i = 1; i < thisDaySlots.size(); i++) {
                if (earlistTimeSlot.getStartTime().isAfter(thisDaySlots.get(i).getStartTime())) {
                    earlistTimeSlot = thisDaySlots.get(i);
                    indextEarlistTimeSlot = i;
                }
            }
            sortedThisDaySlots.add(thisDaySlots.get(indextEarlistTimeSlot));
            sortedThisDayModuleCodesList.add(thisDayModuleCodesList.get(indextEarlistTimeSlot));
            thisDaySlots.remove(indextEarlistTimeSlot);
            thisDayModuleCodesList.remove(indextEarlistTimeSlot);
        }


        return new SlotContainer(sortedThisDaySlots, sortedThisDayModuleCodesList);
    }

    private String getMessageTimetable(List<Module> modules, List<Slot> slots) {
        StringBuilder message = new StringBuilder();
        for (String d: Slot.days) {
            message.append(d).append("\n");
            message.append(getMessageSlotsInADay(modules, slots, d));
        }
        return message.toString();
    }

    private String getMessageLessonAtTime(List<Module> modules, List<Slot> slots,
                                          String dayInput) throws ZoomasterException {
        String message = "";
        if (slots.isEmpty()) {
            throw new ZoomasterException(ZoomasterExceptionType.EMPTY_TIMETABLE);
        } else if (dayInput == null) {
            throw new ZoomasterException(ZoomasterExceptionType.INVALID_TIMETABLE_DAY);
        } else if (dayInput.compareTo("ALL") == 0) {
            return getMessageTimetable(modules,slots);
        }
        message += "Lessons for " + dayInput + "\n";
        message += getMessageSlotsInADay(modules, slots, dayInput);
        return message;
    }

    private String getMessageForModule(Module module) {
        String message = "";
        List<Slot> slots = module.getSlotList();
        if (!slots.isEmpty()) {
            message += module.getModuleCode() + "\n";
            for (int i = 0; i < slots.size(); i++) {
                message += "  " + (i + 1) + ". " + slots.get(i).toString() + "\n";
            }
        } else {
            message += "no slots for " + module.getModuleCode() + "\n";
        }
        return message;
    }

    /**
     * Returns String of today's day of the week.
     *
     * @return outputDay String of today's day of the week readable by Slot class.
     */
    public static String getDayToday() {
        String outputDay;

        assert (LocalDate.now().getDayOfWeek().getValue() <= 7) && (LocalDate.now().getDayOfWeek().getValue() >= 1) :
                "LocalDate.now().getDayOfWeek().getValue() only returns value within range 1 to 7";
        switch (LocalDate.now().getDayOfWeek().getValue()) {
        case 1:
            outputDay = "mon";
            break;
        case 2:
            outputDay = "tue";
            break;
        case 3:
            outputDay = "wed";
            break;
        case 4:
            outputDay = "thu";
            break;
        case 5:
            outputDay = "fri";
            break;
        case 6:
            outputDay = "sat";
            break;
        case 7:
            outputDay = "sun";
            break;
        default:
            outputDay = "mon";
            break;
        }

        return outputDay;
    }

    public static boolean hasLessonNow(Slot slot) {
        boolean isOverlap = false;
        LocalTime timeNow = LocalTime.now();
        if (slot.getStartTime().isBefore(timeNow) && slot.getEndTime().isAfter(timeNow)
                && getDayToday().equals(slot.getDay())) {
            isOverlap = true;
        }
        return isOverlap;
    }

    public static String getIndicatorMessage() {
        DateTimeFormatter hoursAndMinutes = DateTimeFormatter.ofPattern("HH:mm");
        String currentTimeMessage = "<----" + "Current Time: " + LocalTime.now().format(hoursAndMinutes)
                + "---->" + "\n";

        return "\u001b[34m" + currentTimeMessage + "\u001b[0m";
    }

    public static String getHighlighBoxUpperMessage() {
        String message = "[====" + "Lesson now" + "====]" + "\n";

        return "\u001b[32m" + message + "\u001b[0m";
    }

    public static String getHighlighBoxLowerMessage() {
        String message = "[==================]" + "\n";

        return "\u001b[32m" + message + "\u001b[0m";
    }
}
