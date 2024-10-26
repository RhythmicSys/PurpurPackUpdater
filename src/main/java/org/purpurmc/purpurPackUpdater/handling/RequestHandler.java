package org.purpurmc.purpurPackUpdater.handling;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.purpurmc.purpurPackUpdater.PurpurPack;
import org.purpurmc.purpurPackUpdater.PurpurPackUpdater;
import org.purpurmc.purpurPackUpdater.Util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Handles HTTP requests to the Modrinth API to check for updates to Purpur Packs.
 * <p>
 * This class is responsible for determining which packs require updates by comparing the
 * current version of a pack with the latest version available on Modrinth.
 */
public class RequestHandler {

    private static final Logger logger = PurpurPackUpdater.getUpdaterLogger();

    /**
     * Retrieves a list of Purpur Packs that have newer versions available on Modrinth.
     *
     * @param purpurPacks The list of Purpur Packs to check for updates.
     * @return A list of Purpur Packs that need updates.
     */
    public static List<PurpurPack> getPacksThatNeedUpdates(List<PurpurPack> purpurPacks) {
        List<PurpurPack> packsToUpdate = new ArrayList<>();
        for (PurpurPack pack : purpurPacks) {
            if (pack.projectID().isEmpty() || pack.currentVersion().isEmpty()) continue;

            JSONArray versions = fetchModrinthInfo(pack.projectID());
            if (versions == null) continue;

            String latestVersion = getLatestVersion(versions);
            if (latestVersion != null && !latestVersion.equals(pack.currentVersion())) {
                packsToUpdate.add(pack);
                logger.info("Added " + pack.name() + " to update list.");
            }
        }
        return packsToUpdate;
    }

    /**
     * Fetches information about available versions of a project from Modrinth.
     * <p>
     * This method sends an HTTP GET request to the Modrinth API and parses the response
     * into a JSON array containing version data.
     *
     * @param projectID The Modrinth project ID of the Purpur Pack.
     * @return A JSON array of version data, or null if an error occurred.
     */
    private static JSONArray fetchModrinthInfo(String projectID) {
        try {
            String urlString = String.format(Util.API_URL_TEMPLATE, projectID);
            URL url = new URI(urlString).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() != 200) {
                logger.warning("Failed to fetch info for project ID: " + projectID + " (HTTP " + connection.getResponseCode() + ")");
                return null;
            }

            try (Scanner scanner = new Scanner(url.openStream())) {
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
                return (JSONArray) new JSONParser().parse(response.toString());
            }

        } catch (URISyntaxException | IOException | ParseException e) {
            logger.warning("Error fetching Modrinth info: " + e.getMessage());
            return null;
        }
    }

    /**
     * Extracts the latest version number from a JSON array of version data.
     * <p>
     * This method iterates through the JSON array to find the most recent version
     * that supports the "datapack" loader.
     *
     * @param versions A JSON array of version data from Modrinth.
     * @return The latest version number that supports the "datapack" loader, or null if none are found.
     */
    private static String getLatestVersion(JSONArray versions) {
        for (Object obj : versions) {
            JSONObject versionData = (JSONObject) obj;
            JSONArray loaders = (JSONArray) versionData.get("loaders");
            if (loaders.contains("datapack")) {
                return (String) versionData.get("version_number");
            }
        }
        return null;
    }
}

