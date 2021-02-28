package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when a picks the item up
 * @author Matt Lefebvre
 */
public class PlayerPickupItemEventWrapper implements IEventWrapper {
    @Getter
    private final PlayerPickupItemEvent event;
    @Getter
    private final NBTItem nbtItem;

    public PlayerPickupItemEventWrapper(PlayerPickupItemEvent event, NBTItem nbtItem) {
        this.event = event;
        this.nbtItem = nbtItem;
    }

}
