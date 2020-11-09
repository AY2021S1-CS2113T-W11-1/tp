package seedu.zoomaster.command.timetable;

import seedu.zoomaster.Ui;
import seedu.zoomaster.bookmark.Bookmark;
import seedu.zoomaster.bookmark.BookmarkList;
import seedu.zoomaster.command.Command;
import seedu.zoomaster.exception.ZoomasterException;
import seedu.zoomaster.exception.ZoomasterExceptionType;
import seedu.zoomaster.slot.Day;
import seedu.zoomaster.slot.Module;
import seedu.zoomaster.slot.Slot;
import seedu.zoomaster.slot.Timetable;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the user command exit the Duke program.
 */
//@@author xingrong123
public class AddSlotCommand extends Command {
    public static final String ADD_KW = "add";
    public String moduleCode;
    private List<String> commands;

    /**
     * Constructs a new AddSlotCommand instance and stores the information of the slot given by the input.
     *
     * @param command The user input command.
     * @throws ZoomasterException if input command is invalid.
     */
    public AddSlotCommand(String command) throws ZoomasterException {
        assert command.startsWith(ADD_KW);
        String details = command.substring(ADD_KW.length());
        if (details.isBlank()) {
            throw new ZoomasterException(ZoomasterExceptionType.EMPTY_COMMAND, ADD_KW);
        } else if (!details.startsWith(" ")) {
            throw new ZoomasterException(ZoomasterExceptionType.UNKNOWN_INPUT);
        }
        String[] stringArray = details.trim().split("\\s+", 2);
        moduleCode = stringArray[0].toUpperCase();
        if (stringArray.length > 1) {
            commands = Arrays.asList(stringArray[1].split(","));
        }
    }

    /**
     * Adds modules, slots, module bookmarks and slot bookmarks into the timetable.
     * Supports one-shot-command when adding multiple multiple components to a module by using the separator ',' to
     * separate the addition of each component.
     *
     * @param bookmarks The list of bookmarks
     * @param timetable The timetable
     * @param ui The user interface
     * @throws ZoomasterException if the module code is invalid.
     */
    @Override
    public void execute(BookmarkList bookmarks, Timetable timetable, Ui ui) throws ZoomasterException {
        String message = "";
        Module module;
        if (timetable.moduleExists(moduleCode)) {
            module = timetable.getModule(moduleCode);
            message += moduleCode + " already exists" + System.lineSeparator();
        } else if (!isValidModule(moduleCode)) {
            throw new ZoomasterException(ZoomasterExceptionType.INVALID_MODULE);
        } else {
            module = new Module(moduleCode);
            timetable.addModule(module);
            message += moduleCode + " added" + System.lineSeparator();
        }

        if (commands != null) {
            for (String command : commands) {
                message += createSlotAndBookmark(module, command.trim(), timetable);
            }
        }
        ui.print(message);
    }

    protected String createSlotAndBookmark(Module module, String command, Timetable timetable) {
        assert module != null : "module should mot be null";
        String message = "";
        try {
            message += create(command, module, timetable);
        } catch (ZoomasterException e) {
            message += e.getInfo() + System.lineSeparator();
        } catch (IndexOutOfBoundsException e) {
            message += "incorrect format for slot: " + command + System.lineSeparator();
        }
        return message;
    }

    protected String create(String command, Module module, Timetable timetable) throws ZoomasterException {
        String message = "";
        List<String> slotAndBookmark = Arrays.asList(command.trim().split("\\s+"));
        if (isAddModuleBookmark(slotAndBookmark)) {
            message = addBookmarkToModule(module, slotAndBookmark);
        } else if (slotAndBookmark.size() == 2) {
            Slot slot;
            slot = retrieveSlotFromModule(module, slotAndBookmark);
            createBookmark(slotAndBookmark.get(1), slot.getTitle(), slot);
            message = "  bookmark added to " + moduleCode + " " + slot.getTitle() + System.lineSeparator();
        } else if (slotAndBookmark.size() >= 4) {
            String lesson = slotAndBookmark.get(0);
            String day = slotAndBookmark.get(1);
            LocalTime startTime;
            LocalTime endTime;
            if (!Day.isDay(day)) {
                throw new ZoomasterException(ZoomasterExceptionType.INVALID_TIMETABLE_DAY,
                        "  Invalid day input: " + day + ". (make sure that the description only contains one word)");
            }
            try {
                startTime = LocalTime.parse(slotAndBookmark.get(2));
                endTime = LocalTime.parse(slotAndBookmark.get(3));
            } catch (DateTimeParseException e) {
                throw new ZoomasterException(ZoomasterExceptionType.INVALID_TIME_FORMAT,
                        "  Invalid time format for slot. (" + slotAndBookmark.get(2)
                        + " " + slotAndBookmark.get(3) + ") Please check format.");
            }
            Slot newSlot;
            if (module.slotExists(lesson, day, startTime, endTime)) {
                newSlot = module.getSlot(lesson, day, startTime, endTime);
                message += "  Slot already exists." + System.lineSeparator();
            } else {
                if (Timetable.isTimeAGreaterEqualsTimeB(startTime, endTime)) {
                    throw new ZoomasterException(ZoomasterExceptionType.INVALID_TIME_FORMAT,
                            "  Invalid time for slot. (" + slotAndBookmark.get(2) + " " + slotAndBookmark.get(3) + ")");
                } else if (timetable.isOverlapTimeSlot(day, startTime, endTime)) {
                    throw new ZoomasterException(ZoomasterExceptionType.INVALID_TIME_FORMAT,
                            "  Slot clash. (" + slotAndBookmark.get(2) + " " + slotAndBookmark.get(3) + ")"
                            + " Please check timetable.");
                } else {
                    newSlot = new Slot(startTime, endTime, day, lesson);
                    module.addSlot(newSlot);
                    message +=  "  " + lesson + " slot added" + System.lineSeparator();
                }
            }
            message += checkForAndAddBookmarkToSlot(slotAndBookmark, lesson, newSlot);
        } else {
            message = "  Invalid command format." + System.lineSeparator();
        }
        return message;
    }

    private Slot retrieveSlotFromModule(Module module, List<String> slotAndBookmark) throws ZoomasterException {
        Slot slot;
        try {
            int index = Integer.parseInt(slotAndBookmark.get(0)) - 1;
            slot = module.getSlot(index);
        } catch (NumberFormatException e) {
            throw new ZoomasterException(ZoomasterExceptionType.NON_INTEGER_INPUT, "  Invalid slot index.");
        } catch (ZoomasterException e) {
            throw new ZoomasterException(ZoomasterExceptionType.INVALID_SLOT_NUMBER, "  Slot index out of range.");
        }
        return slot;
    }

    protected String checkForAndAddBookmarkToSlot(List<String> slotAndBookmark, String lesson, Slot newSlot)
            throws ZoomasterException {
        String message = "";
        if (slotAndBookmark.size() == 5) {
            createBookmark(slotAndBookmark.get(4), lesson, newSlot);
            message = "    bookmark added to " + moduleCode + " " + lesson + System.lineSeparator();
        } else if (slotAndBookmark.size() > 5) {
            message = "    Invalid url format. Please provide one valid url." + System.lineSeparator();
        }
        return message;
    }

    protected String addBookmarkToModule(Module module, List<String> slotAndBookmark) {
        String description = slotAndBookmark.get(0);
        String url = slotAndBookmark.get(1);
        Bookmark bookmark = new Bookmark(description, url);
        module.addBookmark(bookmark);
        String message = "  bookmark added to module\n";
        return message;
    }

    protected boolean isAddModuleBookmark(List<String> slotAndBookmark) {
        try {
            int num = Integer.parseInt(slotAndBookmark.get(0));
            return false;
        } catch (NumberFormatException e) {
            return (slotAndBookmark.get(1).startsWith("www.") || slotAndBookmark.get(1).startsWith("https://"))
                    && slotAndBookmark.size() == 2;
        }
    }

    private void createBookmark(String url, String lesson, Slot newSlot) throws ZoomasterException {
        if (!Bookmark.isUrlValid(url)) {
            throw new ZoomasterException(ZoomasterExceptionType.INVALID_URL, "invalid url format: " + url
                    + "URL must start with either 'www.', 'http://' or 'https://'"
                    + System.lineSeparator());
        }
        Bookmark bookmark = new Bookmark(lesson, url);
        newSlot.addBookmark(bookmark);
    }

    /**
     * Validates the module code with the list of modules moduleList.
     *
     * @param module The module code to be added.
     *
     * @return
     * true if module exist in the list or list is null.
     *     false if module does not exists in the list.
     */
    private boolean isValidModule(String module) {
        if (Module.getModuleList() == null) { // If unable to get list of modules, always return true.
            return true;
        } else if (Module.getModuleList().contains(module)) {
            return true;
        }
        return false;
    }

}
