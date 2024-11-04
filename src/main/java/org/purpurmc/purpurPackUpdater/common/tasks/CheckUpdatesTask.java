package org.purpurmc.purpurPackUpdater.common.tasks;

import org.purpurmc.purpurPackUpdater.common.DatapackFileHandler;
import org.purpurmc.purpurPackUpdater.common.PurpurPack;
import org.purpurmc.purpurPackUpdater.common.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CheckUpdatesTask implements Runnable {
    private final File worldFolder;
    private final Logger logger;

    public CheckUpdatesTask(File worldFolder, Logger logger) {
        this.worldFolder = worldFolder;
        this.logger = logger;
    }

    @Override
    public void run() {
        // Clear in case they already ran this earlier
        Util.packsToUpdate.clear();
        // Get all datapacks, regardless if they are purpurpacks or not.
        List<File> datapacks = DatapackFileHandler.gatherDatapacks(worldFolder);
        if (datapacks == null || datapacks.isEmpty()) return;
        // Checks the pack for a 'version_info.txt' and then casts these into PurpurPack objects
        List<PurpurPack> purpurPacks = DatapackFileHandler.getPurpurPacks(datapacks);
        List<Thread> threads = new ArrayList<>();
        // For each pack, check if there's a newer version
        for (PurpurPack pack : purpurPacks) {
            Thread thread = new Thread(new CheckPackTask(pack));
            threads.add(thread);
            thread.start();
        }
        // Prevent this from finishing before all the threads do
        for (Thread thread : threads) {
            try {
                thread.join(); // Waits until the thread finishes
            } catch (InterruptedException e) {
                logger.warning(e.getMessage());
                logger.warning("Thread was interrupted while waiting for thread to finish");
                e.printStackTrace();
            }
        }
    }




}
