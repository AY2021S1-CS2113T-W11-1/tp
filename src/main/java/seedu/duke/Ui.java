package seedu.duke;

import seedu.duke.exception.DukeException;

import java.util.Scanner;

/**
 * Represents the user interface on the command line and deals with interactions with the user.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________\n";
    private Scanner scanner;

    /**
     * Constructs a new Ui instance.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Returns a string of the user input in the command line.
     *
     * @return the string of user input.
     */
    public String readCommand() {
        System.out.print("Input: ");
        return scanner.nextLine().trim();
    }

    public void print(String message) {
        System.out.println(LINE
                + message
                + LINE);
    }

    /**
     * Prints a message after starting the program.
     */
    public void showWelcomeScreen() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        String message = "Hello from\n" + logo;
        print(message);
    }

    /**
     * Prints a message before exiting the program.
     */
    public void showExitScreen() {
        String message = "Bye. Hope to see you again soon!\n";
        print(message);
    }

    /**
     * Prints the error message if data file is not found.
     */
    public void showLoadingError() {
        String message = "Data file not found\n";
        print(message);
    }

    /**
     * This method detects the type of dukeException error and prints the corresponding error message.
     *
     * @param dukeException The dukeException error.
     */
    public void showErrorMessage(DukeException dukeException) {
        switch (dukeException.getError()) {
        case FULL_TASK_LIST:
            //printFullTaskListMessage();
            break;
        case UNKNOWN_INPUT:
            printUnknownInputMessage();
            break;
        case INVALID_BOOKMARK_NUMBER:
            //printUseValidTaskNumberMessage();
            break;
        case WRITE_FILE_ERROR:
            //printErrorWritingToFile();
            break;
        case EMPTY_DESCRIPTION:
            printEmptyBookmarkDescriptionMessage();
            break;
        case ERROR_LAUNCHING_URL:
            printErrorLaunchUrlMessage();
            break;
        case INVALID_BOOKMARK_INPUT:
            printInvalidBookmarkInputMessage();
            break;
        case INVALID_URL:
            printInvalidUrl();
            break;
        default:
            // unable to get dukeExceptionType
            break;
        }
    }

    private void printErrorLaunchUrlMessage() {
        print("Error launching url\n");
    }

    private void printInvalidUrl() {
        print("Invalid url\n");
    }

    private void printInvalidBookmarkInputMessage() {
        print("Invalid bookmark input\n");
    }

    private void printEmptyBookmarkDescriptionMessage() {
        print("Empty description\n");
    }

    private void printUnknownInputMessage() {
        print("Unknown input\n");
    }

    private void printInvalidSlotInput() {
        print("Invalid slot input\n");
    }

    private void printInvalidTimeFormat() {
        print("Invalid time format\n");
    }
}