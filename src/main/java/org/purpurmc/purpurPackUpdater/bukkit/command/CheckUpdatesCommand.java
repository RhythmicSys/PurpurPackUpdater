package org.purpurmc.purpurPackUpdater.bukkit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.purpurmc.purpurPackUpdater.PurpurPackUpdater;
import org.purpurmc.purpurPackUpdater.bukkit.MessageUserTask;
import org.purpurmc.purpurPackUpdater.common.LocaleHandler;
import org.purpurmc.purpurPackUpdater.common.tasks.CheckUpdatesTask;

import java.io.File;

public class CheckUpdatesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        sender.sendRichMessage(LocaleHandler.getInstance().getProcessing());
        File worldFolder = PurpurPackUpdater.getInstance().getServer().getWorld("world").getWorldFolder();
        Thread thread = new Thread(new MessageUserTask(worldFolder, sender));
        thread.start();
        return false;
    }
}
