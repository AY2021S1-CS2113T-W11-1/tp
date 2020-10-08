package seedu.duke.command;

import seedu.duke.Storage;
import seedu.duke.Ui;
import seedu.duke.bookmark.BookmarkList;
import seedu.duke.slot.SlotList;

/**
 * Represents the user command exit the Duke program.
 */
public class ExitCommand extends Command {
    public static final String BYE_KW = "exit";

    /**
     * Constructs a new ExitCommand instance and sets isExitCommand to true.
     */
    public ExitCommand() {
        isExitCommand = true;
    }

    /**
     * Prints the exit screen before the program exits.
     *
     * @param bookmarks The list of bookmarks.
     * @param ui The user interface.
     * @param storage The storage for saving and loading.
     */
    @Override
    public void execute(BookmarkList bookmarks, SlotList slotList, Ui ui, Storage storage) {
        ui.showExitScreen();
    }
}
