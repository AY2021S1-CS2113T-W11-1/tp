package seedu.duke.command;

import seedu.duke.ItemList;
import seedu.duke.Storage;
import seedu.duke.Ui;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.bookmark.BookmarkList;
import seedu.duke.exception.DukeException;
import seedu.duke.exception.DukeExceptionType;

public class DeleteBookmarkCommand extends Command {
    public static final String DEL_KW = "delete";
    private int index;


    /**
     * Constructs a new DeleteBookmarkCommand instance and stores the information of the bookmark given by the input.
     *
     * @param command The user input command.
     * @throws DukeException thrown if input command is invalid.
     */
    public DeleteBookmarkCommand(String command) throws DukeException {
        String details = command.substring(DEL_KW.length());
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
     * Deletes the bookmark in the bookmark list.
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
            bookmarks.deleteBookmark(bookmarks.getBookmark(index));
            ui.print(getMessage(bookmark));
            //storage.save(taskList.getData());
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(DukeExceptionType.INVALID_BOOKMARK_NUMBER);
        }
    }

    private String getMessage(Bookmark bookmark) {
        String message = "\tI've deleted this bookmark!:\n"
                + "\t  [" + bookmark.getModule() + "] " + bookmark.getDescription() + " " + bookmark.getUrl() + "\n";
        return message;
    }
}
