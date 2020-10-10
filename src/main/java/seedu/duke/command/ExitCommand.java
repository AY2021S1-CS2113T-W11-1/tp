package seedu.duke.command;

import seedu.duke.ItemList;
import seedu.duke.Storage;
import seedu.duke.Ui;
import seedu.duke.bookmark.BookmarkList;

/**
 * Represents the user command exit the Duke program.
 */
public class ExitCommand extends Command {
    public static final String BYE_KW = "bye";

    /**
     * Constructs a new ExitCommand instance and sets isExitCommand to true.
     */
    public ExitCommand() {
        isExitCommand = true;
    }

    /**
     * Prints the exit screen before the program exits.
     *
     * @param items The list of bookmarks.
     * @param ui The user interface.
     * @param storage The storage for saving and loading.
     */
    @Override
    public void execute(ItemList items, Ui ui, Storage storage) {
        ui.showExitScreen();
    }
}
