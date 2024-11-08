package org.purpurmc.purpurPackUpdater.bukkit;

import org.bukkit.command.CommandSender;
import org.purpurmc.purpurPackUpdater.PurpurPackUpdater;
import org.purpurmc.purpurPackUpdater.common.Util;
import org.purpurmc.purpurPackUpdater.common.tasks.CheckUpdatesTask;

import java.io.File;
import java.util.logging.Logger;

public class MessageUserTask implements Runnable {

    private final File worldFolder;
    private final CommandSender sender;
    private final Logger logger = PurpurPackUpdater.getUpdaterLogger();

    public MessageUserTask(final File worldFolder, final CommandSender sender) {
        this.worldFolder = worldFolder;
        this.sender = sender;
    }

    @Override
    public void run() {
        Thread updatesThread = new Thread(new CheckUpdatesTask(worldFolder, PurpurPackUpdater.getUpdaterLogger()));
        updatesThread.start();
        try {
            updatesThread.join();
        } catch (InterruptedException e) {
            logger.warning(e.getMessage());
            logger.warning("Thread was interrupted while waiting for thread to finish");
            e.printStackTrace();
        }
        sender.sendMessage(Util.listOfPacks(Util.packsToUpdate));
    }
}
