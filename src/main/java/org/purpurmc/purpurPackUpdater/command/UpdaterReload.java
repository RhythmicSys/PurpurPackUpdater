package org.purpurmc.purpurPackUpdater.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.purpurmc.purpurPackUpdater.handling.LocaleHandler;

public class UpdaterReload implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        LocaleHandler.getInstance().loadLocale();
        sender.sendRichMessage(LocaleHandler.getInstance().getReloaded());
        return true;
    }
}
