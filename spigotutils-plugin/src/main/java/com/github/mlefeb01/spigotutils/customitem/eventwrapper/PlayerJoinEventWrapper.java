package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when a player joins holding the item
 * @author Matt Lefebvre
 */
public class PlayerJoinEventWrapper implements IEventWrapper {
    @Getter
    private final PlayerJoinEvent event;
    @Getter
    private final NBTItem nbtItem;

    public PlayerJoinEventWrapper(PlayerJoinEvent event, NBTItem nbtItem) {
        this.event = event;
        this.nbtItem = nbtItem;
    }

}
