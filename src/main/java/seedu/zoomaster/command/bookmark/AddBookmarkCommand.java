package seedu.zoomaster.command.bookmark;

import seedu.zoomaster.Ui;
import seedu.zoomaster.bookmark.Bookmark;
import seedu.zoomaster.bookmark.BookmarkList;
import seedu.zoomaster.command.Command;
import seedu.zoomaster.exception.ZoomasterException;
import seedu.zoomaster.exception.ZoomasterExceptionType;
import seedu.zoomaster.slot.Timetable;

import java.util.List;

/**
 * Represents the user command exit the Duke program.
 */
public class AddBookmarkCommand extends Command {
    public static final String ADD_KW = "add";
    public String description;
    public String url;

    /**
     * Constructs a new AddBookmarkCommand instance and stores the information of the bookmark given by the input.
     *
     * @throws ZoomasterException if input command is invalid, if the description is empty or if the url is invalid.
     */
    public AddBookmarkCommand(String input) throws ZoomasterException {
        assert input.startsWith(AddBookmarkCommand.ADD_KW) : "input should always start with \"add\"";
        String command = input.substring(AddBookmarkCommand.ADD_KW.length());
        if (command.isBlank()) {
            throw new ZoomasterException(ZoomasterExceptionType.EMPTY_COMMAND, ADD_KW);
        }
        if (!command.startsWith(" ")) {
            throw new ZoomasterException(ZoomasterExceptionType.UNKNOWN_INPUT);
        }
        List<String> descAndUrl = Bookmark.extractDescriptionAndUrl(command.trim());
        description = descAndUrl.get(0);
        url = descAndUrl.get(1);
    }

    /**
     * Adds the bookmark to the bookmark list and saves the bookmarks list in the text file.
     *
     * @param bookmarks The list of bookmarks.
     * @param timetable The list of slots.
     * @param ui The user interface.
     * @throws ZoomasterException if there is an error when saving the bookmark.
     */
    @Override
    public void execute(BookmarkList bookmarks, Timetable timetable, Ui ui) throws ZoomasterException {
        Bookmark bookmark = new Bookmark(description, url);
        String message = bookmarks.addBookmark(bookmark);
        ui.print(message);
    }
}