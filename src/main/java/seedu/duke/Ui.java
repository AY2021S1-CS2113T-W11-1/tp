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
        if (Parser.getProgramMode() == 0) {
            System.out.print("[Main Menu] Input: ");
        } else if (Parser.getProgramMode() == 1) {
            System.out.print("[Bookmark mode] Input: ");
        } else if (Parser.getProgramMode() == 2) {
            System.out.print("[Timetable mode] Input: ");
        } else {
            System.out.print("[An error has occurred] ");
        }

        return scanner.nextLine().trim();
    }

    public void print(String message) {
        System.out.println(LINE + message + LINE);
    }

    /**
     * Prints a message after starting the program.
     */
    public void showWelcomeScreen() {
        String logo = "___  ____ ____ _  _ ____ ____ ___ ____ ____ \n"
                    + "  /  |  | |  | |\\/| |__| [__   |  |___ |__/ \n"
                    + " /__ |__| |__| |  | |  | ___]  |  |___ |  \\\n";
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
        case UNKNOWN_INPUT:
            printUnknownInputMessage();
            break;
        case INVALID_MODE:
            printUnknownModeMessage();
            break;
        case WRITE_FILE_ERROR:
            printErrorWritingToFile();
            break;
        case INVALID_ADD_BOOKMARK_INPUT:
            printInvalidAddBookmarkInputMessage();
            break;
        case EMPTY_DESCRIPTION:
            printEmptyBookmarkDescriptionMessage();
            break;
        case EMPTY_COMMAND:
            printEmptyCommandMessage(dukeException.getInfo());
            break;
        case NON_INTEGER_INPUT:
            printUseIntegerAsInput();
            break;
        case BOOKMARK_NUMBER_OUT_OF_BOUNDS:
            printUseValidBookmarkNumberMessage(dukeException.getInfo());
            break;
        case INVALID_URL:
            printInvalidUrl();
            break;
        case ERROR_LAUNCHING_URL:
            printErrorLaunchUrlMessage();
            break;
        case INVALID_TIME_FORMAT:
            printInvalidTimeFormat();
            break;
        case INVALID_SLOT_INPUT:
            printInvalidSlotInput();
            break;
        case INVALID_TIMETABLE_DAY:
            printUnknownDayMessage();
            break;
        case EMPTY_TIMETABLE:
            printEmptyTimetableMessage();
            break;
        default:
            // unable to get dukeExceptionType
            break;
        }
    }

    private void printErrorWritingToFile() {
        print("Error writing to file.\n");
    }

    private void printUseIntegerAsInput() {
        print("Command requires an integer input\n");
    }


    private void printUseValidBookmarkNumberMessage(String info) {
        print("Please enter a valid index number between 1 and " + info + "\n");
    }

    private void printErrorLaunchUrlMessage() {
        print("Error launching url\n");
    }

    private void printInvalidUrl() {
        print("Invalid URL" + "\n" + "URL must start with either 'www.'"
                + " or 'https://' and have no spaces\n");
    }

    private void printInvalidAddBookmarkInputMessage() {
        print("Invalid bookmark input\n"
                + "Format: add {module(optional)} {description} {URL}\n");
    }

    private void printEmptyBookmarkDescriptionMessage() {
        print("Bookmark description required!\n");
    }

    private void printEmptyCommandMessage(String info) {
        print("Please enter " + info + " with input!\n");
    }

    private void printUnknownInputMessage() {
        print("Unknown input\n");
    }

    private void printUnknownModeMessage() {
        print("Unknown mode input\n" + "Valid modes: bookmark, timetable\n");
    }

    private void printUnknownDayMessage() {
        print("Unknown day input\n" 
              + "Valid days: monday, tuesday, wednesday, thursday, friday, saturday, sunday\n");
    }

    private void printEmptyTimetableMessage() {
        print("Timetable is empty\n");
    }

    private void printInvalidSlotInput() {
        print("Invalid slot input\n");
    }

    private void printInvalidTimeFormat() {
        print("Invalid time format\n");
    }

}
