package org.purpurmc.purpurPackUpdater;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;
import org.purpurmc.purpurPackUpdater.command.CheckUpdatesCommand;
import org.purpurmc.purpurPackUpdater.command.UpdaterReload;
import org.purpurmc.purpurPackUpdater.handling.LocaleHandler;

import java.util.logging.Logger;

public final class PurpurPackUpdater extends JavaPlugin {

    private static PurpurPackUpdater instance;
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();


    @Override
    public void onEnable() {
        instance = this;
        this.getCommand("ppureload").setExecutor(new UpdaterReload());
        this.getCommand("checkup").setExecutor(new CheckUpdatesCommand());
        LocaleHandler.getInstance().loadLocale();
    }

    public static PurpurPackUpdater getInstance() {
        return instance;
    }

    public static Logger getUpdaterLogger() {
        return instance.getLogger();
    }

    public static MiniMessage getMiniMessage() {
        return miniMessage;
    }
}
