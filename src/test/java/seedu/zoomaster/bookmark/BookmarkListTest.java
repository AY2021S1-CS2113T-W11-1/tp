package seedu.zoomaster.bookmark;

import org.junit.jupiter.api.Test;
import seedu.zoomaster.exception.ZoomasterException;
import seedu.zoomaster.exception.ZoomasterExceptionType;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class BookmarkListTest {
    private final Bookmark bookmarkTest = new Bookmark("tutorial", "www.google.com");
    private final String lineSeparator = System.lineSeparator();

    @Test
    void addBookmark() throws IndexOutOfBoundsException, ZoomasterException {
        BookmarkList bookmarks = new BookmarkList();
        String description = "something";
        String url = "www.google.com";
        Bookmark bookmark = new Bookmark(description, url);
        bookmarks.addBookmark(bookmark);
        assertEquals(bookmark, bookmarks.getBookmark(0));
    }

    @Test
    void initializeBookmarkListWithArrayList_validBookmark_addsToBookmarkList() throws IndexOutOfBoundsException,
            ZoomasterException {
        ArrayList<String> input = new ArrayList<>();
        input.add("tutorial | www.google.com");
        input.add("lecture | www.yahoo.com");
        BookmarkList bookmarks = new BookmarkList(input);
        assertEquals(bookmarks.getBookmark(0).getExport(), "tutorial | www.google.com");
        assertEquals(bookmarks.getBookmark(1).getExport(), "lecture | www.yahoo.com");
    }


    /**
     * Adds 1 valid and 1 invalid bookmark to the BookmarkList.
     * Asserts IndexOutOfBoundsException for index 1 as only 1 bookmark will be added to index 0.
     */
    @Test
    void initializeBookmarkListWithArrayList_invalidBookmark_throwsIndexOutOfBoundsException() {
        ArrayList<String> input = new ArrayList<>();
        input.add("tutorial | www.google.com");
        input.add("lecture");
        BookmarkList bookmarks = new BookmarkList(input);
        assertThrows(IndexOutOfBoundsException.class, () -> bookmarks.getBookmark(1));
    }

    @Test
    void getData() {
        ArrayList<String> input = new ArrayList<>();
        input.add("tutorial | www.google.com");
        input.add("lecture | www.yahoo.com");
        BookmarkList bookmarks = new BookmarkList(input);
        assertEquals(bookmarks.getData(), "tutorial | www.google.com" + System.lineSeparator()
                + "lecture | www.yahoo.com");
    }

    @Test
    void getSize() {
        ArrayList<String> input = new ArrayList<>();
        input.add("tutorial | www.google.com");
        input.add("lecture | www.yahoo.com");
        input.add("lecture | https://www.youtube.com/");

        BookmarkList bookmarks = new BookmarkList(input);
        assertEquals(bookmarks.getSize(), 3);
    }

    @Test
    void getBookmark() throws IndexOutOfBoundsException, ZoomasterException {
        BookmarkList bookmarks = new BookmarkList();
        bookmarks.addBookmark(bookmarkTest);
        assertEquals(bookmarkTest, bookmarks.getBookmark(0));
    }

    @Test
    void getBookmarkList_returnsListOfBookmarks() {
        BookmarkList bookmarks = new BookmarkList();
        bookmarks.addBookmark(bookmarkTest);
        ArrayList<Bookmark> bookmarksExpected = new ArrayList<>();
        bookmarksExpected.add(bookmarkTest);
        assertEquals(bookmarksExpected, bookmarks.getBookmarks());
    }

    /**
     * Adds 4 valid bookmarks to the BookmarkList.
     * Checks that size decreases to 4 after deletion.
     * Checks that bookmark at index 2 changes after deletion.
     */
    @Test
    void deleteBookmark() throws IndexOutOfBoundsException, ZoomasterException {
        ArrayList<String> input = new ArrayList<>();
        input.add("CS2113T | www.google.com");
        input.add("CS2113T | www.yahoo.com");
        input.add("CS1010 | https://www.youtube.com/");
        input.add("GEH1049 | https://www.stackoverflow.com/");


        BookmarkList bookmarks = new BookmarkList(input);
        assertEquals(bookmarks.getSize(), 4);
        assertEquals(bookmarks.getBookmark(2).getBookmarkAsString(),
                "[CS1010] https://www.youtube.com/" + System.lineSeparator());

        bookmarks.deleteBookmark(bookmarks.getBookmark(2));
        assertEquals(bookmarks.getSize(), 3);
        assertEquals(bookmarks.getBookmark(2).getBookmarkAsString(),
                "[GEH1049] https://www.stackoverflow.com/" + System.lineSeparator());

    }

    @Test
    void showBookmark() {
        ArrayList<String> input = new ArrayList<>();
        input.add("CS2113T | www.google.com");
        input.add("CS2113T | www.yahoo.com");

        BookmarkList bookmarks = new BookmarkList(input);
        assertEquals("Here are the bookmarks in your list:" + lineSeparator
                + "  " + (1) + "." + "[CS2113T] www.google.com" + lineSeparator
                + "  " + (2) + "." + "[CS2113T] www.yahoo.com" + lineSeparator + lineSeparator,
                bookmarks.showBookmarks());
    }

    @Test
    void findBookmark_matchingBookmark() throws IndexOutOfBoundsException, ZoomasterException {
        ArrayList<String> input = new ArrayList<>();
        input.add("CS2112T | www.google.com");
        input.add("CS2113T | www.yahoo.com");

        BookmarkList bookmarks = new BookmarkList(input);
        assertEquals("Here are your matching bookmarks" + lineSeparator + (2) + "."
                        + bookmarks.getBookmark(1).getBookmarkAsString() + lineSeparator,
                bookmarks.findBookmarks("CS2113T"));
    }

    @Test
    void findBookmark_emptyList_returnsEmptyListMessage() throws IndexOutOfBoundsException {
        BookmarkList bookmarks = new BookmarkList();
        assertEquals("Empty List" + lineSeparator,
                bookmarks.findBookmarks("CS2113T"));
    }

    @Test
    void findBookmark_noMatchingKeyword_returnsNoMatchingBookmarkMessage() throws IndexOutOfBoundsException {
        ArrayList<String> input = new ArrayList<>();
        input.add("CS2112T | www.google.com");
        input.add("CS2113T | www.yahoo.com");

        BookmarkList bookmarks = new BookmarkList(input);
        assertEquals("No bookmarks contain the specified keyword!" + lineSeparator,
                bookmarks.findBookmarks("CS3113T"));
    }

    @Test
    void launchBookmark_matchingBookmark() throws IndexOutOfBoundsException, ZoomasterException {
        ArrayList<String> input = new ArrayList<>();
        input.add("tutorial | www.google.com");
        input.add("lecture | www.yahoo.com");
        BookmarkList bookmarks = new BookmarkList(input);
        assertEquals("Launched these bookmarks:" + lineSeparator
                + (2) + "." + bookmarks.getBookmark(1).getBookmarkAsString() + lineSeparator,
                bookmarks.launchBookmarks("lecture"));
    }

    @Test
    void launchBookmark_emptyBookmarkList_returnEmptyListMessage() throws IndexOutOfBoundsException,
            ZoomasterException {
        BookmarkList bookmarks = new BookmarkList();
        ZoomasterException e = assertThrows(ZoomasterException.class, () -> bookmarks.launchBookmarks("lecture"));
        assertEquals(ZoomasterExceptionType.EMPTY_BOOKMARK_LIST, e.getError());

    }


    @Test
    void launchBookmark_noMatchingBookmarks() throws ZoomasterException {
        ArrayList<String> input = new ArrayList<>();
        input.add("tutorial | www.google.com");
        input.add("lecture | www.yahoo.com");

        String description = "NONMATCHINGTERM";
        BookmarkList bookmarks = new BookmarkList(input);
        assertEquals("No bookmarks contain the specified keyword!" + lineSeparator,
                bookmarks.launchBookmarks(description));
    }

    @Test
    void launchAllBookmark_returnLaunchBookmarksMessage() {
        ArrayList<String> input = new ArrayList<>();
        input.add("tutorial | www.google.com");
        input.add("lecture | www.yahoo.com");
        BookmarkList bookmarks = new BookmarkList(input);
        assertEquals("Launched these bookmarks:" + lineSeparator
                        + "1.[tutorial] www.google.com" + lineSeparator
                        + "2.[lecture] www.yahoo.com" + lineSeparator,
                bookmarks.launchAllBookmarks());
    }

    @Test
    void launchAllBookmark_emptyBookmarkList_returnEmptyListMessage() {
        BookmarkList bookmarks = new BookmarkList();
        assertEquals("Empty List" + lineSeparator,
                bookmarks.launchAllBookmarks());
    }
}