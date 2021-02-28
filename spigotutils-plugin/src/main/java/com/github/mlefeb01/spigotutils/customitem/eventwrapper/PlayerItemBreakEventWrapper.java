package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.player.PlayerItemBreakEvent;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when a player breaks the item (e.g. - a tools durability)
 * @author Matt Lefebvre
 */
public class PlayerItemBreakEventWrapper implements IEventWrapper {
    @Getter
    private final PlayerItemBreakEvent event;
    @Getter
    private final NBTItem nbtItem;

    public PlayerItemBreakEventWrapper(PlayerItemBreakEvent event, NBTItem nbtItem) {
        this.event = event;
        this.nbtItem = nbtItem;
    }

}
