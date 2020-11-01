package seedu.zoomaster.bookmark;

import seedu.zoomaster.exception.ZoomasterException;
import seedu.zoomaster.exception.ZoomasterExceptionType;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * This class represents the bookmark list used to store and organize bookmarks.
 */
public class BookmarkList {
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
    public String addBookmark(Bookmark bookmark) {
        assert bookmark != null : "Added bookmark should not be null!";
        bookmarks.add(bookmark);
        return "Added bookmark: " + "[" + bookmark.getDescription() + "] "
                +  bookmark.getUrl() + System.lineSeparator();
    }

    private void loadBookmark(String line) {
        try {
            bookmarks.add(Bookmark.initBookmark(line));
        } catch (IndexOutOfBoundsException | ZoomasterException e) {
            // Invalid task data, skips to the next entry
            // logger.log(Level.WARNING, "invalid bookmark data found in file: " + line, e);
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
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public Bookmark getBookmark(int index) throws IndexOutOfBoundsException, ZoomasterException {
        if (bookmarks.isEmpty()) {
            throw new  ZoomasterException(ZoomasterExceptionType.EMPTY_BOOKMARK_LIST);
        }
        return bookmarks.get(index);
    }

    /**
     * Returns the bookmark list.
     *
     * @return The bookmark list.
     */
    public ArrayList<Bookmark> getBookmarks() {
        return bookmarks;
    }

    /**
     * This method deletes the bookmark from the list.
     *
     * @param bookmark The bookmark to be deleted.
     * @throws NullPointerException if the bookmark does not exist in the list.
     */
    public String deleteBookmark(Bookmark bookmark) {
        assert bookmarks.contains(bookmark) : "Bookmark to be"
                + "deleted not in list!";
        bookmarks.remove(bookmark);
        return "[" + bookmark.getDescription() + "] " + bookmark.getUrl() + "\n";
    }

    /**
     * This method shows all the bookmarks from the list.
     *
     * @return The string message containing the matching bookmarks
     */
    public String showBookmarks() {
        String message = "Here are the bookmarks in your list:" + lineSeparator;
        if (bookmarks.isEmpty()) {
            message = message + "Bookmark list is empty" + lineSeparator;
        } else {
            for (int i = 0; i < bookmarks.size(); i++) {
                message = message + "  " + (i + 1) + "." + bookmarks.get(i).getBookmarkAsString();
            }
        }
        return message + lineSeparator;
    }

    /**
     * This method searches the bookmarks from the list with matching module and description.
     *
     * @param description The description to be searched
     * @return The string message containing the matching bookmarks
     */
    public String findBookmarks(String description) {
        assert !description.equals("") : "Description should not be empty!";
        String message = "";
        if (bookmarks.size() == 0) {
            message = "Empty List" + lineSeparator;
        } else {
            message = getMatchingBookmarks(description.toUpperCase());
            if (!message.isEmpty()) {
                message = "Here are your matching bookmarks" + lineSeparator + message;
            } else {
                message = "No bookmarks contain the specified keyword!" + lineSeparator;
            }
        }
        return message;
    }

    private String getMatchingBookmarks(String description) {
        String message = "";
        for (int i = 0; i < bookmarks.size(); i++) {
            if (bookmarks.get(i).getDescription().toUpperCase().contains(description)) {
                message += (i + 1) + "." + bookmarks.get(i).getBookmarkAsString() + lineSeparator;
            }
        }
        return message;
    }

    /**
     * This method searches the bookmarks from the list with matching module
     * and description and launches them accordingly.
     *
     * @param description The description to be searched
     * @return The string message containing the matching bookmarks
     */
    public String launchBookmarks(String description) throws ZoomasterException {
        assert !description.equals("") : "Description should not be empty!";
        String message = "";
        if (bookmarks.isEmpty()) {
            throw new ZoomasterException(ZoomasterExceptionType.EMPTY_BOOKMARK_LIST);
        } else {
            message = launchMatchingBookmarks(description.toUpperCase());
            if (!message.isBlank()) {
                message = "Launched these bookmarks:" + lineSeparator + message;
            } else {
                message = "No bookmarks contain the specified keyword!" + lineSeparator;
            }
        }
        return message;
    }

    private String launchMatchingBookmarks(String description) {
        String message = "";
        String errorMessage = "";
        for (int i = 0; i < bookmarks.size(); i++) {
            if (bookmarks.get(i).getDescription().toUpperCase().contains(description)) {
                try {
                    bookmarks.get(i).launch();
                    message += (i + 1) + "." + bookmarks.get(i).getBookmarkAsString();
                } catch (ZoomasterException e) {
                    errorMessage += (i + 1) + "." + bookmarks.get(i).getBookmarkAsString();
                }
            }
        }
        if (!errorMessage.isBlank()) {
            message += "Failed to launch these bookmarks:" + lineSeparator + errorMessage;
        }
        return message + lineSeparator;
    }

    public String launchAllBookmarks() {
        String message = "";
        if (bookmarks.size() == 0) {
            message = "Empty List" + lineSeparator;
        } else {
            message = launchBookmarksFromList();
            if (!message.isEmpty()) {
                message = "Launched these bookmarks:" + lineSeparator + message;
            }
        }
        return message;
    }

    private String launchBookmarksFromList() {
        String message = "";
        String errorMessage = "";
        for (int i = 0; i < bookmarks.size(); i++) {
            try {
                bookmarks.get(i).launch();
                message += (i + 1) + "." + bookmarks.get(i).getBookmarkAsString();
            } catch (ZoomasterException e) {
                errorMessage += (i + 1) + "." + bookmarks.get(i).getBookmarkAsString();
            }
        }
        if (!errorMessage.isBlank()) {
            message += "Failed to launch these bookmarks:" + lineSeparator + errorMessage;
        }
        return message;
    }
}
