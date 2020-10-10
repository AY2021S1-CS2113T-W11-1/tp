package seedu.duke;

import seedu.duke.exception.DukeException;
import seedu.duke.exception.DukeExceptionType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Deals with loading tasks from the file and saving tasks in the file.
 */
public class Storage {

    private String filePath;

    /**
     * Constructs a new Storage instance by storing the given pathname of the file.
     *
     * @param filePath The pathname of the file.
     */
    public Storage(String filePath) {
        this.filePath = filePath.replace('/', File.separatorChar);
    }

    /**
     * Returns the tasks found within the file.
     *
     * @return Tasks found in the file.
     * @throws DukeException If file is not found.
     */
    public ArrayList<String> load() throws DukeException {
        File f = new File(filePath);
        ArrayList<String> data;
        try {
            data = getData(f);
        } catch (FileNotFoundException e) {
            throw new DukeException(DukeExceptionType.ERROR_LOADING_FILE);
        }
        return data;
    }

    private ArrayList<String> getData(File f) throws FileNotFoundException {
        ArrayList<String> items = new ArrayList<>();
        Scanner s = new Scanner(f);
        while (s.hasNextLine()) {
            items.add(s.nextLine());
        }
        return items;
    }

    /**
     * This method creates the file if it does not exist and saves tasks data in the file.
     *
     * @param data The string of all the data to be saved in the file.
     * @throws DukeException If there is an error writing to the file.
     */
    public void save(String data) throws DukeException {
        try {
            createDirectory();
            writeToFile(data);
        } catch (IOException e) {
            throw new DukeException(DukeExceptionType.WRITE_FILE_ERROR);
        }
    }

    private void createDirectory() throws IOException {
        String dirPath = getDirectory(filePath);
        Path path = Paths.get(dirPath);
        Files.createDirectories(path);
    }

    private String getDirectory(String path) {
        String dirPath;
        if (path.contains(File.separator)) {
            dirPath = path.substring(0, path.lastIndexOf(File.separator));
        } else {
            dirPath = path;
        }
        return dirPath;
    }

    private void writeToFile(String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        fw.write(textToAdd);
        fw.close();
    }

}
