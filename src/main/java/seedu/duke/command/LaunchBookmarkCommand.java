package seedu.duke.command;

import seedu.duke.ItemList;
import seedu.duke.Storage;
import seedu.duke.Ui;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.bookmark.BookmarkList;
import seedu.duke.exception.DukeException;
import seedu.duke.exception.DukeExceptionType;

public class LaunchBookmarkCommand extends Command {
    public static final String LAUNCH_KW = "launch";
    private int index;


    /**
     * Constructs a new LaunchBookmarkCommand instance and and gets the index of the bookmark to launch.
     */

    public LaunchBookmarkCommand(String command) throws DukeException {
        String details = command.substring(LAUNCH_KW.length());
        if (!details.startsWith(" ")) {
            throw new DukeException(DukeExceptionType.UNKNOWN_INPUT);
        }
        try {
            index = Integer.parseInt(details.trim()) - 1;
        } catch (NullPointerException | NumberFormatException | IndexOutOfBoundsException e) {
            throw new DukeException(DukeExceptionType.INVALID_BOOKMARK_NUMBER);
        }
    }

    /**
     * launches the bookmark.
     *
     * @param items The list of bookmarks.
     * @param ui The user interface.
     * @param storage The storage for saving and loading.
     */
    @Override
    public void execute(ItemList items, Ui ui, Storage storage) throws DukeException {
        BookmarkList bookmarks = (BookmarkList) items;
        try {
            Bookmark bookmark = bookmarks.getBookmark(index);
            bookmark.launch();
            ui.print(getMessage(bookmark));
            //storage.save(taskList.getData());
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(DukeExceptionType.INVALID_BOOKMARK_NUMBER);
        }
    }

    private String getMessage(Bookmark bookmark) {
        String message = "\tNice! I've launched this bookmark!:\n"
                + "\t  [" + bookmark.getModule() + "] " + bookmark.getDescription() + " " + bookmark.getUrl() + "\n";
        return message;
    }
}
