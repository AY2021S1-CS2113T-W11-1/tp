package seedu.duke.bookmark;

import seedu.duke.ItemList;
import seedu.duke.exception.DukeException;
import seedu.duke.exception.DukeExceptionType;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents the bookmark list used to store and organize bookmarks.
 */
public class BookmarkList extends ItemList {
    private ArrayList<Bookmark> bookmarks;
    private final String lineSeparator = System.lineSeparator();
    private static Logger logger = Logger.getLogger(BookmarkList.class.getName());


    /**
     * Constructs a BookmarkList object with an empty ArrayList to store Bookmark objects.
     */
    public BookmarkList() {
        this.bookmarks = new ArrayList<>();
    }

    /**
     * Constructs the BookmarkList object containing an ArrayList to store Bookmark objects.
     * This constructor is used when loading bookmarks from a text file.
     *
     * @param bookmarkStrings the list of strings of bookmarks.
     */
    public BookmarkList(ArrayList<String> bookmarkStrings) {
        this.bookmarks = new ArrayList<>();
        loadBookmarkList(bookmarkStrings);
    }

    private void loadBookmarkList(ArrayList<String> bookmarks) {
        for (String line : bookmarks) {
            loadBookmark(line);
        }
    }

    /**
     * This method adds the given bookmark object to the bookmark list.
     *
     * @param bookmark the bookmark to be added to the list.
     */
    public void addBookmark(Bookmark bookmark)  {
        bookmarks.add(bookmark);
    }

    private void loadBookmark(String line) {
        try {
            bookmarks.add(Bookmark.initBookmark(line));
        } catch (IndexOutOfBoundsException | DukeException e) {
            // Invalid task data, skips to the next entry
            logger.log(Level.WARNING, "invalid bookmark data found in file: " + line, e);
        }
    }

    /**
     * Returns the the data of all bookmarks in the list to be saved in the text file.
     *
     * @return the data of all the bookmarks.
     */
    public String getData() {
        StringBuilder data = new StringBuilder();
        for (Bookmark bookmark : bookmarks) {
            data.append(bookmark.getExport()).append(System.lineSeparator());
        }
        return data.toString().trim();
    }

    /**
     * Returns the number of bookmarks in the list.
     *
     * @return the size of the bookmark list.
     */
    public int getSize() {
        return bookmarks.size();
    }

    /**
     * Returns bookmark based on the index.
     *
     * @param index The index of the bookmark in the list.
     * @return The bookmark with the corresponding index in the list.
     * @throws DukeException if the index is out of range.
     */
    public Bookmark getBookmark(int index) throws DukeException {
        Bookmark bookmark;
        try {
            bookmark = bookmarks.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(DukeExceptionType.INVALID_BOOKMARK_NUMBER);
        }
        return bookmark;
    }

    /**
     * Returns the bookmark list.
     *
     * @return The bookmark list.
     */
    public ArrayList<Bookmark> getBookmarkList() {
        return bookmarks;
    }

    /**
     * This method deletes the bookmark from the list.
     *
     * @param bookmark The bookmark to be deleted.
     * @throws NullPointerException if the bookmark does not exist in the list.
     */
    public void deleteBookmark(Bookmark bookmark) throws DukeException {
        try {
            bookmarks.remove(bookmark);
        } catch (NullPointerException e) {
            throw new DukeException(DukeExceptionType.INVALID_BOOKMARK_NUMBER);
        }
    }

    /**
     * This method shows all the bookmarks from the list.
     *
     * @return The string message containing the matching bookmarks
     */
    public String showBookmarks() {
        String message = "\tHere are the bookmarks in your list:" + lineSeparator;
        for (int i = 0; i < bookmarks.size(); i++) {
            message = message + "\t" + (i + 1) + "." + bookmarks.get(i).getBookmarkAsString() + lineSeparator;
        }
        return message;
    }

    /**
     * This method searches the bookmarks from the list with matching module
     * and description.
     *
     * @param list The list of strings containing the module and description to be searched
     * @return The string message containing the matching bookmarks
     */
    public String findBookmarks(List<String> list) {
        String message = "";
        if (bookmarks.size() == 0) {
            message = "Empty List" + lineSeparator;
        } else {
            String module = list.get(0).toUpperCase();
            String description = list.get(1).toUpperCase();

            message = getMatchingBookmarks(module, description);

            if (!message.isEmpty()) {
                message = "Here are your matching bookmarks" + lineSeparator + message;
            } else {
                message = "No bookmarks contain the specified keyword!" + lineSeparator;
            }
        }
        return message;
    }

    private String getMatchingBookmarks(String module, String description) {
        String message = "";
        for (int i = 0; i < bookmarks.size(); i++) {
            if (bookmarks.get(i).getModule().toUpperCase().contains(module)
                    && bookmarks.get(i).getDescription().toUpperCase().contains(description)) {
                message += (i + 1) + "." + bookmarks.get(i).getBookmarkAsString() + lineSeparator;
            }
        }
        return message;
    }

    /**
     * This method searches the bookmarks from the list with matching module
     * and description and launches them accordingly.
     *
     * @param list The List<String></String> containing the module and description to be searched
     * @return The string message containing the matching bookmarks
     */
    public String launchBookmarks(List<String> list) {
        String message = "";
        if (bookmarks.size() == 0) {
            message = "Empty List" + lineSeparator;
        } else {
            String module = list.get(0).toUpperCase();
            String description = list.get(1).toUpperCase();

            message = launchMatchingBookmarks(module, description);

            if (!message.isEmpty()) {
                message = "Launched these bookmarks:" + lineSeparator + message;
            } else {
                message = "No bookmarks contain the specified keyword!" + lineSeparator;
            }
        }
        return message;
    }

    private String launchMatchingBookmarks(String module, String description) {
        String message = "";
        String errorMessage = "";
        for (int i = 0; i < bookmarks.size(); i++) {
            if (bookmarks.get(i).getModule().toUpperCase().contains(module)
                    && bookmarks.get(i).getDescription().toUpperCase().contains(description)) {
                try {
                    bookmarks.get(i).launch();
                    message += (i + 1) + "." + bookmarks.get(i).getBookmarkAsString() + lineSeparator;
                } catch (DukeException e) {
                    errorMessage += (i + 1) + "." + bookmarks.get(i).getBookmarkAsString() + lineSeparator;
                }
            }
        }
        if (!errorMessage.isBlank()) {
            message += "Failed to launch these bookmarks:" + lineSeparator + errorMessage;
        }
        return message;
    }
}
