package seedu.zoomaster;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import seedu.zoomaster.bookmark.BookmarkList;
import seedu.zoomaster.exception.ZoomasterException;
import seedu.zoomaster.exception.ZoomasterExceptionType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import seedu.zoomaster.slot.Timetable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.BufferedReader;
import java.io.FileReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Deals with loading tasks from the file and saving tasks in the file.
 */
public class Storage<T> {

    private String filePath;
    private Class<T> storageClass;

    /**
     * Constructs a new Storage instance by storing the given pathname of the file.
     *
     * @param path The pathname of the file.
     */
    public Storage(String path, Class<T> storageClass) {
        this.filePath = path.replace('/', File.separatorChar);
        this.filePath = path.replace("./", File.separator);
        this.storageClass = storageClass;
    }

    /**
     * Returns the tasks found within the file.
     *
     * @return Objects T found in the file.
     * @throws ZoomasterException If file is not found.
     */
    public T load() throws ZoomasterException {
        String fileAsString;
        try {
            fileAsString = Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            return createNewInstance();
        }

        if (!fileAsString.equals("null")) {
            Gson gson = new Gson();
            return gson.fromJson(fileAsString, storageClass);
        } else {
            return createNewInstance();
        }
    }

    private T createNewInstance() throws ZoomasterException {
        try {
            return storageClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new ZoomasterException(ZoomasterExceptionType.ERROR_LOADING_FILE);
        }
    }

    //@@author jusufnathanael
    public ArrayList<File> getFiles() {
        File folder = new File(filePath);
        return new ArrayList<>(Arrays.asList(folder.listFiles()));
    }

    //@@author jusufnathanael
    public T loadPlanner() throws ZoomasterException {

        File folder = new File(filePath);
        try {
            createDirectory();
        } catch (IOException e) {
            throw new ZoomasterException(ZoomasterExceptionType.ERROR_LOADING_FILE);
        }
        if (folder.listFiles() == null) {
            return createNewInstance();
        }

        try {
            ArrayList<File> listOfFiles = getFiles();
            StringBuilder fileAsString = new StringBuilder();
            String opening = "{" + System.lineSeparator() + "  \"modules\": [";
            fileAsString.append(opening);
            for (File f : listOfFiles) {
                try {
                    StringBuilder line = new StringBuilder(Files.readString(Paths.get(f.getPath())));
                    Gson gson = new Gson();
                    Timetable temp = (Timetable) gson.fromJson(line.toString(), storageClass);
                    if (!temp.isEmpty()) {
                        fileAsString.append(line.delete(0, 16));
                        fileAsString.delete(fileAsString.length() - 6, fileAsString.length());
                        fileAsString.append(",");
                    }
                } catch (IOException e) {
                    return createNewInstance();
                }
            }
            fileAsString.delete(fileAsString.length() - 1, fileAsString.length());
            String closing = System.lineSeparator() + "  ]" + System.lineSeparator() + "}";
            fileAsString.append(closing).append(System.lineSeparator());

            if (!fileAsString.toString().equals("null")) {
                Gson gson = new Gson();
                return gson.fromJson(fileAsString.toString(), storageClass);
            } else {
                return createNewInstance();
            }

        } catch (Exception e) {
            throw new ZoomasterException(ZoomasterExceptionType.EMPTY_FOLDER);
        }
    }

    //@@author jusufnathanael
    public void writePlanner(Object t, File f) throws ZoomasterException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(t);
        try {
            writeToFile(jsonString);
        } catch (IOException e) {
            throw new ZoomasterException(ZoomasterExceptionType.WRITE_FILE_ERROR);
        }
    }

    //@@author
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
     * @param data An object to be converted into JSON and saved in the file.
     * @throws ZoomasterException If there is an error writing to the file.
     */
    public void save(Object data) throws ZoomasterException {
        try {
            createDirectory();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString = gson.toJson(data);

            writeToFile(jsonString);
        } catch (IOException e) {
            throw new ZoomasterException(ZoomasterExceptionType.WRITE_FILE_ERROR);
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
        FileWriter fw = new FileWriter(filePath, false);
        fw.write(textToAdd);
        fw.close();
    }

    /**
     * Makes a connection to the NUSMODS API URL and retrieves the JSON
     * file moduleList. Then converts JSON to an Array<String></String>
     * of module codes to be returned.
     *
     * @return Array<String></String> of module codes
     * @throws ZoomasterException CONNECTION_ERROR and JSON_PARSE_ERROR
     */
    // Solution below adapted from AY2021S1-CS2113T-T09-2
    // https://github.com/AY2021S1-CS2113T-T09-2/tp/../data/storage/Decoder.java
    private ArrayList<String> nusModuleListFromNusMods() throws ZoomasterException {
        String weblink = ""; // For exception messages
        try {
            URL url = getNusModsUrl();
            weblink = url.toString();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) { //Unable to connect
                return null;
            }
            String jsonAsString = "";
            Scanner sc = new Scanner(url.openStream());
            while (sc.hasNext()) { // if line is empty, means finish reading
                jsonAsString += sc.nextLine();
            }

            return jsonToArrayList(jsonAsString);

        } catch (IOException e) {
            throw new ZoomasterException(ZoomasterExceptionType.CONNECTION_ERROR, weblink);
        } catch (ParseException e) {
            throw new ZoomasterException(ZoomasterExceptionType.JSON_PARSE_ERROR, weblink);
        }
    }

    /**
     * Converts the moduleList JSON to Array<String></String> of module codes.
     *
     * @return Array<String></String> of module codes
     * @throws ParseException When unable to parse JSON files from NUSMODS
     */
    private ArrayList<String> jsonToArrayList(String jsonAsString) throws ParseException {
        JSONParser parse = new JSONParser();
        JSONArray moduleArray = (JSONArray) parse.parse(jsonAsString);
        ArrayList<String> moduleList = new ArrayList<>();

        for (int i = 0; i < moduleArray.size(); i++) {
            JSONObject module = (JSONObject) moduleArray.get(i);
            moduleList.add(module.get("moduleCode").toString());
        }
        return moduleList;
    }

    /**
     * Returns the url of moduleList JSON file from NUSMODS API for the current year.
     *
     * @return URL
     * @throws MalformedURLException If URL is invalid
     */
    private URL getNusModsUrl() throws MalformedURLException {
        int year = LocalDate.now().getYear();

        return new URL("https://api.nusmods.com/v2/" + year + "-" + (year + 1)
                + "/moduleList.json");
    }

    /**
     * Loads modulelist from the txt file if the file exists locally and returns
     * it as an Array<String></String>.
     * Else tries to retrieve online to be returned. If retrieved successfully,
     * save the information locally as txt file.
     *
     * @return Array<String></String> of module codes or null if unable to make
     *     connection and file does not exist locally.
     */
    public ArrayList<String> loadModuleList() throws IOException, ZoomasterException {
        String moduleListPath = filePath;
        assert filePath.contains("data") : "filepath to be loaded is from wrong directory";
        assert filePath.contains("timetable") : "filepath to be loaded has errors";
        moduleListPath = moduleListPath.replace("timetable", "modulelist");
        File f = new File(moduleListPath);
        ArrayList<String> moduleList = new ArrayList<>();
        String s = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(f));

            while ((s = bufferedReader.readLine()) != null) {
                moduleList.add(s);
            }
            if (moduleList.isEmpty()) {
                throw new FileNotFoundException();
            }
            return moduleList;


        } catch (FileNotFoundException e) {

            moduleList = nusModuleListFromNusMods();

            if (moduleList != null) { // If moduleList is successfully filled, store the list locally
                saveModuleList(moduleListPath, moduleList);
            }
            return moduleList;
        }
    }

    /**
     * Saves the moduleList locally.
     * @param  moduleListPath Filepath for local txt file of module codes
     * @param  moduleList The list of module codes
     */
    private void saveModuleList(String moduleListPath, ArrayList<String> moduleList)
            throws IOException {
        String dirPath = getDirectory(moduleListPath);
        Path path = Paths.get(dirPath);
        Files.createDirectories(path);

        FileWriter fw = new FileWriter(moduleListPath, false);
        for (String str: moduleList) {
            fw.write(str + System.lineSeparator());
        }
        fw.close();
    }


}
