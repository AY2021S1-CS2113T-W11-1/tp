package seedu.zoomaster;

import org.fusesource.jansi.AnsiConsole;
import seedu.zoomaster.command.ChangeModeCommand;
import seedu.zoomaster.command.ClearCommand;
import seedu.zoomaster.command.ExitCommand;
import seedu.zoomaster.command.LaunchNowCommand;
import seedu.zoomaster.command.bookmark.AddBookmarkCommand;
import seedu.zoomaster.command.bookmark.DeleteBookmarkCommand;
import seedu.zoomaster.command.bookmark.FindBookmarkCommand;
import seedu.zoomaster.command.bookmark.LaunchBookmarkCommand;
import seedu.zoomaster.command.bookmark.ShowBookmarkCommand;
import seedu.zoomaster.command.planner.AddMeetingCommand;
import seedu.zoomaster.command.planner.LoadPlannerCommand;
import seedu.zoomaster.command.planner.SavePlannerCommand;
import seedu.zoomaster.command.timetable.AddSlotCommand;
import seedu.zoomaster.command.timetable.DeleteSlotCommand;
import seedu.zoomaster.command.timetable.ShowTimetableCommand;
import seedu.zoomaster.command.timetable.EditSlotCommand;
import seedu.zoomaster.command.timetable.LaunchModuleAndSlotBookmark;
import seedu.zoomaster.exception.ZoomasterException;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.Color.BLACK;
import static org.fusesource.jansi.Ansi.Color.BLUE;
import static org.fusesource.jansi.Ansi.Color.CYAN;
import static org.fusesource.jansi.Ansi.Color.MAGENTA;
import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.Color.WHITE;
import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Represents the user interface on the command line and deals with interactions with the user.
 */
public class Ui {
    private static final String NEW_LINE = System.lineSeparator();
    private static final String LINE = "____________________________________________________________" + NEW_LINE;
    private Scanner scanner;

    private String logo2 =
            "                                                                                                 \n"
            + "                                            ███████                                              \n"
            + "                                      ██████████████████                                         \n"
            + "                                    ███████████████████████                                      \n"
            + "                                  ███████████████████████████                                    \n"
            + "                                 ████             █████  █████                                   \n"
            + "                                █████              █     ██████                                  \n"
            + "                                █████              █     ██████                                  \n"
            + "                                █████              █     ██████                                  \n"
            + "                                 █████             ████  █████                                   \n"
            + "                                  ███████████████████████████                                    \n"
            + "                                   █████████████████████████                                     \n"
            + "                                      ███████████████████                                        \n"
            + "                                          ███████████                                            \n"
            + "                                                                         ██                      \n"
            + "█████████   ███████     ████████   ███████ ███████     █████     █████  █████   █████     █ ████ \n"
            + "     ███  ██      ███  ██      ███ ██     ██     ██         ██  ██       ██   ██      ██  ██   ██\n"
            + "   ███   ███       ██ ███       ██ ██     ██     ██  █████████  ███████  ██  ████████████ ██     \n"
            + "███       ███     ███  ██      ███ ██     ██     ██  ██     ██        ██ ██   ██          ██     \n"
            + "██████████   █████       ██████    ██     ██     ██   ████████  ███████   ███  ████████   ██     \n";



    /**
     * Constructs a new Ui instance.
     */
    public Ui() {
        scanner = new Scanner(System.in);
        AnsiConsole.systemInstall();
    }

    /**
     * Returns a string of the user input in the command line.
     *
     * @return the string of user input.
     */
    public String readCommand() {
        if (Parser.getProgramMode() == 0) {
            System.out.print(ansi().fg(GREEN).a("[Main Menu] Input: ").reset());
        } else if (Parser.getProgramMode() == 1) {
            System.out.print(ansi().fg(CYAN).a("[Bookmark mode] Input: ").reset());
        } else if (Parser.getProgramMode() == 2) {
            System.out.print(ansi().fg(YELLOW).a("[Timetable mode] Input: ").reset());
        } else if (Parser.getProgramMode() == 3) {
            System.out.print(ansi().fg(MAGENTA).a("[Planner mode] Input: ").reset());
        } else {
            System.out.print("[An error has occurred] ");
        }

        return scanner.nextLine().trim();
    }

    public void print(String message) {
        System.out.println(LINE + message + LINE);
    }

    public void printRedWithBorder(String message) {
        System.out.println(LINE);
        System.out.print(ansi().fg(RED).a(message).reset());
        System.out.println(LINE);
    }

    public void printGreenWithBorder(String message) {
        System.out.println(LINE);
        System.out.print(ansi().fg(GREEN).a(message).reset());
        System.out.println(LINE);
    }

    public void printYellowWithBorder(String message) {
        System.out.println(LINE);
        System.out.print(ansi().fg(YELLOW).a(message).reset());
        System.out.println(LINE);
    }

    public void printRed(String message) {
        System.out.print(ansi().fg(RED).a(message).reset());
    }

    public void printGreen(String message) {
        System.out.print(ansi().fg(GREEN).a(message).reset());
    }

    public void printYellow(String message) {
        System.out.print(ansi().fg(YELLOW).a(message).reset());
    }

    public void printCyan(String message) {
        System.out.print(ansi().fg(CYAN).a(message).reset());
    }

    public void clearScreen() {
        System.out.print("\033[2J");
    }

    /**
     * Prints a message after starting the program.
     */
    public void showWelcomeScreen() {
        System.out.println(LINE);

        System.out.println("\t\t\t\t  ++++{  WELCOME TO  }++++");
        System.out.println(ansi().bg(WHITE));
        System.out.println(ansi().fg(CYAN).a(logo2).reset());

        System.out.println(ansi().bg(BLACK));
        System.out.println(LINE);
    }

    /**
     * Prints a message before exiting the program.
     */
    public void showExitScreen() {
        String message = "Bye. Hope to see you again soon!" + NEW_LINE;
        print(message);
        AnsiConsole.systemUninstall();
    }

    /**
     * Prints the error message if data file is not found.
     */
    public void showLoadingError() {
        String message = "Data file not found" + NEW_LINE;
        printRedWithBorder(message);
    }

    /**
     * This method detects the type of ZoomasterException error and prints the corresponding error message.
     *
     * @param e The ZoomasterException error.
     */
    public void showErrorMessage(ZoomasterException e) {
        switch (e.getError()) {
        case UNKNOWN_INPUT:
            printRedWithBorder("Unknown input" + NEW_LINE);
            printHelpMessage();
            break;
        case UNKNOWN_HELP_COMMAND:
            printRedWithBorder("Unknown command " + e.getInfo() + NEW_LINE);
            printHelpMessage();
            break;
        case INVALID_MODE:
            printUnknownModeMessage();
            break;
        case WRITE_FILE_ERROR:
            printErrorWritingToFile();
            break;
        case ERROR_LOADING_FILE:
            printErrorLoadingFile();
            break;
        case INVALID_ADD_BOOKMARK_INPUT:
            printInvalidAddBookmarkInputMessage();
            break;
        case EMPTY_BOOKMARK_LIST:
            printEmptyBookmarkListMessage();
            break;
        case EMPTY_DESCRIPTION:
            printEmptyBookmarkDescriptionMessage();
            break;
        case EMPTY_COMMAND:
            printEmptyCommandMessage(e.getInfo());
            break;
        case NON_INTEGER_INPUT:
            printUseIntegerAsInput();
            break;
        case BOOKMARK_NUMBER_OUT_OF_BOUNDS:
            printUseValidBookmarkNumberMessage(e.getInfo());
            break;
        case INVALID_URL:
            printInvalidUrl();
            break;
        case ERROR_LAUNCHING_URL:
            printErrorLaunchUrlMessage();
            break;
        case INVALID_COMMAND_FORMAT:
            printRedWithBorder("invalid command format" + NEW_LINE);
            break;
        case INVALID_MODULE:
            printRedWithBorder("module does not exist" + NEW_LINE);
            break;
        case INVALID_SLOT_NUMBER:
            printInvalidSlotNumber(e.getInfo());
            break;
        case INVALID_TIME_FORMAT:
            printInvalidTimeFormat();
            break;
        case INVALID_SLOT_INPUT:
            printInvalidSlotInput();
            break;
        case INVALID_EDIT_INPUT:
            printInvalidEditInput();
            break;
        case INVALID_TIMETABLE_DAY:
            printUnknownDayMessage();
            break;
        case EMPTY_TIMETABLE:
            printEmptyTimetableMessage();
            break;
        case CONNECTION_ERROR:
            printConnectionErrorMessage(e.getInfo());
            break;
        case JSON_PARSE_ERROR:
            printJsonParseErrorMessage(e.getInfo());
            break;
        default:
            // unable to get dukeExceptionType
            break;
        }
    }

    private void printEmptyBookmarkListMessage() {
        printRedWithBorder("Bookmark list is empty!" + NEW_LINE);
    }

    private void printInvalidSlotNumber(String index) {
        printRedWithBorder("Invalid slot number. Please enter a valid index number between 1 and " + index + NEW_LINE
                + "Enter command: \"show <module>\" to view slot index" + NEW_LINE);
    }

    private void printJsonParseErrorMessage(String weblink) {
        printRedWithBorder("Unable to parse modules from " + weblink + NEW_LINE
                + "The app will not check for valid modules" + NEW_LINE);
    }

    private void printConnectionErrorMessage(String weblink) {
        printRedWithBorder("Unable to connect to " + weblink + NEW_LINE
                + "Please check your internet connection" + NEW_LINE
                + "The app will not check for valid modules" + NEW_LINE);
    }

    private void printErrorLoadingFile() {
        printRedWithBorder("Error loading file." + NEW_LINE);
    }

    private void printErrorWritingToFile() {
        printRedWithBorder("Error writing to file." + NEW_LINE);
    }

    private void printUseIntegerAsInput() {
        printRedWithBorder("Command requires an integer input" + NEW_LINE);
    }


    private void printUseValidBookmarkNumberMessage(String info) {
        printRedWithBorder("Please enter a valid index number between 1 and " + info + NEW_LINE);
    }

    private void printErrorLaunchUrlMessage() {
        printRedWithBorder("Error launching url" + NEW_LINE);
    }

    private void printInvalidUrl() {
        printRedWithBorder("Invalid URL" + NEW_LINE + "URL must start with either 'www.', 'http://'"
                + " or 'https://' and have no spaces" + NEW_LINE);
    }

    private void printInvalidAddBookmarkInputMessage() {
        printRedWithBorder("Invalid bookmark input" + NEW_LINE
                + "Format: add {module(optional)} {description} {URL}" + NEW_LINE);
    }

    private void printEmptyBookmarkDescriptionMessage() {
        printRedWithBorder("Bookmark description required!" + NEW_LINE);
    }

    private void printEmptyCommandMessage(String info) {
        printRedWithBorder("Please enter " + info + " with input!" + NEW_LINE);
    }

    public void printHelpMessage() {
        assert (Parser.programMode >= 0) && (Parser.programMode <= 3) : "only modes of Zoomaster are 0, 1, 2, 3";
        if (Parser.programMode == 0) {
            System.out.println(LINE);
            printYellowWithBorder("Available inputs in Main menu are" + NEW_LINE
                    + "1) " + ChangeModeCommand.MODE_KW + " bookmark/timetable/planner" + NEW_LINE
                    + "2) " + ClearCommand.CLEAR_KW + NEW_LINE
                    + "3) " + LaunchNowCommand.LAUNCH_NOW_KW + NEW_LINE
                    + "4) " + ExitCommand.EXIT_KW + NEW_LINE);
        } else if (Parser.programMode == 1) {
            printYellowWithBorder("Available inputs in Bookmark mode are" + NEW_LINE
                    + "1) " + AddBookmarkCommand.ADD_KW + NEW_LINE
                    + "2) " + DeleteBookmarkCommand.DEL_KW + NEW_LINE
                    + "3) " + ShowBookmarkCommand.SHOW_KW + NEW_LINE
                    + "4) " + FindBookmarkCommand.FIND_KW + NEW_LINE
                    + "5) " + LaunchBookmarkCommand.LAUNCH_KW + NEW_LINE
                    + "6) " + ClearCommand.CLEAR_KW + NEW_LINE
                    + "7) " + ChangeModeCommand.MODE_KW + " timetable/planner" + NEW_LINE
                    + "8) " + ExitCommand.EXIT_KW + NEW_LINE);
        } else if (Parser.programMode == 2) {
            printYellowWithBorder("Available inputs in Timetable mode are" + NEW_LINE
                    + "1) " + AddSlotCommand.ADD_KW + NEW_LINE
                    + "2) " + DeleteSlotCommand.DEL_KW + NEW_LINE
                    + "3) " + ShowTimetableCommand.SHOW_KW + NEW_LINE
                    + "4) " + EditSlotCommand.EDIT_KW + NEW_LINE
                    + "5) " + LaunchModuleAndSlotBookmark.LAUNCH_KW + NEW_LINE
                    + "6) " + ChangeModeCommand.MODE_KW + " bookmark/planner" + NEW_LINE
                    + "7) " + ClearCommand.CLEAR_KW + NEW_LINE
                    + "8) " + ExitCommand.EXIT_KW + NEW_LINE);
        } else {
            printYellowWithBorder("Available inputs in Planner mode are" + NEW_LINE
                    + "1) " + LoadPlannerCommand.LOAD_KW + NEW_LINE
                    + "2) " + AddMeetingCommand.ADD_KW + NEW_LINE
                    + "3) " + ShowTimetableCommand.SHOW_KW + NEW_LINE
                    + "4) " + SavePlannerCommand.SAVE_KW + NEW_LINE
                    + "6) " + ChangeModeCommand.MODE_KW + " bookmark/timetable" + NEW_LINE
                    + "7) " + ClearCommand.CLEAR_KW + NEW_LINE
                    + "8) " + ExitCommand.EXIT_KW + NEW_LINE);
        }
        printYellow("You can also check what each command does using: ");
        printCyan("help {command}" + NEW_LINE);
    }

    public void printHelpMessage(String input) {
        assert (Parser.programMode >= 0) && (Parser.programMode <= 3) : "only modes of Zoomaster are 0, 1, 2, 3";
        if (input.equals(ClearCommand.CLEAR_KW)) {
            printYellowWithBorder("Clears the visible command line screen" + NEW_LINE);
        } else if (input.equals(ExitCommand.EXIT_KW)) {
            printYellowWithBorder("Exits the application. What else did you expect ^_^" + NEW_LINE);
        } else if (input.equals(ChangeModeCommand.MODE_KW)) {
            System.out.println(LINE);
            printYellow("Changes the current mode. You can change to Bookmark, "
                    + "Timetable or Planner mode" + NEW_LINE);
            printCyan("Format: mode {bookmark/timetable/planner}" + NEW_LINE);
            System.out.println(LINE);
        } else if (input.equals(LaunchNowCommand.LAUNCH_NOW_KW)) {
            printYellow("Launches bookmarks for lessons happening at the current time" + NEW_LINE);
            printCyan("Format: launch now" + NEW_LINE);
            System.out.println(LINE);
        } else if (Parser.programMode == 1) {
            switch (input) {
            case AddBookmarkCommand.ADD_KW:
                System.out.println(LINE);
                printYellow("Adds a bookmark to the bookmark list" + NEW_LINE
                        + "URL must start with www or https:// or http://" + NEW_LINE);
                printCyan("Format: add {description} {URL}" + NEW_LINE);
                System.out.println(LINE);
                break;
            case DeleteBookmarkCommand.DEL_KW:
                System.out.println(LINE);
                printYellow("Deletes bookmark from the bookmark list with their indexes" + NEW_LINE);
                printCyan("Format: delete {index} " + NEW_LINE);
                System.out.println(LINE);
                break;
            case ShowBookmarkCommand.SHOW_KW:
                printYellowWithBorder("Shows the whole list of bookmarks." + NEW_LINE);
                break;
            case FindBookmarkCommand.FIND_KW:
                System.out.println(LINE);
                printYellow("Finds and shows bookmarks with description matching the keyword" + NEW_LINE);
                printCyan("Format: find {keyword} " + NEW_LINE);
                System.out.println(LINE);
                break;
            case LaunchBookmarkCommand.LAUNCH_KW:
                System.out.println(LINE);
                printYellow("Finds and launches bookmarks with description matching the keyword"
                        + " or index " + NEW_LINE);
                printCyan("Format: launch {keyword} " + NEW_LINE
                        + "Format: launch {index} " + NEW_LINE);
                System.out.println(LINE);
                break;
            default:
                printYellowWithBorder("something went wrong...");
                break;
            }

        } else if (Parser.programMode == 2) {
            switch (input) {
            case AddSlotCommand.ADD_KW:
                System.out.println(LINE);
                printYellow("Adds modules and their timeslots to the timetable " + NEW_LINE);
                printCyan("Format: add {module} {slot description} {day of the week} "
                        + "{start time} {end time} {URL}" + NEW_LINE);
                printGreen("eg. add CS2113 lecture fri 16:00 18:00 www.google.com" + NEW_LINE + NEW_LINE);
                printYellow("You can also add the module first then add the slot afterwards, "
                        + "then add the bookmark to that slot. " + NEW_LINE);
                printGreen("eg. add CS2113" + NEW_LINE
                        + "    add CS2113 lecture fri 16:00 18:00" + NEW_LINE
                        + "    add CS2113 lecture fri 16:00 18:00 www.google.com" + NEW_LINE + NEW_LINE);

                printYellow("You can also add bookmarks tagged to the entire module" + NEW_LINE);
                printCyan("Format: add {module} {description} {URL}" + NEW_LINE);
                printGreen("eg. add CS2113 homepage https://nus-cs2113-ay2021s1.github.io/website/index.html" + NEW_LINE);
                System.out.println(LINE);
                break;
            case DeleteSlotCommand.DEL_KW:
                System.out.println(LINE);
                printYellow("Deletes slots or bookmarks belonging to that slot " + NEW_LINE);
                printCyan("Format: delete {module} {slot index} 'bookmarks'" + NEW_LINE);
                printGreen("eg. delete CS2113 1 (Deletes the slot at index 1 of CS2113)" + NEW_LINE);
                printGreen("eg. delete CS2113 1 bookmarks"
                        + " (Deletes the bookmarks of slot at index 1 of CS2113)" + NEW_LINE);
                printYellow("You can display the slot indexes using: ");
                printCyan("show {module}" + NEW_LINE + NEW_LINE);
                printYellow("You can also delete the entire module or the module's bookmarks" + NEW_LINE);
                printCyan("Format: delete {module} 'bookmarks'" + NEW_LINE);
                printGreen("eg. delete CS2113 (Deletes the module CS2113)" + NEW_LINE);
                printGreen("eg. delete CS2113 bookmarks"
                        + " (Deletes the bookmarks tagged to CS2113)" + NEW_LINE);
                System.out.println(LINE);
                break;
            case ShowTimetableCommand.SHOW_KW:
                System.out.println(LINE);
                printYellow("Shows the entire timetable, or slots on a particular day" + NEW_LINE);
                printCyan("Format: show '{day}'" + NEW_LINE);
                printGreen("eg. show" + NEW_LINE);
                printGreen("eg. show mon" + NEW_LINE + NEW_LINE);

                printYellow("You can also shows the slots for a module, or bookmarks for a module " + NEW_LINE);
                printCyan("Format: show {module} 'bookmarks'" + NEW_LINE);
                printGreen("eg. show CS2113" + NEW_LINE);
                printGreen("eg. show CS2113 bookmarks" + NEW_LINE);
                System.out.println(LINE);
                break;
            case EditSlotCommand.EDIT_KW:
                System.out.println(LINE);
                printYellow("Edits the details of slots in the timetable based on their "
                        + "index in the timetable" + NEW_LINE);
                printYellow("Details you can edit include the module, title of slot and time of slot"
                        + NEW_LINE);
                printYellow("You can view the indexes using: ");
                printCyan("show" + NEW_LINE + NEW_LINE);

                printCyan("Format: edit module {day} {index} {new module}" + NEW_LINE);
                printGreen("eg. edit module mon 1 CS1010 (Changes the module of slot 1 on Monday "
                        + "to CS1010)" + NEW_LINE + NEW_LINE);
                printCyan("Format: edit title {day} {index} {new description}" + NEW_LINE);
                printGreen("eg. edit title mon 1 lecture (Changes the description of slot 1 on Monday "
                        + "to lecture)" + NEW_LINE + NEW_LINE);
                printCyan("Format: edit time {day} {index} {new time}" + NEW_LINE);
                printGreen("eg. edit time mon 1 fri 10:00 12:00 (Changes the time of slot 1 on Monday "
                        + "to fri 10:00 12:00)" + NEW_LINE + NEW_LINE);
                System.out.println(LINE);
                break;
            case LaunchModuleAndSlotBookmark.LAUNCH_KW:
                System.out.println(LINE);
                printYellow("Launches the bookmarks of slots based on their index in the module " + NEW_LINE);
                printYellow("You can also launch bookmarks tagged to the module itself" + NEW_LINE);
                printCyan("Format: launch {module} {index}" + NEW_LINE);
                printGreen("eg. launch CS2113 2 (Bookmarks for slot 2 of CS2113)" + NEW_LINE);
                printGreen("eg. launch CS2113 (Bookmarks tagged directly to CS2113)" + NEW_LINE + NEW_LINE);
                break;
            default:
                printYellowWithBorder("something went wrong...");
                break;
            }

        } else if (Parser.programMode == 3) {
            switch (input) {
            case AddMeetingCommand.ADD_KW:
                System.out.println(LINE);
                printYellow("Adds modules and their timeslots to the common timetable " + NEW_LINE);
                printCyan("Format: add {module} {slot description} {day of the week} "
                        + "{start time} {end time} {URL}" + NEW_LINE);
                printGreen("eg. add CS2113 lecture fri 16:00 18:00 www.google.com" + NEW_LINE + NEW_LINE);
                printYellow("You can also add the module first then add the slot afterwards, "
                        + "then add the bookmark to that slot. " + NEW_LINE);
                printGreen("eg. add CS2113" + NEW_LINE
                        + "    add CS2113 lecture fri 16:00 18:00" + NEW_LINE
                        + "    add CS2113 lecture fri 16:00 18:00 www.google.com" + NEW_LINE + NEW_LINE);

                printYellow("You can also add bookmarks tagged to the entire module" + NEW_LINE);
                printCyan("Format: add {module} {description} {URL}" + NEW_LINE);
                printGreen("eg. add CS2113 homepage https://nus-cs2113-ay2021s1.github.io/website/index.html" + NEW_LINE);
                System.out.println(LINE);
                break;
            case LoadPlannerCommand.LOAD_KW:
                System.out.println(LINE);
                printYellow("Loads the common empty slots from a few individual timetables " + NEW_LINE);
                printCyan("Format: load" + NEW_LINE);
                System.out.println(LINE);
                break;
            case ShowTimetableCommand.SHOW_KW:
                System.out.println(LINE);
                printYellow("Shows the common empty slots along with the newly added slots (if any)" + NEW_LINE);
                printCyan("Format: show '{day}'" + NEW_LINE);
                printGreen("eg. show" + NEW_LINE);
                printGreen("eg. show mon" + NEW_LINE + NEW_LINE);

                printYellow("You can also shows the slots for a module, or bookmarks for a module " + NEW_LINE);
                printCyan("Format: show {module} bookmarks" + NEW_LINE);
                printGreen("eg. show CS2113" + NEW_LINE);
                printGreen("eg. show CS2113 bookmarks" + NEW_LINE);
                System.out.println(LINE);
                break;
            case SavePlannerCommand.SAVE_KW:
                System.out.println(LINE);
                printYellow("Saves the newly added slot(s) to each individual timetables" + NEW_LINE);
                printCyan("Format: save" + NEW_LINE);
                System.out.println(LINE);
                break;
            default:
                printYellowWithBorder("something went wrong...");
                break;
            }
        }

    }

    private void printUnknownModeMessage() {
        printRedWithBorder("Unknown mode input" + NEW_LINE + "Valid modes: bookmark, timetable, planner" + NEW_LINE);
    }

    private void printUnknownDayMessage() {
        printRedWithBorder("Unknown day input" + NEW_LINE
              + "Valid days: mon, tue, wed, thu, fri, sat, sun" + NEW_LINE);
    }

    private void printEmptyTimetableMessage() {
        printRedWithBorder("Timetable is empty" + NEW_LINE);
    }

    private void printInvalidSlotInput() {
        printRedWithBorder("Invalid slot input" + NEW_LINE);
    }

    private void printInvalidEditInput() {
        printRedWithBorder("Invalid edit format" + NEW_LINE
        + "Enter \"help edit\" for the correct format." + NEW_LINE);
    }

    private void printInvalidTimeFormat() {
        printRedWithBorder("Invalid time format" + NEW_LINE);
    }

}
