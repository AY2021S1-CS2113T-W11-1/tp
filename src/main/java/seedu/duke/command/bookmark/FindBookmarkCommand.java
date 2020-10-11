package seedu.duke.command.bookmark;

import seedu.duke.Storage;
import seedu.duke.Ui;
import seedu.duke.bookmark.BookmarkList;
import seedu.duke.command.Command;
import seedu.duke.exception.DukeException;
import seedu.duke.exception.DukeExceptionType;
import seedu.duke.slot.SlotList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindBookmarkCommand extends Command {
    public static final String FIND_KW = "find";
    private final List<String> moduleAndDescription;


    /**
     * Constructs a new FindBookmarkCommand instance and and gets the module and description
     * to be searched.
     * If only one term is entered (term meaning string without a space), method asserts
     * this to be the module and inserts an empty description
     * @param command The command input by the user.
     */

    public FindBookmarkCommand(String command) throws DukeException {
        String details = command.substring(FIND_KW.length());
        if (!details.startsWith(" ")) {
            throw new DukeException(DukeExceptionType.UNKNOWN_INPUT);
        }
        moduleAndDescription = new ArrayList<>(Arrays.asList(details.trim().split(" ", 2)));
        if (moduleAndDescription.size() == 1) {
            moduleAndDescription.add("");  // Empty entry for description
        }
    }

    /**
     * prints the matching bookmark.
     *
     * @param bookmarks The list of bookmarks.
     * @param ui The user interface.
     * @param slotStorage The storage for saving and loading.
     */
    @Override
    public void execute(BookmarkList bookmarks, SlotList slotList, Ui ui,
                        Storage bookmarkStorage, Storage slotStorage) {
        ui.print(bookmarks.findBookmarks(moduleAndDescription));
    }
}
