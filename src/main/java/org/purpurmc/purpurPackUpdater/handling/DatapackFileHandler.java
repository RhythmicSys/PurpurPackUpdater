package org.purpurmc.purpurPackUpdater.handling;

import org.json.simple.JSONObject;
import org.purpurmc.purpurPackUpdater.PurpurPack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DatapackFileHandler {

    /**
     * Gathers all datapacks from the specified world folder, including both Purpur
     * datapacks and non-Purpur datapacks.
     *
     * @param worldFolder The world folder from which to gather datapacks.
     * @return A list of files representing the gathered datapacks.
     */

    public static List<File> gatherDatapacks(File worldFolder) {
        List<File> datapacks = new ArrayList<>();
        File datapacksDirectory = new File(worldFolder, "datapacks");
        if (datapacksDirectory.exists() && datapacksDirectory.isDirectory()) {
            File[] files = datapacksDirectory.listFiles();
            if (files == null) return null;
            for (File file : files) {
                if (file.isDirectory() || file.getName().endsWith(".zip")) {
                    datapacks.add(file);
                }
            }
        }
        return datapacks;
    }

    /**
     * Obtains a list of PurpurPacks, which are datapacks that contain a file named 'version_info.txt'.
     *
     * @param datapacks A list of files representing the datapacks to search through.
     * @return A list of PurpurPack objects created from the information provided in the file
     */
    public static List<PurpurPack> getPurpurPacks(List<File> datapacks) {
        List<PurpurPack> purpurPacks = new ArrayList<>();
        for (File file : datapacks) {
            JSONObject versionInfo = MetaReader.readVersionInfo(file);
            if (versionInfo == null) continue;
            PurpurPack purpurPack = new PurpurPack(MetaReader.getProjectID(versionInfo), MetaReader.getCurrentVersion(versionInfo), file.getName());
            purpurPacks.add(purpurPack);
        }
        return purpurPacks;
    }
}
