package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when a hopper picks up the item
 * @author Matt Lefebvre
 */
public class InventoryPickupItemEventWrapper implements IEventWrapper {
    @Getter
    private final InventoryPickupItemEvent event;
    @Getter
    private final NBTItem nbtItem;

    public InventoryPickupItemEventWrapper(InventoryPickupItemEvent event, NBTItem nbtItem) {
        this.event = event;
        this.nbtItem = nbtItem;
    }

}
