package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when a player interacts with the item
 * @author Matt Lefebvre
 */
public class PlayerInteractEventWrapper implements IEventWrapper {
    @Getter
    private final PlayerInteractEvent event;
    @Getter
    private final NBTItem nbtItem;

    public PlayerInteractEventWrapper(PlayerInteractEvent event, NBTItem nbtItem) {
        this.event = event;
        this.nbtItem = nbtItem;
    }

}
