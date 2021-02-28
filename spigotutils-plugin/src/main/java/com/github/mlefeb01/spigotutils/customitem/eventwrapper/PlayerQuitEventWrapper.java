package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when a player quits holding the item
 * @author Matt Lefebvre
 */
public class PlayerQuitEventWrapper implements IEventWrapper {
    @Getter
    private final PlayerQuitEvent event;
    @Getter
    private final NBTItem nbtItem;

    public PlayerQuitEventWrapper(PlayerQuitEvent event, NBTItem nbtItem) {
        this.event = event;
        this.nbtItem = nbtItem;
    }

}
