package seedu.duke.command.bookmark;

import seedu.duke.Storage;
import seedu.duke.Ui;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.bookmark.BookmarkList;
import seedu.duke.command.Command;
import seedu.duke.exception.DukeException;
import seedu.duke.exception.DukeExceptionType;
import seedu.duke.slot.Timetable;

public class DeleteBookmarkCommand extends Command {
    public static final String DEL_KW = "delete";
    private final int index;

    /**
     * Constructs a new DeleteBookmarkCommand instance and stores the information of the bookmark given by the input.
     *
     * @param command The user input command.
     * @throws DukeException thrown if input command is invalid or if the bookmark number is invalid.
     */
    public DeleteBookmarkCommand(String command) throws DukeException {
        String details = command.substring(DEL_KW.length());
        if (details.isBlank()) {
            throw new DukeException(DukeExceptionType.EMPTY_COMMAND, DEL_KW);
        }
        if (!details.startsWith(" ")) {
            throw new DukeException(DukeExceptionType.UNKNOWN_INPUT);
        }
        try {
            index = Integer.parseInt(details.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new DukeException(DukeExceptionType.NON_INTEGER_INPUT);
        }
    }

    /**
     * Deletes the bookmark in the bookmark list.
     *
     * @param bookmarks The list of bookmarks.
     * @param timetable The list of slots.
     * @param ui The user interface.
     * @throws DukeException if the bookmark number is invalid or if there is an error when saving the bookmark.
     */
    @Override
    public void execute(BookmarkList bookmarks, Timetable timetable, Ui ui) throws DukeException {
        try {
            Bookmark bookmark = bookmarks.getBookmark(index);
            String message = "I've deleted this bookmark!:\n" + bookmarks.deleteBookmark(bookmark);
            ui.print(message);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            throw new DukeException(DukeExceptionType.BOOKMARK_NUMBER_OUT_OF_BOUNDS, ""
                    + bookmarks.getBookmarkList().size());
        }
    }
}
