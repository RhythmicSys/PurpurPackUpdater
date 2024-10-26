package org.purpurmc.purpurPackUpdater.handling;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.purpurmc.purpurPackUpdater.PurpurPackUpdater;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Utility class for reading and processing version information from Purpur datapacks.
 */
public class MetaReader {
    private static final Logger logger = PurpurPackUpdater.getUpdaterLogger();

    /**
     * Reads the version information from a given datapack. The method determines whether the datapack
     * is a directory or a zip file, and reads the 'version_info.txt' file accordingly.
     *
     * @param datapack The datapack file (either a directory or a zip file) to read the version information from.
     * @return A JSONObject representing the version information, or {@code null} if the file could not be read.
     */
    public static JSONObject readVersionInfo(File datapack) {
        if (datapack.isDirectory()) {
            return readFromFolder(datapack);
        } else if (datapack.getName().endsWith(".zip")) {
            return readFromZip(datapack);
        }
        return null;
    }

    /**
     * Reads the version information from a folder containing a datapack. This method assumes
     * the version information is stored in a file named 'version_info.txt'.
     *
     * @param folder The folder representing the datapack.
     * @return A JSONObject representing the version information, or {@code null} if the file could not be read.
     */

    private static JSONObject readFromFolder(File folder) {
        File versionFile = new File(folder, "version_info.txt");
        return readJsonFile(versionFile);
    }

    /**
     * Reads the version information from a zip file containing a datapack. This method assumes
     * the version information is stored in a file named 'version_info.txt' inside the zip.
     *
     * @param zipFile The zip file representing the datapack.
     * @return A JSONObject representing the version information, or {@code null} if the file could not be read.
     */
    private static JSONObject readFromZip(File zipFile) {
        try (ZipFile zip = new ZipFile(zipFile)) {
            ZipEntry entry = zip.getEntry("version_info.txt");
            if (entry == null) return null;
            try (InputStream stream = zip.getInputStream(entry); InputStreamReader reader = new InputStreamReader(stream)) {
                JSONParser jsonParser = new JSONParser();
                return (JSONObject) jsonParser.parse(reader);
            }
        } catch (IOException | ParseException e) {
            logger.warning("Issue parsing version file\n" + Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    /**
     * Reads and parses a JSON file from the filesystem.
     *
     * @param file The file to read and parse.
     * @return A JSONObject representing the contents of the file, or {@code null} if the file could not be read.
     */
    private static JSONObject readJsonFile(File file) {
        if (!file.exists()) return null;
        try (FileReader reader = new FileReader(file)) {
            JSONParser jsonParser = new JSONParser();
            return (JSONObject) jsonParser.parse(reader);
        } catch (IOException | ParseException e) {
            logger.warning("Issue parsing version file\n" + Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    /**
     * Retrieves the project ID from the provided version information.
     *
     * @param versionInfo The JSONObject containing the version information.
     * @return The project ID as a string, or {@code null} if the version information is {@code null}.
     */
    public static String getProjectID(JSONObject versionInfo) {
        if (versionInfo == null) return null;
        return versionInfo.get("project_id").toString();
    }

    /**
     * Retrieves the current version number from the provided version information.
     *
     * @param versionInfo The JSONObject containing the version information.
     * @return The current version number as a string, or {@code null} if the version information is {@code null}.
     */
    public static String getCurrentVersion(JSONObject versionInfo) {
        if (versionInfo == null) return null;
        return versionInfo.get("version_number").toString();
    }
}
