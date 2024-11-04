package org.purpurmc.purpurPackUpdater.common.tasks;

import org.json.simple.JSONArray;
import org.purpurmc.purpurPackUpdater.common.ModrinthAPIHandler;
import org.purpurmc.purpurPackUpdater.common.PurpurPack;
import org.purpurmc.purpurPackUpdater.common.Util;

public class CheckPackTask implements Runnable{
    private final PurpurPack purpurPack;

    public CheckPackTask(PurpurPack purpurPack) {
        this.purpurPack = purpurPack;
    }

    @Override
    public void run() {
        System.out.println(purpurPack.toString());
        if (purpurPack.projectID().isBlank() || purpurPack.projectID().isEmpty()) return;
        JSONArray versions = ModrinthAPIHandler.fetchModrinthInfo(purpurPack.projectID());
        if (versions == null) return;
        String latestVersion = ModrinthAPIHandler.getLatestVersion(versions);
        if (latestVersion == null || latestVersion.isBlank() || latestVersion.isEmpty()) return;
        if (latestVersion.equals(purpurPack.currentVersion())) return;
        Util.packsToUpdate.add(purpurPack);
    }
}
