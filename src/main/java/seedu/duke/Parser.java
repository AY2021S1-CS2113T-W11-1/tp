package seedu.duke;

import seedu.duke.command.AddBookmarkCommand;
import seedu.duke.command.Command;
import seedu.duke.command.DeleteBookmarkCommand;
import seedu.duke.command.ExitCommand;
import seedu.duke.command.LaunchBookmarkCommand;
import seedu.duke.command.ListCommand;

import seedu.duke.exception.DukeException;
import seedu.duke.exception.DukeExceptionType;

/**
 * This class deals with making sense of the user command.
 */
public class Parser {

    /**
     * Creates and returns the command based on user input.
     *
     * @param input full user input.
     * @return command.
     * @throws DukeException If input command is unknown.
     */
    public static Command parse(String input) throws DukeException {
        Command command = createCommand(input);
        return command;
    }

    private static Command createCommand(String input) throws DukeException {
        Command command;

        if (input.compareToIgnoreCase(ExitCommand.BYE_KW) == 0) {
            command = new ExitCommand();

        } else if (input.compareToIgnoreCase(ListCommand.LIST_KW) == 0) {
            command = new ListCommand();
        } else if (input.startsWith(DeleteBookmarkCommand.DEL_KW)) {
            command = new DeleteBookmarkCommand(input);
        } else if (input.startsWith(AddBookmarkCommand.ADD_KW)) {
            command = new AddBookmarkCommand(input);
        } else if (input.startsWith(LaunchBookmarkCommand.LAUNCH_KW)) {
            command = new LaunchBookmarkCommand(input);

        } else {
            throw new DukeException(DukeExceptionType.UNKNOWN_INPUT);
        }

        return command;
    }
}
