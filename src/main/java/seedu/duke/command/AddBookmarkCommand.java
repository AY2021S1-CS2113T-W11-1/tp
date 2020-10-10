package seedu.duke.command;

import seedu.duke.ItemList;
import seedu.duke.Storage;
import seedu.duke.Ui;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.bookmark.BookmarkList;
import seedu.duke.exception.DukeException;

import java.util.List;

/**
 * Represents the user command exit the Duke program.
 */
public class AddBookmarkCommand extends Command {
    public static final String ADD_KW = "add";
    public String module;
    public String description;
    public String url;

    /**
     * Constructs a new AddBookmarkCommand instance and stores the information of the bookmark given by the input.
     *
     * @param command The user input command.
     * @throws DukeException thrown if input command is invalid.
     */
    public AddBookmarkCommand(String command) throws DukeException {
        List<String> descAndAt = Bookmark.extractModuleDescriptionAndUrl(command);
        module = descAndAt.get(0);
        description = descAndAt.get(1);
        url = descAndAt.get(2);
    }

    /**
     * Adds the bookmark to the bookmark list and saves the bookmarks list in the text file.
     *
     * @param items The list of bookmarks.
     * @param ui The user interface.
     * @param storage The storage for saving and loading.
     */
    @Override
    public void execute(ItemList items, Ui ui, Storage storage) {
        BookmarkList bookmarks = (BookmarkList) items;
        Bookmark bookmark = new Bookmark(module, description, url);
        bookmarks.addBookmark(bookmark);
        ui.print("Added bookmark: " + "[" + module + "] "
                + description + " " +  url + System.lineSeparator());
        try {
            storage.save(bookmarks.getData());
        } catch (DukeException e) {
            e.printStackTrace();
        }
    }
}
