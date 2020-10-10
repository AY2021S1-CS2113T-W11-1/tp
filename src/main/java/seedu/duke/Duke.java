package seedu.duke;

import seedu.duke.bookmark.BookmarkList;
import seedu.duke.slot.SlotList;
import seedu.duke.command.Command;
import seedu.duke.exception.DukeException;

public class Duke {

    private Storage bookmarkStorage;
    private Storage slotStorage;
    private BookmarkList bookmarks;
    private SlotList slots;
    private Ui ui;

    /**
     * Constructs a new Duke instance.
     * Pass the filepath of the txt file to set up storage.
     *
     * @param bookmarkFilePath The filepath of the bookmark txt file.
     * @param slotFilePath The filepath of the slot txt file.
     */
    public Duke(String bookmarkFilePath, String slotFilePath) {
        ui = new Ui();
        bookmarkStorage = new Storage(bookmarkFilePath);
        slotStorage = new Storage(slotFilePath);

        try {
            bookmarks = new BookmarkList(bookmarkStorage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            bookmarks = new BookmarkList();
        }

        try {
            slots = new SlotList(slotStorage.load());
        } catch (DukeException e) {
            slots = new SlotList();
        }
    }

    /**
     * This method is used run the Duke program.
     */
    public void run() {
        boolean isExit = false;

        ui.showWelcomeScreen();

        do {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                c.execute(bookmarks, ui, bookmarkStorage);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showErrorMessage(e);
            }
        } while (!isExit);
    }

    /**
     * Main entry-point for the java.duke.Duke application.
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        new Duke("./data/data.txt", "./data/slot.txt").run();
    }
}