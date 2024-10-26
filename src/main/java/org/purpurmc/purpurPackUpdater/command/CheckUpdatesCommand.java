package org.purpurmc.purpurPackUpdater.command;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.purpurmc.purpurPackUpdater.PurpurPack;
import org.purpurmc.purpurPackUpdater.PurpurPackUpdater;
import org.purpurmc.purpurPackUpdater.Util;
import org.purpurmc.purpurPackUpdater.handling.DatapackFileHandler;
import org.purpurmc.purpurPackUpdater.handling.LocaleHandler;
import org.purpurmc.purpurPackUpdater.handling.RequestHandler;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

public class CheckUpdatesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<File> datapacks = DatapackFileHandler.gatherDatapacks(PurpurPackUpdater.getInstance().getServer().getWorld("world").getWorldFolder());
        if (datapacks == null || datapacks.isEmpty()) {
            return false;
        }
        sender.sendRichMessage(LocaleHandler.getInstance().getProcessing());
        List<PurpurPack> purpurPacks = DatapackFileHandler.getPurpurPacks(datapacks);
        List<PurpurPack> packsToUpdate = RequestHandler.getPacksThatNeedUpdates(purpurPacks);
        if (packsToUpdate.isEmpty()) {
            sender.sendRichMessage(LocaleHandler.getInstance().getUpToDate());
            return true;
        }
        Component messageToSend = Util.listOfPacks(packsToUpdate);
        sender.sendMessage(messageToSend);

        return false;
    }
}
