package seedu.zoomaster.command.bookmark;

import seedu.zoomaster.Ui;
import seedu.zoomaster.bookmark.BookmarkList;
import seedu.zoomaster.command.Command;
import seedu.zoomaster.slot.Timetable;

public class ShowBookmarkCommand extends Command {
    public static final String LIST_KW = "show";

    /**
     * Constructs a new ShowBookmarkCommand instance.
     */
    public ShowBookmarkCommand() {

    }

    @Override
    public void execute(BookmarkList bookmarks, Timetable timetable, Ui ui) {
        ui.print(bookmarks.showBookmarks());
    }
}
