package seedu.duke.command;

import seedu.duke.ItemList;
import seedu.duke.Storage;
import seedu.duke.Ui;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.bookmark.BookmarkList;
import seedu.duke.exception.DukeException;

public class ListCommand extends Command {
    public static final String LIST_KW = "list";

    /**
     * Constructs a new ListCommand instance.
     */
    public ListCommand() {

    }

    private String getMessage(BookmarkList bookmarks) {
        String message = "\tHere are the bookmarks in your list:\n";
        for (int i = 0; i < bookmarks.getSize(); i++) {
            message = message + "\t" + (i + 1) + "." + bookmarks.getBookmark(i).getBookmarkAsString() + "\n";
        }
        return message;
    }

    @Override
    public void execute(ItemList items, Ui ui, Storage storage) throws DukeException {
        BookmarkList bookmarks = (BookmarkList) items;
        String message = getMessage(bookmarks);
        ui.print(message);
    }
}
