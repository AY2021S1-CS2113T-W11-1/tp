package seedu.zoomaster;

import seedu.zoomaster.command.Command;
import seedu.zoomaster.command.ExitCommand;
import seedu.zoomaster.command.ChangeModeCommand;
import seedu.zoomaster.command.ClearCommand;
import seedu.zoomaster.command.HelpCommand;
import seedu.zoomaster.command.LaunchNowCommand;
import seedu.zoomaster.command.bookmark.AddBookmarkCommand;
import seedu.zoomaster.command.planner.AddMeetingCommand;
import seedu.zoomaster.command.planner.LoadPlannerCommand;
import seedu.zoomaster.command.timetable.AddSlotCommand;
import seedu.zoomaster.command.bookmark.DeleteBookmarkCommand;
import seedu.zoomaster.command.timetable.DeleteSlotCommand;
import seedu.zoomaster.command.bookmark.LaunchBookmarkCommand;
import seedu.zoomaster.command.bookmark.FindBookmarkCommand;
import seedu.zoomaster.command.timetable.LaunchModuleAndSlotBookmark;
import seedu.zoomaster.command.timetable.EditSlotCommand;
import seedu.zoomaster.command.timetable.ShowTimetableCommand;
import seedu.zoomaster.command.bookmark.ShowBookmarkCommand;
import seedu.zoomaster.exception.ZoomasterException;
import seedu.zoomaster.exception.ZoomasterExceptionType;

/**
 * This class deals with making sense of the user command.
 */
public class Parser {
    /*
     * Variable programMode controls the mode Zoomaster program is in.
     * programMode == 0: Main menu mode
     * programMode == 1: Bookmark mode
     * programMode == 2: Timetable mode
     */
    public static int programMode = 0;

    /**
     * Creates and returns the command based on user input.
     *
     * @param input full user input.
     * @return command.
     * @throws ZoomasterException If input command is unknown.
     */
    public static Command parse(String input) throws ZoomasterException {
        Command command = createCommand(input);
        return command;
    }

    private static Command createCommand(String input) throws ZoomasterException {
        Command command;

        if (input.compareToIgnoreCase(ExitCommand.BYE_KW) == 0) {
            command = new ExitCommand();
        } else if (input.compareToIgnoreCase(LaunchNowCommand.LAUNCH_NOW_KW) == 0) {
            command = new LaunchNowCommand();
        } else if (input.startsWith(ChangeModeCommand.MODE_KW)) {
            command = new ChangeModeCommand(input);
        } else if (input.startsWith(ClearCommand.CLEAR_KW)) {
            command = new ClearCommand();
        } else if (input.toLowerCase().equals(HelpCommand.HELP_KW)) {
            command = new HelpCommand();
        } else if (programMode == 1) {
            command = createBookmarkCommand(input);
        } else if (programMode == 2) {
            command = createTimetableCommand(input);
        } else if (programMode == 3) {
            command = createPlannerCommand(input);
        } else {
            throw new ZoomasterException(ZoomasterExceptionType.UNKNOWN_INPUT);
        }

        return command;
    }

    //@@author TYS0n1
    private static Command createBookmarkCommand(String input) throws ZoomasterException {

        if (input.compareToIgnoreCase(ShowBookmarkCommand.LIST_KW) == 0) {
            return new ShowBookmarkCommand();
        } else if (input.startsWith(DeleteBookmarkCommand.DEL_KW)) {
            return new DeleteBookmarkCommand(input);
        } else if (input.startsWith(AddBookmarkCommand.ADD_KW)) {
            return new AddBookmarkCommand(input);
        } else if (input.startsWith(LaunchBookmarkCommand.LAUNCH_KW)) {
            return new LaunchBookmarkCommand(input);
        } else if (input.startsWith(FindBookmarkCommand.FIND_KW)) {
            return new FindBookmarkCommand(input);
        } else if (input.startsWith(ClearCommand.CLEAR_KW)) {
            return new ClearCommand();
        } else {
            throw new ZoomasterException(ZoomasterExceptionType.UNKNOWN_INPUT);
        }
    }

    //@@author TYS0n1
    private static Command createTimetableCommand(String input) throws ZoomasterException {
        Command command;

        // Add Timetable Commands on merge
        if (input.startsWith(AddSlotCommand.ADD_KW)) {
            command = new AddSlotCommand(input);
        } else if (input.startsWith(DeleteSlotCommand.DEL_KW)) {
            command = new DeleteSlotCommand(input);
        } else if (input.startsWith(ShowTimetableCommand.SHOW_KW)) {
            return new ShowTimetableCommand(input);
        } else if (input.startsWith(ClearCommand.CLEAR_KW)) {
            command = new ClearCommand();
        } else if (input.startsWith(LaunchModuleAndSlotBookmark.LAUNCH_KW)) {
            command = new LaunchModuleAndSlotBookmark(input);
        } else if (input.startsWith(EditSlotCommand.EDIT_KW)) {
            command = new EditSlotCommand(input);
        } else {
            throw new ZoomasterException(ZoomasterExceptionType.UNKNOWN_INPUT);
        }

        return command;
    }

    private static Command createPlannerCommand(String input) throws ZoomasterException {

        if (input.startsWith(AddMeetingCommand.ADD_KW)) {
            return new AddMeetingCommand(input);
        } else if (input.startsWith(ShowTimetableCommand.SHOW_KW)) {
            return new ShowTimetableCommand(input);
        //} else if (input.startsWith(ClearCommand.CLEAR_KW)) {
        //    return new ClearCommand();
        } else if (input.startsWith(LoadPlannerCommand.LOAD_KW)) {
            return new LoadPlannerCommand();
        } else {
            throw new ZoomasterException(ZoomasterExceptionType.UNKNOWN_INPUT);
        }

    }

    public static int getProgramMode() {
        return programMode;
    }

}
