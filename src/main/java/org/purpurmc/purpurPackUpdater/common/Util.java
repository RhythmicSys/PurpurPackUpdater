package org.purpurmc.purpurPackUpdater.common;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.purpurmc.purpurPackUpdater.PurpurPackUpdater;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static final String MODRINTH_PROJECT = "https://modrinth.com/project/";
    public static final String API_URL_TEMPLATE = "https://api.modrinth.com/v2/project/%s/version?l=datapack";

    public static final ArrayList<PurpurPack> packsToUpdate = new ArrayList<>();
    private static final MiniMessage miniMessage = PurpurPackUpdater.getMiniMessage();

    public static Component listOfPacks(List<PurpurPack> purpurPackList) {
        int size = purpurPackList.size();
        Component message = Component.empty();
        Component packsToUpdate = miniMessage.deserialize(LocaleHandler.getInstance().getPacksToUpdate(),
                Placeholder.unparsed("value", String.valueOf(size)));
        message = message.append(packsToUpdate);
        for (PurpurPack purpurPack : purpurPackList) {
            message = message.appendNewline();
            message = message.append(listItem(purpurPack));
        }
        return message;
    }

    public static Component listItem(PurpurPack purpurPack) {
        if (purpurPack == null) return null;
        String name = purpurPack.name();
        String ID = purpurPack.projectID();
        String link = MODRINTH_PROJECT + ID;
        return miniMessage.deserialize(LocaleHandler.getInstance().getListItem(),
                Placeholder.unparsed("pack", name),
                Placeholder.parsed("link", link));
    }


}
