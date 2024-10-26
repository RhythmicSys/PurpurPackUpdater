package org.purpurmc.purpurPackUpdater.handling;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.purpurmc.purpurPackUpdater.PurpurPackUpdater;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class LocaleHandler {
    private static LocaleHandler instance;
    private final String fileName = "locale.yml";
    private final File localeFile = new File(PurpurPackUpdater.getInstance().getDataFolder(), fileName);
    private final FileConfiguration localeConfig = new YamlConfiguration();
    private final Logger logger = PurpurPackUpdater.getInstance().getLogger();
    private String reloaded, processing, upToDate, packsToUpdate, listItem;


    private LocaleHandler() {
        if (!localeFile.exists()) {
            PurpurPackUpdater.getInstance().saveResource(fileName, false);
        }
    }

    public static LocaleHandler getInstance() {
        if (instance == null) instance = new LocaleHandler();
        return instance;
    }

    public FileConfiguration getLocaleConfig() {
        return localeConfig;
    }

    public void loadLocale() {
        try {
            localeConfig.load(localeFile);
        } catch (IOException | InvalidConfigurationException e) {
            logger.severe("Issue loading locale.yml");
            e.printStackTrace();
        }
        reloaded = localeConfig.getString("feedback.reloaded", "<gold>PurpurPackUpdater has been reloaded!</gold>");
        processing = localeConfig.getString("feedback.processing", "<yellow>Checking for updates! Please wait!</yellow>");
        upToDate = localeConfig.getString("feedback.up-to-date", "<green>All of your Purpur Packs are up-to-date!</green>");
        packsToUpdate = localeConfig.getString("feedback.packs-to-update", "<aqua>You have <value> update(s) available</aqua>");
        listItem = localeConfig.getString("feedback.list-item", "<gray>• <click:open_url:'<link>'><hover:show_text:'Click to go to the <pack> download link'><u><pack></u></hover></click></gray>");
    }

    public String getProcessing() {
        return processing;
    }

    public String getUpToDate() {
        return upToDate;
    }

    public String getPacksToUpdate() {
        return packsToUpdate;
    }

    public String getListItem() {
        return listItem;
    }

    public String getReloaded() {
        return reloaded;
    }
}

