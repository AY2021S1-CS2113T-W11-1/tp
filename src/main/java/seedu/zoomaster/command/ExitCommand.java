package seedu.zoomaster.command;

import seedu.zoomaster.Ui;
import seedu.zoomaster.bookmark.BookmarkList;
import seedu.zoomaster.slot.Timetable;

/**
 * Represents the user command exit the Duke program.
 */
public class ExitCommand extends Command {
    public static final String EXIT_KW = "exit";

    /**
     * Constructs a new ExitCommand instance and sets isExitCommand to true.
     */
    //@@author xingrong123
    public ExitCommand() {
        isExitCommand = true;
    }

    /**
     * Prints the exit screen before the program exits.
     *
     * @param bookmarks The list of bookmarks.
     * @param timetable The timetable.
     * @param ui The user interface.
     */
    @Override
    public void execute(BookmarkList bookmarks, Timetable timetable, Ui ui) {
        ui.showExitScreen();
    }
}
